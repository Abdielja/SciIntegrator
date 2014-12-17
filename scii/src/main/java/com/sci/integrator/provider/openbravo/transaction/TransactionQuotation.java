/**
 * 
 */
package com.sci.integrator.provider.openbravo.transaction;

import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.transform.dom.DOMSource;
import javax.xml.xpath.XPathConstants;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpServerErrorException;
import org.w3c.dom.Node;

import com.sci.integrator.domain.core.SciiException;
import com.sci.integrator.domain.core.SciiRequest;
import com.sci.integrator.domain.core.SciiResponse;
import com.sci.integrator.domain.core.SciiResult;
import com.sci.integrator.domain.quotation.Quotation;
import com.sci.integrator.domain.quotation.QuotationLine;
import com.sci.integrator.helpers.XmlHelper;
import com.sci.integrator.transaction.Transaction;

/**
 * @author Abdiel Jaramillo Ojedis
 *
 */

@Entity
@XmlRootElement(name="transactionQuotation")
@DiscriminatorValue(value="0")
@XmlAccessorType(XmlAccessType.FIELD)
public class TransactionQuotation extends Transaction implements Serializable
{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  // ***** Properties *****
  
  private Quotation quotation;

  // *** Quotation  ***
  @OneToOne(targetEntity=Quotation.class)
  @Cascade({CascadeType.SAVE_UPDATE})
  @JoinColumn(name="trx_object_oid")
  public Quotation getquotation()
  {
    return quotation;
  }

  public void setquotation(Quotation quotation)
  {
    this.quotation = quotation;
  }

  // ***** ITransaction Implementation Methods *****
  
  public SciiRequest buildMainRequest()
  {
      
    SciiRequest request = null;
    
    // *** Get Quotation object ***
    Quotation quotation = this.getquotation(); 

    // *** Add Quotation Header insert string ***
    if (quotation.getstatus() == Quotation.STATUS_PENDING
        || quotation.getstatus() == Quotation.STATUS_FAILED)
    {
      System.out.println("  Quotation " + quotation.getoid() + " request created.");

      request = new SciiRequest();
      request.setUrlExtension("ws/dal/Order");
      request.setStrRequest(buildPostQuotationRequest(quotation));
      request.setHttpMethod(HttpMethod.POST);
      request.setWhereClause("");
    }
    
    return request;

  }

  public List<SciiRequest> buildSubRequests()
  {
    
    ArrayList<SciiRequest> requests = new ArrayList<SciiRequest>();

    // *** Add Quotation Lines insert strings ***
    List<QuotationLine> quotationsLines = quotation.getquotationLine();
    for (int i = 0; i < quotationsLines.size(); i++)
    {
      if (quotationsLines.get(i).getstatus() == Quotation.STATUS_PENDING
          || quotationsLines.get(i).getstatus() == Quotation.STATUS_FAILED)
      {
        QuotationLine line = quotationsLines.get(i);
        System.out.println("    Quotation line: "
            + line.getlineNumber() + " request created.");
               
        SciiRequest request = new SciiRequest();
        request.setUrlExtension("ws/dal/OrderLine");
        request.setStrRequest(buildPostQuotationLineRequest(line));
        request.setHttpMethod(HttpMethod.POST);
        request.setWhereClause("");
        
        requests.add(request);
      }
    }

    return requests;
  }
  
  public void processMainResponse(SciiResponse response)
  {
    
    if (response.getException() == null)
    {
      
      ResponseEntity<DOMSource> responseEntity = response.getResponseEntity();
      Node xmlDoc = responseEntity.getBody().getNode();
      String id = (String) XmlHelper.readFromXml(xmlDoc,
          "//inserted//Order/@id", XPathConstants.STRING);
      this.getquotation().setserverId(id);
      this.getquotation().setstatus(Quotation.STATUS_DRAFT);
      System.out.println("  Quotation Id = " + id + " processed.");
    }
    else
    {
      this.getquotation().setstatus(Quotation.STATUS_FAILED);
    }
    
  }

  public void processSubResponse(SciiResponse response)
  {

    if (response.getException() == null)
    {
      
      QuotationLine line = this.getquotation().getquotationLine().get(response.getIndex());

      if (line.getstatus() != Quotation.STATUS_COMPLETE)
      {
        ResponseEntity<DOMSource> responseEntity = response.getResponseEntity();
        Node xmlDoc = responseEntity.getBody().getNode();
        String id = (String) XmlHelper.readFromXml(xmlDoc,
            "//inserted//OrderLine/@id", XPathConstants.STRING);
        
        line.setstatus(Quotation.STATUS_COMPLETE);
        
        System.out.println("    QuotationLine Id = " + id + " processed.");
      }
    }
    else
    {
      this.getquotation().setstatus(Quotation.STATUS_FAILED);
    }

  }


