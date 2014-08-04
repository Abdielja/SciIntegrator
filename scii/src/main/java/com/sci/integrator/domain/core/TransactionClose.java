/**
 * 
 */
package com.sci.integrator.domain.core;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.sci.integrator.services.ITransactionService;
import com.sci.integrator.services.IUserDataService;
import com.sci.integrator.services.IUserService;

/**
 * @author Abdiel Jaramillo Ojedis
 *
 */

@Entity
@XmlRootElement(name = "transactionClose")
@DiscriminatorValue(value = "101")
@XmlAccessorType(XmlAccessType.FIELD)
@SuppressWarnings("serial")
public class TransactionClose extends Transaction
{

  @Inject
  IUserService userService;
  
  @Inject 
  IUserDataService userDataService;
  
  /**
   * 
   */
  public TransactionClose()
  {
    // TODO Auto-generated constructor stub
  }

  public SciiResult beforProcess() throws SciiException
  {
    
    SciiResult sciiResult = new SciiResult();
    
    User user = this.getcreatedBy();

    // *** Update user's data object ***
    UserData userData = this.userDataService.getByUserOid(user.getoid());
    userData.setEndDate(new Date());
    this.userDataService.update(userData);
    
    // *** Replicate user's data object to history table ***
        
    // *** Update user's status ***
    user.setStatus(User.STATUS_CLOSED);
    userService.update(user);
    
    sciiResult.setreturnCode(SciiResult.RETURN_CODE_OK);
    sciiResult.setreturnMessage("TransactionClose executed susccesfully");
    return sciiResult;
    
  }

  public SciiRequest buildMainRequest()
  {
    // TODO Auto-generated method stub
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

}
