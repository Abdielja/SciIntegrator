/**
 * 
 */
package com.sci.integrator.domain.customer;

import java.util.Date;
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
import com.sci.integrator.domain.core.Transaction;
import com.sci.integrator.domain.core.User;

/**
 * @author Abdiel Jaramillo Ojedis
 *
 */

@Entity
@XmlRootElement(name = "transactionCustomerExtra")
@DiscriminatorValue(value = "7")
@XmlAccessorType(XmlAccessType.FIELD)
public class TransactionCustomerExtra extends Transaction
{

  /**
   * Constants
   */
  
  private static final long serialVersionUID = 1L;

  /**
   * Properties
   */
  
  private CustomerExtra customerExtra;
  
  /**
   * @return the customerExtra
   */
  @OneToOne(targetEntity=CustomerExtra.class)
  @Cascade({CascadeType.SAVE_UPDATE})
  @JoinColumn(name="trx_object_oid")
  public CustomerExtra getCustomerExtra()
  {
    return customerExtra;
  }

  /**
   * @param customerExtra the customerExtra to set
   */
  public void setCustomerExtra(CustomerExtra customerExtra)
  {
    this.customerExtra = customerExtra;
  }

 
  
  /**
   * Constructors
   */
  
  public TransactionCustomerExtra()
  {
    // TODO Auto-generated constructor stub
  }

  
  
  /**
   * Transaction Implementation Methods
   */
  
  public SciiRequest buildMainRequest()
  {
    System.out.println("Processed.");
    return null;
  }

  public List<SciiRequest> buildSubRequests()
  {
    // TODO Auto-generated method stub
    return null;
  }

  public void processMainResponse(SciiResponse response)
  {
    // TODO Auto-generated method stub
    
  }

  public void processSubResponse(SciiResponse response)
  {
    // TODO Auto-generated method stub
    
  }

  public void validate() throws SciiException
  {
    super.validate();
    System.out.println("END - Transaction Validation.");
  }
  
}
