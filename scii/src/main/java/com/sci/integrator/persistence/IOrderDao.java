/**
 * 
 */
package com.sci.integrator.persistence;

import java.util.Date;

import com.sci.integrator.domain.core.SciiResult;
import com.sci.integrator.domain.order.Order;
import com.sci.integrator.domain.order.Orders;

/**
 * @author Abdiel Jaramillo Ojedis
 *
 */
public interface IOrderDao
{
  Order getByOid(long _oid);
  Order getByServerId(String _serverId);
  Orders getByUserOidWithinDateRange(long userOid, Date startDate, Date endDate);

  SciiResult save(Order order);
  SciiResult update(Order order);
  SciiResult save(Orders pendingOrders);
  SciiResult deleteByUserOid(long userOid);
  
}
