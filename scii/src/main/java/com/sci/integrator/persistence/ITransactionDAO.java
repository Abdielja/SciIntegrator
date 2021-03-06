/**
 * 
 */
package com.sci.integrator.persistence;

import java.util.Date;
import java.util.List;

import com.sci.integrator.domain.core.SciiResult;
import com.sci.integrator.domain.core.User;
import com.sci.integrator.provider.openbravo.transaction.TransactionInvoice;
import com.sci.integrator.transaction.Transaction;
import com.sci.integrator.transaction.Transactions;

/**
 * @author Abdiel Jaramillo Ojedis
 *
 */

public interface ITransactionDAO 
{

	Transaction getByOid(long _oid);	
	Transactions getAll();
  Transactions getAllToday(); 
  Transactions getByUserOidToday(long userOid);
	Transactions getAllPending();
  Transactions getAllPendingToday();
  Transactions getAllFailed();
  Transactions getAllFailedToday();
  Transactions getAllWithRetriesReached();
  SciiResult save(Transaction trx);
	void update(Transaction trx);
  Transactions getByUserOidAndTypeToday(long userOid, int trxType);
  Transactions getByTrxTypeToday(int trxType);
	
}
