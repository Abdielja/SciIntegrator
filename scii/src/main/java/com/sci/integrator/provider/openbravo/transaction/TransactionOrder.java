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
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.w3c.dom.Node;

import com.sci.integrator.domain.core.SciiException;
import com.sci.integrator.domain.core.SciiRequest;
import com.sci.integrator.domain.core.SciiResponse;
import com.sci.integrator.domain.core.SciiResult;
import com.sci.integrator.domain.order.Order;
import com.sci.integrator.domain.order.OrderLine;
import com.sci.integrator.helpers.XmlHelper;
import com.sci.integrator.transaction.Transaction;

/**
 * @author Abdiel Jaramillo Ojedis
 *
 */

@Entity
@XmlRootElement(name="transactionOrder")
@DiscriminatorValue(value="1")
@XmlAccessorType(XmlAccessType.FIELD)
public class TransactionOrder extends Transaction implements Serializable
{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  
  // ***** Properties *****
  
  //@XmlElement(name="invoice")  
  private Order order;
  
  // *** Order  ***
  @OneToOne(targetEntity=Order.class)
  @Cascade({CascadeType.SAVE_UPDATE})
  @JoinColumn(name="trx_object_oid")
  public Order getorder()
  {
    return order;
  }

  public void setorder(Order order)
  {
    this.order = order;
  }

  public SciiResult process()
  {
    SciiResult result = null;

    super.process();

    return result;
  }

  // ***** ITransaction implementation methods *****
  
  public SciiRequest buildMainRequest()
  {
  
    SciiRequest request = null;
    
    // *** Get Order object ***
    Order order = this.getorder(); 

    // *** Add Order Header insert string ***
    if (order.getstatus() == Order.STATUS_PENDING
        || order.getstatus() == Order.STATUS_FAILED)
    {
      System.out.println("  Order " + order.getoid() + " request created.");

      request = new SciiRequest();
      request.setUrlExtension("ws/dal/Order");
      request.setStrRequest(buildPostOrderRequest(order));
      request.setHttpMethod(HttpMethod.POST);
      request.setWhereClause("");
    }
    
    return request;

  }

  public List<SciiRequest> buildSubRequests()
  {
    
    ArrayList<SciiRequest> requests = new ArrayList<SciiRequest>();

    // *** Add Order Lines insert strings ***
    List<OrderLine> orderLines = order.getorderLine();
    for (int i = 0; i < orderLines.size(); i++)
    {
      if (orderLines.get(i).getstatus() == Order.STATUS_PENDING
          || orderLines.get(i).getstatus() == Order.STATUS_FAILED)
      {
        OrderLine line = orderLines.get(i);
        System.out.println("    Order line: "
            + line.getlineNumber() + " request created.");
               
        SciiRequest request = new SciiRequest();
        request.setTag("Order Line");
        request.setUrlExtension("ws/dal/OrderLine");
        request.setStrRequest(buildPostOrderLineRequest(line));
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
      this.getorder().setserverId(id);
      this.getorder().setstatus(Order.STATUS_DRAFT);
      System.out.println("  Order Id = " + id + " processed.");
    }
    else
    {
      this.getorder().setstatus(Order.STATUS_FAILED);
    }
    
  }

  public void processSubResponse(SciiResponse response)
  {
    
    if (response.getException() == null)
    {
      
      OrderLine line = this.getorder().getorderLine().get(response.getIndex());

      if (line.getstatus() != Order.STATUS_DRAFT)
      {
        ResponseEntity<DOMSource> responseEntity = response.getResponseEntity();
        Node xmlDoc = responseEntity.getBody().getNode();
        String id = (String) XmlHelper.readFromXml(xmlDoc,
            "//inserted//OrderLine/@id", XPathConstants.STRING);
        
        line.setstatus(Order.STATUS_DRAFT);
        
        System.out.println("    OrderLine Id = " + id + " processed.");
      }
    }
    else
    {
      this.getorder().setstatus(Order.STATUS_FAILED);
    }

  }

  
  // ***** Helper methods *****
    
  private String buildPostOrderRequest(Order order)
  {

    StringWriter sw = new StringWriter();

    sw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
    sw.write("<ob:Openbravo xmlns:ob=\"http://www.openbravo.com\">");

    sw.write("<Order>");
    sw.write("<client id=\"" + order.getclientId() + "\"/>");
    sw.write("<organization id=\"" + order.getorganizationId() + "\"/>");
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
    sw.write("<transactionDocument id=\"CB6EEA256BBC41109911215C5A14D39B\"/>"); // **
                                                                                // Standard
                                                                                // Order
                                                                                // //
                                                                                // **
    sw.write("<print>N</print>");
    sw.write("<orderDate>" + order.getcreationDate() + "</orderDate>");
    sw.write("<accountingDate>" + order.getcreationDate() + "</accountingDate>");
    sw.write("<businessPartner id=\"" + order.getcustomerId() + "\"/>");
    sw.write("<partnerAddress id=\"" + order.getcustomerAddressId() + "\"/>");
    sw.write("<printDiscount>false</printDiscount>");
    sw.write("<currency id=\"100\"/>");
    sw.write("<formOfPayment>5</formOfPayment>");
    sw.write("<paymentMethod id=\"" + order.getpaymentMethod().getserverId()
        + "\"/>");
    sw.write("<paymentTerms id=\"" + order.getpaymentTermsId() + "\"/>");
    sw.write("<priceList id=\"" + order.getpriceListId() + "\"/>");
    sw.write("<chargeAmount>0</chargeAmount>");
    sw.write("<summedLineAmount>" + order.getsubTotal() + "</summedLineAmount>");
    sw.write("<grandTotalAmount>" + order.gettotal() + "</grandTotalAmount>");
    sw.write("<warehouse id=\"" + this.getwarehouseId() + "\"/>");

    sw.write("</Order>");

    sw.write("</ob:Openbravo>");

    return sw.toString();
  }

  private String buildPostOrderLineRequest(OrderLine orderLine)
  {

    StringWriter sw = new StringWriter();

    sw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
    sw.write("<ob:Openbravo xmlns:ob=\"http://www.openbravo.com\">");

    sw.write("<OrderLine>");

    sw.write("<client id=\"" + orderLine.getOrder().getclientId() + "\"/>");
    sw.write("<organization id=\"" + orderLine.getOrder().getorganizationId()
        + "\"/>");
    sw.write("<active>true</active>");
    sw.write("<salesOrder id=\"" + orderLine.getOrder().getserverId() + "\"/>");
    sw.write("<lineNo>" + orderLine.getlineNumber() + "</lineNo>");
    sw.write("<product id=\"" + orderLine.getproductId() + "\"/>");
    sw.write("<orderedQuantity>" + orderLine.getquantity()
        + "</orderedQuantity>");
    sw.write("<listPrice>" + orderLine.getprice() + "</listPrice>");
    sw.write("<unitPrice>" + orderLine.getprice() + "</unitPrice>");
    sw.write("<priceLimit>" + orderLine.getprice() + "</priceLimit>");
    sw.write("<lineNetAmount>" + orderLine.getnetAmount() + "</lineNetAmount>");
    sw.write("<uOM id=\"100\"/>");
    sw.write("<tax id=\"" + orderLine.gettaxRateId() + "\"/>");
    sw.write("<descriptionOnly>false</descriptionOnly>");
    sw.write("<standardPrice>" + orderLine.getprice() + "</standardPrice>");
    sw.write("<editLineAmount>false</editLineAmount>");
    sw.write("<orderDate>" + orderLine.getOrder().getcreationDate() + "</orderDate>");
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
