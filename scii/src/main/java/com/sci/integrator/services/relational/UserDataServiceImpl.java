/**
 * 
 */
package com.sci.integrator.services.relational;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.sci.integrator.domain.core.Invoices;
import com.sci.integrator.domain.core.SciiException;
import com.sci.integrator.domain.core.SciiResult;
import com.sci.integrator.domain.core.UserData;
import com.sci.integrator.domain.core.UserPeriodData;
import com.sci.integrator.domain.payment.Payments;
import com.sci.integrator.persistence.ICustomersDao;
import com.sci.integrator.persistence.IInvoiceDao;
import com.sci.integrator.persistence.IPaymentDao;
import com.sci.integrator.persistence.IProductDao;
import com.sci.integrator.persistence.IRolesDao;
import com.sci.integrator.persistence.IUserDataDao;
import com.sci.integrator.persistence.hibernate.TransactionDaoImpl;
import com.sci.integrator.services.IUserDataService;

/**
 * @author Abdiel
 *
 */
public class UserDataServiceImpl implements IUserDataService
{

  private static final Logger logger = LoggerFactory.getLogger(TransactionDaoImpl.class);

  private IUserDataDao userDataDao;
  private IInvoiceDao invoiceDao;
  private IPaymentDao paymentDao;
  
  public void setUserDataDao(IUserDataDao userDataDao)
  {
    this.userDataDao = userDataDao;
  }
  
  /**
   * @return the invoiceDao
   */
  public IInvoiceDao getInvoiceDao()
  {
    return invoiceDao;
  }

  /**
   * @param invoiceDao the invoiceDao to set
   */
  public void setInvoiceDao(IInvoiceDao invoiceDao)
  {
    this.invoiceDao = invoiceDao;
  }

  public void setPaymentDao(IPaymentDao paymentDao)
  {
    this.paymentDao = paymentDao;
  }

  public UserDataServiceImpl()
  {
    // TODO Auto-generated constructor stub
  }

  @Transactional
  public SciiResult saveUserData(UserData userData)
  {
    SciiResult res;
    
    // *** Delete previous UserData objects for this User ***
    
//    userDataDao.deleteByUserOid(userData.getUserOid());
//    productDao.deleteListByOid(userData.getProducts().getOid());
    
    res = userDataDao.save(userData);
 
    return res;
  }

  @Transactional
  public void deleteUserDataByUserOid(long userOid)
  {
      userDataDao.deleteByUserOid(userOid);
  }

  @Transactional
  public UserData getByUserOid(long userOid) throws SciiException
  {
    UserData userData = null;
    
    try
    {
      userData = userDataDao.getByUserOid(userOid);
      Date now = new Date();
      Invoices invoices = invoiceDao.getByUserOidWithinDateRange(userOid, userData.getStartDate(), now);
      Payments payments = paymentDao.getByUserOidWithinDateRange(userOid, userData.getStartDate(), now);
      
      userData.setInvoices(invoices);
      userData.setPayments(payments);
      
    }
    catch(SciiException e)
    {
      logger.error(e.getMessage());
    }
    
    return userData;
  }

  @Transactional
  public SciiResult update(UserData userData)
  {
     SciiResult sciiResult = userDataDao.update(userData);
     
     return sciiResult;
  }

  @Transactional
  public SciiResult saveUserPeriodData(UserPeriodData upd)
  {
    SciiResult sciiResult = userDataDao.saveUserPeriodData(upd);
    upd.setOid(sciiResult.getaffectedObjectOid());
    return sciiResult;
  }

}
