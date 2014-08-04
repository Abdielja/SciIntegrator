/**
 * 
 */
package com.sci.integrator.services;

import java.util.Date;
import java.util.List;

import com.sci.integrator.domain.core.SciiException;
import com.sci.integrator.domain.core.SciiResult;
import com.sci.integrator.domain.core.Transaction;
import com.sci.integrator.domain.core.Transactions;
import com.sci.integrator.domain.core.User;
import com.sci.integrator.domain.invoice.TransactionInvoice;

/**
 * @author Abdiel Jaramillo Ojedes
 *
 */
public interface ITransactionService 
{

  SciiResult saveTransaction(Transaction transaction) throws SciiException;
  void updateTransaction(Transaction transaction);
  void deleteTransaction(long id);    
  Transaction getTransactionByOid(long oid);	  
  Transactions getAllTransactions();
  Transactions getAllToday();
  Transactions getByUserOidToday(long userOid);
  Transactions getAllPending();
  Transactions getAllPendingToday();
  Transactions getAllFailed();
  Transactions getAllFailedToday();
  Transactions getAllWithRetriesReached();
  
  User validateUser(String userName, String password) throws SciiException;
  void validateTransaction(Transaction trx) throws SciiException;
  Transactions getByUserOidAndTypeToday(long userOid, int trxType);
  Transactions getByTrxTypeToday(int trxType);
  
}
