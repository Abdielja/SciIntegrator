package com.sci.integrator.services;

import java.util.Date;

import com.sci.integrator.domain.payment.Payments;

public interface IPaymentService
{
  public Payments getByUserOidWithinDateRange(long userOid, Date startDate, Date endDate);

}
