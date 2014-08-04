/**
 * 
 */
package com.sci.integrator.domain.core;

/**
 * @author Abdiel Jaramillo O.
 *
 */
public class SciiException extends Exception
{

  private static final long serialVersionUID = 1L;

  private int sciiReturnCode;
  
  /**
   * @return the sciiReturnCode
   */
  public int getSciiReturnCode()
  {
    return sciiReturnCode;
  }

  /**
   * @param sciiReturnCode the sciiReturnCode to set
   */
  public void setSciiReturnCode(int sciiReturnCode)
  {
    this.sciiReturnCode = sciiReturnCode;
  }

  public SciiException(String message)
  {
    super(message);
  }
  
  public SciiException(String message, int returnCode)
  {
    super(message);
    this.setSciiReturnCode(returnCode);
  }
  
}
