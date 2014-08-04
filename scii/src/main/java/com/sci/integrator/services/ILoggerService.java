/**
 * 
 */
package com.sci.integrator.services;

import java.util.Date;

import com.sci.integrator.domain.core.User;

/**
 * @author Abdiel Jaramillo Ojedis
 *
 */
public interface ILoggerService
{

  void addEntry(int level, User user, String description);
}
