/**
 * 
 */
package com.sci.integrator.domain.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 * @author Abdiel Jaramillo Ojedis
 *
 */
@Entity
@Table(name="up_data")
public class UserPeriodData implements Serializable
{

  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  
  /**
   * 
   * Properties
   * 
   */

  private long oid;
  private List<UserPeriodProduct> upProducts;
  private List<UserPeriodPaymentDetail> upPaymentDetails;
  private List<UserPeriodCashDetail> upCashDetails;
  private User user;
  private Date creationDate;
  private double totalInvoiced;
  private double totalCollected;
  private double totalCredit;
  private double totalMissing;
  
  
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
   * @return the upProducts
   */
  @OneToMany(mappedBy = "userPeriodData")
  @Cascade({ CascadeType.SAVE_UPDATE })
  public List<UserPeriodProduct> getUpProducts()
  {
    return upProducts;
  }


  /**
   * @param upProducts the upProducts to set
   */
  public void setUpProducts(List<UserPeriodProduct> upProducts)
  {
    this.upProducts = upProducts;
  }


  /**
   * @return the upPaymentDetails
   */
  @OneToMany(mappedBy = "userPeriodData")
  @Cascade({ CascadeType.SAVE_UPDATE })
  public List<UserPeriodPaymentDetail> getUpPaymentDetails()
  {
    return upPaymentDetails;
  }


  /**
   * @param upPaymentDetails the upPaymentDetails to set
   */
  public void setUpPaymentDetails(List<UserPeriodPaymentDetail> upPaymentDetails)
  {
    this.upPaymentDetails = upPaymentDetails;
  }


  /**
   * @return the upCashDetails
   */
  @OneToMany(mappedBy = "userPeriodData")
  @Cascade({ CascadeType.SAVE_UPDATE })
  public List<UserPeriodCashDetail> getUpCashDetails()
  {
    return upCashDetails;
  }


  /**
   * @param upCashDetails the upCashDetails to set
   */
  public void setUpCashDetails(List<UserPeriodCashDetail> upCashDetails)
  {
    this.upCashDetails = upCashDetails;
  }


  /**
   * @return the user
   */
  @OneToOne(targetEntity=User.class)
  @JoinColumn(name = "user_oid")
  public User getUser()
  {
    return user;
  }


  /**
   * @param user the user to set
   */
  public void setUser(User user)
  {
    this.user = user;
  }


  /**
   * @return the creationDate
   */
  @Column(name="creation_date")
  public Date getCreationDate()
  {
    return creationDate;
  }


  /**
   * @param creationDate the creationDate to set
   */
  public void setCreationDate(Date creationDate)
  {
    this.creationDate = creationDate;
  }


  /**
   * @return the totalInvoiced
   */
  @Column(name="total_invoiced")
  public double getTotalInvoiced()
  {
    return totalInvoiced;
  }


  /**
   * @param totalInvoiced the totalInvoiced to set
   */
  public void setTotalInvoiced(double totalInvoiced)
  {
    this.totalInvoiced = totalInvoiced;
  }


  /**
   * @return the totalCollected
   */
  @Column(name="total_collected")
  public double getTotalCollected()
  {
    return totalCollected;
  }


  /**
   * @param totalCollected the totalCollected to set
   */
  public void setTotalCollected(double totalCollected)
  {
    this.totalCollected = totalCollected;
  }


  /**
   * @return the totalCredit
   */
  @Column(name="total_credit")
  public double getTotalCredit()
  {
    return totalCredit;
  }


  /**
   * @param totalCredit the totalCredit to set
   */
  public void setTotalCredit(double totalCredit)
  {
    this.totalCredit = totalCredit;
  }


  /**
   * @return the totalMissing
   */
  @Column(name="total_missing")
  public double getTotalMissing()
  {
    return totalMissing;
  }


  /**
   * @param totalMissing the totalMissing to set
   */
  public void setTotalMissing(double totalMissing)
  {
    this.totalMissing = totalMissing;
  }


  /**
   * 
   * Constructors
   * 
   */
  public UserPeriodData()
  {
    upProducts = new ArrayList<UserPeriodProduct>();
    upPaymentDetails = new ArrayList<UserPeriodPaymentDetail>();
    upCashDetails = new ArrayList<UserPeriodCashDetail>();
  }


  /**
   * 
   * Public methods
   * 
   */
  
  public SciiResult validate()
  {
    SciiResult result = new SciiResult();
    
    result.setreturnCode(SciiResult.RETURN_CODE_OK);
    result.setreturnMessage("OK");
    
    return result;
  }
}
