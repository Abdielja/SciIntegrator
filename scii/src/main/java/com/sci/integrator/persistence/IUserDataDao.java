/**
 * 
 */
package com.sci.integrator.persistence;

import com.sci.integrator.domain.core.SciiException;
import com.sci.integrator.domain.core.SciiResult;
import com.sci.integrator.domain.core.SyncData;
import com.sci.integrator.domain.core.UserData;
import com.sci.integrator.domain.core.UserPeriodData;

/**
 * @author Abdiel
 *
 */
public interface IUserDataDao
{
  public SciiResult update(UserData userData);
  SciiResult save(UserData userData);

  void delete(long oid);
  void deleteAll();
  void deleteByUserOid(long userOid);
  UserData getByUserOid(long userOid) throws SciiException;
  SciiResult saveUserPeriodData(UserPeriodData upd); 

}
