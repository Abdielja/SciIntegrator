/**
 * 
 */
package com.sci.integrator.persistence;

import com.sci.integrator.domain.core.Transaction;
import com.sci.integrator.domain.core.Transactions;

/**
 * @author Abdiel
 *
 */
public interface ITransactionStatusDAO
{

  Transaction getByOid(long _oid);  
  Transactions getAll();

}
