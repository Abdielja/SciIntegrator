/**
 * 
 */
package com.sci.integrator.persistence.hibernate;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sci.integrator.domain.core.TransactionStatus;

/**
 * @author Abdiel Jarimillo Ojedis 
 *
 */

@Entity
@Table(name="transaction")
public class TransactionStatusDaoImpl
{

  private static final Logger logger = LoggerFactory.getLogger(TransactionStatusDaoImpl.class);

  private SessionFactory sessionFactory;
  
  public void setSessionFactory(SessionFactory sessionFactory) 
  {
    this.sessionFactory = sessionFactory;
  } 
  
  private Session currentSession() 
  {
    return sessionFactory.getCurrentSession();
  }

  /*
   * GetByOid(long _oid) 
   * (non-Javadoc)
   * @see com.sci.integrator.persistence.TransactionDAOHibernate#getByOid(long)
   */
  public TransactionStatus getByOid(long _oid) 
  {
    
    TransactionStatus ts = (TransactionStatus)currentSession().get(TransactionStatus.class, _oid);
    
    return ts;
        
  }
  
}
