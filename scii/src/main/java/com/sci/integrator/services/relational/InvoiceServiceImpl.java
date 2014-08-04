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
import com.sci.integrator.persistence.IInvoiceDao;
import com.sci.integrator.persistence.ITransactionDAO;
import com.sci.integrator.services.IInvoiceService;

/**
 * @author Abdiel Jaramillo Ojedis
 *
 */
@Service("invoiceService")
public class InvoiceServiceImpl implements IInvoiceService
{

  private IInvoiceDao invoiceDao;
  
  public void setInvoiceDao(IInvoiceDao invoiceDao)
  {
    this.invoiceDao = invoiceDao;
  }

  @Transactional
  public Invoice getByOid(long _oid)
  {
    Invoice invoice = invoiceDao.getByOid(_oid);
    return invoice;
  }

  @Transactional
  public Invoice getByServerId(String _serverId)
  {
    Invoice invoice = invoiceDao.getByServerId(_serverId);
    return invoice;
  }

  @Transactional
  public Invoices getByUserOidWithinDateRange(long userOid, Date startDate, Date endDate)
  {
    Invoices invoices;
    invoices = invoiceDao.getByUserOidWithinDateRange(userOid, startDate, endDate);
    return invoices;
  }

  @Transactional
  public SciiResult saveInvoice(Invoice invoice)
  {
    SciiResult result = invoiceDao.save(invoice);
    return result;
  }

  @Transactional
  public void updateInvoice(Invoice invoice)
  {
    invoiceDao.update(invoice);    
  }

}
