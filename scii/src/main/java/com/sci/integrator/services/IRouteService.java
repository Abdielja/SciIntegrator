package com.sci.integrator.services;

import com.sci.integrator.domain.core.Route;
import com.sci.integrator.domain.core.Routes;
import com.sci.integrator.domain.core.SciiResult;

public interface IRouteService
{

  SciiResult save(Route route);
  void update(Route route);
  void delete(long oid);    
  Route getByOid(long oid);    
  Routes getAll();

}
