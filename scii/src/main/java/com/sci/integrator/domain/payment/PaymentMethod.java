/**
 * 
 */
package com.sci.integrator.domain.payment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Abdiel Jaramillo Ojedis
 *
 */

@Entity
@Table(name="payment_method")
@XmlRootElement(name="paymentMethod")
@XmlAccessorType(XmlAccessType.FIELD)
public class PaymentMethod
{

  // ***** Properties *****

  private long oid;
  private String serverId;
  private String description;
  
  @Id
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
  @Column(name="server_id")
  public String getserverId()
  {
    return serverId;
  }

  public void setserverId(String serverId)
  {
    this.serverId = serverId;
  }

  // *** Description ***
  @Column(name="description")
  public String getdescription()
  {
    return description;
  }

  public void setdescription(String description)
  {
    this.description = description;
  }

}
