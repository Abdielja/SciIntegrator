/**
 * 
 */
package com.sci.integrator.domain.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.sci.integrator.domain.invoice.Invoice;

/**
 * @author Abdiel Jaramillo Ojedis
 *
 */
@Entity
@Table(name="invoices")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Invoices implements Serializable
{

  private static final long serialVersionUID = 1L;

  // ********** Properties **********

  private long oid;
  private int count;
  private List<Invoice> invoice = new ArrayList<Invoice>();
  
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
   * @return the count
   */
  @Column(name="count")
  public int getCount()
  {
    return count;
  }

  /**
   * @param count the count to set
   */
  public void setCount(int count)
  {
    this.count = count;
  }

  /**
   * @return the invoices list
   */
  @OneToMany(fetch = FetchType.LAZY)
  @JoinColumn(name="invoice_list_oid", referencedColumnName="oid")
  @Cascade({CascadeType.ALL})
  public List<Invoice> getInvoice()
  {
    return invoice;
  }

  public void setInvoice(List<Invoice> invoice)
  {
    this.invoice = invoice;
  }

  public void add(Invoice _invoice)
  {
    this.invoice.add(_invoice);
  }
  
  // ********** Constructors **********
  

  public Invoices()
  {
    // TODO Auto-generated constructor stub
  }

  @Transient
  public double getTotalAmount()
  {
    double totalAmount = 0;
    
    for(int i=0; i < invoice.size(); i++)
    {
      totalAmount += invoice.get(i).gettotal();
    }
    
    return totalAmount;
  }
  
}
