/**
 * 
 */
package com.sci.integrator.provider;

import com.sci.integrator.domain.core.SciiException;
import com.sci.integrator.transaction.Transaction;

/**
 * @author Abdiel
 *
 */
public interface IProvider
{
    
    // ***** BEGIN: get/set methods *****
  
    public String getBaseUrl();
    public void setBaseUrl(String baseUrl);
    
    public String getName();
    public void setName(String name);
    
    public IUserProviderGateway getUserProviderGateway();
    public void setUserProviderGateway(IUserProviderGateway userProviderGateway);

    public ITransactionProviderGateway getTransactionProviderGateway();
    public void setTransactionProviderGateway(ITransactionProviderGateway transactionProviderGateway);

    public IProviderSyncProcess getSyncProcess();
    public void setSyncProcess(IProviderSyncProcess syncProcess);
    
    // ***** END: get/set methods *****
    
}
