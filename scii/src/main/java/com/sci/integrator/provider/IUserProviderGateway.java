/**
 * 
 */
package com.sci.integrator.provider;

import com.sci.integrator.domain.core.User;
import com.sci.integrator.domain.core.UserData;

/**
 * @author Abdiel
 *
 */
public interface IUserProviderGateway
{
  public UserData getUserByName(String username);
  public void setDefaultUserRole(User user, String organizationId, String roleId, String warehouseId);
}
