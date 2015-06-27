/**
 * 
 */
package com.sci.integrator.provider.openbravo.transaction;

import java.io.Serializable;
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

import com.sci.integrator.domain.core.SciiException;
import com.sci.integrator.domain.core.SciiRequest;
import com.sci.integrator.domain.core.SciiResponse;
import com.sci.integrator.domain.incidence.Incidence;
import com.sci.integrator.transaction.Transaction;

/**
 * @author Abdiel Jaramillo Ojedis
 *
 */

@Entity
@XmlRootElement(name="transactionIncidence")
@DiscriminatorValue(value="6")
@XmlAccessorType(XmlAccessType.FIELD)
public class TransactionIncidence extends Transaction implements Serializable
{

  private static final long serialVersionUID = 1L;

  // ***** Properties *****
  
  private Incidence incidence;

  /**
   * @return the incidence
   */
  // *** Incidence  ***
  @OneToOne(targetEntity=Incidence.class)
  @Cascade({CascadeType.SAVE_UPDATE})
  @JoinColumn(name="trx_object_oid")
  public Incidence getIncidence()
  {
    return incidence;
  }

  /**
   * @param incidence the incidence to set
   */
  public void setIncidence(Incidence incidence)
  {
    this.incidence = incidence;
  }

  
  // ***** Construtors *****
  
  public TransactionIncidence()
  {
    // TODO Auto-generated constructor stub
  }

  // ***** ITransaction Implementation Methods *****
  
  public SciiRequest buildMainRequest()
  {
	  
	SciiRequest sr = new SciiRequest();
	sr.setStatus(STATUS_PROCESSED);
	
    return sr;
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
