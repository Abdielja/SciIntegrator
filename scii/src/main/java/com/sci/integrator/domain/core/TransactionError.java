/**
 * 
 */
package com.sci.integrator.domain.core;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Abdiel Jaramillo Ojedis
 *
 */
@Entity
@Table(name="transaction_error")
@XmlRootElement(name="error")
@XmlAccessorType(XmlAccessType.FIELD)
public class TransactionError implements Serializable
{
  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  private long oid;
  private String message;
  private String cause;
  private String stackTrace;

  /**
   * @return the oid
   */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name="oid")
  public long getOid()
  {
    return oid;
  }

  /**
   * @param oid the oid to set
   */
  public void setOid(long oid)
  {
    this.oid = oid;
  }

  /**
   * @return the message
   */
  @Column(name="message")
  public String getMessage()
  {
    return message;
  }

  /**
   * @param message the message to set
   */
  public void setMessage(String message)
  {
    this.message = message;
  }

  /**
   * @return the cause
   */
  @Column(name="cause")
  public String getCause()
  {
    return cause;
  }

  /**
   * @param cause the cause to set
   */
  public void setCause(String cause)
  {
    this.cause = cause;
  }

  /**
   * @return the stackTrace
   */
  @Column(name="stack_trace")
  public String getStackTrace()
  {
    return stackTrace;
  }

  /**
   * @param stackTrace the stackTrace to set
   */
  public void setStackTrace(String stackTrace)
  {
    this.stackTrace = stackTrace;
  }
  
}
