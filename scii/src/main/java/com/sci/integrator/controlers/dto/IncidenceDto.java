/**
 * 
 */
package com.sci.integrator.controlers.dto;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.sci.integrator.domain.core.User;
import com.sci.integrator.domain.incidence.Incidence;
import com.sci.integrator.domain.incidence.IncidenceType;

/**
 * @author Abdiel
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class IncidenceDto implements Serializable
{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  // ***** Properties *****
  
  private long oid;
  private Date creationDate;
  private String userName;
  private String customerName;
  private String incidenceType;
  private String description;
  

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
   * @return the creationDate
   */
  public Date getCreationDate()
  {
    return creationDate;
  }

  /**
   * @param creationDate the creationDate to set
   */
  public void setCreationDate(Date creationDate)
  {
    this.creationDate = creationDate;
  }

  /**
   * @return the userName
   */
  public String getUserName()
  {
    return userName;
  }


  /**
   * @param userName the userName to set
   */
  public void setUserName(String userName)
  {
    this.userName = userName;
  }


  /**
   * @return the customerName
   */
  public String getCustomerName()
  {
    return customerName;
  }


  /**
   * @param customerName the customerName to set
   */
  public void setCustomerName(String customerName)
  {
    this.customerName = customerName;
  }


  /**
   * @return the incidenceType
   */
  public String getIncidenceType()
  {
    return incidenceType;
  }


  /**
   * @param incidenceType the incidenceType to set
   */
  public void setIncidenceType(String incidenceType)
  {
    this.incidenceType = incidenceType;
  }


  /**
   * @return the description
   */
  public String getDescription()
  {
    return description;
  }


  /**
   * @param description the description to set
   */
  public void setDescription(String description)
  {
    this.description = description;
  }


  /**
   * 
   */
  public IncidenceDto()
  {
    // TODO Auto-generated constructor stub
  }

  public IncidenceDto(Incidence incidence)
  {
    this.oid = incidence.getOid();
    this.creationDate = incidence.getCreationDate();
    this.userName = incidence.getCreatedBy().getname();
    this.customerName = incidence.getCustomerName();
    this.incidenceType = incidence.getIncidenceType().getDescription();
    this.description = incidence.getDescription();
  }
  
}
