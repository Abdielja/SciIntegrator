/**
 * 
 */
package com.sci.integrator.provider.openbravo;


import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.xml.namespace.QName;
import javax.xml.transform.dom.DOMSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sci.integrator.domain.core.Companies;
import com.sci.integrator.domain.core.Company;
import com.sci.integrator.domain.core.Role;
import com.sci.integrator.domain.core.Roles;
import com.sci.integrator.domain.core.User;
import com.sci.integrator.domain.core.UserData;
import com.sci.integrator.domain.invoice.Invoice;
import com.sci.integrator.helpers.XmlHelper;
import com.sci.integrator.provider.IProvider;
import com.sci.integrator.provider.IUserProviderGateway;
import com.sci.integrator.provider.RestBaseProviderGateway;

/**
 * @author Abdiel Jaramillo O.
 *
 */
public class ObUserProviderGateway extends RestBaseProviderGateway implements IUserProviderGateway
{

  private static final Logger logger = LoggerFactory.getLogger(ObUserProviderGateway.class);

  private RestTemplate restTemplate;  
  
  public ObUserProviderGateway(String baseUrl)
  {
    this.setBaseUrl(baseUrl);
    this.restTemplate = new RestTemplate();    
  }
  
  public UserData getUserByName(String username)
  {
    
    String strUrl = getBaseUrl() +  "ws/dal/ADUser?where={whereClause}";
    String whereClause = "username='" + username + "'";
    
        
    ResponseEntity<DOMSource> response = restTemplate.exchange(strUrl, HttpMethod.GET, 
        new HttpEntity<Object>(createHeaders("Openbravo", "SPIRITSCIOB")), DOMSource.class, whereClause);

    Node xmlDoc = response.getBody().getNode();
        
    UserData user = new UserData();
    
    try
    {
      
      // ** Get user's basic information **
      user.setUserServerId((String)XmlHelper.readFromXml(xmlDoc, "//ADUser//id", XPathConstants.STRING));
      user.setName((String)XmlHelper.readFromXml(xmlDoc, "//ADUser//name", XPathConstants.STRING));
      user.setBusinessPartnerId((String)XmlHelper.readFromXml(xmlDoc, "//ADUser//businessPartner/@id", XPathConstants.STRING));
      user.setoid(1);
      
      // ** Get companies **
      //user.setCompanies(getUserCompanies());
      
      // ** Get roles **
      Roles roles = new Roles();
      NodeList nRoles = (NodeList)XmlHelper.readFromXml(xmlDoc, "//ADUser//aDUserRolesList/ADUserRoles", XPathConstants.NODESET);
      
      for (int i=0; i<nRoles.getLength(); i++)
      {
        Node nRole = nRoles.item(i);
        String roleId = (String)XmlHelper.readFromXml(nRole, "role/@id", XPathConstants.STRING);       
        Role role = getUserRoleById(roleId);

        roles.add(role);
      }

      //user.setRoles(roles);
      
    } 
    catch (Exception e)
    {
      e.printStackTrace();
    }
    
    return user;
    
  }

  private Companies getUserCompanies()
  {

    String strUrl = getBaseUrl() + "ws/dal/Organization?where={whereClause}";
    String whereClause = "organizationType.id=1";
    
    ResponseEntity<DOMSource> response = restTemplate.exchange(strUrl, HttpMethod.GET, 
        new HttpEntity<Object>(createHeaders("Openbravo", "SPIRITSCIOB")), DOMSource.class, whereClause);
    
    Node xmlDoc = response.getBody().getNode();
    
    Companies companies = new Companies();

    NodeList nCompanies = (NodeList)XmlHelper.readFromXml(xmlDoc, "//Organization", XPathConstants.NODESET);

    for (int i=0; i<nCompanies.getLength(); i++)
    {
      Node nCompany = nCompanies.item(i);
      Company company = new Company();
      company.setServerId((String)XmlHelper.readFromXml(nCompany, "id", XPathConstants.STRING));
      company.setName((String)XmlHelper.readFromXml(nCompany, "name", XPathConstants.STRING));
      
      // ** Get Company extra Info **
      company = getUserCompanyExtra(company);
      
      companies.add(company);
    }
    
    return companies;
  
  }
  
