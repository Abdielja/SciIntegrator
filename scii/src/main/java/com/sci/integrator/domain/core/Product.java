/**
 * 
 */
package com.sci.integrator.domain.core;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author Abdiel Jaramillo Ojedis
 *
 */
@Entity
@Table(name="product")
@XmlRootElement(name="Product")
public class Product implements Serializable
{

  // ********** Constants **********
  
  private static final long serialVersionUID = 1L;

  // ********** Properties **********
  
  private long oid;
  private String serverId;
  private String name;
  private int quantityOnHand;
  private int quantityInvoiced;           // ** Quantity Invoiced at end of day
  private int quantityReturned;           // ** Quantity returned at end of day
  private double amountInvoiced;          // ** Amount Invoiced at end of day
  private double amountReturned;          // ** Amount returned at end of day
  private double standardPrice;
  private String productOrganizationId;
  private String taxCategoryId;
  private double totalTaxRate;
  private String uPCEAN;
  private String storageBinId;
  private String storageBinOrganizationId;
  
  private Products productList = new Products();
  private ProductPrices productPrices = new ProductPrices();
  
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
   * @return the serverId
   */
  
  @Column(name="server_id")  
  public String getServerId()
  {
    return serverId;
  }

  /**
   * @param serverId the serverId to set
   */
  public void setServerId(String serverId)
  {
    this.serverId = serverId;
  }

  /**
   * @return the name
   */
  @Column(name="name")  
  public String getName()
  {
    return name;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name)
  {
    this.name = name;
  }

  /**
   * @return the quantityOnHand
   */
  @Column(name="quantity_on_hand")  
  public int getQuantityOnHand()
  {
    return quantityOnHand;
  }

  /**
   * @param quantityOnHand the quantityOnHand to set
   */
  public void setQuantityOnHand(int quantityOnHand)
  {
    this.quantityOnHand = quantityOnHand;
  }

  /**
   * @return the quantityInvoiced
   */
  @Column(name="quantity_invoiced")  
  public int getQuantityInvoiced()
  {
    return quantityInvoiced;
  }

  /**
   * @param quantityInvoiced the quantityInvoiced to set
   */
  public void setQuantityInvoiced(int quantityInvoiced)
  {
    this.quantityInvoiced = quantityInvoiced;
  }

  /**
   * @return the quantityReturned
   */
  @Column(name="quantity_returned")  
  public int getQuantityReturned()
  {
    return quantityReturned;
  }

  /**
   * @param quantityReturned the quantityReturned to set
   */
  public void setQuantityReturned(int quantityReturned)
  {
    this.quantityReturned = quantityReturned;
  }

  /**
   * @return the amountInvoiced
   */
  @Column(name="amount_invoiced")  
  public double getAmountInvoiced()
  {
    return amountInvoiced;
  }

  /**
   * @param amountInvoiced the amountInvoiced to set
   */
  public void setAmountInvoiced(double amountInvoiced)
  {
    this.amountInvoiced = amountInvoiced;
  }

  /**
   * @return the amountReturned
   */
  @Column(name="amount_returned")  
  public double getAmountReturned()
  {
    return amountReturned;
  }

  /**
   * @param amountReturned the amountReturned to set
   */
  public void setAmountReturned(double amountReturned)
  {
    this.amountReturned = amountReturned;
  }

  /**
   * @return the standardPrice
   */
  @Column(name="standard_price")  
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
   * @return the productOrganizationId
   */
  @Column(name="product_org_id")  
  public String getProductOrganizationId()
  {
    return productOrganizationId;
  }

  /**
   * @param productOrganizationId the productOrganizationId to set
   */
  public void setProductOrganizationId(String productOrganizationId)
  {
    this.productOrganizationId = productOrganizationId;
  }

  /**
   * @return the taxCategoryId
   */
  @Column(name="tax_category_id")  
  public String getTaxCategoryId()
  {
    return taxCategoryId;
  }

  /**
   * @param taxCategoryId the taxCategoryId to set
   */
  public void setTaxCategoryId(String taxCategoryId)
  {
    this.taxCategoryId = taxCategoryId;
  }

  /**
   * @return the totalTaxRate
   */
  @Column(name="total_tax")  
  public double getTotalTaxRate()
  {
    return totalTaxRate;
  }

  /**
   * @param totalTaxRate the totalTaxRate to set
   */
  public void setTotalTaxRate(double totalTaxRate)
  {
    this.totalTaxRate = totalTaxRate;
  }

  /**
   * @return the uPCEAN
   */
  @Column(name="upcean")  
  public String getuPCEAN()
  {
    return uPCEAN;
  }

  /**
   * @param uPCEAN the uPCEAN to set
   */
  public void setuPCEAN(String uPCEAN)
  {
    this.uPCEAN = uPCEAN;
  }

  /**
   * @return the storageBinId
   */
  @Column(name="storage_bin_id")  
  public String getStorageBinId()
  {
    return storageBinId;
  }

  /**
   * @param storageBinId the storageBinId to set
   */
  public void setStorageBinId(String storageBinId)
  {
    this.storageBinId = storageBinId;
  }

  /**
   * @return the storageBinOrganizationId
   */
  @Column(name="storage_bin_org_id")  
  public String getStorageBinOrganizationId()
  {
    return storageBinOrganizationId;
  }

  /**
   * @param storageBinOrganizationId the storageBinOrganizationId to set
   */
  public void setStorageBinOrganizationId(String storageBinOrganizationId)
  {
    this.storageBinOrganizationId = storageBinOrganizationId;
  }


  /**
   * @return the productList
   */
  //@ManyToOne
  //@JoinColumn(name = "role_list_oid")
  @Transient
  @XmlTransient
  public Products getProductList()
  {
    return productList;
  }

  /**
   * @param productList the productList to set
   */
  public void setProductList(Products productList)
  {
    this.productList = productList;
  }

  /**
   * @return the productPrices
   */
  @Transient
  public ProductPrices getProductPrices()
  {
    return productPrices;
  }

  /**
   * @param productPrices the productPrices to set
   */
  public void setProductPrices(ProductPrices productPrices)
  {
    this.productPrices = productPrices;
  }

  
  // ********** Constructors **********
  
  
  public Product()
  {
    // TODO Auto-generated constructor stub
  }

}
