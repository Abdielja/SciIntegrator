/**
 * 
 */
package com.sci.integrator.controlers.rest;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.dom.DOMSource;
import javax.xml.xpath.XPathConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sci.integrator.controlers.dto.UserDto;
import com.sci.integrator.controlers.dto.UserDtoList;
import com.sci.integrator.controlers.view.UserMonitorController;
import com.sci.integrator.domain.core.AuthenticationResult;
import com.sci.integrator.domain.core.Company;
import com.sci.integrator.domain.core.Product;
import com.sci.integrator.domain.core.SciiException;
import com.sci.integrator.domain.core.SciiResult;
import com.sci.integrator.domain.core.User;
import com.sci.integrator.domain.core.UserData;
import com.sci.integrator.domain.core.UserPeriodCashDetail;
import com.sci.integrator.domain.core.UserPeriodData;
import com.sci.integrator.domain.core.UserPeriodPaymentDetail;
import com.sci.integrator.domain.core.UserPeriodProduct;
import com.sci.integrator.domain.core.Users;
import com.sci.integrator.domain.payment.PaymentMethod;
import com.sci.integrator.helpers.XmlHelper;
import com.sci.integrator.services.IPaymentMethodService;
import com.sci.integrator.services.IProductService;
import com.sci.integrator.services.IUserDataService;
import com.sci.integrator.services.IUserService;

/**
 * @author Abdiel Jaramillo O.
 *
 */

@Controller
@RequestMapping("/rest/users")
public class UserRestController
{

  private static final Logger logger = LoggerFactory.getLogger(UserMonitorController.class);

  @Inject
  private IUserService userService;

  @Inject
  private IUserDataService userDataService;

  @Inject
  private IProductService productService;
  
  @Inject
  private IPaymentMethodService paymentMethodService;
  
  // ***** Constructors *****
  
  
  // ***** Public Methods *****
  
  
  // *** Authenticate ***
  @RequestMapping(value = "/authenticate", method = RequestMethod.HEAD)  
  public @ResponseBody AuthenticationResult authenticate()
  {    
    AuthenticationResult ar = new AuthenticationResult();
    return ar;
  }
  
  @RequestMapping(value = "/getByOid", method = RequestMethod.GET)
  public @ResponseBody User getByOid(@RequestParam(value = "userOid", required = true) int userOid, Model model)
  {
    logger.info("Received request to retrieve user's data");

    // Delegate to service to do the actual fetch
    User user = userService.getByOid(userOid);

    // @ResponseBody will automatically convert the returned value into XML
    // format
    return user;
  }

  @RequestMapping(method = RequestMethod.GET)
  public @ResponseBody Users getAll()
  {
    logger.info("Received request to retrieve user's data");

    // Delegate to service to do the actual fetch
    List<User> usersList = userService.getAllActive();
    Users users = new Users();
    users.setCount(usersList.size());
    
    for(int i=0; i < usersList.size(); i++)
    {
      users.getUser().add(usersList.get(i));
    }
    
    return users;
  }

  @RequestMapping(value = "/getUserData",method = RequestMethod.GET)
  public @ResponseBody UserData getUserDataByOid(@RequestParam long userOid) throws SciiException
  {
    logger.info("Received request to retrieve user's data");

    // Delegate to service to do the actual fetch
    UserData userData = userDataService.getByUserOid(userOid);
    
    return userData;
  }

