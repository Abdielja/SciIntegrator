package com.sci.integrator.persistence;

import java.util.Date;

import com.sci.integrator.domain.payment.Payments;

public interface IPaymentDao
{
  Payments getByUserOidWithinDateRange(long userOid, Date startDate, Date endDate);

}
