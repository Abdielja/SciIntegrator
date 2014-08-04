/**
 * 
 */
package com.sci.integrator.persistence;

import com.sci.integrator.domain.core.Role;
import com.sci.integrator.domain.core.Roles;
import com.sci.integrator.domain.core.SciiResult;

/**
 * @author Abdiel
 *
 */
public interface IRolesDao
{

  SciiResult save(Roles role);
  void update(Roles role);
  Role getByOid(long _oid);  
  Roles getAll();

}
