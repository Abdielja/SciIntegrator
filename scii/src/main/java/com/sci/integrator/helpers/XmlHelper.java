/**
 * 
 */
package com.sci.integrator.helpers;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Node;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;

/**
 * @author Abdiel
 *
 */
public class XmlHelper
{

  private static XPathFactory xpathFactory = XPathFactory.newInstance();
  private static XPath xpath = xpathFactory.newXPath();
  
  public static Object readFromXml(Node xmlDoc, String expression,  QName returnType)
  {
    
    try 
    {
      XPathExpression xPathExpression = xpath.compile(expression);
      return xPathExpression.evaluate (xmlDoc, returnType);
    } 
    catch (XPathExpressionException ex) 
    {
      ex.printStackTrace();
      return null;
    }
    
  }

  public static String getStringFromDoc(org.w3c.dom.Document doc)    
  {
    DOMImplementationLS domImplementation = (DOMImplementationLS) doc.getImplementation();
    LSSerializer lsSerializer = domImplementation.createLSSerializer();
    return lsSerializer.writeToString(doc);   
  }

}
