/**
 * 
 */
package com.sci.integrator.provider;

import com.sci.integrator.domain.core.SciiException;
import com.sci.integrator.domain.core.SciiResult;
import com.sci.integrator.domain.core.TransactionOpen;
import com.sci.integrator.provider.openbravo.transaction.TransactionInvoice;
import com.sci.integrator.provider.openbravo.transaction.TransactionInvoiceReversal;
import com.sci.integrator.provider.openbravo.transaction.TransactionOrder;
import com.sci.integrator.provider.openbravo.transaction.TransactionPayment;
import com.sci.integrator.provider.openbravo.transaction.TransactionQuotation;
import com.sci.integrator.transaction.Transaction;

/**
 * @author Abdiel Jaramillo Ojedis
 *
 */
public interface ITransactionProviderGateway
{
  
  // ***** Transaction Processor *****

  public Transaction processTransaction(Transaction trx) throws SciiException;
  
}