  @RequestMapping(value = "/closeUserPeriod", method = RequestMethod.POST)
  @ResponseStatus(HttpStatus.CREATED)
  public @ResponseBody SciiResult closeUserPeriod(@RequestBody DOMSource xmlTrx, HttpServletResponse response) throws Exception 
  {
    
    SciiResult result = new SciiResult();

    System.out.println("\n UserRestController...");
    
    System.out.println("\nClose User Period Transaction recevied...\n");

    // ** Authenticate Transaction **
    
    Node xmlDoc = xmlTrx.getNode();
    
    Node xmlUserData = (Node)XmlHelper.readFromXml(xmlDoc, "//UserPeriodData", XPathConstants.NODE);

    String strUserOid = (String)XmlHelper.readFromXml(xmlUserData, "oid", XPathConstants.STRING);
    int userOid = Integer.parseInt(strUserOid);
    User user;
    
    try
    {
      user = userService.getByOid(userOid);
    }
    catch(Exception e)
    {      
      result.setreturnCode((SciiResult.RETURN_CODE_UNIDENTFIED_ERROR));
      result.setreturnMessage("Error - Error retreiving user with oid " + userOid);

      System.out.println("Error - Error retreiving user with oid " + userOid);

      return result;
    }

    // ** Unmarshall XML **
    System.out.println("  Unmarshalling...");
    UserPeriodData upd = new UserPeriodData();
    
    upd.setUser(user);
    upd.setCreationDate(new Date());
    upd.setTotalInvoiced((Double)XmlHelper.readFromXml(xmlUserData, "totalInvoiced", XPathConstants.NUMBER));
    upd.setTotalCollected((Double)XmlHelper.readFromXml(xmlUserData, "totalCollected", XPathConstants.NUMBER));
    upd.setTotalCredit((Double)XmlHelper.readFromXml(xmlUserData, "totalCredit", XPathConstants.NUMBER));
    upd.setTotalMissing((Double)XmlHelper.readFromXml(xmlUserData, "totalMissing", XPathConstants.NUMBER));
         
    // *** Get products quantities ***
    NodeList xmlUserDataProducts = (NodeList)XmlHelper.readFromXml(xmlUserData, "//Product", XPathConstants.NODESET);
    for(int i=0; i < xmlUserDataProducts.getLength(); i++)
    {
      
      // *** Get Product instance ***
      String strProductOid = (String)XmlHelper.readFromXml(xmlUserDataProducts.item(i), "oid", XPathConstants.STRING);

      Product product = productService.getByOid(Long.parseLong(strProductOid));
      
      UserPeriodProduct upp = new UserPeriodProduct();
      
      upp.setUserPeriodData(upd);
      upp.setProduct(product);
      
      String strInitQuantity = (String)XmlHelper.readFromXml(xmlUserDataProducts.item(i), "initialQuantity", XPathConstants.STRING);
      String strRetQuantity = (String)XmlHelper.readFromXml(xmlUserDataProducts.item(i), "returnedQuantity", XPathConstants.STRING);
      
      upp.setInitialQuantity(Integer.parseInt(strInitQuantity));
      upp.setReturnedQuantity(Integer.parseInt(strRetQuantity));
      
      upd.getUpProducts().add(upp);
    }
    
    // *** Get payment details ***
    NodeList xmlUserDataPaymentDetails = (NodeList)XmlHelper.readFromXml(xmlUserData, "//PaymentDetail", XPathConstants.NODESET);
    for(int i=0; i < xmlUserDataPaymentDetails.getLength(); i++)
    {
      
      // *** Get PaymentMethod instance ***
      String strPaymentMethodOid = (String)XmlHelper.readFromXml(xmlUserDataPaymentDetails.item(i), "paymentMethodOid", XPathConstants.STRING);

      PaymentMethod paymentMethod = paymentMethodService.getByOid(Long.parseLong(strPaymentMethodOid));
      double amount = (Double)XmlHelper.readFromXml(xmlUserDataPaymentDetails.item(i), "amount", XPathConstants.NUMBER);
      
      UserPeriodPaymentDetail uppm = new UserPeriodPaymentDetail();
      
      uppm.setUserPeriodData(upd);
      uppm.setPaymentMethod(paymentMethod);
      uppm.setAmount(amount);
      
      upd.getUpPaymentDetails().add(uppm);
    }
    
    // *** Get cash details ***
    NodeList xmlUserDataCashDetails = (NodeList)XmlHelper.readFromXml(xmlUserData, "//CashDetail", XPathConstants.NODESET);
    for(int i=0; i < xmlUserDataCashDetails.getLength(); i++)
    {
      
      String strValue = (String)XmlHelper.readFromXml(xmlUserDataCashDetails.item(i), "value", XPathConstants.STRING);
      String strDenomination = (String)XmlHelper.readFromXml(xmlUserDataCashDetails.item(i), "denomination", XPathConstants.STRING);
      String strQuantity = (String)XmlHelper.readFromXml(xmlUserDataCashDetails.item(i), "quantity", XPathConstants.STRING);
      
      UserPeriodCashDetail upcd = new UserPeriodCashDetail();
      
      upcd.setUserPeriodData(upd);
      upcd.setValue(Double.parseDouble(strValue));
      upcd.setValue(Double.parseDouble(strQuantity));
      upcd.setDenomination(strDenomination);
      
      upd.getUpCashDetails().add(upcd);
    }
    
        
    // *** Save User Period Data ***
    System.out.println("  Saving UserPeriodData...");
    result = userDataService.saveUserPeriodData(upd); 
    System.out.println("  UserPeriodData succesfully saved");

    System.out.println("\nClose User Period Transaction processed\n");

    result.setaffectedObjectOid(upd.getOid());
    result.setreturnCode(SciiResult.RETURN_CODE_OK);
    result.setreturnMessage("OK");
    
    return result;
    
    // ** Validate Data Before Saving **
/*
    System.out.println("  Validating...");
    try
    {
      upd.validate();
    }
    catch(SciiException e)
    {      

      System.out.println(e.getMessage());
      result.setreturnCode((e.getSciiReturnCode()));
      result.setreturnMessage("Error - " +  e.getMessage());
   
      return result;
    }
*/
  }
  
  @RequestMapping(value = "/getAllDto",method = RequestMethod.GET)
  public @ResponseBody UserDtoList getAllDto() throws SciiException
  {
    
    logger.info("Received request to retrieve all users as DTOs");

    // Delegate to service to do the actual fetch
    List<User> users = userService.getAllActive();
    UserDtoList userDtos = new UserDtoList();

    userDtos.setCount(users.size());
    
    for (int i = 0; i < users.size(); i++)
    {
      UserDto ud = new UserDto(users.get(i));
      userDtos.add(ud);
    }
    
    return userDtos;
  
  }
  
}
