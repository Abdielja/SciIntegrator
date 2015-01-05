/**
 * 
 */
package com.sci.integrator.provider.adempiere;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.xml.transform.dom.DOMSource;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpServerErrorException;

import com.sci.integrator.domain.core.Invoices;
import com.sci.integrator.domain.core.SciiException;
import com.sci.integrator.domain.core.SciiRequest;
import com.sci.integrator.domain.core.SciiResponse;
import com.sci.integrator.domain.core.SciiResult;
import com.sci.integrator.domain.core.User;
import com.sci.integrator.domain.invoice.Invoice;
import com.sci.integrator.domain.order.Order;
import com.sci.integrator.domain.order.Orders;
import com.sci.integrator.domain.payment.PaymentMethod;
import com.sci.integrator.provider.ITransactionProviderGateway;
import com.sci.integrator.provider.IUserProviderGateway;
import com.sci.integrator.provider.SOAPBaseProviderGateway;
import com.sci.integrator.provider.openbravo.transaction.TransactionOpen;
import com.sci.integrator.services.IInvoiceService;
import com.sci.integrator.services.IOrderService;
import com.sci.integrator.services.IPaymentMethodService;
import com.sci.integrator.services.ITransactionService;
import com.sci.integrator.services.IUserService;
import com.sci.integrator.transaction.Transaction;

/**
 * @author Abdiel Jaramillo Ojedis
 *
 */
public class AdempiereTransactionProviderGateway extends SOAPBaseProviderGateway implements ITransactionProviderGateway 
{

  // ***** Other ProviderGateways Required  *****
  
  @Inject
  IUserProviderGateway        userProviderGateway;

  // ***** Persistence Services *****
  
  @Inject
  ITransactionService         transactionService;

  @Inject
  IInvoiceService             invoiceService;

  @Inject
  IOrderService               orderService;

  @Inject
  IPaymentMethodService       paymentMethodService;

  @Inject
  IUserService                userService;
  
  
  private long startTime;
  
