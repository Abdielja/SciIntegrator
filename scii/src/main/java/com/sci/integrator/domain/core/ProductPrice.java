/**
 * 
 */
package com.sci.integrator.domain.core;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Abdiel Jaramillo Ojedis
 *
 */

@XmlRootElement(name="ProductPrice")
public class ProductPrice implements Serializable
{

  // ********** Constants **********
  
  private static final long serialVersionUID = 1L;

  // ********** Properties **********
  
  private String listVersionId;
  private double standardPrice;
  private double listPrice;
  
  /**
   * @return the listVersionId
   */
  public String getListVersionId()
  {
    return listVersionId;
  }

  /**
   * @param listVersionId the listVersionId to set
   */
  public void setListVersionId(String listVersionId)
  {
    this.listVersionId = listVersionId;
  }

  /**
   * @return the standardPrice
   */
  public double getStandardPrice()
  {
    return standardPrice;
  }

  /**
   * @param standardPrice the standardPrice to set
   */
  public void setStandardPrice(double standardPrice)
  {
    this.standardPrice = standardPrice;
  }

  /**
   * @return the listPrice
   */
  public double getListPrice()
  {
    return listPrice;
  }

  /**
   * @param listPrice the listPrice to set
   */
  public void setListPrice(double listPrice)
  {
    this.listPrice = listPrice;
  }

  
  // ********** Constructors **********
  
  public ProductPrice()
  {
    // TODO Auto-generated constructor stub
  }

}
