/**
 * 
 */
package com.sci.integrator.domain.core;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.transform.dom.DOMSource;
import javax.xml.xpath.XPathConstants;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpServerErrorException;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sci.integrator.helpers.XmlHelper;

/**
 * @author Abdiel
 *
 */
@XmlRootElement
public class SciiResponse
{

  // ***** Properties *****
  
  private int index;  // Used for multi-request transactions
  private String tag; // Used for multi-request transactions
  private Exception exception;
  private ResponseEntity<DOMSource> responseEntity;
  
  /**
   * @return the index
   */
  public int getIndex()
  {
    return index;
  }

  /**
   * @param index the index to set
   */
  public void setIndex(int index)
  {
    this.index = index;
  }

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
   * @return the exception
   */
  public Exception getException()
  {
    return exception;
  }

  /**
   * @param se the exception to set
   */
  public void setException(Exception se)
  {
    this.exception = se;
  }

 
  /**
   * @return the responseEntity
   */
  public ResponseEntity<DOMSource> getResponseEntity()
  {
    return responseEntity;
  }

  /**
   * @param responseEntity the responseEntity to set
   */
  public void setResponseEntity(ResponseEntity<DOMSource> responseEntity)
  {
    this.responseEntity = responseEntity;
  }

  // ***** Constructors *****
  
  public SciiResponse()
  {
    // TODO Auto-generated constructor stub
  }

  public String getErrorMessage()
  {
    String retVal = "";

    Node xmlDoc = responseEntity.getBody().getNode();
    Node xmlError = (Node)XmlHelper.readFromXml(xmlDoc, "//Error", XPathConstants.NODE);
    
    if (xmlError == null)
    {
      retVal = "NONE";
    }
    else
    {
      retVal = ((String)XmlHelper.readFromXml(xmlError, "Message", XPathConstants.STRING));
    }
    
    return retVal;
  }
  
}
