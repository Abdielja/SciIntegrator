/**
 * 
 */
package com.sci.integrator.services;

import com.sci.integrator.domain.payment.PaymentMethod;

/**
 * @author Abdiel
 *
 */
public interface IPaymentMethodService
{
  public PaymentMethod getByOid(long oid);
  public PaymentMethod getByServerId(String serverId);
}
