/**
 * 
 */
package com.sci.integrator.persistence.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sci.integrator.domain.core.SciiResult;
import com.sci.integrator.domain.core.SyncData;
import com.sci.integrator.persistence.ISyncDataDao;

/**
 * @author Abdiel
 *
 */
public class SyncDataDaoImpl implements ISyncDataDao
{

  private static final Logger logger = LoggerFactory.getLogger(TransactionDaoImpl.class);

  private SessionFactory sessionFactory;
  
  public void setSessionFactory(SessionFactory sessionFactory) 
  {
    this.sessionFactory = sessionFactory;
  } 
  
  private Session currentSession() 
  {
    return sessionFactory.getCurrentSession();
  }

  /**
   * 
   */
  public SyncDataDaoImpl()
  {
    // TODO Auto-generated constructor stub
  }

  public SciiResult save(SyncData syncData)
  {
    SciiResult result = new SciiResult(); 
    
    Long syncDataOid = (Long) currentSession().save(syncData);
    
    result.setaffectedObjectOid(syncDataOid);
    result.setreturnCode(SciiResult.RETURN_CODE_OK);
    result.setreturnMessage("OK");
    
    return result;
  }

  public void delete(long oid)
  {
    // TODO Auto-generated method stub
  }

  public void deleteAll()
  {
    // TODO Auto-generated method stub
  }

}
