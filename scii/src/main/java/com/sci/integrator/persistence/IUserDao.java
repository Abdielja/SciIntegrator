/**
 * 
 */
package com.sci.integrator.persistence;

import java.util.Date;
import java.util.List;

import com.sci.integrator.domain.core.User;
import com.sci.integrator.domain.core.UserData;
import com.sci.integrator.transaction.Transactions;

/**
 * @author Abdiel
 *
 */
public interface IUserDao
{

  public void update(User user);
  public User getByOid(long oid);
  public User getByUserName(String userName);
  public List<User> getAllActive();

  public Transactions getInvoiceTransactions(User user, Date trxDate);

}