	public AdempiereTransactionProviderGateway(String serverUrl)
  {
	  this.setBaseUrl(serverUrl);
  }

	
  @Override
	public Transaction processTransaction(Transaction trx) throws SciiException 
  {

    startTime = new Date().getTime();
    
    HttpHeaders headers;

    com.sci.integrator.domain.core.User user = trx.getcreatedBy();
    
    // *** Set credentials for server side authentication ***
    if (trx.getClass().equals(TransactionOpen.class))
    {
      headers = this.createHeaders("Adempiere", "123");
    }
    else
    {
      headers = this.createHeaders(user.getuserName(), user.getpassword());
    }
    
    // *** Get Main Transaction request string ***
    SciiRequest mainRequest = trx.buildMainRequest();

    if (mainRequest != null)
    {
      
      try
      {
  
        setDefaultUserRole(trx);        
        
        ResponseEntity<DOMSource> responseEntity = sendRequestToServer(mainRequest, headers);
                     
        SciiResponse response = new SciiResponse();
        response.setException(null);
        response.setResponseEntity(responseEntity);
       
        // *** Process main response ***
        trx.processMainResponse(response);
        
      }
      catch (HttpServerErrorException eHttp)
      {
        
        mainRequest.setStatus(Transaction.STATUS_FAILED);
        
        SciiResponse response = new SciiResponse();
        response.setException(eHttp);
        
        // *** Process response ***
        trx.processMainResponse(response);
  
        throw eHttp;
      }  
      // ***** TODO - Temporal. Used to return empty response. Create implementation *****
      catch (SciiException eSe)
      {
        
        mainRequest.setStatus(Transaction.STATUS_PROCESSED);
        
        SciiResponse response = new SciiResponse();
        response.setException(eSe);
        
        // *** Process response ***
        trx.processMainResponse(response);
  
      }
      // *********************************************************************************
    }
    else
    {
      trx.setstatus(Transaction.STATUS_FAILED);
      throw new SciiException("Adempiere: Failed to build request for transaction with oid " + trx.getoid() + " - " + trx.getdescription());
    }
    
    // *** Process sub requests if any ***
    List<SciiRequest> subRequests = trx.buildSubRequests();
    if (subRequests != null)
    {
      
      // *** Send the requests ***
      for(int i = 0; i < subRequests.size(); i++)
      {  
        SciiRequest request = subRequests.get(i);

        ResponseEntity<DOMSource> subResponseEntity;
       
        try
        {

          setDefaultUserRole(trx);        
          
          subResponseEntity = sendRequestToServer(request, headers);
                  
          SciiResponse subResponse = new SciiResponse();
          subResponse.setIndex(i);
          subResponse.setResponseEntity(subResponseEntity);
          
          String errorMessage = subResponse.getErrorMessage();
          if (errorMessage.equals("NONE"))
          {
            subResponse.setTag(request.getTag());
            subResponse.setException(null);
            request.setStatus(Transaction.STATUS_PROCESSED);
          }
          else
          {
            SciiException se = new SciiException(errorMessage);
            subResponse.setException(se);
            request.setStatus(Transaction.STATUS_FAILED);
            throw se;
          }
          
          // *** Process sub response ***
          trx.processSubResponse(subResponse);
          
          //transactionService.updateTransaction(trx);
                  
        }
        catch (HttpServerErrorException e)
        {
          
          request.setStatus(Transaction.STATUS_FAILED);
          
          SciiResponse subResponse = new SciiResponse();
          subResponse.setIndex(i);
          subResponse.setTag("HttpServer Error");
          subResponse.setException(e);
          
          // *** Process response ***
          trx.processSubResponse(subResponse);

          throw e;
        }    

      }
            
    }
    
    if (trx.getClass().equals(TransactionOpen.class))
    {
      
      // *** Create or update local Invoice records ***
      System.out.println("  Adempiere: Synchronicing Pending Invoices...");

      TransactionOpen trxOpen = (TransactionOpen)trx;

      Invoices pendingInvoices = trxOpen.getuserData().getInvoices();
      for(int i=0; i < pendingInvoices.getCount(); i++)
      {
        Invoice pendingInvoice = pendingInvoices.getInvoice().get(i);
        Invoice localInvoice = invoiceService.getByServerId(pendingInvoice.getserverId());

        PaymentMethod pm = paymentMethodService.getByServerId(pendingInvoice.getpaymentMethod().getserverId());
        pendingInvoice.setpaymentMethod(pm);
        
        if (localInvoice != null)
        {
          pendingInvoice.setoid(localInvoice.getoid());
          invoiceService.updateInvoice(pendingInvoice);
        }
        else
        {
          SciiResult result = invoiceService.saveInvoice(pendingInvoice);
          pendingInvoice.setoid(result.getaffectedObjectOid());   
        }
   
        pendingInvoice.setCreatedBy(trx.getcreatedBy());

      }

      System.out.println("  Adempiere: Pending Invoices Synchronized");

      // *** Create or update local Order records ***

      System.out.println("  Adempiere: Synchronicing Pending Orders...");
      
      // *** Delete local pending orders ***
      orderService.deleteByUserOid(trx.getcreatedBy().getoid());
  
      Orders pendingOrders = trxOpen.getuserData().getOrders();
      for(int i=0; i < pendingOrders.getCount(); i++)
      {
        Order pendingOrder = pendingOrders.getOrder().get(i);
        Order localOrder = orderService.getByServerId(pendingOrder.getserverId());

        PaymentMethod pm = paymentMethodService.getByServerId(pendingOrder.getpaymentMethod().getserverId());
        pendingOrder.setpaymentMethod(pm);
        pendingOrder.setCreatedBy(trx.getcreatedBy());
        pendingOrder.setsubTotal(pendingOrder.gettotal());
  
        if (localOrder == null)
        {
          System.out.println("Insert new order " + pendingOrder.getserverId());
          
          try
          {
            SciiResult result = orderService.saveOrder(pendingOrder);
            pendingOrder.setoid(result.getaffectedObjectOid());   
            System.out.println("Order " + pendingOrder.getoid() + "created");
          }
          catch(Exception e)
          {
            System.out.println(e.getMessage());            
          }
        }
        else
        {
          System.out.println("Update order " + localOrder.getoid());
          pendingOrder.setoid(localOrder.getoid());
          
          orderService.updateOrder(localOrder);
          System.out.println("Order " + localOrder.getoid() + "updated");
        }
   
      }

      System.out.println("  Adempiere: Pending Orders Synchronized");

      // *** Update user's status ***

      trx.getcreatedBy().setStatus(User.STATUS_OPEN);
      
      // ***** This should be in the CloseTransaction *****
      trx.getcreatedBy().setLastBalanceDate(new Date());
      
      userService.update(trx.getcreatedBy());
      
    }
    
    // ******** Abdiel 10/03/2014 ****************
    trx.setstatus(Transaction.STATUS_PROCESSED);
    // ******** Abdiel 10/03/2014 ****************
    
    transactionService.updateTransaction(trx);
    
    long currentTime = new Date().getTime();
    double elapsedTime = currentTime - startTime;
    
    System.out.println("Adempiere: Total Processing Time: " + elapsedTime / 60000 + " Minutes.");
    
    
		return trx;
	}

  private ResponseEntity<DOMSource> sendRequestToServer(SciiRequest request, HttpHeaders headers) throws SciiException
  {
    
    // ******************************* WARNING - Temporal ***********************************
    ResponseEntity<DOMSource> responseEntity;
    throw new SciiException("Transaction processing for Adempiere is not implemented yet.", SciiResult.RETURN_CODE_UNIDENTFIED_ERROR);
    // **************************************************************************************
/*     
    HttpEntity<Object> httpEntity = new HttpEntity<Object>(request.getStrRequest() , headers);
   
    if(!request.getWhereClause().isEmpty())
    {
      responseEntity = restTemplate.exchange(getBaseUrl() + request.getUrlExtension(), request.getHttpMethod(), httpEntity, DOMSource.class, request.getWhereClause());
    }
    else
    {
      responseEntity = restTemplate.exchange(getBaseUrl() + request.getUrlExtension(), request.getHttpMethod(), httpEntity, DOMSource.class, request.getVars());          
    }

    return responseEntity;
*/    
  }

  public void setDefaultUserRole(Transaction trx) throws SciiException
  {
    
    if (trx.getClass() != TransactionOpen.class)
    {
      // ***** Change default role *****
      // ******** Change this. Should only be one call ********
      //userProviderGateway.setDefaultUserRole(trx.getcreatedBy(), trx.getorganizationId(), trx.getroleId(), trx.getwarehouseId());
      //userProviderGateway.setDefaultUserRole(trx.getcreatedBy(), trx.getorganizationId(), trx.getroleId(), trx.getwarehouseId());
      // ******************************************************    
    }
    
  }
  
}
