/**
 * 
 */
package com.sci.integrator.domain.order;

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

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.sci.integrator.domain.order.Order;

/**
 * @author Abdiel Jaramillo Ojedis
 *
 */
@Entity
@Table(name="orders")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Orders implements Serializable
{

  private static final long serialVersionUID = 1L;

  // ********** Properties **********

  private long oid;
  private int count;
  private List<Order> order = new ArrayList<Order>();
  
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
   * @return the orders list
   */
  @OneToMany(fetch = FetchType.LAZY)
  @JoinColumn(name="order_list_oid", referencedColumnName="oid")
  @Cascade({CascadeType.ALL})
  public List<Order> getOrder()
  {
    return order;
  }

  /**
   * @param order the order to set
   */
  public void setOrder(List<Order> order)
  {
    this.order = order;
  }

  
  /**
   *
   * Default Constructor
   */
  public Orders()
  {
    // TODO Auto-generated constructor stub
  }

  public void add(Order _order)
  {
    this.order.add(_order);
  }
  
  @Transient
  public double getTotalAmount()
  {
    double totalAmount = 0;
    
    for(int i=0; i < order.size(); i++)
    {
      totalAmount += order.get(i).gettotal();
    }
    
    return totalAmount;
  }
  
  
}
