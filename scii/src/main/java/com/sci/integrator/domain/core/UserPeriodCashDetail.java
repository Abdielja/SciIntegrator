/**
 * 
 */
package com.sci.integrator.domain.core;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name="up_cash_detail")
public class UserPeriodCashDetail
{

  private long oid;
  private UserPeriodData userPeriodData;
  private double value;
  private double quantity;
  private String denomination; 

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
   * @return the value
   */
  @Column(name="value")  
  public double getValue()
  {
    return value;
  }


  /**
   * @param value the value to set
   */
  public void setValue(double value)
  {
    this.value = value;
  }


  /**
   * @return the quantity
   */
  @Column(name="quantity")  
  public double getQuantity()
  {
    return quantity;
  }


  /**
   * @param quantity the quantity to set
   */
  public void setQuantity(double quantity)
  {
    this.quantity = quantity;
  }


  /**
   * @return the denomination
   */
  @Column(name="denomination")  
  public String getDenomination()
  {
    return denomination;
  }


  /**
   * @param denomination the denomination to set
   */
  public void setDenomination(String denomination)
  {
    this.denomination = denomination;
  }


  /**
   *
   * Constructors
   * 
   */

  public UserPeriodCashDetail()
  {
    // TODO Auto-generated constructor stub
  }

}
