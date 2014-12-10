/**
 * 
 */
package com.sci.integrator.domain.invoice;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.CascadeType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.http.HttpMethod;

import com.sci.integrator.domain.core.SciiException;
import com.sci.integrator.domain.core.SciiRequest;
import com.sci.integrator.domain.core.SciiResponse;
import com.sci.integrator.domain.core.SciiResult;
import com.sci.integrator.domain.core.Transaction;
import com.sci.integrator.domain.payment.Payment;

/**
 * @author Abdiel Jaramillo Ojedis
 * 
 */
@Entity
@XmlRootElement(name = "transactionInvoiceReversal")
@DiscriminatorValue(value = "5")
@XmlAccessorType(XmlAccessType.FIELD)
public class TransactionInvoiceReversal extends Transaction
{

  private static final long serialVersionUID = 1L;
  
  private Invoice invoice;

  // *** Invoice ***
  @OneToOne(targetEntity = Invoice.class, cascade=CascadeType.MERGE)
  @JoinColumn(name = "trx_object_oid")
  public Invoice getinvoice()
  {
    return invoice;
  }

  public void setinvoice(Invoice invoice)
  {
    this.invoice = invoice;
  }


  // ***** ITransaction methods implementation *****

  public SciiResult beforProcess() throws SciiException
  {

    SciiResult sr = new SciiResult();
    sr.setreturnCode(Transaction.STATUS_VALIDATED);
    
    /* 
     * Esta validacion es para asegurarse de que la factura halla sido procesada por el erp 
     * antes de tratar de reversarla. -- Abdiel. Julio 17, 2014. 
     */
    if (invoice.getstatus() == Invoice.STATUS_COMPLETE || invoice.getstatus() == Invoice.STATUS_SHIPED || invoice.getstatus() == Invoice.STATUS_LOADED_FROM_ERP)
    {
      sr.setreturnMessage("Invoice Reversal" + invoice.getoid() + " validated.");
      sr.setreturnCode(Transaction.STATUS_VALIDATED);
    }
    else 
    {
      sr.setreturnCode(Transaction.STATUS_INVOICE_PENDING);
      sr.setreturnMessage("Invoice " + invoice.getoid() + " can not be reversed because it has not been processed yet.");
      return sr;
    }

    /* 
     * Esta validacion es para asegurarse de que la factura, si es al contado, 
     * halla sido pagada antes de tratar de reversarla. -- Abdiel. Julio 17, 2014. 
     */
    /*
    if (invoice.getstatus() != Invoice.STATUS_PAID)
    {
      if (!invoice.isCredit())
      {
        sr.setreturnCode(Transaction.STATUS_PAYMENT_PENDING);
        sr.setreturnMessage("Invoice " + invoice.getoid() + " can not be reversed because it has not been paid yet.");
        return sr;        
      }
    }
    */
    return sr;
  }

  public SciiRequest buildMainRequest()
  {
    com.sci.integrator.domain.core.User user = this.getcreatedBy();

    System.out.println("Reverse Invoice Transaction: " + this.getoid());

    SciiRequest request = null;
    
    // *** Get InvoiceDO object ***
    Invoice invoice = this.getinvoice(); 

    if (((invoice.getstatus() == Invoice.STATUS_COMPLETE
        || invoice.getstatus() == Invoice.STATUS_SHIPED
        || invoice.getstatus() == Invoice.STATUS_PAID) && this.getstatus() == Transaction.STATUS_PENDING)
        || this.getstatus() == Transaction.STATUS_FAILED
        )
    {
      System.out.println("  Invoice: " + this.getinvoice().getoid());
      request = new SciiRequest();
      request.setUrlExtension("ws/com.spiritsci.spimo.reverseInvoice?invoiceId=" + invoice.getserverId());
      request.setStrRequest("");
      request.setHttpMethod(HttpMethod.GET);
      request.setWhereClause("");

    }

    System.out.println();

    return request;
  }

  public List<SciiRequest> buildSubRequests()
  {
    // TODO Auto-generated method stub
    return null;
  }

  public void processMainResponse(SciiResponse response)
  {
    
    if (response.getException() == null)
    {

      invoice.setstatus(Invoice.STATUS_REVERSED);

      // *** Update InvoiceDO Lines statuses ***
      List<InvoiceLine> invoiceLines = invoice.getinvoiceLine();
      for (int i = 0; i < invoiceLines.size(); i++)
      {
        if (invoiceLines.get(i).getstatus() == Invoice.STATUS_PENDING
            || invoiceLines.get(i).getstatus() == Invoice.STATUS_FAILED)
        {
          System.out.println("    InvoiceLine: "
              + invoiceLines.get(i).getlineNumber());
          invoiceLines.get(i).setstatus(Invoice.STATUS_REVERSED);
        }
      }
      
      this.getinvoice().setstatus(Invoice.STATUS_REVERSED);
      this.setstatus(Transaction.STATUS_PROCESSED);
      System.out.println("Invoice " + this.getinvoice().getoid() + " reversal processed in ERP");

    }
    else
    {
      this.setstatus(Transaction.STATUS_FAILED);
    }
    
  }

  public void processSubResponse(SciiResponse response)
  {
    // TODO Auto-generated method stub
    
  }

  public void validate() throws SciiException
  {
    super.validate();
    System.out.println("END - Transaction Validation.");
  }

}
