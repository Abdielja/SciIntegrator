/**
 * 
 */
package com.sci.integrator.domain.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Abdiel Jaramillo Ojedis
 *
 */

@Entity
@Table(name="up_product")
public class UserPeriodProduct
{

  /**
   * 
   * Properties
   * 
   */
  
  private long oid;
  private UserPeriodData userPeriodData;
  private Product product;
  private int initialQuantity;
  private int returnedQuantity;
  
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
   * @return the userPeriodData
   */
  @ManyToOne
  @JoinColumn(name = "up_data_oid")
  public UserPeriodData getUserPeriodData()
  {
    return userPeriodData;
  }

  /**
   * @param userPeriodData the userPeriodData to set
   */
  public void setUserPeriodData(UserPeriodData userPeriodData)
  {
    this.userPeriodData = userPeriodData;
  }

  /**
   * @return the product
   */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_oid")
  public Product getProduct()
  {
    return product;
  }

  /**
   * @param product the product to set
   */
  public void setProduct(Product product)
  {
    this.product = product;
  }

  /**
   * @return the initialQuantity
   */
  @Column(name="initial_quantity")  
  public int getInitialQuantity()
  {
    return initialQuantity;
  }

  /**
   * @param initialQuantity the initialQuantity to set
   */
  public void setInitialQuantity(int initialQuantity)
  {
    this.initialQuantity = initialQuantity;
  }

  /**
   * @return the returnedQuantity
   */
  @Column(name="returned_quantity")  
  public int getReturnedQuantity()
  {
    return returnedQuantity;
  }

  /**
   * @param returnedQuantity the returnedQuantity to set
   */
  public void setReturnedQuantity(int returnedQuantity)
  {
    this.returnedQuantity = returnedQuantity;
  }

  /**
   * 
   * Constructors
   * 
   */

  public UserPeriodProduct()
  {
    // TODO Auto-generated constructor stub
  }

}
