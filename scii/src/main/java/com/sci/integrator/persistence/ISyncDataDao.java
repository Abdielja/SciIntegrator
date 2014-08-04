/**
 * 
 */
package com.sci.integrator.persistence;

import com.sci.integrator.domain.core.SciiResult;
import com.sci.integrator.domain.core.SyncData;
import com.sci.integrator.domain.core.Transaction;

/**
 * @author Abdiel Jaramillo Ojedis
 *
 */
public interface ISyncDataDao
{  
  SciiResult save(SyncData syncData);
  void delete(long oid);
  void deleteAll();
}
