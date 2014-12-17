/**
 * 
 */
package com.sci.integrator.provider.openbravo.transaction;

import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.transform.dom.DOMSource;
import javax.xml.xpath.XPathConstants;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.w3c.dom.Node;

import com.sci.integrator.domain.core.SciiException;
import com.sci.integrator.domain.core.SciiRequest;
import com.sci.integrator.domain.core.SciiResponse;
import com.sci.integrator.domain.core.SciiResult;
import com.sci.integrator.domain.invoice.Invoice;
import com.sci.integrator.domain.invoice.InvoiceLine;
import com.sci.integrator.domain.order.Order;
import com.sci.integrator.domain.order.OrderLine;
import com.sci.integrator.helpers.XmlHelper;
import com.sci.integrator.transaction.Transaction;

/**
 * @author Abdiel Jaramillo Ojedis
 * 
 */

@Entity
@XmlRootElement(name = "transactionInvoice")
@DiscriminatorValue(value = "2")
@XmlAccessorType(XmlAccessType.FIELD)
public class TransactionInvoice extends Transaction implements Serializable
{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  // ***** Properties *****

  // @XmlElement(name="invoice")
  private Invoice           invoice;

  // *** Invoice ***
  @OneToOne(targetEntity = Invoice.class)
  @Cascade({ CascadeType.SAVE_UPDATE })
  @JoinColumn(name = "trx_object_oid")
  public Invoice getinvoice()
  {
    return invoice;
  }

  public void setinvoice(Invoice invoice)
  {
    this.invoice = invoice;
  }

  public SciiResult process()
  {
    SciiResult result = null;

    super.process();

    return result;
  }

  // ***** ITransaction methods implementation *****

  public SciiRequest buildMainRequest()
  {

    SciiRequest request = null;

    // *** Get Order object ***
    Invoice invoice = this.getinvoice();

    // *** Add Invoice Header insert string ***
    if (invoice.getstatus() == Invoice.STATUS_PENDING
        || invoice.getstatus() == Invoice.STATUS_FAILED)
    {
      System.out.println("  Invoice " + invoice.getoid() + " request created.");

      request = new SciiRequest();
//      request.setUrlExtension("ws/dal/Invoice");
      request.setUrlExtension("ws/com.spiritsci.spimo.createInvoice");
      request.setStrRequest(buildPostInvoiceRequest(invoice));
      request.setHttpMethod(HttpMethod.POST);
      request.setWhereClause("");
    }

    return request;

  }

  public List<SciiRequest> buildSubRequests()
  {

    ArrayList<SciiRequest> requests = new ArrayList<SciiRequest>();

    /* *** Not used in version 3.0 ***

    // *** Add Order Lines insert requests ***
    List<InvoiceLine> invoiceLines = invoice.getinvoiceLine();
    for (int i = 0; i < invoiceLines.size(); i++)
    {
      if (invoiceLines.get(i).getstatus() == Order.STATUS_PENDING
          || invoiceLines.get(i).getstatus() == Order.STATUS_FAILED)
      {
        InvoiceLine line = invoiceLines.get(i);
        System.out.println("    Invoice line: " + line.getlineNumber()
            + " request created.");

        SciiRequest request = new SciiRequest();
        request.setTag("Invoice Line");
        request.setUrlExtension("ws/dal/InvoiceLine");
        request.setStrRequest(buildPostInvoiceLineRequest(line));
        request.setHttpMethod(HttpMethod.POST);
        request.setWhereClause("");

        requests.add(request);
      }
    }

    // *** Add request to complete the invoice ***
    SciiRequest request = new SciiRequest();
    request.setTag("Process Invoice");
    request
        .setUrlExtension("ws/com.spiritsci.spimo.processInvoice?invoiceId="
            + invoice.getserverId());
    request.setStrRequest("");
    request.setHttpMethod(HttpMethod.GET);
    request.setWhereClause("");

    requests.add(request);
*/
    return requests;
  }

