/**
 * 
 */
package com.sci.integrator.domain.incidence;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.sci.integrator.domain.core.User;
import com.sci.integrator.domain.customer.Customer;

/**
 * @author Abdiel Jaramillo Ojedis
 *
 */
@Entity
@Table(name="incidence")
@XmlRootElement(name="incidence")
@XmlAccessorType(XmlAccessType.FIELD)
public class Incidence implements Serializable
{

  // ***** Constants *****
  
  private static final long serialVersionUID = 1L;

  
  // ***** Properties *****
  
  private long oid;
  private Date creationDate;
  private User createdBy;
  private String customerName;
  private IncidenceType incidenceType;
  private String description;
  
  /**
   * @return the oid
   */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name="oid")
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
  @Column(name="creation_date")
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
   * @return the createdBy
   */
  // *** Created By ***
  @OneToOne(targetEntity=User.class)
  @JoinColumn(name="user_oid")
  public User getCreatedBy()
  {
    return createdBy;
  }


  /**
   * @param createdBy the createdBy to set
   */
  public void setCreatedBy(User createdBy)
  {
    this.createdBy = createdBy;
  }


  /**
   * @return the customer
   */
  @Column(name = "customer_name")
  public String getCustomerName()
  {
    return customerName;
  }

  /**
   * @param customer the customer to set
   */
  public void setCustomerName(String customerName)
  {
    this.customerName = customerName;
  }


  /**
   * @return the incidenceType
   */
  @OneToOne(targetEntity=IncidenceType.class)
  @JoinColumn(name="incidence_type_oid")
  public IncidenceType getIncidenceType()
  {
    return incidenceType;
  }


  /**
   * @param incidenceType the incidenceType to set
   */
  public void setIncidenceType(IncidenceType incidenceType)
  {
    this.incidenceType = incidenceType;
  }


  /**
   * @return the description
   */
  @Column(name="description")
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


  // ***** Constructors *****
  
  public Incidence()
  {
    // TODO Auto-generated constructor stub
  }

}
