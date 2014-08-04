/**
 * 
 */
package com.sci.integrator.services.relational;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sci.integrator.domain.core.SciiResult;
import com.sci.integrator.domain.core.SyncData;
import com.sci.integrator.persistence.ICustomersDao;
import com.sci.integrator.persistence.IInvoiceDao;
import com.sci.integrator.persistence.ISyncDataDao;
import com.sci.integrator.persistence.ITransactionDAO;
import com.sci.integrator.services.ISyncDataService;

/**
 * @author Abdiel
 *
 */
@Service("syncDataService")
public class SyncDataServiceImpl implements ISyncDataService
{

  private ISyncDataDao syncDataDao;
  private ICustomersDao customersDao;
  private IInvoiceDao invoiceDao;
  
  public void setSyncDataDao(ISyncDataDao syncDataDao)
  {
    this.syncDataDao = syncDataDao;
  }
  
  public void setCustomersDao(ICustomersDao customersDao)
  {
    this.customersDao = customersDao;
  }

  public void setInvoiceDao(IInvoiceDao invoiceDao)
  {
    this.invoiceDao = invoiceDao;
  }

  public SyncDataServiceImpl()
  {
    // TODO Auto-generated constructor stub
  }

  @Transactional
  public SciiResult saveSyncData(SyncData syncData)
  {
    
    SciiResult res;
    
    // *** Save Customers ***
    //SciiResult customersRes = customersDao.save(syncData.getCustomers());
    //syncData.getCustomers().setOid(customersRes.getaffectedObjectOid());

    // *** Save Customers ***
    SciiResult resPendingInvoices = invoiceDao.save(syncData.getPendingInvoices());
    syncData.getCustomers().setOid(resPendingInvoices.getaffectedObjectOid());

    res = syncDataDao.save(syncData);
    
    return res;
  }

  @Transactional
  public void deleteSyncData(long oid)
  {
    syncDataDao.delete(oid);   
  }

  @Transactional
  public void deleteAllSyncData()
  {
    syncDataDao.deleteAll();
  }
  
}
