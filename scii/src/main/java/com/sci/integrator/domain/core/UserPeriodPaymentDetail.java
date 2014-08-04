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

import com.sci.integrator.domain.payment.PaymentMethod;

/**
 * @author Abdiel Jaramillo Ojedis
 *
 */

@Entity
@Table(name="up_payment_detail")
public class UserPeriodPaymentDetail
{

  /**
   * 
   * Prpoerties
   * 
   */
  
  private long oid;
  private UserPeriodData userPeriodData;
  private PaymentMethod paymentMethod;
  private double amount;
  
  
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
   * @return the paymentMethod
   */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "payment_method_oid")
  public PaymentMethod getPaymentMethod()
  {
    return paymentMethod;
  }


  /**
   * @param paymentMethod the paymentMethod to set
   */
  public void setPaymentMethod(PaymentMethod paymentMethod)
  {
    this.paymentMethod = paymentMethod;
  }


  /**
   * @return the amount
   */
  public double getAmount()
  {
    return amount;
  }


  /**
   * @param amount the amount to set
   */
  public void setAmount(double amount)
  {
    this.amount = amount;
  }


  /**
   * 
   * Constructors
   * 
   */
  
  public UserPeriodPaymentDetail()
  {
    // TODO Auto-generated constructor stub
  }

}
