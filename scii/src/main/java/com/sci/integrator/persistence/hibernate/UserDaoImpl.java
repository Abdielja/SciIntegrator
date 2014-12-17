/**
 * 
 */
package com.sci.integrator.persistence.hibernate;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.sci.integrator.domain.core.Routes;
import com.sci.integrator.domain.core.User;
import com.sci.integrator.domain.core.UserData;
import com.sci.integrator.persistence.IUserDao;
import com.sci.integrator.transaction.Transaction;
import com.sci.integrator.transaction.Transactions;

/**
 * @author Abdiel
 *
 */
public class UserDaoImpl implements IUserDao
{

  private static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

  // ********** properties **********
  
  private SessionFactory sessionFactory;

  
  public UserDaoImpl()
  {
    // TODO Auto-generated constructor stub
  }

  // ********** Helper Methods **********
  
  public void setSessionFactory(SessionFactory sessionFactory) 
  {
    this.sessionFactory = sessionFactory;
  } 
  
  private Session currentSession() 
  {
    return sessionFactory.getCurrentSession();
  }


  public void update(User user)
  {
    currentSession().update(user);
  }
  
  /*
   * GetByOid(long _oid) 
   * (non-Javadoc)
   * @see com.sci.integrator.persistence.ITransactionDAO#getByOid(long)
   */
  @Transactional
  public User getByOid(long userOid) 
  {
    
    User u = (User)currentSession().get(User.class, userOid);
    
    return u;
        
  }

  @Transactional
  @SuppressWarnings("unchecked")
  public User getByUserName(String userName)
  {
    
    User user = null;
    
    List<User> users = currentSession().createQuery( "FROM User WHERE userName = '" + userName + "' AND active = '1' ORDER BY name" ).list(); 
    
    if (users != null)
    {
      user = users.get(0);
    }
    
    return user;
  }

  @Transactional
  @SuppressWarnings("unchecked")
  public List<User> getAllActive()
  {
    List<User> users;
    users = currentSession().createQuery( "FROM User WHERE active = '1' ORDER BY name" ).list(); 
    return users;
  }

  @SuppressWarnings("unchecked")
  public Transactions getInvoiceTransactions(User user, Date trxDate)
  {
    Transactions transactions = new Transactions();
    transactions.setTransaction(currentSession().createQuery( "FROM Transaction WHERE type = " + Transaction.TRANSACTION_INVOICE + " AND createdBy.oid = " + user.getoid()).list()); 
    return transactions;
  }
}
