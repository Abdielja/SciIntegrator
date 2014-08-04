package com.sci.integrator.persistence.hibernate;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sci.integrator.domain.core.Invoices;
import com.sci.integrator.domain.core.SciiResult;
import com.sci.integrator.domain.invoice.Invoice;
import com.sci.integrator.domain.payment.Payment;
import com.sci.integrator.domain.payment.Payments;
import com.sci.integrator.persistence.IPaymentDao;

public class PaymentDaoImpl implements IPaymentDao
{

  public PaymentDaoImpl()
  {
    // TODO Auto-generated constructor stub
  }

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

  @SuppressWarnings("unchecked")
  public Payments getByUserOidWithinDateRange(long userOid, Date startDate, Date endDate)
  {
    Payments payments = null;
    
    Query query = currentSession().createQuery("FROM Payment WHERE createdBy.oid = " + userOid + "AND creationDate >= :startDate AND creationDate <= :endDate AND (invoice.status = :statusComplete OR invoice.status = :statusShiped OR invoice.status = :statusPaid OR invoice.status = :statusReversed)");
    query.setParameter("startDate", startDate);
    query.setParameter("endDate", endDate);
    query.setParameter("statusComplete", Invoice.STATUS_COMPLETE);
    query.setParameter("statusShiped", Invoice.STATUS_SHIPED);
    query.setParameter("statusPaid", Invoice.STATUS_PAID);
    query.setParameter("statusReversed", Invoice.STATUS_REVERSED);
    
    List<Payment> paymentList = (List<Payment>)query.list();
    
    if (paymentList != null)
    {
      payments = new Payments();
      payments.setCount(paymentList.size());
      payments.setPayment(paymentList);
    }
    
    return payments;
   
  }

}
