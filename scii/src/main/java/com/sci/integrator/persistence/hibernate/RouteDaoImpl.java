package com.sci.integrator.persistence.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sci.integrator.domain.core.Route;
import com.sci.integrator.domain.core.Routes;
import com.sci.integrator.domain.core.SciiResult;
import com.sci.integrator.domain.core.TransactionStatus;
import com.sci.integrator.persistence.IRouteDao;
import com.sci.integrator.persistence.IUserDao;

public class RouteDaoImpl implements IRouteDao
{


  // ********** Constants **********
  
  private static final Logger logger = LoggerFactory.getLogger(TransactionStatusDaoImpl.class);

  // ********** properties **********
  
  private SessionFactory sessionFactory;

  // ********** Constructors **********
  
  public RouteDaoImpl()
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

  // ********** IRouteDao Implementation Methods **********
  
  public SciiResult save(Route route)
  {
    
    SciiResult result = new SciiResult();
    
    Long oid = (Long)currentSession().save(route);
    route.setOid(oid);
    result.setaffectedObjectOid(oid);
    result.setAffectedObjectServerId(route.getServerId());
      
    return result;
  }

  public void update(Route route)
  {
    currentSession().update(route);
  }

  public Route getByOid(long _oid)
  {
    Route obj = (Route)currentSession().get(TransactionStatus.class, _oid);
    return obj;
  }

  @SuppressWarnings("unchecked")
  public Routes getAll()
  {
    Routes routes = new Routes();
    routes.setRoute(currentSession().createQuery( "FROM route ORDER BY oid DESC LIMIT 10" ).list()); 
    return routes;
  }

}
