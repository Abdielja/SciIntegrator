/**
 * 
 */
package com.sci.integrator.domain.core;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Abdiel
 *
 */

@XmlRootElement
public class AuthenticationResult implements Serializable
{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private int returnCode;

  /**
   * @return the returnCode
   */
  public int getReturnCode()
  {
    return returnCode;
  }

  /**
   * @param returnCode the returnCode to set
   */
  public void setReturnCode(int returnCode)
  {
    this.returnCode = returnCode;
  }
  
  // ***** Constructors *****
  
  public AuthenticationResult()
  {  
    
  }

}
