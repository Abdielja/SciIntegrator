/**
 * 
 */
package com.sci.integrator.transaction;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * @author Abdiel Jaramillo Ojedis
 *
 */
@Entity
@Table(name = "transaction_object")
@Inheritance(strategy=InheritanceType.JOINED)
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class TransactionObject
{

  private long   oid;  

  // *** Oid ***
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name="oid")
  public long getoid() 
  {
    return oid;
  }
  
  public void setoid(long oid) 
  {
    this.oid = oid;
  }
  
  
}
