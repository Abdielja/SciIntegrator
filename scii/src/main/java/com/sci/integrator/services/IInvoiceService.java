/**
 * 
 */
package com.sci.integrator.services;

import java.util.Date;

import com.sci.integrator.domain.core.Invoices;
import com.sci.integrator.domain.core.SciiResult;
import com.sci.integrator.domain.invoice.Invoice;

/**
 * @author Abdiel
 *
 */
public interface IInvoiceService
{

  public Invoice getByOid(long _oid);
  public Invoice getByServerId(String _serverId);
  public Invoices getByUserOidWithinDateRange(long userOid, Date startDate, Date endDate);
  SciiResult saveInvoice(Invoice invoice);
  void updateInvoice(Invoice invoice);
  
}
