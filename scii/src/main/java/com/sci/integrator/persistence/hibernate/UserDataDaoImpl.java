/**
 * 
 */
package com.sci.integrator.persistence.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sci.integrator.domain.core.SciiException;
import com.sci.integrator.domain.core.SciiResult;
import com.sci.integrator.domain.core.UserData;
import com.sci.integrator.domain.core.UserPeriodData;
import com.sci.integrator.persistence.IUserDataDao;

/**
 * @author Abdiel
 *
 */
public class UserDataDaoImpl implements IUserDataDao
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


  public UserDataDaoImpl()
  {
    // TODO Auto-generated constructor stub
  }

  public SciiResult save(UserData userData)
  {
  
    SciiResult result = new SciiResult(); 
    
    Long userDataOid = (Long) currentSession().save(userData);
    
    result.setaffectedObjectOid(userDataOid);
    result.setreturnCode(SciiResult.RETURN_CODE_OK);
    result.setreturnMessage("OK");
    
    return result;

  }

  public SciiResult update(UserData userData)
  {
  
    SciiResult result = new SciiResult(); 
    
    currentSession().update(userData);
    
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

  @SuppressWarnings("unchecked")
  public void deleteByUserOid(long userOid)
  {
    Query query = currentSession().createQuery("FROM UserData WHERE userOid = " + userOid);
    List<UserData> userDataList = query.list();
    for(int i = 0; i < userDataList.size(); i++)
    {
      UserData userData = userDataList.get(i);
      currentSession().delete(userData);
    }
  }

  @SuppressWarnings("unchecked")
  public UserData getByUserOid(long userOid) throws SciiException
  {
    
    UserData userData = null;
    
    Query query = currentSession().createQuery("FROM UserData WHERE userOid = " + userOid);
    List<UserData> userDataList = query.list();
    
    if (userDataList.size() > 0)
    {
      userData = userDataList.get(0);
    }
    else
    {
      logger.error("User with oid " + userOid + " does not have any data associated.");
      throw new SciiException("User with oid " + userOid + " does not have any data associated.");
    }
    
    return userData;
  }

  public SciiResult saveUserPeriodData(UserPeriodData upd)
  {
    SciiResult result = new SciiResult(); 
    
    Long userPeriodDataOid = (Long) currentSession().save(upd);
    
    result.setaffectedObjectOid(userPeriodDataOid);
    result.setreturnCode(SciiResult.RETURN_CODE_OK);
    result.setreturnMessage("OK");
    
    return result;
  }

}
