/**
 * 
 */
package com.sci.integrator.persistence.hibernate;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sci.integrator.domain.core.Invoices;
import com.sci.integrator.domain.core.Product;
import com.sci.integrator.domain.core.Route;
import com.sci.integrator.domain.core.SciiResult;
import com.sci.integrator.domain.invoice.Invoice;
import com.sci.integrator.persistence.IInvoiceDao;
import com.sci.integrator.transaction.Transaction;

/**
 * @author Abdiel Jaramillo Ojedis
 *
 */
public class InvoiceDaoImpl implements IInvoiceDao
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

  public SciiResult save(Invoice invoice)
  {
    
    SciiResult result = new SciiResult();
    
    Long oid = (Long)currentSession().save(invoice);
    invoice.setoid(oid);
    result.setaffectedObjectOid(oid);
    result.setAffectedObjectServerId(invoice.getserverId());
      
    return result;
  }

  public void update(Invoice invoice)
  {
    currentSession().update(invoice);
  }

  public Invoice getByOid(long _oid)
  {
    
    Invoice i = (Invoice)currentSession().get(Invoice.class, _oid);
    
    return i;
  }

  @SuppressWarnings("unchecked")
  public Invoice getByServerId(String _serverId)
  {
    
    Invoice invoice = null;
    
    Query query = currentSession().createQuery("FROM Invoice WHERE serverId = '" + _serverId + "'");
    List<Invoice> invoiceList = (List<Invoice>)query.list();

    if(invoiceList == null || invoiceList.size() < 1)
    {
      invoice = null;
    }
    else
    {
      invoice = invoiceList.get(0);
    }

    return invoice;
  }

  @SuppressWarnings("unchecked")
  public Invoices getByUserOidWithinDateRange(long userOid, Date startDate, Date endDate)
  {
    Invoices invoices = null;
    
    Query query = currentSession().createQuery("FROM Invoice WHERE createdBy.oid = " + userOid + "AND creationDate >= :startDate AND creationDate <= :endDate AND (status = :statusComplete OR status = :statusShiped OR status = :statusPaid OR status = :statusReversed)");
    
    query.setParameter("startDate", startDate);
    query.setParameter("endDate", endDate);
    query.setParameter("statusComplete", Invoice.STATUS_COMPLETE);
    query.setParameter("statusShiped", Invoice.STATUS_SHIPED);
    query.setParameter("statusPaid", Invoice.STATUS_PAID);
    query.setParameter("statusReversed", Invoice.STATUS_REVERSED);
    
    List<Invoice> invoiceList = (List<Invoice>)query.list();

    if(invoiceList == null || invoiceList.size() < 1)
    {
      invoices = null;
    }
    else
    {
      invoices = new Invoices();
      invoices.setCount(invoiceList.size());
      invoices.setInvoice(invoiceList);
    }

    return invoices;
  }

  public SciiResult save(Invoices invoices)
  {
    SciiResult res = new SciiResult();
    
    for(int i=0; i < invoices.getInvoice().size(); i++)
    {
      Invoice invoice = invoices.getInvoice().get(i);
      save(invoice);
    }
    
    return res;
  }

}