  // ***** Private Methods *****
  
  private String buildPostQuotationRequest(Quotation quotation)
  {

    StringWriter sw = new StringWriter();

    sw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
    sw.write("<ob:Openbravo xmlns:ob=\"http://www.openbravo.com\">");

    sw.write("<Order>");
    sw.write("<client id=\"" + quotation.getclientId() + "\"/>");
    sw.write("<organization id=\"" + quotation.getorganizationId() + "\"/>");
    sw.write("<active>true</active>");
    sw.write("<salesTransaction>true</salesTransaction>");
    // sw.write("<documentNo>" + _order.DocumentNo + "</documentNo>");
    sw.write("<documentNo></documentNo>");

    sw.write("<documentStatus>DR</documentStatus>");
    sw.write("<documentAction>CO</documentAction>");
    sw.write("<processNow>false</processNow>");
    sw.write("<processed>false</processed>");
    sw.write("<posted>N</posted>");
    sw.write("<documentType id=\"0\"/>"); // ** New Document **
    sw.write("<transactionDocument id=\"0A1D4A2DB7144D81A821E31A9B332ACB\"/>"); // **
                                                                                // Quotation
                                                                                // //
                                                                                // **
    sw.write("<print>N</print>");
    sw.write("<orderDate>" + quotation.getcreationDate() + "</orderDate>");
    sw.write("<accountingDate>" + quotation.getcreationDate()
        + "</accountingDate>");
    sw.write("<businessPartner id=\"" + quotation.getcustomerId() + "\"/>");
    sw.write("<partnerAddress id=\"" + quotation.getcustomerAddressId()
        + "\"/>");
    sw.write("<printDiscount>false</printDiscount>");
    sw.write("<currency id=\"100\"/>");
    sw.write("<formOfPayment>5</formOfPayment>");
    sw.write("<paymentMethod id=\""
        + quotation.getpaymentMethod().getserverId() + "\"/>");
    sw.write("<paymentTerms id=\"" + quotation.getpaymentTermsId() + "\"/>");
    sw.write("<priceList id=\"" + quotation.getpriceListId() + "\"/>");
    sw.write("<chargeAmount>0</chargeAmount>");
    sw.write("<summedLineAmount>" + quotation.getsubTotal()
        + "</summedLineAmount>");
    sw.write("<grandTotalAmount>" + quotation.gettotal()
        + "</grandTotalAmount>");
    sw.write("<warehouse id=\"" + this.getwarehouseId() + "\"/>");

    sw.write("</Order>");

    sw.write("</ob:Openbravo>");

    return sw.toString();
  }

  private String buildPostQuotationLineRequest(QuotationLine quotationLine)
  {

    StringWriter sw = new StringWriter();

    sw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
    sw.write("<ob:Openbravo xmlns:ob=\"http://www.openbravo.com\">");

    sw.write("<OrderLine>");

    sw.write("<client id=\"" + quotationLine.getQuotation().getclientId()
        + "\"/>");
    sw.write("<organization id=\""
        + quotationLine.getQuotation().getorganizationId() + "\"/>");
    sw.write("<active>true</active>");
    sw.write("<salesOrder id=\"" + quotationLine.getQuotation().getserverId()
        + "\"/>");
    sw.write("<lineNo>" + quotationLine.getlineNumber() + "</lineNo>");
    sw.write("<product id=\"" + quotationLine.getproductId() + "\"/>");
    sw.write("<orderedQuantity>" + quotationLine.getquantity()
        + "</orderedQuantity>");
    sw.write("<listPrice>" + quotationLine.getprice() + "</listPrice>");
    sw.write("<unitPrice>" + quotationLine.getprice() + "</unitPrice>");
    sw.write("<priceLimit>" + quotationLine.getprice() + "</priceLimit>");
    sw.write("<lineNetAmount>" + quotationLine.getnetAmount()
        + "</lineNetAmount>");
    sw.write("<uOM id=\"100\"/>");
    sw.write("<tax id=\"" + quotationLine.gettaxRateId() + "\"/>");
    sw.write("<descriptionOnly>false</descriptionOnly>");
    sw.write("<standardPrice>" + quotationLine.getprice() + "</standardPrice>");
    sw.write("<editLineAmount>false</editLineAmount>");
    sw.write("<orderDate>" + quotationLine.getQuotation().getcreationDate()
        + "</orderDate>");
    sw.write("<warehouse id=\"" + this.getwarehouseId() + "\"/>");
    sw.write("<currency id=\"100\"/>");

    sw.write("</OrderLine>");

    sw.write("</ob:Openbravo>");

    return sw.toString();
  }

  public void validate() throws SciiException
  {
    super.validate();
    System.out.println("END - Transaction Validation.");
  }
 
}
