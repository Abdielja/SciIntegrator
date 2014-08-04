/**
 * 
 */
package com.sci.integrator.persistence;

import java.util.Date;

import com.sci.integrator.domain.core.Invoices;
import com.sci.integrator.domain.core.SciiResult;
import com.sci.integrator.domain.invoice.Invoice;

/**
 * @author Abdiel Jaramillo Ojedis
 *
 */
public interface IInvoiceDao
{
  Invoice getByOid(long _oid);
  Invoice getByServerId(String _serverId);
  Invoices getByUserOidWithinDateRange(long userOid, Date startDate, Date endDate);

  SciiResult save(Invoice invoice);
  void update(Invoice invoice);
  SciiResult save(Invoices pendingInvoices);
}
