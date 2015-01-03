/**
 * 
 */
package com.sci.integrator.domain.core;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Abdiel Jaramillo Ojedis
 *
 */

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SciiResult implements Serializable
{

  public static int RETURN_CODE_UNIDENTFIED_ERROR = 0;
  public static int RETURN_CODE_OK = 1;
  public static int RETURN_CODE_INVALID_CREDENTIALS = 2;
  public static int RETURN_CODE_INVALID_TRANSACTION_PARAMETER = 3;
  public static int RETURN_CODE_NON_EXISTENT_INVOICE_OID = 4;
  public static int RETURN_CODE_INVOICE_OID_ALREADY_EXISTS = 5;
  public static int RETURN_CODE_INVALID_PROVIDER = 6;
  public static int RETURN_CODE_INVALID_DATA_RETURNED = 7;
  
  private static final long serialVersionUID = 1L;
  
  // ***** Properties *****

  private int returnCode;
  private String returnMessage;
  private long  transactionOid;
  private long affectedObjectOid;
  private String affectedObjectServerId;
  
  private String transactionObject;
  
  // ** Return Code **
  public int getreturnCode()
  {
    return returnCode;
  }

  public void setreturnCode(int returCode)
  {
    this.returnCode = returCode;
  }

  public String getreturnMessage()
  {
    return returnMessage;
  }

  public void setreturnMessage(String returnMessage)
  {
    this.returnMessage = returnMessage;
  }

  // ** Transaction Oid **
  public long gettransactionOid()
  {
    return transactionOid;
  }

  public void settransactionOid(long transactionOid)
  {
    this.transactionOid = transactionOid;
  }

  // ** Affected Object Oid **
  public long getaffectedObjectOid()
  {
    return affectedObjectOid;
  }

  public void setaffectedObjectOid(long affectedObjectOid)
  {
    this.affectedObjectOid = affectedObjectOid;
  }

  // ***** Constructors *****
  
  /**
   * @return the affectedObjectServerId
   */
  public String getAffectedObjectServerId()
  {
    return affectedObjectServerId;
  }

  /**
   * @param affectedObjectServerId the affectedObjectServerId to set
   */
  public void setAffectedObjectServerId(String affectedObjectServerId)
  {
    this.affectedObjectServerId = affectedObjectServerId;
  }

  /**
   * @return the transactionObject
   */
  public String getTransactionObject()
  {
    return transactionObject;
  }

  /**
   * @param transactionObject the transactionObject to set
   */
  public void setTransactionObject(String transactionObject)
  {
    this.transactionObject = transactionObject;
  }

  // ***** Constructors *****
  
  public SciiResult()
  {
  }

  public SciiResult(int returnCode)
  {
    this.returnCode = returnCode;
  }

}
