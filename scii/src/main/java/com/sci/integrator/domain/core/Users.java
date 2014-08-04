/**
 * 
 */
package com.sci.integrator.domain.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Abdiel
 *
 */
@XmlRootElement
public class Users implements Serializable
{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  // ***** Properties *****

  private long oid;
  private int count;
  private List<User> user = new ArrayList<User>();

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
   * @return the count
   */
  public int getCount()
  {
    return count;
  }

  /**
   * @param count the count to set
   */
  public void setCount(int count)
  {
    this.count = count;
  }

  /**
   * @return the users
   */
  public List<User> getUser()
  {
    return user;
  }

  /**
   * @param users the users to set
   */
  public void setUser(List<User> user)
  {
    this.user = user;
  }


  // ***** Constructors *****

  public Users()
  {
    // TODO Auto-generated constructor stub
  }

}
