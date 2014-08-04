package com.sci.integrator.services;

import java.util.Date;

import com.sci.integrator.domain.core.SciiResult;
import com.sci.integrator.domain.order.Order;
import com.sci.integrator.domain.order.Orders;

public interface IOrderService
{

  public Order getByOid(long _oid);
  public Order getByServerId(String _serverId);
  public Orders getByUserOidWithinDateRange(long userOid, Date startDate, Date endDate);
  SciiResult saveOrder(Order order);
  void updateOrder(Order order);
  SciiResult deleteByUserOid(long userOid);
  
}