  private Company getUserCompanyExtra(Company company)
  {

    String strUrl = getBaseUrl() + "ws/dal/OrganizationInformation?where={whereClause}";
    String whereClause = "organization.id='" + company.getServerId() + "'";
    
    ResponseEntity<DOMSource> response = restTemplate.exchange(strUrl, HttpMethod.GET, 
        new HttpEntity<Object>(createHeaders("Openbravo", "SPIRITSCIOB")), DOMSource.class, whereClause);
    
    Node xmlDoc = response.getBody().getNode();
    
    Node nCompany = (Node)XmlHelper.readFromXml(xmlDoc, "//OrganizationInformation", XPathConstants.NODE);

    company.setRuc((String)XmlHelper.readFromXml(nCompany, "dndRuc", XPathConstants.STRING));

    return company;
  
  }
  
  private Role getUserRoleById(String roleId)
  {

    String strUrl = getBaseUrl() + "ws/dal/ADRole?where={whereClause}";
    String whereClause = "id='" + roleId + "'";
    
    ResponseEntity<DOMSource> response = restTemplate.exchange(strUrl, HttpMethod.GET, 
        new HttpEntity<Object>(createHeaders("Openbravo", "SPIRITSCIOB")), DOMSource.class, whereClause);
    
    Node xmlDoc = response.getBody().getNode();
    
    Node nRole = (Node)XmlHelper.readFromXml(xmlDoc, "//ADRole", XPathConstants.NODE);

    Role role = new Role();
    role.setServerId((String)XmlHelper.readFromXml(nRole, "id", XPathConstants.STRING));
    role.setName((String)XmlHelper.readFromXml(nRole, "name", XPathConstants.STRING));

    return role;
  
  }

  public void setDefaultUserRole(User user, String organizationId, String roleId, String warehouseId)
  throws HttpServerErrorException
  {
    
    HttpHeaders headers = this.createHeaders("DNDAdmin", "123");
  
    HttpEntity<String> httpEntity = new HttpEntity<String>(headers);    
    restTemplate.exchange(getBaseUrl() + "ws/com.spiritsci.spimo." +  "changeUserDefaultRole?userId=" + user.getserverId() + "&organizationId=" + organizationId + "&roleId=" + roleId + "&warehouseId=" + warehouseId, HttpMethod.GET, httpEntity, DOMSource.class);

 /*
    StringWriter sw = new StringWriter();
    
    sw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
    sw.write("<ob:Openbravo xmlns:ob=\"http://www.openbravo.com\">");
    sw.write("<ADUser id=\"" + user.getserverId() + "\">");
    sw.write("<defaultClient id=\"" + "1081EFAA3340C56701334C2555C601B9" + "\"/>");
    sw.write("<defaultRole id=\"" + roleId + "\"/>");
    sw.write("<defaultOrganization id=\"" + organizationId + "\"/>");
    sw.write("<defaultWarehouse id=\"" + warehouseId + "\"/>");
    sw.write("</ADUser>");
    sw.write("</ob:Openbravo>");
    
    String strRequest = sw.toString();
    
    HttpEntity<String> httpEntity = new HttpEntity<String>(strRequest, headers);
    
    try
    {
      ResponseEntity<DOMSource> responseEntity = restTemplate.exchange(strUrl2, HttpMethod.POST, httpEntity, DOMSource.class);
      System.out.println("User default role has been changed.");
    }
    catch(HttpServerErrorException e)
    {
      System.out.println(e.getResponseBodyAsString());
      throw e; 
    }
*/
    
  }
  
}
