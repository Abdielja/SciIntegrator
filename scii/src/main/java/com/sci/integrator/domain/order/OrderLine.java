/**
 * 
 */
package com.sci.integrator.domain.order;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.sci.integrator.domain.order.Order;

/**
 * @author Abdiel Jaramillo Ojedis
 *
 */

@Entity
@Table(name="sales_order_line")
@XmlRootElement(name="orderLine")
//@XmlAccessorType(XmlAccessType.FIELD)
public class OrderLine implements Serializable
{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  private long oid;
  private Order order;
  private int lineNumber;
  private String taxRateId;
  private double netAmount;
  private String productId;
  private double price;
  private int quantity;
  private int status;
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name="oid")
  public long getOid()
  {
    return oid;
  }

  public void setOid(long oid)
  {
    this.oid = oid;
  }

  @ManyToOne
  @JoinColumn(name = "sales_order_oid")
  @XmlTransient
  public Order getOrder()
  {
    return order;
  }

  public void setOrder(Order order)
  {
    this.order = order;
  }

  // *** Line number ***
  @Column(name="line_number")
  public int getlineNumber()
  {
    return lineNumber;
  }

  public void setlineNumber(int lineNumber)
  {
    this.lineNumber = lineNumber;
  }

  // *** Tax Rate Id ***
  @Column(name="tax_rate_id")
  public String gettaxRateId()
  {
    return taxRateId;
  }

  public void settaxRateId(String taxRateId)
  {
    this.taxRateId = taxRateId;
  }

  // *** Product Id ***
  @Column(name="product_id")
  public String getproductId()
  {
    return productId;
  }

  public void setproductId(String productId)
  {
    this.productId = productId;
  }

  // *** Price ***
  @Column(name="price")
  public double getprice()
  {
    return price;
  }

  public void setprice(double price)
  {
    this.price = price;
  }

  // *** Quantity ***
  @Column(name="quantity")
  public int getquantity()
  {
    return quantity;
  }

  public void setquantity(int quantity)
  {
    this.quantity = quantity;
  }

  // *** Net amount ***
  @Column(name="net_amount")
  public double getnetAmount()
  {
    return netAmount;
  }

  public void setnetAmount(double netAmount)
  {
    this.netAmount = netAmount;
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
