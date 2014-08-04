/**
 * 
 */
package com.sci.integrator.domain.core;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author Abdiel Jaramillo Ojedis
 *
 */
@Entity
@Table(name="user_scii")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class User implements Serializable
{

  private static final long serialVersionUID = 1L;

  // ********** Status Constants **********
  public static final int STATUS_OPEN = 1;
  public static final int STATUS_CLOSED = 2;
  
  
  private long oid;
  private String serverId;
  private String userName;
  private String password;
  private String name;
  private String region;
  private String active;
  private int status;
  private Date lastBalanceDate;
    
  //private Invoices invoices = new Invoices();
  //private Payments payments = new Payments();
  
  // *** Oid ***
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name="oid")
  public long getoid() 
  {
    return oid;
  }
  
  public void setoid(long oid) 
  {
    this.oid = oid;
  }
  
  // *** Server Id ***
  @Column(name="server_Id")
  public String getserverId()
  {
    return serverId;
  }

  public void setserverId(String serverId)
  {
    this.serverId = serverId;
  }

  /**
   * @return the userName
   */
  @Column(name="user_name")
  public String getuserName()
  {
    return userName;
  }
  
  /**
   * @param userName the userName to set
   */

  public void setuserName(String userName)
  {
    this.userName = userName;
  }

  /**
   * @return the password
   */
  @Column(name="password")
  public String getpassword()
  {
    return password;
  }
  
  /**
   * @param userName the userName to set
   */

  public void setpassword(String password)
  {
    this.password = password;
  }

  /**
   * @return the name
   */
  @Column(name="full_name")
  public String getname()
  {
    return name;
  }
  
  /**
   * @param name the name to set
   */
  public void setname(String name)
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
   * @return the active
   */
  @Column(name="active")
  public String getActive()
  {
    return active;
  }

  /**
   * @param active the active to set
   */
  public void setActive(String active)
  {
    this.active = active;
  }

  /**
   * @return the status
   */
  @Column(name="status")
  public int getStatus()
  {
    return status;
  }

  /**
   * @param status the status to set
   */
  public void setStatus(int status)
  {
    this.status = status;
  }

  /**
   * @return the lastBalanceDate
   */
  @Column(name="last_balance_date")
  public Date getLastBalanceDate()
  {
    return lastBalanceDate;
  }

  /**
   * @param lastBalanceDate the lastBalanceDate to set
   */
  public void setLastBalanceDate(Date lastBalanceDate)
  {
    this.lastBalanceDate = lastBalanceDate;
  }
    
}
