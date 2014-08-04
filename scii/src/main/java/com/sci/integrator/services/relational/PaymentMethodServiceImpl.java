/**
 * 
 */
package com.sci.integrator.services.relational;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sci.integrator.domain.payment.PaymentMethod;
import com.sci.integrator.persistence.IPaymentMethodDao;
import com.sci.integrator.services.IPaymentMethodService;

/**
 * @author Abdiel Jaramillo Ojedis
 *
 */
@Service("paymentMethodService")
public class PaymentMethodServiceImpl implements IPaymentMethodService
{

  private IPaymentMethodDao paymentMethodDao;
  
  public void setPaymentMethodDao(IPaymentMethodDao paymentMethodDao)
  {
    this.paymentMethodDao = paymentMethodDao;
  }
  /**
   * 
   */
  public PaymentMethodServiceImpl()
  {
    // TODO Auto-generated constructor stub
  }

  @Transactional
  public PaymentMethod getByOid(long oid)
  {
    PaymentMethod pm = null;
    
    pm = paymentMethodDao.getByOid(oid);
    
    return pm;
  }

  @Transactional
  public PaymentMethod getByServerId(String serverId)
  {
    PaymentMethod pm = null;
    
    pm = paymentMethodDao.getByServerId(serverId);
    
    return pm;
  }

}
