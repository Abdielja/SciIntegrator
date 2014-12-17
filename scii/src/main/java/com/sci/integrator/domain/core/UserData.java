/**
 * 
 */
package com.sci.integrator.domain.core;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.sci.integrator.domain.customer.Customers;
import com.sci.integrator.domain.invoice.Invoice;
import com.sci.integrator.domain.invoice.InvoiceLine;
import com.sci.integrator.domain.order.Orders;
import com.sci.integrator.domain.payment.Payments;
import com.sci.integrator.transaction.TransactionObject;

/**
 * @author Abdiel Jaramillo Ojedis
 *
 */
@Entity
@Table(name="user_data")
@PrimaryKeyJoinColumn(name="transaction_object_oid")
@XmlRootElement
public class UserData extends TransactionObject implements Serializable
{

  /**
   * 
   */
  private static final long serialVersionUID = -8159349672103838562L;
  
  private long   userOid;
  private String userServerId;
  private String name;
  private String businessPartnerId;

  private Date startDate;
  private Date endDate;
  
  private Roles roles;
  private Companies companies;
  private Customers customers;
  private Products products;
  private Invoices invoices;
  private Orders orders;
  private Payments payments;
  
  //private Warnings warnings;
  
  // ***** Get/Set Methods *****

  /**
   * @return the userOid
   */
  @Column(name="user_oid")
  public long getUserOid()
  {
    return userOid;
  }

  /**
   * @param userOid the userOid to set
   */
  public void setUserOid(long userOid)
  {
    this.userOid = userOid;
  }

  /**
   * @return the userServerId
   */
  @Column(name="user_server_id")
  public String getUserServerId()
  {
    return userServerId;
  }

  /**
   * @param userServerId the userServerId to set
   */
  public void setUserServerId(String userServerId)
  {
    this.userServerId = userServerId;
  }

  /**
   * @return the name
   */
  @Column(name="name")
  public String getName()
  {
    return name;
  }
  
  /**
   * @param name the name to set
   */
  public void setName(String name)
  {
    this.name = name;
  }
  
  /**
   * @return the businessPartnerId
   */
  @Column(name="business_partner_server_id")
  public String getBusinessPartnerId()
  {
    return businessPartnerId;
  }

  /**
   * @param businessPartnerId the businessPartnerId to set
   */
  public void setBusinessPartnerId(String businessPartnerId)
  {
    this.businessPartnerId = businessPartnerId;
  }

  /**
   * @return the startDate
   */
  @Column(name="start_date")
  public Date getStartDate()
  {
    return startDate;
  }

  /**
   * @param startDate the startDate to set
   */
  public void setStartDate(Date startDate)
  {
    this.startDate = startDate;
  }

  /**
   * @return the endDate
   */
  @Column(name="end_date")
  public Date getEndDate()
  {
    return endDate;
  }

  /**
   * @param endDate the endDate to set
   */
  public void setEndDate(Date endDate)
  {
    this.endDate = endDate;
  }

  /**
   * @return the roles
   */
  //@OneToOne
  //@JoinColumn(name="role_list_oid")
  //@Cascade({CascadeType.ALL})
  @Transient
  public Roles getRoles()
  {
    return roles;
  }

  /**
   * @param roles the roles to set
   */
  public void setRoles(Roles roles)
  {
    this.roles = roles;
  }

  // ***** Constructors *****
  
  /**
   * @return the companies
   */
  @Transient
  public Companies getCompanies()
  {
    return companies;
  }

  /**
   * @param companies the companies to set
   */
  public void setCompanies(Companies companies)
  {
    this.companies = companies;
  }

  /**
   * @return the customers
   */
  //@OneToOne
  //@JoinColumn(name="customer_list_oid")
  //@Cascade({CascadeType.ALL})
  @Transient
  public Customers getCustomers()
  {
    return customers;
  }

  /**
   * @param customers the customers to set
   */
  public void setCustomers(Customers customers)
  {
    this.customers = customers;
  }

  /**
   * @return the products
   */
  @OneToOne
  @JoinColumn(name="product_list_oid")
  @Cascade({CascadeType.ALL})
  //@Transient
  public Products getProducts()
  {
    return products;
  }

  /**
   * @param products the products to set
   */
  public void setProducts(Products products)
  {
    //products.setCount(products.getProduct().size());
    this.products = products;
  }

  /**
   * @return the invoices
   */
  @OneToOne
  @JoinColumn(name="invoice_list_oid")
  @Cascade({CascadeType.ALL})
  //@Transient
  public Invoices getInvoices()
  {
    return invoices;
  }

  /**
   * @param invoices the invoices to set
   */
  public void setInvoices(Invoices invoices)
  {
    if (invoices == null)
    {
      invoices = new Invoices();
    }
    this.invoices = invoices;
    this.invoices.setCount(invoices.getInvoice().size());
    if (this.invoices.getCount() > 0)
    {
      updateProductsTotals();
    }
  }

  /**
   * @return the orders
   */
  @OneToOne
  @JoinColumn(name="order_list_oid")
  @Cascade({CascadeType.ALL})
  //@Transient
  public Orders getOrders()
  {
    return orders;
  }

  /**
   * @param orders the orders to set
   */
  public void setOrders(Orders orders)
  {
    if (orders == null)
    {
      orders = new Orders();
    }
    this.orders = orders;
    this.orders.setCount(orders.getOrder().size());
  }
  
  /**
   * @return the payments
   */
  @OneToOne
  @JoinColumn(name="payment_list_oid")
  @Cascade({CascadeType.ALL})
  public Payments getPayments()
  {
    return payments;
  }

  /**
   * @param payments the payments to set
   */
  public void setPayments(Payments payments)
  {
    this.payments = payments;
  }

  public UserData()
  {
    roles = new Roles();
    companies = new Companies();
    customers = new Customers();
    products = new Products();
    invoices = new Invoices();
    orders = new Orders();
    payments = new Payments();
    
    //warnings = new Warnings();
  }
  
  // ***** Public Methods *****
  
  private void updateProductsTotals()
  {
    
    products.clearAccumulators();
    
    for(int i = 0; i < invoices.getCount(); i++)
    {
      
      Invoice invoice = invoices.getInvoice().get(i);
      
      if (invoice.getstatus() != Invoice.STATUS_REVERSED)
      {
        for(int j = 0; j < invoice.getinvoiceLine().size(); j++)
        {
          InvoiceLine il = invoice.getinvoiceLine().get(j);
          String productId = il.getproductId();
          Product product = products.getByServerId(productId);
          if(product != null)
          {
            product.setQuantityInvoiced(product.getQuantityInvoiced() + il.getquantity());
            product.setAmountInvoiced(product.getAmountInvoiced() + il.getnetAmount());
          }
          else
          {
            System.out.println("\nERROR - UserData.updateProductsTotals(): Product with ServerId " + productId + " not found !!");
          }
        }
      }
      
    }
  }

}
