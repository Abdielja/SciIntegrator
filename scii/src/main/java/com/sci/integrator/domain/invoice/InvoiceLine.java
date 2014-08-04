/**
 * 
 */
package com.sci.integrator.domain.invoice;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


/**
 * @author Abdiel Jaramillo Ojedis
 *
 */

@Entity
@Table(name="invoice_line")
public class InvoiceLine implements Serializable
{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  private long oid;
  private Invoice invoice;
  private int lineNumber;
  private String taxRateId;
  private double netAmount;
  private String productId;
  private double price;
  private int quantity;
  private int status;
  private int planoInicial;
  private int planoFinal;
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name="oid")
  public long getOid()
  {
    return oid;
  }

  public void setOid(long oid)
  {
    this.oid = oid;
  }

  @ManyToOne
  @JoinColumn(name = "invoice_oid")
  @XmlTransient
  public Invoice getInvoice()
  {
    return invoice;
  }

  public void setInvoice(Invoice invoice)
  {
    this.invoice = invoice;
  }

  // *** Line number ***
  @Column(name="line_number")
  public int getlineNumber()
  {
    return lineNumber;
  }

  public void setlineNumber(int lineNumber)
  {
    this.lineNumber = lineNumber;
  }

  // *** Tax Rate Id ***
  @Column(name="tax_rate_id")
  public String gettaxRateId()
  {
    return taxRateId;
  }

  public void settaxRateId(String taxRateId)
  {
    this.taxRateId = taxRateId;
  }

  // *** Product Id ***
  @Column(name="product_id")
  public String getproductId()
  {
    return productId;
  }

  public void setproductId(String productId)
  {
    this.productId = productId;
  }

  // *** Price ***
  @Column(name="price")
  public double getprice()
  {
    return price;
  }

  public void setprice(double price)
  {
    this.price = price;
  }

  // *** Quantity ***
  @Column(name="quantity")
  public int getquantity()
  {
    return quantity;
  }

  public void setquantity(int quantity)
  {
    this.quantity = quantity;
  }

  // *** Net amount ***
  @Column(name="net_amount")
  public double getnetAmount()
  {
    return netAmount;
  }

  public void setnetAmount(double netAmount)
  {
    this.netAmount = netAmount;
  }

  // *** Status ***
  @Column(name="status")
  public int getstatus()
  {
    return status;
  }

  public void setstatus(int status)
  {
    this.status = status;
  }

  /**
   * @return the planoInicial
   */
  @Column(name="plano_inicial")
  @XmlElement(name="dndPinicial")
  public int getplanoInicial()
  {
    return planoInicial;
  }

  /**
   * @param planoInicial the planoInicial to set
   */
  public void setplanoInicial(int planoInicial)
  {
    this.planoInicial = planoInicial;
  }

  /**
   * @return the planoFinal
   */
  @Column(name="plano_final")
  @XmlElement(name="dndPfinal")
  public int getplanoFinal()
  {
    return planoFinal;
  }

  /**
   * @param planoFinal the planoFinal to set
   */
  public void setplanoFinal(int planoFinal)
  {
    this.planoFinal = planoFinal;
  }
  
  public String toXML()
  {
   
    StringBuilder sb = new StringBuilder();

    sb.append("<invoiceLine>");
    
    sb.append("<oid>" + this.getOid() + "</oid>");
    sb.append("<invoiceOid>" + this.getInvoice().getoid() + "</invoiceOid>");
    sb.append("<lineNumber>" + this.getlineNumber() + "</lineNumber>");
    sb.append("<productId>" + this.getproductId() + "</productId>");
    sb.append("<price>" + this.getprice() + "</price>");
    sb.append("<quantity>" + this.getquantity() + "</quantity>");
    sb.append("<netAmount>" + this.getnetAmount() + "</netAmount>");
    sb.append("<taxRateId>" + this.gettaxRateId() + "</taxRateId>");
    sb.append("<planoInicial>" + this.getplanoInicial() + "</planoInicial>");
    sb.append("<planoFinal>" + this.getplanoFinal() + "</planoFinal>");
    sb.append("<status>" + this.getstatus() + "</status>");

    sb.append("</invoiceLine>");

    return sb.toString();

  }

}
