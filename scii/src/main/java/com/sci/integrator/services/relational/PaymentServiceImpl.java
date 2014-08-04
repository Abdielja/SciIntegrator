package com.sci.integrator.services.relational;

import java.util.Date;

import org.springframework.transaction.annotation.Transactional;

import com.sci.integrator.domain.core.Invoices;
import com.sci.integrator.domain.payment.Payment;
import com.sci.integrator.domain.payment.Payments;
import com.sci.integrator.persistence.IInvoiceDao;
import com.sci.integrator.persistence.IPaymentDao;
import com.sci.integrator.services.IPaymentService;

public class PaymentServiceImpl implements IPaymentService
{

  private IPaymentDao paymentDao;
  
  public void setInvoiceDao(IPaymentDao paymentDao)
  {
    this.paymentDao = paymentDao;
  }

  
  public PaymentServiceImpl()
  {
    // TODO Auto-generated constructor stub
  }

  @Transactional
  public Payments getByUserOidWithinDateRange(long userOid, Date startDate,
      Date endDate)
  {
    Payments payments;
    payments = paymentDao.getByUserOidWithinDateRange(userOid, startDate, endDate);
    return payments;
  }

}
