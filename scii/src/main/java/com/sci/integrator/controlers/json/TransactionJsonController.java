/**
 * 
 */
package com.sci.integrator.controlers.json;

import java.io.StringWriter;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathConstants;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;





import com.sci.integrator.domain.core.SciiException;
import com.sci.integrator.domain.core.SciiResult;
import com.sci.integrator.domain.core.TransactionOpen;
import com.sci.integrator.domain.core.User;
import com.sci.integrator.domain.core.UserData;
import com.sci.integrator.handlers.TransactionHandler;
import com.sci.integrator.helpers.XmlHelper;
import com.sci.integrator.services.ILoggerService;
import com.sci.integrator.services.ITransactionService;
import com.sci.integrator.services.relational.SciiLoggerService;
import com.sci.integrator.transaction.Transaction;

/**
 * @author Abdiel Jaramillo Ojedis
 *
 */
@Controller
@RequestMapping("/json/transactions")
public class TransactionJsonController
{

  @Inject
  private ITransactionService transactionService;

  @Inject
  private TransactionHandler transactionHandler;
  
  @Inject
  private ILoggerService loggerService;
  
  // *** Create Transaction *** 
  @RequestMapping(method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces ="application/json")
  @ResponseStatus(HttpStatus.CREATED)  
  public @ResponseBody SciiResult processTransaction(@RequestBody TransactionOpen trx, HttpServletResponse response) throws Exception 
  {

    SciiResult result = new SciiResult();

    System.out.println("\nENTERED: JSON RestController...");
    
    System.out.println("\n  Transaction recevied...\n");

    System.out.println("\n    Transaction: " + trx.getcreatedBy().getuserName());

    result = process(trx);
    System.out.println("\nEXITED: JSON RestController...");

    return result;
  
  }
  
  public SciiResult process(Transaction trx)
  {
    SciiResult result = new SciiResult();

    User user = trx.getcreatedBy();
    
    // ** Authenticate iser credentials **
    
    System.out.println("  Authenticating User Credentials...");

    try
    {
      user = transactionService.validateUser(trx.getcreatedBy().getuserName(), trx.getcreatedBy().getpassword());
      
      result.setreturnCode((SciiResult.RETURN_CODE_OK));
      result.setreturnMessage("User " + user.getname() + " validated corectly.");

      System.out.println("Welcome " + user.getname());
    }
    catch(SciiException e)
    {      
      result.setreturnCode((SciiResult.RETURN_CODE_INVALID_CREDENTIALS));
      result.setreturnMessage("Error - Credenciales Invalidas. Por favor vuelva a intentar.");
      
      System.out.println("  Error - Credenciales Invalidas.");
    }

    // ** Validate Transaction Before Saving**
    System.out.println("  Validating transaction data...");
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
        
        String strXmlTrxObject = sw.toString();
        result.setTransactionObject(strXmlTrxObject);

      }
      
      result.setreturnCode((SciiResult.RETURN_CODE_OK));
      result.setreturnMessage("OK");

    }
    
    return result;      
    
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
