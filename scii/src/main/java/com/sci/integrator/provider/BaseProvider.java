/**
 * 
 */
package com.sci.integrator.provider;

import com.sci.integrator.domain.core.Identifiable;
import com.sci.integrator.domain.core.SciiException;
import com.sci.integrator.domain.core.Transaction;

/**
 * @author Abdiel
 *
 */
public class BaseProvider extends Identifiable implements IProvider
{

  private String baseUrl;
  private String name;
  
  private IProviderSyncProcess providerSyncProcess;
  private IUserProviderGateway userProviderGateway;
  private ITransactionProviderGateway transactionProviderGateway;
 
  // ***
  public String getBaseUrl()
  {
    return baseUrl;
  }

  public void setBaseUrl(String baseUrl)
  {
    this.baseUrl = baseUrl;
  }

  // ***
  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  /**
   * @return the providerSyncProcess
   */
  public IProviderSyncProcess getProviderSyncProcess()
  {
    return providerSyncProcess;
  }

  /**
   * @param providerSyncProcess the providerSyncProcess to set
   */
  public void setProviderSyncProcess(IProviderSyncProcess providerSyncProcess)
  {
    this.providerSyncProcess = providerSyncProcess;
  }

  /**
   * @return the userService
   */
  public IUserProviderGateway getUserProviderGateway()
  {
    return userProviderGateway;
  }

  /**
   * @param userService the userService to set
   */
  public void setUserProviderGateway(IUserProviderGateway userProviderGateway)
  {
    this.userProviderGateway = userProviderGateway;
  }

  /**
   * @return the transactionProviderGateway
   */
  public ITransactionProviderGateway getTransactionProviderGateway()
  {
    return transactionProviderGateway;
  }

  /**
   * @param userService the userService to set
   */
  public void setTransactionProviderGateway(ITransactionProviderGateway transactionProviderGateway)
  {
    this.transactionProviderGateway = transactionProviderGateway;
  }

  public IProviderSyncProcess getSyncProcess()
  {
    return providerSyncProcess;
  }

  public void setSyncProcess(IProviderSyncProcess providerSyncProcess)
  {
    this.providerSyncProcess = providerSyncProcess;
  }

}
