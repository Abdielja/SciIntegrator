/**
 * 
 */
package com.sci.integrator.persistence.hibernate;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sci.integrator.domain.core.LogEntry;
import com.sci.integrator.domain.core.SciiResult;
import com.sci.integrator.domain.core.User;
import com.sci.integrator.persistence.ILogEntryDao;

/**
 * @author Abdiel
 *
 */
public class LogEntryDaoImpl implements ILogEntryDao
{

  public LogEntryDaoImpl()
  {
    // TODO Auto-generated constructor stub
  }

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

  public SciiResult save(int level, User user, String description)
  {
    SciiResult result = null;
    
    LogEntry  logEntry = new LogEntry();
    logEntry.setLevel(level);
    logEntry.setUser(user);
    logEntry.setDate(new Date());
    logEntry.setDescription(description);
    
    result = save(logEntry);
    
    return result;
    
  }

  public SciiResult save(LogEntry logEntry)
  {
    SciiResult result = new SciiResult();

    Long oid = (Long)currentSession().save(logEntry);
    logEntry.setOid(oid);
    result.setaffectedObjectOid(oid);
    result.setAffectedObjectServerId("0");
    
    return result;
  }

}
