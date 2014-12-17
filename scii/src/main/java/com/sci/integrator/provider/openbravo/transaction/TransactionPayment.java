/**
 * 
 */

/**
 * @author Abdiel Jaramillo Ojedis
 *
 */

package com.sci.integrator.provider.openbravo.transaction;

import java.io.Serializable;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.http.HttpMethod;

import com.sci.integrator.domain.core.SciiException;
import com.sci.integrator.domain.core.SciiRequest;
import com.sci.integrator.domain.core.SciiResponse;
import com.sci.integrator.domain.core.SciiResult;
import com.sci.integrator.domain.invoice.Invoice;
import com.sci.integrator.domain.payment.Payment;
import com.sci.integrator.transaction.Transaction;

@Entity
@XmlRootElement(name = "transactionPayment")
@DiscriminatorValue(value = "3")
@XmlAccessorType(XmlAccessType.FIELD)
public class TransactionPayment extends Transaction implements Serializable
{

  private static final long serialVersionUID = 1L;

  private Payment           payment;

  // *** InvoiceDO ***
  @OneToOne(targetEntity = Payment.class)
  @Cascade({ CascadeType.SAVE_UPDATE })
  @JoinColumn(name = "trx_object_oid")
  public Payment getpayment()
  {
    return payment;
  }

  public void setpayment(Payment payment)
  {
    this.payment = payment;
  }

  // ***** ITransaction methods implementation *****

  public SciiResult beforProcess() throws SciiException
  {
    SciiResult sr = new SciiResult();
    sr.setreturnCode(Transaction.STATUS_VALIDATED);
    
    Invoice invoice = payment.getinvoice();
    if (invoice.getstatus() == Invoice.STATUS_COMPLETE || invoice.getstatus() == Invoice.STATUS_SHIPED || invoice.getstatus() == Invoice.STATUS_LOADED_FROM_ERP)
    {
      sr.setreturnMessage("Payment " + payment.getoid() + " validated.");
      sr.setreturnCode(Transaction.STATUS_VALIDATED);
    }
    else
    {
      if (invoice.getstatus() == Invoice.STATUS_DRAFT || invoice.getstatus() ==  Invoice.STATUS_PENDING)
      {
        sr.setreturnCode(Transaction.STATUS_INVOICE_PENDING);
        sr.setreturnMessage("Payment " + payment.getoid() + " can not be processed because Invoice " + invoice.getoid() + " has not been processed yet.");              
      }
      if (invoice.getstatus() == Invoice.STATUS_REVERSED)
      {
        sr.setreturnCode(Transaction.STATUS_INVOICE_REVERSED);
        sr.setreturnMessage("Payment " + payment.getoid() + " can not be processed because Invoice " + invoice.getoid() + " has been reversed.");              
      }
    }
    
    return sr;
  }

  
  public SciiRequest buildMainRequest()
  {
    com.sci.integrator.domain.core.User user = this.getcreatedBy();

    System.out.println("Pay Invoice Transaction: " + this.getoid());

    SciiRequest request = null;

    // *** Get Payment object ***
    Payment payment = this.getpayment();

    if (payment.getstatus() == Payment.STATUS_PENDING || payment.getstatus() == Payment.STATUS_FAILED)
    {
      System.out.println("  Payment: " + this.getpayment().getoid());
      request = new SciiRequest();
      request.setUrlExtension("ws/com.spiritsci.spimo.payInvoice?invoiceId=" + payment.getinvoice().getserverId()
          + "&amount=" + payment.getamount() + "&paymentMethodId=" + payment.getpaymentMethod().getserverId());
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
      this.setstatus(Transaction.STATUS_PROCESSED);
      this.getpayment().setstatus(Payment.STATUS_PROCESSED);
      this.getpayment().getinvoice().setstatus(Invoice.STATUS_PAID);
      System.out.println("Payment " + this.getpayment().getoid() + " processed in ERP");
    }
    else
    {
      this.setstatus(Transaction.STATUS_FAILED);
      this.getpayment().setstatus(Payment.STATUS_FAILED);
    }
  }

  public void processSubResponse(SciiResponse response)
  {
    // TODO Auto-generated method stub

  }

  public void validate() throws SciiException
  {
    super.validate();

    if (this.getpayment().getinvoiceServerOid() == 0)
    {
      SciiException e = new SciiException("Transaction Validation Error: Invoice's oid for this payment is 0 or null");
      throw e;
    }

    System.out.println("END - Transaction Validation.");
  }
}
