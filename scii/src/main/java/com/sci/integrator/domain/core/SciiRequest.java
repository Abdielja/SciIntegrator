/**
 * 
 */
package com.sci.integrator.domain.core;

import java.util.HashMap;
import java.util.Map;

import javax.xml.soap.SOAPMessage;

import org.springframework.http.HttpMethod;

/**
 * @author Abdiel
 *
 */
public class SciiRequest
{

  // ***** Properties ***** 

  private String tag;
  private String urlExtension;
  private String strRequest;
  private SOAPMessage soapRequest;
  private HttpMethod httpMethod;
  private String whereClause;
  private Map<String, String> vars;

  private int status;
  
  /**
   * @return the tag
   */
  public String getTag()
  {
    return tag;
  }

  /**
   * @param tag the tag to set
   */
  public void setTag(String tag)
  {
    this.tag = tag;
  }

  /**
   * @return the urlExtension
   */
  public String getUrlExtension()
  {
    return urlExtension;
  }

  /**
   * @param urlExtension the urlExtension to set
   */
  public void setUrlExtension(String urlExtension)
  {
    this.urlExtension = urlExtension;
  }

  /**
   * @return the strRequest
   */
  public String getStrRequest()
  {
    return strRequest;
  }

  /**
   * @param strRequest the strRequest to set
   */
  public void setStrRequest(String strRequest)
  {
    this.strRequest = strRequest;
  }

  /**
   * @return the soapRequest
   */
  public SOAPMessage getSOAPRequest()
  {
    return soapRequest;
  }

  /**
   * @param status the status to set
   */
  public void setSOAPRequest(SOAPMessage soapRequest)
  {
    this.soapRequest = soapRequest;
  }

  /**
   * @return the status
   */
  public int getStatus()
  {
    return status;
  }

  /**
   * @param status the status to set
   */
  public void setStatus(int status)
  {
    this.status = status;
  }

  /**
   * @return the httpMethod
   */
  public HttpMethod getHttpMethod()
  {
    return httpMethod;
  }

  /**
   * @param httpMethod the httpMethod to set
   */
  public void setHttpMethod(HttpMethod httpMethod)
  {
    this.httpMethod = httpMethod;
  }

  /**
   * @return the whereClause
   */
  public String getWhereClause()
  {
    return whereClause;
  }

  /**
   * @param whereClause the whereClause to set
   */
  public void setWhereClause(String whereClause)
  {
    this.whereClause = whereClause;
  }

  // ***** Constructors *****  

  /**
   * @return the vars
   */
  public Map<String, String> getVars()
  {
    return vars;
  }

  /**
   * @param vars the vars to set
   */
  public void setVars(Map<String, String> vars)
  {
    this.vars = vars;
  }

  public SciiRequest()
  {    
    vars = new HashMap<String, String>();
  }

}
