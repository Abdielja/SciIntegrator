/**
 * 
 */
package com.sci.integrator.domain.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Abdiel Jaramillo Ojedis
 *
 */
@XmlRootElement(name = "Routes")
public class Routes implements Serializable
{

  
  private static final long serialVersionUID = 1L;
  
  private List<Route> route = new ArrayList<Route>();

  public List<Route> getRoute()
  {
    return route;
  }

  public void setRoute(List<Route> route)
  {
    this.route = route;
  }
  
  public void add(Route _route)
  {
    this.route.add(_route);
  }

  
}
