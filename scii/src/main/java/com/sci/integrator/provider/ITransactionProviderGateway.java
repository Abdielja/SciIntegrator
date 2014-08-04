/**
 * 
 */
package com.sci.integrator.provider;

import com.sci.integrator.domain.core.SciiException;
import com.sci.integrator.domain.core.SciiResult;
import com.sci.integrator.domain.core.Transaction;
import com.sci.integrator.domain.core.TransactionOpen;
import com.sci.integrator.domain.invoice.TransactionInvoice;
import com.sci.integrator.domain.invoice.TransactionInvoiceReversal;
import com.sci.integrator.domain.order.TransactionOrder;
import com.sci.integrator.domain.payment.TransactionPayment;
import com.sci.integrator.domain.quotation.TransactionQuotation;

/**
 * @author Abdiel Jaramillo Ojedis
 *
 */
public interface ITransactionProviderGateway
{
  
  // ***** Transaction Processor *****

  public Transaction processTransaction(Transaction trx) throws SciiException;
  
}
