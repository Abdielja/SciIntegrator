/**
 * 
 */
package com.sci.integrator.domain.customer;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.sci.integrator.domain.core.User;

/**
 * @author Abdiel Jaramillo Ojedis
 *
 */

@Entity
@Table(name="customer_extra")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerExtra implements Serializable
{

  /**
   * Constants
   */
  
  private static final long serialVersionUID = 1L;
  
  
  /**
   * Properties
   */
  
  private long oid;
  private String customerServerId;
  private String organization;
  private String commercialName;
  private String contactName;
  private String contactCellPhone;
  private String contactEmail;
  private String address;
  private String landLinePhone;
  private Date creationDate;
  private User createdBy;
  private String latitud;
  private String longitud;
  
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
   * @return the customerServerId
   */
  @Column(name="customer_server_id")
  public String getCustomerServerId()
  {
    return customerServerId;
  }

  /**
   * @param customerServerId the customerServerId to set
   */
  public void setCustomerServerId(String customerServerId)
  {
    this.customerServerId = customerServerId;
  }

  /**
   * @return the organizationId
   */
  @Column(name="organization")
  public String getOrganization()
  {
    return organization;
  }

  /**
   * @param organizationId the organizationId to set
   */
  public void setOrganization(String organization)
  {
    this.organization = organization;
  }

  /**
   * @return the commercialName
   */
  @Column(name="commercial_name")
  public String getCommercialName()
  {
    return commercialName;
  }

  /**
   * @param commercialName the commercialName to set
   */
  public void setCommercialName(String commercialName)
  {
    this.commercialName = commercialName;
  }

  /**
   * @return the contactName
   */
  @Column(name="contact_name")
  public String getContactName()
  {
    return contactName;
  }
  
  /**
   * @param contactName the contactName to set
   */
  public void setContactName(String contactName)
  {
    this.contactName = contactName;
  }

  /**
   * @return the contactCellPhone
   */
  @Column(name="contact_cellphone")
  public String getContactCellPhone()
  {
    return contactCellPhone;
  }

  /**
   * @param contactCellPhone the contactCellPhone to set
   */
  public void setContactCellPhone(String contactCellPhone)
  {
    this.contactCellPhone = contactCellPhone;
  }
  
  /**
   * @return the contactEmail
   */
  @Column(name="contact_email")
  public String getContactEmail()
  {
    return contactEmail;
  }

  /**
   * @param contactEmail the contactEmail to set
   */
  public void setContactEmail(String contactEmail)
  {
    this.contactEmail = contactEmail;
  }

  /**
   * @return the address
   */
  @Column(name="address")
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

  /**
   * @return the landLinePhone
   */
  @Column(name="landline_phone")
  public String getLandLinePhone()
  {
    return landLinePhone;
  }

  /**
   * @param landLinePhone the landLinePhone to set
   */
  public void setLandLinePhone(String landLinePhone)
  {
    this.landLinePhone = landLinePhone;
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
  @OneToOne(targetEntity=User.class)
  @JoinColumn(name="created_by")
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
   * @return the latitud
   */
  @Column(name="latitud")
  public String getLatitud()
  {
    return latitud;
  }

  /**
   * @param latitud the latitud to set
   */
  public void setLatitud(String latitud)
  {
    this.latitud = latitud;
  }

  /**
   * @return the longitud
   */
  @Column(name="longitud")
  public String getLongitud()
  {
    return longitud;
  }

  /**
   * @param longitud the longitud to set
   */
  public void setLongitud(String longitud)
  {
    this.longitud = longitud;
  }


  
  /**
   * Constructors
   */
  public CustomerExtra()
  {
    
  }

  
}
