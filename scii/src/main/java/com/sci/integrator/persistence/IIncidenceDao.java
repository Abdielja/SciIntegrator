/**
 * 
 */
package com.sci.integrator.persistence;

import java.util.List;

import com.sci.integrator.domain.core.SciiResult;
import com.sci.integrator.domain.core.User;
import com.sci.integrator.domain.incidence.Incidence;

/**
 * @author Abdiel Jaramillo Ojedis
 *
 */
public interface IIncidenceDao
{

  SciiResult save(Incidence incidence);
  void update(Incidence incidence);
  Incidence getByOid(long _oid);  
  Incidence getAll();
  List<Incidence> getByUserOid(long userOid);
  List<Incidence> getByUserOidToday(long userOid);
  void deleteAll();
  void deleteByOid(long oid);
}
