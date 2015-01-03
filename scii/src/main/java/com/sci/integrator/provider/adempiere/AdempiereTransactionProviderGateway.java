/**
 * 
 */
package com.sci.integrator.provider.adempiere;

import javax.inject.Inject;

import com.sci.integrator.domain.core.SciiException;
import com.sci.integrator.provider.ITransactionProviderGateway;
import com.sci.integrator.provider.IUserProviderGateway;
import com.sci.integrator.provider.SOAPBaseProviderGateway;
import com.sci.integrator.services.IInvoiceService;
import com.sci.integrator.services.IOrderService;
import com.sci.integrator.services.IPaymentMethodService;
import com.sci.integrator.services.ITransactionService;
import com.sci.integrator.services.IUserService;
import com.sci.integrator.transaction.Transaction;

/**
 * @author Administrator
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
  
  
	public AdempiereTransactionProviderGateway(String serverUrl)
  {
	  this.setBaseUrl(serverUrl);
  }


  @Override
	public Transaction processTransaction(Transaction trx) throws SciiException 
  {
	
		return trx;
	}

}
