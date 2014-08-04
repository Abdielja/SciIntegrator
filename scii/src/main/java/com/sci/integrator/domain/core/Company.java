/**
 * 
 */
package com.sci.integrator.domain.core;

import java.io.Serializable;

/**
 * @author Abdiel
 *
 */
public class Company implements Serializable
{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  private String serverId;
  private String name;
  private String ruc;

  /**
   * @return the serverId
   */
  public String getServerId()
  {
    return serverId;
  }
  /**
   * @param serverId the serverId to set
   */
  public void setServerId(String serverId)
  {
    this.serverId = serverId;
  }
  
  /**
   * @return the name
   */
  public String getName()
  {
    return name;
  }
  /**
   * @param name the name to set
   */
  public void setName(String name)
  {
    this.name = name;
  }
  
  /**
   * @return the ruc
   */
  public String getRuc()
  {
    return ruc;
  }
  /**
   * @param ruc the ruc to set
   */
  public void setRuc(String ruc)
  {
    this.ruc = ruc;
  }
    
}
