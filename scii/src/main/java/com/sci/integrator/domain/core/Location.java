/**
 * 
 */
package com.sci.integrator.domain.core;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Abdiel Jaramillo Ojedis
 *
 */
@XmlRootElement
public class Location implements Serializable
{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  // ***** Properties *****

  private String serverId;
  private String address;
    
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
   * @return the address
   */
  public String getAddress()
  {
    return address;
  }


  /**
   * @param address the address to set
   */
  public void setAddress(String address)
  {
    this.address = address;
  }


  // ***** Constructors *****
  
  public Location()
  {
    // TODO Auto-generated constructor stub
  }

}
