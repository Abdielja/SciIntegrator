/**
 * 
 */
package com.sci.integrator.persistence;

import com.sci.integrator.transaction.Transaction;
import com.sci.integrator.transaction.Transactions;

/**
 * @author Abdiel
 *
 */
public interface ITransactionStatusDAO
{

  Transaction getByOid(long _oid);  
  Transactions getAll();

}
