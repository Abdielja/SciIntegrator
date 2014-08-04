/**
 * 
 */
package com.sci.integrator.controlers.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.sci.integrator.domain.core.User;

/**
 * @author Abdiel Jaramillo Ojedis
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class UserDto implements Serializable
{

  /**
   * Serial id 
   */
  private static final long serialVersionUID = 1L;

  /**
   * 
   * Properties
   * 
   */
  
  private long oid;
  private String name;
  private String region;
  
  /**
   * @return the oid
   */
  public long getOid()
  {
    return oid;
  }

  /**
   * @param oid the oid to set
   */
  public void setOid(long oid)
  {
    this.oid = oid;
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
   * @return the region
   */
  public String getRegion()
  {
    return region;
  }

  /**
   * @param region the region to set
   */
  public void setRegion(String region)
  {
    this.region = region;
  }

  /**
   * 
   * Constructors
   * 
   */
  
  public UserDto()
  {
    // TODO Auto-generated constructor stub
  }

  public UserDto(User user)
  {
    this.oid = user.getoid();
    this.name = user.getname();
    this.region = user.getRegion();
  }

}
