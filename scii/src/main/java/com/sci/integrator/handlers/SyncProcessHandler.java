/**
 * 
 */
package com.sci.integrator.handlers;

import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sci.integrator.provider.IProvider;
import com.sci.integrator.services.ISyncDataService;
import com.sci.integrator.services.ITransactionService;

/**
 * 
 * @author Abdiel Jaramillo Ojedis
 *
 */

public class SyncProcessHandler
{

  private final Log   logger = LogFactory.getLog(this.getClass());

  // ********** Constants **********
  
  

  // ********** Injected Components **********
    
  @Inject
  ISyncDataService syncDataService;

  @Inject
  IProvider           providerGateway;

  
  // ********** Properties **********
  


  // ********** Constructors **********
  
  public SyncProcessHandler()
  {
    // TODO Auto-generated constructor stub
  }

  
  // ********** Public Methods **********
  
  public void process()
  {
    providerGateway.getSyncProcess().synchronize();
  }
  
  
}
