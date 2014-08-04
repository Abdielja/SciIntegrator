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
@Table(name="products")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Products implements Serializable
{
  

  // ********** Constants **********
  
  private static final long serialVersionUID = 1L;
  

  // ********** Properties **********
  
  private long oid;
  private int count;

  private List<Product> product = new ArrayList<Product>();
  
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
   * @return the product
   */
  @OneToMany(fetch = FetchType.EAGER)
  @JoinColumn(name="product_list_oid", referencedColumnName="oid")
  @Cascade({CascadeType.ALL})
  public List<Product> getProduct()
  {
    return product;
  }

  /**
   * @param product the product to set
   */
  public void setProduct(List<Product> product)
  {
    this.product = product;
  }

  public void add(Product _product)
  {
    this.product.add(_product);
  }
  
  
  // ********** Constructors **********
  
  public Products()
  {
    // TODO Auto-generated constructor stub
  }

  public Product getByServerId(String serverId)
  {

    Product p = null;
    
    for (int i = 0; i < product.size(); i++)
    {
      if (product.get(i).getServerId().equals(serverId))
      {
        p = product.get(i);
      }
    }
    
    return p;

  }
  
  public void clearAccumulators()
  {
    for(int i = 0; i < product.size(); i++)
    {
      product.get(i).setAmountInvoiced(0);
      product.get(i).setQuantityInvoiced(0);
    }
  }
}
