/**
 * 
 */
package com.sci.integrator.domain.core;


/**
 * @author Abdiel Jaramillo Ojedis
 *
 */
public class AppSettings
{
 
  private String serverUrl;
  private int maxRetries;
  private int maxPendingInvoices;

  /**
   * @return the serverUrl
   */
  public String getServerUrl()
  {
    return serverUrl;
  }

  /**
   * @param serverUrl the serverUrl to set
   */
  public void setServerUrl(String serverUrl)
  {
    this.serverUrl = serverUrl;
  }

  /**
   * @return the maxRetries
   */
  public int getMaxRetries()
  {
    return maxRetries;
  }

  /**
   * @param maxRetries the maxRetries to set
   */
  public void setMaxRetries(int maxRetries)
  {
    this.maxRetries = maxRetries;
  }

  /**
   * @return the MaxPendingInvoices to retrieve from server
   */
 public int getMaxPendingInvoices()
  {
    return maxPendingInvoices;
  }

  /**
   * @param maxPendingInvoices the MaxPendingInvoices to retrive from server
   */
  public void setMaxPendingInvoices(int maxPendingInvoices)
  {
    this.maxPendingInvoices = maxPendingInvoices;
  }
  
}
