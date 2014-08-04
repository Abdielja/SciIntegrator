/**
 * 
 */
package com.sci.integrator.services.relational;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.sci.integrator.domain.core.SciiResult;
import com.sci.integrator.domain.incidence.Incidence;
import com.sci.integrator.persistence.IIncidenceDao;
import com.sci.integrator.services.IIncidenceService;

/**
 * @author Abdiel
 *
 */
public class IncidenceServiceImpl implements IIncidenceService
{

  private IIncidenceDao incidenceDao;
  
  public void setIncidenceDao(IIncidenceDao incidenceDao)
  {
    this.incidenceDao = incidenceDao;
  }

  public IncidenceServiceImpl()
  {
    // TODO Auto-generated constructor stub
  }

  @Transactional
  public SciiResult save(Incidence incidence)
  {
    SciiResult sciiResult;
    sciiResult = incidenceDao.save(incidence);
    return sciiResult;
  }

  @Transactional
  public void update(Incidence product)
  {
    // TODO Auto-generated method stub
    
  }

  @Transactional
  public void delete(long oid)
  {
    // TODO Auto-generated method stub
    
  }

 @Transactional
 public Incidence getByOid(long oid)
  {
    // TODO Auto-generated method stub
    return null;
  }

 @Transactional
  public List<Incidence> getByUserOid(long userOid)
  {
    List<Incidence> incidences = incidenceDao.getByUserOid(userOid);
    return incidences;
  }

 @Transactional
 public List<Incidence> getByUserOidToday(long userOid)
 {
   List<Incidence> incidences = incidenceDao.getByUserOidToday(userOid);
   return incidences;
 }

 @Transactional
  public Incidence getAll()
  {
    // TODO Auto-generated method stub
    return null;
  }

}
