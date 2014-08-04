/**
 * 
 */
package com.sci.integrator.services.relational;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sci.integrator.domain.core.User;
import com.sci.integrator.persistence.ILogEntryDao;
import com.sci.integrator.services.ILoggerService;

/**
 * @author Abdiel
 *
 */

@Service("loggerService")
public class SciiLoggerService implements ILoggerService
{

  public static int INFO = 0;
  public static int DEBUG = 1;
  public static int ERROR = 2;
  
  @Inject ILogEntryDao sciiLogger;
  
  /**
   * 
   * Default Constructor 
   */
  public SciiLoggerService()
  {
    // TODO Auto-generated constructor stub
  }

  /* (non-Javadoc)
   * @see com.sci.integrator.utils.ILoggerService#addEntry(int, com.sci.integrator.domain.core.User, java.lang.String)
   */
  @Transactional
  public void addEntry(int level, User user, String description)
  {
    sciiLogger.save(level, user, description);
  }

}
