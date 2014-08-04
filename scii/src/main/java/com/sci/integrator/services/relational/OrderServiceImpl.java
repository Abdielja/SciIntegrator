/**
 * 
 */
package com.sci.integrator.services.relational;

import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sci.integrator.domain.core.Invoices;
import com.sci.integrator.domain.core.SciiResult;
import com.sci.integrator.domain.invoice.Invoice;
import com.sci.integrator.domain.order.Order;
import com.sci.integrator.domain.order.Orders;
import com.sci.integrator.persistence.IInvoiceDao;
import com.sci.integrator.persistence.IOrderDao;
import com.sci.integrator.services.IOrderService;

/**
 * @author Abdiel Jaramillo Ojedis
 *
 */
@Service("orderService")
public class OrderServiceImpl implements IOrderService
{

    private IOrderDao orderDao;
    
    public void setOrderDao(IOrderDao orderDao)
    {
      this.orderDao = orderDao;
    }

    @Transactional
    public Order getByOid(long _oid)
    {
      Order order = orderDao.getByOid(_oid);
      return order;
    }

    @Transactional
    public Order getByServerId(String _serverId)
    {
      Order order = orderDao.getByServerId(_serverId);
      return order;
    }

    @Transactional
    public Orders getByUserOidWithinDateRange(long userOid, Date startDate, Date endDate)
    {
      Orders orders;
      orders = orderDao.getByUserOidWithinDateRange(userOid, startDate, endDate);
      return orders;
    }

    @Transactional
    public SciiResult saveOrder(Order order)
    {
      SciiResult result = orderDao.save(order);
      return result;
    }

    @Transactional
    public void updateOrder(Order order)
    {
      orderDao.update(order);    
    }

    @Transactional
    public SciiResult deleteByUserOid(long userOid)
    {
      SciiResult result = orderDao.deleteByUserOid(userOid);
      return result;
    }
  
}
