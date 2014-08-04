/**
 * 
 */
package com.sci.integrator.domain.core;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.sci.integrator.domain.customer.Customers;

/**
 * @author Abdiel Jaramillo Ojedis
 *
 */

@Entity
@Table(name="sync_data")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SyncData implements Serializable
{

  // ********** Constants **********
  
  private static final long serialVersionUID = 1L;


  // ********** Properties **********
  
  private long oid;
  private String tag;
  private Date syncDate;
  private Roles roles;
  private Customers customers;
  private Invoices pendingInvoices;
  
  // ********** Get/Set Methods **********
  
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
   * @return the tag
   */
  @Column(name="tag")
  public String getTag()
  {
    return tag;
  }

  /**
   * @param tag the tag to set
   */
  public void setTag(String tag)
  {
    this.tag = tag;
  }

  /**
   * @return the syncDate
   */
  @Column(name="sync_date")
  public Date getSyncDate()
  {
    return syncDate;
  }

  /**
   * @param syncDate the syncDate to set
   */
  public void setSyncDate(Date syncDate)
  {
    this.syncDate = syncDate;
  }

  /**
   * @return the roles
   */
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


  /**
   * @return the customers
   */
  @OneToOne
  @JoinColumn(name="customer_list_oid")
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

  
  // ********** Constructors **********
  
  /**
   * @return the pendingInvoices
   */
  public Invoices getPendingInvoices()
  {
    return pendingInvoices;
  }

  /**
   * @param pendingInvoices the pendingInvoices to set
   */
  public void setPendingInvoices(Invoices pendingInvoices)
  {
    this.pendingInvoices = pendingInvoices;
  }

  public SyncData()
  {
    // TODO Auto-generated constructor stub
  }

}
