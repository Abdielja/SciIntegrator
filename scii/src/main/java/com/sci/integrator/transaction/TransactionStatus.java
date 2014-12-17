/**
 * 
 */
package com.sci.integrator.transaction;

/**
 * @author Abdiel Jaramillo Ojedis
 *
 */
public class TransactionStatus
{

  private int oid;
  private String description;

  // ***** Get/Set Methods *****
  
  //** Oid ***
  public int getOid()
  {
    return oid;
  }
  public void setOid(int oid)
  {
    this.oid = oid;
  }
  
  //** Description ***
  public String getDescription()
  {
    return description;
  }
  public void setDescription(String description)
  {
    this.description = description;
  }
  
  // ***** Constructors *****
  
  public TransactionStatus()
  {
  }
  
  public TransactionStatus(int _oid, String _description)
  {
    oid = _oid;
    description = _description;
  }
  

}
