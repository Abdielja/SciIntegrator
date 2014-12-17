/**
 * 
 */
package com.sci.integrator.transaction;

import java.util.List;

import com.sci.integrator.domain.core.SciiException;
import com.sci.integrator.domain.core.SciiRequest;
import com.sci.integrator.domain.core.SciiResponse;
import com.sci.integrator.domain.core.SciiResult;

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
