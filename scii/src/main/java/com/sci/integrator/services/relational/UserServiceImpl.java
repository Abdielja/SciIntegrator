/**
 * 
 */
package com.sci.integrator.services.relational;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sci.integrator.domain.core.Invoices;
import com.sci.integrator.domain.core.Transaction;
import com.sci.integrator.domain.core.Transactions;
import com.sci.integrator.domain.core.User;
import com.sci.integrator.domain.core.UserData;
import com.sci.integrator.domain.invoice.TransactionInvoice;
import com.sci.integrator.persistence.IUserDao;
import com.sci.integrator.provider.IProvider;
import com.sci.integrator.services.IUserService;


/**
 * @author Abdiel Jaramillo Ojedis
 *
 */
@Service("userService")
public class UserServiceImpl implements IUserService
{

  // ***** Properties *****
  
  private IUserDao userDao;  
  
  public void setUserDao(IUserDao userDao)
  {
    this.userDao = userDao;
  }
    
  // ***** Public Methods *****
  
  @Transactional
  public User getByOid(int oid)
  {
    User user = userDao.getByOid(oid);
    //user.setInvoices(getInvoices(user));
    return user;
  }

  @Transactional
  public List<User> getAllActive()
  {
    List<User> users = userDao.getAllActive();
    /*
    for(int i=0; i < users.size(); i++)
    {
      users.get(i).setInvoices(getInvoices(users.get(i)));
    }
    */
    return users;
  }

  @Transactional
  public Transactions getInvoiceTransactions(User user, Date trxDate)
  {
    Transactions transactions = userDao.getInvoiceTransactions(user, trxDate);
    return transactions;
  }
  
  public Invoices getInvoices(User user)
  {
   
    Invoices invoices = new Invoices();
    
    Transactions transactions = getInvoiceTransactions(user, new Date());
    for(int i=0; i < transactions.getTransaction().size(); i++)
    {
      TransactionInvoice ti = (TransactionInvoice)transactions.getTransaction().get(i);
      invoices.add(ti.getinvoice());
    }
    
    return invoices;
    
  }

  
  @Transactional
  public void update(User user)
  {
    userDao.update(user);
  }
  
}
