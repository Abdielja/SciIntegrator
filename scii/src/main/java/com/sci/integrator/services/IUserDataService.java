/**
 * 
 */
package com.sci.integrator.services;

import com.sci.integrator.domain.core.SciiException;
import com.sci.integrator.domain.core.SciiResult;
import com.sci.integrator.domain.core.SyncData;
import com.sci.integrator.domain.core.UserData;
import com.sci.integrator.domain.core.UserPeriodData;

/**
 * @author Abdiel
 *
 */
public interface IUserDataService
{
  public SciiResult update(UserData userData);

  SciiResult saveUserData(UserData userData);
  void deleteUserDataByUserOid(long userOid);    
  public UserData getByUserOid(long userOid) throws SciiException;
  SciiResult saveUserPeriodData(UserPeriodData upd); 
  
}
