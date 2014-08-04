/**
 * 
 */
package com.sci.integrator.persistence;

import com.sci.integrator.domain.core.LogEntry;
import com.sci.integrator.domain.core.SciiResult;
import com.sci.integrator.domain.core.User;

/**
 * @author Abdiel
 *
 */
public interface ILogEntryDao
{

  public SciiResult save(int level, User user, String description);
  public SciiResult save(LogEntry logEntry);
}
