/**
 * 
 */
package com.sci.integrator.domain.quotation;

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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.sci.integrator.domain.payment.PaymentMethod;

/**
 * @author Abdiel Jaramillo Ojedis
 *
 */

@Entity
@Table(name="quotation")
@XmlRootElement(name="quotation")
@XmlAccessorType(XmlAccessType.FIELD)
public class Quotation implements Serializable
{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  // **** Public Constants *****
  
  public static int STATUS_PENDING = 0;
  public static int STATUS_FAILED = 1;
  public static int STATUS_DRAFT = 2;
  public static int STATUS_COMPLETE = 3;
  public static int STATUS_REVERSED = 6;

  // ***** Properties *****

  private long oid;
  private String serverId;
  private Date creationDate;
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
  
  private List<QuotationLine> quotationLine = new ArrayList<QuotationLine>();
  
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
  @Column(name = "status")
  public int getstatus()
  {
    return status;
  }

  public void setstatus(int status)
  {
    this.status = status;
  }

  // *** InvoiceDO Lines ***
  @OneToMany(mappedBy="quotation", fetch = FetchType.EAGER)
  @Cascade({CascadeType.SAVE_UPDATE})
  public List<QuotationLine> getquotationLine()
  {
    return quotationLine;
  }
  public void setquotationLine(List<QuotationLine> quotationLine)
  {
    this.quotationLine = quotationLine;
  }

  // ***** Constructors *****

  public Quotation()
  {
    
  }
  
  // ***** Public Methods *****
  
}
