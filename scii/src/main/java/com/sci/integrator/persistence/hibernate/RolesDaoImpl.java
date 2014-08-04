/**
 * 
 */
package com.sci.integrator.persistence.hibernate;

import java.util.Iterator;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sci.integrator.domain.core.Role;
import com.sci.integrator.domain.core.Roles;
import com.sci.integrator.domain.core.SciiResult;
import com.sci.integrator.domain.customer.Customer;
import com.sci.integrator.domain.customer.Customers;
import com.sci.integrator.persistence.IRolesDao;

/**
 * @author Abdiel
 *
 */
public class RolesDaoImpl implements IRolesDao
{

  // ********** Constants **********
  
  private static final Logger logger = LoggerFactory.getLogger(TransactionStatusDaoImpl.class);

  // ********** properties **********
  
  private SessionFactory sessionFactory;

  
  // ********** Constructors **********
  
  public RolesDaoImpl()
  {
    // TODO Auto-generated constructor stub
  }

  // ********** Helper Methods **********
  
  public void setSessionFactory(SessionFactory sessionFactory) 
  {
    this.sessionFactory = sessionFactory;
  } 
  
  private Session currentSession() 
  {
    return sessionFactory.getCurrentSession();
  }

  // ********** ICustomerDao Implementation Methods **********
  
  public SciiResult save(Roles roles)
  {
    SciiResult result = new SciiResult();
    
    // *** Save Role list header first ***
   
    Long rolesOid = (Long)currentSession().save(roles);
    roles.setOid(rolesOid);
    result.setaffectedObjectOid(roles.getOid());
    
    // *** Save Customer instances ***
    Iterator<Role> iRole = roles.getRoles().iterator();
    while (iRole.hasNext())
    {
      Role role = iRole.next();
      role.setRoleList(roles);
      currentSession().save(role);
    }
    
    return result;
  }

  public void update(Roles role)
  {
    // TODO Auto-generated method stub
    
  }

  public Role getByOid(long _oid)
  {
    // TODO Auto-generated method stub
    return null;
  }

  public Roles getAll()
  {
    // TODO Auto-generated method stub
    return null;
  }

}
