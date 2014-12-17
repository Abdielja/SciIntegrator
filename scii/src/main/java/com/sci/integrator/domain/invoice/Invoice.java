/**
 * 
 */
package com.sci.integrator.domain.invoice;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.Parameter;

import com.sci.integrator.domain.core.User;
import com.sci.integrator.domain.payment.PaymentMethod;
import com.sci.integrator.provider.openbravo.transaction.TransactionInvoice;

/**
 * @author Abdiel Jaramillo Ojedis
 *
 */

@Entity
@Table(name="invoice")
public class Invoice implements Serializable
{
 
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  // ***** Public Constants *****
  
  public static int STATUS_PENDING = 0;
  public static int STATUS_FAILED = 1;
  public static int STATUS_DRAFT = 2;
  public static int STATUS_COMPLETE = 3;
  public static int STATUS_SHIPED = 4;
  public static int STATUS_PAID = 5;
  public static int STATUS_REVERSED = 6;
  public static int STATUS_LOADED_FROM_ERP = 7;

  // ***** Properties *****

  private long oid;
  private TransactionInvoice transaction;
  private String serverId;
  private String documentNumber;
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
  
  private List<InvoiceLine> invoiceLine = new ArrayList<InvoiceLine>();
  
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

  @ManyToOne
  @JoinColumn(name = "transaction_oid")
  @XmlTransient
  public TransactionInvoice getTransaction()
  {
    return transaction;
  }

  public void setTransaction(TransactionInvoice transaction)
  {
    this.transaction = transaction;
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

  /**
   * @return the documentNumber
   */
  @Column(name="document_number")
  public String getdocumentNumber()
  {
    return documentNumber;
  }

  /**
   * @param documentNumber the documentNumber to set
   */
  public void setdocumentNumber(String documentNumber)
  {
    this.documentNumber = documentNumber;
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

  /**
   * @param createdBy the createdBy to set
   */
  public void setCreatedBy(User created_by)
  {
    this.createdBy = created_by;
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

  // *** Invoice Lines ***
  @OneToMany(mappedBy="invoice", fetch = FetchType.EAGER)
  @Cascade({CascadeType.SAVE_UPDATE})
  public List<InvoiceLine> getinvoiceLine()
  {
    return invoiceLine;
  }
  public void setinvoiceLine(List<InvoiceLine> invoiceLine)
  {
    this.invoiceLine = invoiceLine;
  }

  // ***** Constructors *****

  public Invoice()
  {
    
  }
  
  // ***** Public Methods *****
  
  @Transient
  public boolean isCredit()
  {
    /* 
     * Esto de ser cambiado para que evalue realmente si el PaymentTerms 
     * es a credito o contado.
     */
    return false;
  }
  
  public String toXML()
  {
    StringBuilder sb = new StringBuilder();
    
    // *** Invoice Header ***
    
    sb.append("<Invoice>");
    
    sb.append("<oid>" + this.oid + "</oid>");
    sb.append("<clientId>" + this.clientId + "</clientId>");
    sb.append("<createdById>" + this.createdBy.getserverId() + "</createdById>");
    sb.append("<creationDate>" + this.creationDate + "</creationDate>");
    sb.append("<customerAddressId>" + this.customerAddressId + "</customerAddressId>");
    sb.append("<customerId>" + this.customerId + "</customerId>");
    sb.append("<documentNumber>" + this.documentNumber + "</documentNumber>");
    sb.append("<organizationId>" + this.organizationId + "</organizationId>");
    sb.append("<paymentMethodId>" + this.paymentMethod.getserverId() + "</paymentMethodId>");
    sb.append("<paymentTermsId>" + this.paymentTermsId + "</paymentTermsId>");
    sb.append("<priceListId>" + this.getpriceListId() + "</priceListId>");
    sb.append("<serverId>" + this.serverId + "</serverId>");
    sb.append("<status>" + this.status + "</status>");
    sb.append("<subTotal>" + this.subTotal + "</subTotal>");
    sb.append("<total>" + this.total + "</total>");
    sb.append("<currencyId>100</currencyId>");
    sb.append("<autoProcess>true</autoProcess>");

    // *** Invoice Lines ***
    
    int numLines = invoiceLine.size();
    
    if (numLines == 0)
    {
      System.out.println("ERROR: Invoice.toXML() - Invoice " + this.oid + " does not have any lines.");
      return null;
    }
      
    sb.append("<invoiceLines>");

    for(int i = 0; i < numLines; i++)
    {     
      InvoiceLine il = this.invoiceLine.get(i);
      sb.append(il.toXML()); 
    }
       
    sb.append("</invoiceLines>");
    
    sb.append("</Invoice>");

    return sb.toString();
  
  }
  
}
