package com.sci.integrator.services;

import java.util.Date;
import java.util.List;

import com.sci.integrator.domain.core.User;
import com.sci.integrator.domain.core.UserData;
import com.sci.integrator.transaction.Transactions;

public interface IUserService
{

  public void update(User user);
  
  public User getByOid(int oid);
  public List<User> getAllActive();
  
  public Transactions getInvoiceTransactions(User user, Date trxDate);
  
}
