/**
 * 
 */
package com.sci.integrator.persistence;

import com.sci.integrator.domain.core.SciiResult;
import com.sci.integrator.domain.customer.Customers;

/**
 * @author Abdiel
 *
 */
public interface ICustomersDao
{

  SciiResult save(Customers customers);
  void update(Customers route);
  Customers getByOid(long _oid);  

}
