/**
 * 
 */
package com.sci.integrator.domain.core;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Abdiel Jaramillo Ojedis
 *
 */
@XmlRootElement(name = "Invoice")
public class InvoiceDO implements Serializable
{

  // ********** Constants **********
  
  private static final long serialVersionUID = 1L;

  // ********** Properties **********
  
  private String serverId;
  private String clientId;
  private String organizationId;
  private Date creationDate;
  private String documentNo;
  private String businessPartnerId;
  private String paymentMethodId;
  private String PaymentTermsId;
  private String priceListId;
  private Double totalAmount;
  private Double outstandingAmount;
  

  /**
   * @return the serverId
   */
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
   * @return the clientId
   */
  public String getClientId()
  {
    return clientId;
  }


  /**
   * @param clientId the clientId to set
   */
  public void setClientId(String clientId)
  {
    this.clientId = clientId;
  }


  /**
   * @return the organizationId
   */
  public String getOrganizationId()
  {
    return organizationId;
  }


  /**
   * @param organizationId the organizationId to set
   */
  public void setOrganizationId(String organizationId)
  {
    this.organizationId = organizationId;
  }


  /**
   * @return the creationDate
   */
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
   * @return the documentNo
   */
  public String getDocumentNo()
  {
    return documentNo;
  }


  /**
   * @param documentNo the documentNo to set
   */
  public void setDocumentNo(String documentNo)
  {
    this.documentNo = documentNo;
  }


  /**
   * @return the businessPartnerId
   */
  public String getBusinessPartnerId()
  {
    return businessPartnerId;
  }


  /**
   * @param businessPartnerId the businessPartnerId to set
   */
  public void setBusinessPartnerId(String businessPartnerId)
  {
    this.businessPartnerId = businessPartnerId;
  }


  /**
   * @return the paymentMethodId
   */
  public String getPaymentMethodId()
  {
    return paymentMethodId;
  }


  /**
   * @param paymentMethodId the paymentMethodId to set
   */
  public void setPaymentMethodId(String paymentMethodId)
  {
    this.paymentMethodId = paymentMethodId;
  }


  /**
   * @return the paymentTermId
   */
  public String getPaymentTermsId()
  {
    return PaymentTermsId;
  }


  /**
   * @param paymentTermId the paymentTermId to set
   */
  public void setPaymentTermsId(String paymentTermsId)
  {
    PaymentTermsId = paymentTermsId;
  }


  /**
   * @return the priceListId
   */
  public String getPriceListId()
  {
    return priceListId;
  }


  /**
   * @param priceListId the priceListId to set
   */
  public void setPriceListId(String priceListId)
  {
    this.priceListId = priceListId;
  }


  /**
   * @return the totalAmount
   */
  public Double getTotalAmount()
  {
    return totalAmount;
  }


  /**
   * @param totalAmount the totalAmount to set
   */
  public void setTotalAmount(Double totalAmount)
  {
    this.totalAmount = totalAmount;
  }


  /**
   * @return the outstandingAmount
   */
  public Double getOutstandingAmount()
  {
    return outstandingAmount;
  }


  /**
   * @param outstandingAmount the outstandingAmount to set
   */
  public void setOutstandingAmount(Double outstandingAmount)
  {
    this.outstandingAmount = outstandingAmount;
  }


  public InvoiceDO()
  {
    // TODO Auto-generated constructor stub
  }

}