  public void processMainResponse(SciiResponse response)
  {

    if (response.getException() == null)
    {

      ResponseEntity<DOMSource> responseEntity = response.getResponseEntity();
      Node xmlDoc = responseEntity.getBody().getNode();
      
      String id = (String) XmlHelper.readFromXml(xmlDoc, "//Invoice/id", XPathConstants.STRING);
      String identifier = (String) XmlHelper.readFromXml(xmlDoc, "//Invoice/identifier", XPathConstants.STRING);
 
      StringTokenizer st = new StringTokenizer(identifier, " ");
      String docNumber = st.nextToken();
      
      this.getinvoice().setserverId(id);
      this.getinvoice().setdocumentNumber(docNumber);
      this.getinvoice().setstatus(Invoice.STATUS_COMPLETE);
      
      this.setstatus(Transaction.STATUS_PROCESSED);
      System.out.println("  Invoice Id = " + id + ". document no. " + docNumber + " created in ERP.");

    } 
    else
    {
      this.getinvoice().setstatus(Invoice.STATUS_FAILED);
      this.setstatus(Transaction.STATUS_FAILED);
    }

  }

  public void processSubResponse(SciiResponse response)
  {

    // *** Not needed since version 3.0 ***
    
/*  
    if (response.getTag() != null && response.getTag().equals("Invoice Line"))
    {
      if (response.getException() == null)
      {

        InvoiceLine line = this.getinvoice().getinvoiceLine()
            .get(response.getIndex());

        if (line.getstatus() != Invoice.STATUS_DRAFT)
        {
          ResponseEntity<DOMSource> responseEntity = response
              .getResponseEntity();
          Node xmlDoc = responseEntity.getBody().getNode();
          String id = (String) XmlHelper.readFromXml(xmlDoc,
              "//inserted//InvoiceLine/@id", XPathConstants.STRING);

          line.setstatus(Invoice.STATUS_DRAFT);

          System.out.println("    InvoiceLine Id = " + id + " created in ERP.");
        }

        if (invoice.getstatus() == Invoice.STATUS_COMPLETE)
        {
          invoice.setstatus(Invoice.STATUS_SHIPED);
        }
      } 
      else
      {
        this.getinvoice().setstatus(Invoice.STATUS_FAILED);
      }

    }

    if (response.getTag() != null && response.getTag().equals("Process Invoice"))
    {
      this.getinvoice().setstatus(Invoice.STATUS_COMPLETE);
      System.out.println("  Invoice Id = " + this.getinvoice().getserverId() + " processed in ERP.");
    }

*/

  }
  
  
  // ***** Helper methods *****

