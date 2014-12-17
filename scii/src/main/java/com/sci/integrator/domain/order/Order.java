/**
 * 
 */
package com.sci.integrator.domain.order;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.sci.integrator.domain.core.User;
import com.sci.integrator.domain.order.OrderLine;
import com.sci.integrator.domain.payment.PaymentMethod;
import com.sci.integrator.provider.openbravo.transaction.TransactionInvoice;
import com.sci.integrator.provider.openbravo.transaction.TransactionOrder;

/**
 * @author Abdiel Jaramillo Ojedis
 *
 */

@Entity
@Table(name="sales_order")
@XmlRootElement(name="order")
//@XmlAccessorType(XmlAccessType.FIELD)
public class Order implements Serializable
{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  // ***** Public Constants *****
  
  public static int STATUS_PENDING = 0;
  public static int STATUS_FAILED = 1;
  public static int STATUS_DRAFT = 2;
  public static int STATUS_BOOKED = 3;
  public static int STATUS_SHIPED = 4;
  public static int STATUS_PAID = 5;
  public static int STATUS_REVERSED = 6;
  public static int STATUS_LOADED_FROM_ERP = 7;

  // ***** Properties *****

  private long oid;
  private TransactionOrder transaction;
  private String serverId;
  private Date creationDate;
  private User createdBy;
  private String clientId;
  private String organizationId;
  private String customerId;
  private String customerAddressId;
  private PaymentMethod paymentMethod;
  private String paymentTermsId;
  private String priceListId;
  private double subTotal;
  private double total;
  private int status;
  
  private List<OrderLine> orderLine = new ArrayList<OrderLine>();
  
  @Id
  //@GeneratedValue(strategy = GenerationType.AUTO)
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

  @ManyToOne
  @JoinColumn(name = "transaction_oid")
  @XmlTransient
  public TransactionOrder getTransaction()
  {
    return transaction;
  }

  public void setTransaction(TransactionOrder transaction)
  {
    this.transaction = transaction;
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
  @JoinColumn(name = "user_oid")
  public User getCreatedBy()
  {
    return createdBy;
  }

  public void setCreatedBy(User createdBy)
  {
    this.createdBy = createdBy;
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

  // *** CustomerAddressId ***
  @Column(name = "customer_address_id")
  public String getcustomerAddressId()
  {
    return customerAddressId;
  }

  public void setcustomerAddressId(String customerAddressId)
  {
    this.customerAddressId = customerAddressId;
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

  // *** PaymentTermsId ***
  @Column(name = "payment_terms_id")
  public String getpaymentTermsId()
  {
    return paymentTermsId;
  }
  public void setpaymentTermsId(String paymentTermsId)
  {
    this.paymentTermsId = paymentTermsId;
  }
 
  // *** PriceListId ***
  @Column(name = "price_list_id")
  public String getpriceListId()
  {
    return priceListId;
  }
  public void setpriceListId(String priceListId)
  {
    this.priceListId = priceListId;
  }
  
  // *** Sub-Total  ***
  @Column(name = "sub_total")
  public double getsubTotal()
  {
    return subTotal;
  }

  public void setsubTotal(double subTotal)
  {
    this.subTotal = subTotal;
  }

  // *** Total ***
  @Column(name = "total")
  public double gettotal()
  {
    return total;
  }

  public void settotal(double total)
  {
    this.total = total;
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

  // *** Order Lines ***
  @OneToMany(mappedBy="order", fetch = FetchType.EAGER)
  @Cascade({CascadeType.SAVE_UPDATE})
  public List<OrderLine> getorderLine()
  {
    return orderLine;
  }
  
  public void setorderLine(List<OrderLine> orderLine)
  {
    this.orderLine = orderLine;
  }

  // ***** Constructors *****

  public Order()
  {
    
  }


  // ***** Public Methods *****
  

}
