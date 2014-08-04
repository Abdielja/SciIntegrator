/**
 * 
 */
package com.sci.integrator.controlers.rest;


import java.io.StringWriter;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathConstants;

import org.springframework.http.HttpStatus;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sci.integrator.domain.core.SciiException;
import com.sci.integrator.domain.core.SciiResult;
import com.sci.integrator.domain.core.Transaction;
import com.sci.integrator.domain.core.TransactionError;
import com.sci.integrator.domain.core.TransactionOpen;
import com.sci.integrator.domain.core.Transactions;
import com.sci.integrator.domain.core.User;
import com.sci.integrator.domain.core.UserData;
import com.sci.integrator.handlers.TransactionHandler;
import com.sci.integrator.helpers.XmlHelper;
import com.sci.integrator.services.ILoggerService;
import com.sci.integrator.services.ITransactionService;
import com.sci.integrator.services.relational.SciiLoggerService;

/**
 * @author Abdiel Jaramillo Ojedis
 *
 */
@Controller
@RequestMapping("/rest/transactions")
public class TransactionRestController
{

  @Inject 
  private Jaxb2Marshaller marshaller;
  
  @Inject
	private ITransactionService transactionService;

  @Inject
  private TransactionHandler transactionHandler;
  
  @Inject
  private ILoggerService loggerService;
  
  // *** Create Transaction *** 
  @RequestMapping(method=RequestMethod.POST, headers ="Content-Type=text/xml")
  @ResponseStatus(HttpStatus.CREATED)  
  public @ResponseBody SciiResult processTransaction(@RequestBody DOMSource xmlTrx, HttpServletResponse response) throws Exception 
  {

    SciiResult result = new SciiResult();

    System.out.println("\n RestController...");
    
    System.out.println("\nTransaction recevied...\n");

    // ** Authenticate Transaction **
    
    System.out.println("  Authenticating...");

    Node xmlDoc = xmlTrx.getNode();
    
    Node xmlUser = (Node)XmlHelper.readFromXml(xmlDoc, "//createdBy", XPathConstants.NODE);

    String userName = (String)XmlHelper.readFromXml(xmlUser, "userName", XPathConstants.STRING);
    String password = (String)XmlHelper.readFromXml(xmlUser, "password", XPathConstants.STRING);
    Node xmlUserOid = (Node)XmlHelper.readFromXml(xmlUser, "oid", XPathConstants.NODE);
   
    User user;
    
    try
    {
      user = transactionService.validateUser(userName, password);
      xmlUserOid.setNodeValue(String.valueOf(user.getoid()));
    }
    catch(SciiException e)
    {      
      result.setreturnCode((SciiResult.RETURN_CODE_INVALID_CREDENTIALS));
      result.setreturnMessage("Error - Credenciales Invalidas. Por favor vuelva a intentar.");

      System.out.println("Error - Credenciales Invalidas.");

      return result;
    }

    // ** Unmarshall Transaction **
    System.out.println("  Unmarshalling...");
    Transaction trx = (Transaction)marshaller.unmarshal(xmlTrx);
    trx.setcreatedBy(user);
     
    // ** Validate Transaction Before Saving**
    System.out.println("  Validating...");
    try
    {
     trx.validate();
     transactionService.validateTransaction(trx);
    }
    catch(SciiException e)
    {      

      System.out.println(e.getMessage());
      result.setreturnCode((e.getSciiReturnCode()));
      result.setreturnMessage("Error - " +  e.getMessage());
   
      loggerService.addEntry(SciiLoggerService.ERROR, trx.getcreatedBy(), result.getreturnMessage());

      return result;
    }

    if (trx.getasynchronous().equals("true"))
    {
      result = processAsynchronous(trx);
    }
    else
    {
      trx = processSynchronous(trx);

      // *** TransactionOpen. Return UserData ***      
      if (trx.getClass() == TransactionOpen.class)
      {
 
        UserData ud = ((TransactionOpen)trx).getuserData();

        StringWriter sw = new StringWriter();
        StreamResult xmlResult = new StreamResult(sw);
        
        marshaller.marshal(ud, xmlResult);
        
        String strXmlTrxObject = sw.toString();
        result.setTransactionObject(strXmlTrxObject);

      }
      
      result.setreturnCode((SciiResult.RETURN_CODE_OK));
      result.setreturnMessage("OK");

    }
    
    return result;
  
  }
  
  // *** Get Transaction by Oid *** 
  @RequestMapping(value = "/{oid}", method = RequestMethod.GET)
  public @ResponseBody Transaction getTransactionByOid(@PathVariable("oid") long oid) 
  {
    Transaction transaction = transactionService.getTransactionByOid(oid);
    //TransactionInvoice trxInvoice = (TransactionInvoice) transaction;
    return transaction;
  }

  // *** Get all transactions *** 
  @RequestMapping(method = RequestMethod.GET)
  public @ResponseBody Transactions getAllTransactions() 
  {
     Transactions transactions =  transactionService.getAllTransactions();
     return transactions;
  }
  
  private SciiResult processAsynchronous(Transaction trx)
  {
    System.out.println("Saving asynchronous transaction.");
    SciiResult result = saveTransaction(trx);
    System.out.println("Asynchronous transaction saved.");
    
    return result;    
  }
  
  private Transaction processSynchronous(Transaction trx)
  {
  
    // *** Save transaction to Database *** 
    System.out.println("Saving synchronous transaction.");
    SciiResult result = saveTransaction(trx);
    System.out.println("Synchronous transaction saved.");
    
    // *** Reload transaction, to obtain complete object graph ***
    System.out.println("Load saved transaction to obtain complete object graph.");
    System.out.println("   Transaction Oid " + result.gettransactionOid() + "\n");
    trx = transactionService.getTransactionByOid(result.gettransactionOid());
    System.out.println("Saved transaction reloded.");
    
    System.out.println("Procesing synchronous transaction. Waiting for Response ...");

    // *** Process transaction immediately ***
    transactionHandler.process(trx);

    System.out.println("Synchronous transaction processed.");
    
    return trx;
  }
  
  private SciiResult saveTransaction(Transaction trx)
  {
  
    SciiResult result = null;
    
    System.out.println("  Saving to database...");
 
    try
    {
      result = transactionService.saveTransaction(trx);    
      System.out.println("  Saved to database.");
    }
    catch(SciiException sciie)
    {      

      System.out.println(sciie.getMessage());
      result = new SciiResult();
      result.setreturnCode((SciiResult.RETURN_CODE_UNIDENTFIED_ERROR));
      result.setreturnMessage("Error - " +  sciie.getMessage());
   
      loggerService.addEntry(SciiLoggerService.ERROR, trx.getcreatedBy(), result.getreturnMessage());

      return result;
    }
    /*
    catch(Exception e)
    {
      System.out.println(e.getMessage());      
    }
    */
    return result;
  
  }

}