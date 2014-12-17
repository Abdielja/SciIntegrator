/**
 * 
 */
package com.sci.integrator.services;

import com.sci.integrator.domain.core.SciiResult;
import com.sci.integrator.domain.core.SyncData;
import com.sci.integrator.transaction.Transaction;

/**
 * @author Abdiel
 *
 */
public interface ISyncDataService
{
  SciiResult saveSyncData(SyncData syncData);
  void deleteSyncData(long id);    
}
