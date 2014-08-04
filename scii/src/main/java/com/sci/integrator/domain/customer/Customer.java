/**
 * 
 */
package com.sci.integrator.domain.customer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.sci.integrator.domain.core.Location;

/**
 * @author Abdiel Jaramillo Ojedis
 *
 */
@Entity
@Table(name="customer")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Customer implements Serializable
{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  // ***** Properties *****
  
  private long  oid;
  private Customers customerList;
  private String serverId;
  private String name;
  private String clientId;
  private String organizationId;
  private String paymentMethodId;
  private String paymentTermsId;
  private String priceListId;
  private String upcean;
  private String dndRutaOrg;
  
  private List<Location> locations = new ArrayList<Location>();
  
  // ***** get/set methods *****

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


  //@ManyToOne
  //@JoinColumn(name = "customer_list_oid")
  @Transient
  @XmlTransient
  public Customers getCustomerList()
  {
    return customerList;
  }

  public void setCustomerList(Customers customerList)
  {
    this.customerList = customerList;
  }
  
  // *** Server Id ***
  @Column(name="server_id")
  public String getServerId()
  {
    return serverId;
  }

  public void setServerId(String serverId)
  {
    this.serverId = serverId;
  }
  
  // *** Name ***
  @Column(name="name")
  public String getName()
  {
    return name;
  }
  public void setName(String name)
  {
    this.name = name;
  }

  /**
   * @return the clientId
   */
  @Column(name="client_id")
  public String getClientId()
  {
    return clientId;
  }

  /**
   * @param clientId the clientId to set
   */
  public void setClientId(String clientId)
  {
    this.clientId = clientId;
  }

  /**
   * @return the organizationId
   */
  @Column(name="organization_id")
  public String getOrganizationId()
  {
    return organizationId;
  }

  /**
   * @param organizationId the organizationId to set
   */
  public void setOrganizationId(String organizationId)
  {
    this.organizationId = organizationId;
  }

  /**
   * @return the paymentMethodId
   */
  @Column(name="payment_method_id")
  public String getPaymentMethodId()
  {
    return paymentMethodId;
  }

  /**
   * @param paymentMethodId the paymentMethodId to set
   */
  public void setPaymentMethodId(String paymentMethodId)
  {
    this.paymentMethodId = paymentMethodId;
  }

  /**
   * @return the paymentTermsId
   */
  @Column(name="payment_terms_id")
  public String getPaymentTermsId()
  {
    return paymentTermsId;
  }

  /**
   * @param paymentTermsId the paymentTermsId to set
   */
  public void setPaymentTermsId(String paymentTermsId)
  {
    this.paymentTermsId = paymentTermsId;
  }

  /**
   * @return the priceListId
   */
  @Column(name="price_list_id")
  public String getPriceListId()
  {
    return priceListId;
  }

  /**
   * @param priceListId the priceListId to set
   */
  public void setPriceListId(String priceListId)
  {
    this.priceListId = priceListId;
  }

  /**
   * @return the upcean
   */
  @Column(name="upcean")
  public String getUpcean()
  {
    return upcean;
  }

  /**
   * @param upcean the upcean to set
   */
  public void setUpcean(String upcean)
  {
    this.upcean = upcean;
  }

  /**
   * @return the dndRutaOrg
   */
  @Column(name="dnd_ruta_org")
  public String getDndRutaOrg()
  {
    return dndRutaOrg;
  }

  /**
   * @param dndRutaOrg the dndRutaOrg to set
   */
  public void setDndRutaOrg(String dndRutaOrg)
  {
    this.dndRutaOrg = dndRutaOrg;
  }

  /**
   * @return the locations
   */
  @Transient
  public List<Location> getLocations()
  {
    return locations;
  }

  /**
   * @param locations the locations to set
   */
  public void setLocations(List<Location> locations)
  {
    this.locations = locations;
  }
  
}
