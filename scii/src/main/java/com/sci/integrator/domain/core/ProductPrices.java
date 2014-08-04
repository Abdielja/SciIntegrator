/**
 * 
 */
package com.sci.integrator.domain.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Abdiel Jaramillo Ojedis
 *
 */
@XmlRootElement(name="ProductPrices")
public class ProductPrices implements Serializable
{

  // ********** Constants **********
  
  private static final long serialVersionUID = 1L;

  // ********** Properties **********
  
  private List<ProductPrice> productPrice = new ArrayList<ProductPrice>();
  
  public List<ProductPrice> getProductPrice()
  {
    return productPrice;
  }

  public void setProductPrice(List<ProductPrice> _productPrice)
  {
    this.productPrice = _productPrice;
  }

  public void add(ProductPrice _productPrice)
  {
    this.productPrice.add(_productPrice);
  }
  
    
  public ProductPrices()
  {
    // TODO Auto-generated constructor stub
  }

}