  private String buildPostInvoiceRequest(Invoice invoice)
  {

    StringWriter sw = new StringWriter();

    //sw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
    //sw.write("<ob:Openbravo xmlns:ob=\"http://www.openbravo.com\">");

    sw.write(invoice.toXML());
    
/*    
    sw.write("<Invoice>");
    //sw.write("<createdBy>");
    //sw.write("<ADUser id=\"" + this.getcreatedBy().getserverId() + "\" />");
    //sw.write("</createdBy>");
    sw.write("<client id=\"" + invoice.getclientId() + "\"/>");
    sw.write("<organization id=\"" + invoice.getorganizationId() + "\"/>");
    sw.write("<active>true</active>");
    sw.write("<salesTransaction>true</salesTransaction>");
    // sw.write("<documentNo>" + _order.DocumentNo + "</documentNo>");
    sw.write("<documentNo></documentNo>");

    sw.write("<documentStatus>DR</documentStatus>");
    sw.write("<documentAction>CO</documentAction>");
    sw.write("<processNow>false</processNow>");
    sw.write("<processed>false</processed>");
    sw.write("<posted>N</posted>");
    sw.write("<documentType id=\"0\"/>"); // ** New AR InvoiceDO **
    // sw.write("<transactionDocument id=\"40EE9B1CD3B345FABEFDA62B407B407F\"/>");
    // // ** AR InvoiceDO **
    sw.write("<transactionDocument id=\"40EE9B1CD3B345FABEFDA62B407B407F\"/>"); // **
                                                                                // AR
                                                                                // InvoiceDO
                                                                                // **
    sw.write("<print>N</print>");
    sw.write("<invoiceDate>" + invoice.getcreationDate() + "</invoiceDate>");
    sw.write("<accountingDate>" + invoice.getcreationDate()
        + "</accountingDate>");
    sw.write("<businessPartner id=\"" + invoice.getcustomerId() + "\"/>");
    sw.write("<partnerAddress id=\"" + invoice.getcustomerAddressId() + "\"/>");
    sw.write("<printDiscount>false</printDiscount>");
    sw.write("<currency id=\"100\"/>");
    sw.write("<formOfPayment>5</formOfPayment>");
    sw.write("<paymentMethod id=\"" + invoice.getpaymentMethod().getserverId()
        + "\"/>");
    sw.write("<paymentTerms id=\"" + invoice.getpaymentTermsId() + "\"/>");
    sw.write("<priceList id=\"" + invoice.getpriceListId() + "\"/>");
    sw.write("<chargeAmount>0</chargeAmount>");
    sw.write("<summedLineAmount>" + invoice.getsubTotal()
        + "</summedLineAmount>");
    sw.write("<grandTotalAmount>" + invoice.gettotal() + "</grandTotalAmount>");
    sw.write("<totalPaid>0</totalPaid>");

    sw.write("</Invoice>");
*/
    
//    sw.write("</ob:Openbravo>");

    return sw.toString();
  }

  private String buildPostInvoiceLineRequest(InvoiceLine invoiceLine)
  {

    StringWriter sw = new StringWriter();

    sw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
    sw.write("<ob:Openbravo xmlns:ob=\"http://www.openbravo.com\">");

    sw.write("<InvoiceLine>");

    sw.write("<client id=\"" + invoiceLine.getInvoice().getclientId() + "\"/>");
    sw.write("<organization id=\""
        + invoiceLine.getInvoice().getorganizationId() + "\"/>");
    sw.write("<active>true</active>");
    sw.write("<invoice id=\"" + invoiceLine.getInvoice().getserverId() + "\"/>");
    sw.write("<lineNo>" + invoiceLine.getlineNumber() + "</lineNo>");
    sw.write("<financialInvoiceLine>false</financialInvoiceLine>");
    sw.write("<product id=\"" + invoiceLine.getproductId() + "\"/>");
    sw.write("<invoicedQuantity>" + invoiceLine.getquantity()
        + "</invoicedQuantity>");
    sw.write("<listPrice>" + invoiceLine.getprice() + "</listPrice>");
    sw.write("<unitPrice>" + invoiceLine.getprice() + "</unitPrice>");
    sw.write("<priceLimit>" + invoiceLine.getprice() + "</priceLimit>");
    sw.write("<lineNetAmount>" + invoiceLine.getnetAmount()
        + "</lineNetAmount>");
    sw.write("<uOM id=\"100\"/>");
    sw.write("<tax id=\"" + invoiceLine.gettaxRateId() + "\"/>");
    sw.write("<taxAmount>0</taxAmount>");
    sw.write("<descriptionOnly>false</descriptionOnly>");
    sw.write("<standardPrice>" + invoiceLine.getprice() + "</standardPrice>");
    sw.write("<editLineAmount>false</editLineAmount>");
    
    // *** Esto antes era dNDPINICIAL y dNDPFINAL se cambio para OBDND en el servidor de sci ***
    sw.write("<dndPinicial>" + invoiceLine.getplanoInicial() + "</dndPinicial>");
    sw.write("<dndPfinal>" + invoiceLine.getplanoFinal() + "</dndPfinal>");
    // *****************************************************************************************
    
    sw.write("</InvoiceLine>");

    sw.write("</ob:Openbravo>");

    return sw.toString();
  }

  public void validate() throws SciiException
  {
    super.validate();
    System.out.println("END - Transaction Validation.");
  }

}
