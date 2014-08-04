/**
 * 
 */
package com.sci.integrator.persistence.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sci.integrator.domain.core.Product;
import com.sci.integrator.domain.core.SciiResult;
import com.sci.integrator.domain.core.User;
import com.sci.integrator.domain.incidence.Incidence;
import com.sci.integrator.persistence.IIncidenceDao;

/**
 * @author Abdiel
 *
 */
public class IncidenceDaoImpl implements IIncidenceDao
{

  // ********** Constants **********
  
  private static final Logger logger = LoggerFactory.getLogger(ProductsDaoImpl.class);

  // ********** Properties **********
  
  private SessionFactory sessionFactory;


  // ********** Constructors **********
  
  public IncidenceDaoImpl()
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

  // ********** IIncidenceDao Implementation Methods **********
  
  public SciiResult save(Incidence incidence)
  {
    SciiResult result = new SciiResult();
    
    Long oid = (Long)currentSession().save(incidence);
    incidence.setOid(oid);
    result.setaffectedObjectOid(oid);
    //result.setAffectedObjectServerId(incidence.getServerId());
      
    return result;
  }

  public void update(Incidence incidence)
  {
    currentSession().update(incidence);    
  }

  public Incidence getByOid(long _oid)
  {
    // TODO Auto-generated method stub
    return null;
  }

  public Incidence getAll()
  {
    // TODO Auto-generated method stub
    return null;
  }

  @SuppressWarnings("unchecked")
  public List<Incidence> getByUserOid(long userOid)
  {
    Query query = currentSession().createQuery("FROM Incidence WHERE userOid = " + userOid);
    List<Incidence> incidenceList = query.list();
    return incidenceList;
  }

  @SuppressWarnings("unchecked")
  public List<Incidence> getByUserOidToday(long userOid)
  {
    Query query = currentSession().createQuery("FROM Incidence WHERE createdBy.oid = " + userOid + " AND creationDate >= createdBy.lastBalanceDate ORDER BY oid ASC");
    List<Incidence> incidenceList = query.list();
    return incidenceList;
  }

  public void deleteAll()
  {
    // TODO Auto-generated method stub
    
  }

  public void deleteByOid(long oid)
  {
    // TODO Auto-generated method stub
    
  }

}
