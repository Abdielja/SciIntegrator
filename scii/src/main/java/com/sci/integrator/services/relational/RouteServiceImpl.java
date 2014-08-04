package com.sci.integrator.services.relational;

import org.springframework.stereotype.Service;

import com.sci.integrator.domain.core.Route;
import com.sci.integrator.domain.core.Routes;
import com.sci.integrator.domain.core.SciiResult;
import com.sci.integrator.domain.core.Transaction;
import com.sci.integrator.domain.core.Transactions;
import com.sci.integrator.persistence.IRouteDao;
import com.sci.integrator.services.IRouteService;

@Service("routeService")
public class RouteServiceImpl implements IRouteService
{

  // ********** Injected Components **********
  
  private IRouteDao routeDao;
  
  public void setRouteDao(IRouteDao dao)
  {
    this.routeDao = dao;
  }
    
  
  // ********** Constructors **********
  
  public RouteServiceImpl()
  {
    // TODO Auto-generated constructor stub
  }


  // ********** IRouteService Implementation Methods **********
  
  public SciiResult save(Route route)
  {
    return routeDao.save(route);
  }

  public void update(Route route)
  {
    routeDao.update(route);
  }

  public void delete(long oid)
  {
    // TODO Auto-generated method stub
    
  }

  public Route getByOid(long _oid)
  {
    return routeDao.getByOid(_oid);
  }

  public Routes getAll()
  {
    return routeDao.getAll();
  }

}
