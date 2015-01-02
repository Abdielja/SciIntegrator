/**
 * 
 */
package com.sci.integrator.provider.adempiere;

import com.sci.integrator.domain.core.User;
import com.sci.integrator.domain.core.UserData;
import com.sci.integrator.provider.IUserProviderGateway;
import com.sci.integrator.provider.RestBaseProviderGateway;

/**
 * @author Abdiel Jaramillo O.
 *
 */
public class AdempiereUserProviderGateway extends RestBaseProviderGateway implements IUserProviderGateway
{

  @Override
  public UserData getUserByName(String username)
  {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setDefaultUserRole(User user, String organizationId, String roleId, String warehouseId)
  {
    // TODO Auto-generated method stub
    
  }

}
