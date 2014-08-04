/**
 * 
 */
package com.sci.integrator.domain.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Abdiel Jaramillo Ojedis
 *
 */
@XmlRootElement
public class Transactions implements Serializable
{

  // ***** Constants *****
  
  private static final long serialVersionUID = 1L;

  // ***** Properties *****
  
  private List<Transaction> transaction = new ArrayList<Transaction>();

  /**
   * @return the company
   */
  public List<Transaction> getTransaction()
  {
    return transaction;
  }

  /**
   * @param company the company to set
   */
  public void setTransaction(List<Transaction> transaction)
  {
    this.transaction = transaction;
  }
  
  // ***** Public Methods *****
  
  public void add(Transaction _transaction)
  {
    this.transaction.add(_transaction);
  }
}
