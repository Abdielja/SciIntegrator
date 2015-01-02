package com.sci.integrator.provider;

import javax.inject.Inject;

import com.sci.integrator.domain.core.AppSettings;
import com.sci.integrator.domain.core.SciiException;
import com.sci.integrator.provider.adempiere.AdempiereProvider;
import com.sci.integrator.provider.adempiere.AdempiereTransactionProviderGateway;
import com.sci.integrator.provider.openbravo.ObProvider;
import com.sci.integrator.provider.openbravo.ObTransactionProviderGateway;
import com.sci.integrator.provider.openbravo.ObUserProviderGateway;
import com.sci.integrator.transaction.Transaction;

public class ProviderFactory
{

  @Inject
  AppSettings appSettings;
  
  public IProvider getProvider(Transaction trx) throws SciiException
  {
    long providerOid = trx.getprovider().getOid();
    IProvider provider = null;
    
    if (providerOid == 1)
    {
      provider = new ObProvider();
 
      ObTransactionProviderGateway trxProviderGateway = new ObTransactionProviderGateway(appSettings.getServerUrl());
      ObUserProviderGateway userProviderGateway = new ObUserProviderGateway(appSettings.getServerUrl());
      
      provider.setTransactionProviderGateway(trxProviderGateway);
      provider.setUserProviderGateway(userProviderGateway);
      provider.setSyncProcess(null);
    }
    
    if (providerOid == 2)
    {
      provider = new AdempiereProvider();

      AdempiereTransactionProviderGateway providerGateway = new AdempiereTransactionProviderGateway("");
      
      provider.setTransactionProviderGateway(providerGateway);
      provider.setUserProviderGateway(null);
      provider.setSyncProcess(null);
    }
    
    if (provider == null)
    {
      throw new SciiException("Provider with oid " + providerOid + " does not exist.", 0);
    }
    return provider;
  }
}
