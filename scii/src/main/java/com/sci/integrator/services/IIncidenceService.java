/**
 * 
 */
package com.sci.integrator.services;

import java.util.List;

import com.sci.integrator.domain.core.SciiResult;
import com.sci.integrator.domain.core.User;
import com.sci.integrator.domain.incidence.Incidence;

/**
 * @author Abdiel
 *
 */
public interface IIncidenceService
{

  SciiResult save(Incidence incidence);
  void update(Incidence product);
  void delete(long oid);    
  Incidence getByOid(long oid);    
  List<Incidence> getByUserOid(long userOid);
  List<Incidence> getByUserOidToday(long userOid);    
  Incidence getAll();

}
