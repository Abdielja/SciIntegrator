/**
 * 
 */
package com.sci.integrator.persistence.hibernate;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sci.integrator.domain.core.SciiResult;
import com.sci.integrator.domain.order.Order;
import com.sci.integrator.domain.order.OrderLine;
import com.sci.integrator.domain.order.Orders;
import com.sci.integrator.persistence.IOrderDao;

/**
 * @author Abdiel Jaramillo Ojedis
 *
 */
public class OrderDaoImpl implements IOrderDao
{

  private static final Logger logger = LoggerFactory.getLogger(TransactionDaoImpl.class);

  private SessionFactory sessionFactory;
  
  public void setSessionFactory(SessionFactory sessionFactory) 
  {
    this.sessionFactory = sessionFactory;
  } 
  
  private Session currentSession() 
  {
    return sessionFactory.getCurrentSession();
  }

  public SciiResult save(Order order)
  {
    
    SciiResult result = new SciiResult();
        
    List<OrderLine> orderLines = order.getorderLine();
    order.setorderLine(null);
    
    Long oid = (Long)currentSession().save(order);
    order.setoid(oid);
    result.setaffectedObjectOid(oid);
    result.setAffectedObjectServerId(order.getserverId());
      
    // *** Order lines ***
    Iterator<OrderLine> iOrderLines = orderLines.iterator();
    while (iOrderLines.hasNext())
    {
      OrderLine ol = iOrderLines.next();
      ol.setOrder(order);
      currentSession().save(ol);
    }

    order.setorderLine(orderLines);
    return result;
  }

  public SciiResult update(Order order)
  {

    SciiResult result = new SciiResult();
    
    try
    {
      Order tOrder= (Order)currentSession().merge(order);

      result.setaffectedObjectOid(tOrder.getoid());
      result.setAffectedObjectServerId(order.getserverId());
      result.setreturnCode(SciiResult.RETURN_CODE_OK);
      result.setreturnMessage("OK");
    }
    catch(Exception e)
    {
      result.setreturnCode(SciiResult.RETURN_CODE_UNIDENTFIED_ERROR);
      result.setreturnMessage(e.getMessage());
    }
    
    return result;
  }

  public Order getByOid(long _oid)
  {
    
    Order i = (Order)currentSession().get(Order.class, _oid);
    
    return i;
  }

  @SuppressWarnings("unchecked")
  public Order getByServerId(String _serverId)
  {
    
    Order order = null;
    
    Query query = currentSession().createQuery("FROM Order WHERE serverId = '" + _serverId + "'");
    List<Order> orderList = (List<Order>)query.list();

    if(orderList == null || orderList.size() < 1)
    {
      order = null;
    }
    else
    {
      order = orderList.get(0);
    }

    return order;
  }

  @SuppressWarnings("unchecked")
  public Orders getByUserOidWithinDateRange(long userOid, Date startDate, Date endDate)
  {
    Orders orders = null;
    
    Query query = currentSession().createQuery("FROM Order WHERE createdBy.oid = " + userOid + "AND creationDate >= :startDate AND creationDate <= :endDate AND (status = :statusBooked OR status = :statusShiped OR status = :statusPaid OR status = :statusReversed)");
    
    query.setParameter("startDate", startDate);
    query.setParameter("endDate", endDate);
    query.setParameter("statusBooked", Order.STATUS_BOOKED);
    query.setParameter("statusShiped", Order.STATUS_SHIPED);
    query.setParameter("statusPaid", Order.STATUS_PAID);
    query.setParameter("statusReversed", Order.STATUS_REVERSED);
    
    List<Order> orderList = (List<Order>)query.list();

    if(orderList == null || orderList.size() < 1)
    {
      orders = null;
    }
    else
    {
      orders = new Orders();
      orders.setCount(orderList.size());
      orders.setOrder(orderList);
    }

    return orders;
  }

  public SciiResult save(Orders orders)
  {
    SciiResult res = new SciiResult();
    
    for(int i=0; i < orders.getOrder().size(); i++)
    {
      Order order = orders.getOrder().get(i);
      save(order);
    }
    
    return res;
  }

  @SuppressWarnings("unchecked")
  public SciiResult deleteByUserOid(long userOid)
  {
    Orders orders = null;
    
    Query query = currentSession().createQuery("FROM Order WHERE createdBy.oid = " + userOid);
    
    List<Order> orderList = (List<Order>)query.list();

    if(orderList == null || orderList.size() < 1)
    {
      orders = null;
    }
    else
    {
      
      Iterator<Order> iOrders = orderList.iterator();
      while (iOrders.hasNext())
      {
        Order order = iOrders.next();
        currentSession().delete(order);
      }

     }

    return null;
  }

}
