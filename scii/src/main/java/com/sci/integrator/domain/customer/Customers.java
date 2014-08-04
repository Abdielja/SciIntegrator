/**
 * 
 */
package com.sci.integrator.domain.customer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
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

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 * @author Abdiel Jaramillo Ojedis
 *
 */
@Entity
@Table(name="customers")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Customers implements Serializable
{

  private static final long serialVersionUID = 1L;

  // ***** Properties *****

  private long oid;
  private int count;
  private List<Customer> customer = new ArrayList<Customer>();
  
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

  @OneToMany(fetch = FetchType.EAGER)
  @JoinColumn(name="customer_list_oid", referencedColumnName="oid")
  @Cascade({CascadeType.ALL})
  public List<Customer> getCustomer()
  {
    return customer;
  }

  public void setCustomer(List<Customer> customer)
  {
    this.customer = customer;
  }
  

  public void add(Customer _customer)
  {
    this.customer.add(_customer);
  }
  
  
  // ***** Constructors *****
  
  public Customers()
  {
  }

}
