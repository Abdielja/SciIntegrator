/**
 * 
 */
package com.sci.integrator.persistence.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sci.integrator.domain.core.Product;
import com.sci.integrator.domain.invoice.Invoice;
import com.sci.integrator.domain.payment.PaymentMethod;
import com.sci.integrator.persistence.IPaymentMethodDao;

/**
 * @author Abdiel
 *
 */
public class PaymentMethodDaoImpl implements IPaymentMethodDao
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

  /**
   * Constructors 
   */
  public PaymentMethodDaoImpl()
  {
    // TODO Auto-generated constructor stub
  }
  
  public PaymentMethod getByOid(long _oid)
  {
    PaymentMethod obj = (PaymentMethod)currentSession().get(PaymentMethod.class, _oid);
    return obj;
  }

  @SuppressWarnings("unchecked")
  public PaymentMethod getByServerId(String serverId)
  {
    PaymentMethod pm = null;
    
    Query query = currentSession().createQuery("FROM PaymentMethod WHERE serverId = '" + serverId + "'");
    List<PaymentMethod> paymentMethodList = (List<PaymentMethod>)query.list();

    if(paymentMethodList == null || paymentMethodList.size() < 1)
    {
      pm = null;
    }
    else
    {
      pm = paymentMethodList.get(0);
    }
    return pm;
    
  }
  
}
