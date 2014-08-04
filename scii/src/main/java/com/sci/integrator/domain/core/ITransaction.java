/**
 * 
 */
package com.sci.integrator.domain.core;

import java.util.List;

/**
 * @author Abdiel Jaramillo Ojedis
 *
 */
public interface ITransaction
{

  public void validate() throws SciiException;
  
  public SciiResult beforProcess() throws SciiException;
  
  public SciiResult process();
  
  public SciiRequest buildMainRequest();
  public List<SciiRequest> buildSubRequests();
  
  public void processMainResponse(SciiResponse response);
  public void processSubResponse(SciiResponse response);

  public SciiResult afterProcess();

}
