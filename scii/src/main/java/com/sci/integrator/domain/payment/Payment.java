/**
 * 
 */

package com.sci.integrator.domain.payment;

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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.sci.integrator.domain.core.User;
import com.sci.integrator.domain.invoice.Invoice;

/**
 * @author Abdiel Jaramillo Ojedis
 *
 */

@Entity
@Table(name="payment")
public class Payment implements Serializable
{

  private static final long serialVersionUID = 1L;

  // ***** Public Constants *****
  
  public static int STATUS_PENDING = 0;
  public static int STATUS_FAILED = 1;
  public static int STATUS_PROCESSED = 2;
  public static int STATUS_REVERSED = 6;
  
  // ***** Properties *****

  private long oid;
  private String serverId;
  private Invoice invoice;
  private Date creationDate;
  private User createdBy;
  private String clientId;
  private String organizationId;
  private String customerId;
  private long invoiceServerOid;
  
  private PaymentMethod paymentMethod;
  private double amount;
  private int status;
  
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
  @Column(name="server_id")
  public String getserverId()
  {
    return serverId;
  }

  public void setserverId(String serverId)
  {
    this.serverId = serverId;
  }
 
  @Column(name="invoice_server_oid", insertable=true)
  public long getinvoiceServerOid() 
  {
    return invoiceServerOid;
  }

  public void setinvoiceServerOid(long oid) 
  {
    this.invoiceServerOid = oid;
  }
  
  // *** Creation Date ***
  @Column(name="creation_date")
  public Date getcreationDate() 
  {
    return creationDate;
  }

  public void setcreationDate(Date creationDate) 
  {
    this.creationDate = creationDate;
  }
     
  
  // *** CreatedBy ***
  @OneToOne(targetEntity=User.class)
  @JoinColumn(name="created_by")
  public User getCreatedBy()
  {
    return createdBy;
  }

  /**
   * @param user the createdBy to set
   */
  public void setCreatedBy(User user)
  {
    this.createdBy = user;
  }

  // *** InvoiceDO  ***
  @ManyToOne(targetEntity=Invoice.class)
  @JoinColumn(name="invoice_server_oid", insertable=false, updatable=false)
  //@XmlTransient
  public Invoice getinvoice()
  {
    return invoice;
  }

  public void setinvoice(Invoice invoice)
  {
    this.invoice = invoice;
  }

  // *** ClientId ***
  @Column(name = "client_id")
  public String getclientId()
  {
    return clientId;
  }

  public void setclientId(String clientId)
  {
    this.clientId = clientId;
  }

  // *** OrganizationId ***  
  @Column(name = "organization_id")
  public String getorganizationId()
  {
    return organizationId;
  }

  public void setorganizationId(String organizationId)
  {
    this.organizationId = organizationId;
  }

  // *** CustomerId ***
  @Column(name="customer_id")
  public String getcustomerId()
  {
    return customerId;
  }

  public void setcustomerId(String customer)
  {
    this.customerId = customer;
  }

  // *** PaymentMethod ***
  @OneToOne(targetEntity=PaymentMethod.class)
  @JoinColumn(name = "payment_method_oid")
  public PaymentMethod getpaymentMethod()
  {
    return paymentMethod;
  }

  public void setpaymentMethod(PaymentMethod paymentMethod)
  {
    this.paymentMethod = paymentMethod;
  }

  // *** Amount ***
  @Column(name = "amount")
  public double getamount()
  {
    return amount;
  }

  public void setamount(double amount)
  {
    this.amount = amount;
  }

  // *** Status ***
  @Column(name="status")
  public int getstatus()
  {
    return status;
  }

  public void setstatus(int status)
  {
    this.status = status;
  }

}
