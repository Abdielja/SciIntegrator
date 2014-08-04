package com.sci.integrator.persistence;

import com.sci.integrator.domain.core.Route;
import com.sci.integrator.domain.core.Routes;
import com.sci.integrator.domain.core.SciiResult;

public interface IRouteDao
{

  SciiResult save(Route route);
  void update(Route route);
  Route getByOid(long _oid);  
  Routes getAll();

}
