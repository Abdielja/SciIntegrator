package com.sci.integrator.persistence;

import com.sci.integrator.domain.payment.PaymentMethod;

public interface IPaymentMethodDao
{
  public PaymentMethod getByOid(long oid);
  public PaymentMethod getByServerId(String serverId);
}
