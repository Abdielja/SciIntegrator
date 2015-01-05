/**
 * 
 */
package com.sci.integrator.provider.openbravo.transaction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.xpath.XPathConstants;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.http.HttpMethod;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sci.integrator.domain.core.AppSettings;
import com.sci.integrator.domain.core.Company;
import com.sci.integrator.domain.core.Location;
import com.sci.integrator.domain.core.Product;
import com.sci.integrator.domain.core.ProductPrice;
import com.sci.integrator.domain.core.Role;
import com.sci.integrator.domain.core.Route;
import com.sci.integrator.domain.core.SciiException;
import com.sci.integrator.domain.core.SciiRequest;
import com.sci.integrator.domain.core.SciiResponse;
import com.sci.integrator.domain.core.SciiResult;
import com.sci.integrator.domain.core.User;
import com.sci.integrator.domain.core.UserData;
import com.sci.integrator.domain.customer.Customer;
import com.sci.integrator.domain.invoice.Invoice;
import com.sci.integrator.domain.order.Order;
import com.sci.integrator.domain.order.OrderLine;
import com.sci.integrator.domain.payment.PaymentMethod;
import com.sci.integrator.helpers.XmlHelper;
import com.sci.integrator.transaction.Transaction;

/**
 * @author Abdiel Jaramillo Ojedis
 * 
 */
@Entity
@XmlRootElement(name = "transactionOpen")
@DiscriminatorValue(value = "100")
//@XmlAccessorType(XmlAccessType.FIELD)
public class TransactionOpen extends Transaction
{

  private static final long serialVersionUID = 1L;

  // ***** Persistence Services *****
  
  // ***** Properties *****

  private UserData userData;
  private User user;
  
  @OneToOne(targetEntity=UserData.class)
  @Cascade({CascadeType.ALL})
  @JoinColumn(name="trx_object_oid")
  public UserData getuserData()
  {
    return userData;
  }

  public void setuserData(UserData userData)
  {
    this.userData = userData;
  }
  
  // ***** Constructors *****
  
  public TransactionOpen()
  {
  }

  public SciiResult process()
  {

    super.process();
    
    SciiResult result = new SciiResult();
    result.setreturnCode(SciiResult.RETURN_CODE_UNIDENTFIED_ERROR);
    
    com.sci.integrator.domain.core.User user = this.getcreatedBy();

    System.out.println("Transaction - Open: " + this.getoid());

    UserData userData = new UserData();
    userData.setUserOid(user.getoid());

    this.setstatus(Transaction.STATUS_PROCESSED);

    return result;
    
  }

  public SciiRequest buildMainRequest()
  {
    
    SciiRequest request = new SciiRequest();
    
    // *** Get User object ***
    user = this.getcreatedBy(); 

    String strUrlExt = "ws/dal/ADUser?where={whereClause}";
    String whereClause = "id='" + user.getserverId() + "'";

    request.setUrlExtension(strUrlExt);
    request.setWhereClause(whereClause);
    request.setHttpMethod(HttpMethod.GET);
    request.setWhereClause(whereClause);
    
    System.out.println("  TransactionOpen - " + this.getoid() + " main request created.");
    
    this.timeKeeper = new Date().getTime();
    
    return request;
  }

  public void processMainResponse(SciiResponse response)
  {
    
    Node xmlDoc = response.getResponseEntity().getBody().getNode();
    
    // *** Parse response's XML ***

    userData = new UserData();
    
    userData.setUserOid(this.getcreatedBy().getoid());
    userData.setUserServerId((String)XmlHelper.readFromXml(xmlDoc, "//ADUser//id", XPathConstants.STRING));
    userData.setName((String)XmlHelper.readFromXml(xmlDoc, "//ADUser//name", XPathConstants.STRING));
    userData.setBusinessPartnerId((String)XmlHelper.readFromXml(xmlDoc, "//ADUser//businessPartner/@id", XPathConstants.STRING));
    userData.setStartDate(new Date());
    
    long currentTime = new Date().getTime();
    long elapsedTime = currentTime - this.timeKeeper;
    this.timeKeeper = currentTime;
    
    System.out.println("  TransactionOpen - " + this.getoid() + " main request processed. " + elapsedTime + " ms.");
  }

  public List<SciiRequest> buildSubRequests()
  {

    ArrayList<SciiRequest> requests = new ArrayList<SciiRequest>();
    SciiRequest request;
   
    // *** Create Load Companies Request ***
    
    request = new SciiRequest();
    request.setTag("Load Companies");
    request.setUrlExtension("ws/dal/Organization?where={orgType}&includeChildren={includeChildren}");

    request.getVars().clear();
    request.getVars().put("orgType", "organizationType.id=1");
    request.getVars().put("includeChildren", "true");
    request.setWhereClause("");
        
    request.setHttpMethod(HttpMethod.GET);
    
    requests.add(request);

    System.out.println("    Load Companies request created.");
    
    // *** Create Load Routes and Roles Request ***
 
    request = new SciiRequest();
    request.setTag("Load Roles");
    request.setUrlExtension("ws/com.spiritsci.spimo.getRoutesByUserId?userId=" + userData.getUserServerId());
    request.getVars().clear();
    request.setWhereClause("");
    request.setHttpMethod(HttpMethod.GET);
    
    requests.add(request);

    System.out.println("    Load Roles request created.");
    
    // *** Create Load Customers Request ***
    
    request = new SciiRequest();
    request.setTag("Load Customers");
    request.setUrlExtension("ws/com.spiritsci.spimo.getCustomersByUserId?userId=" + userData.getUserServerId());
    request.getVars().clear();
    request.setWhereClause("");
    request.setHttpMethod(HttpMethod.GET);
    
    requests.add(request);

    System.out.println("    Load Customers request created.");
    
    // *** Create Load Products Request ***

    request = new SciiRequest();
    request.setTag("Load Products");
    request.setUrlExtension("ws/com.spiritsci.spimo.getInventoryBySalesRep?salesRepId=" + userData.getUserServerId());
    request.getVars().clear();
    request.setWhereClause("");
    request.setHttpMethod(HttpMethod.GET);
    
    requests.add(request);

    System.out.println("    Load Products request created.");
    
    // *** Create Load Pending Invoices Request ***
    
    request = new SciiRequest();
    request.setTag("Load Pending Invoices");
//    request.setUrlExtension("ws/com.spiritsci.spimo.getPendingInvoicesByUserId?userId=" + userData.getUserServerId() + "&maxDays=" + appSettings.getMaxPendingInvoices());
    request.setUrlExtension("ws/com.spiritsci.spimo.getPendingInvoicesByUserId?userId=" + userData.getUserServerId() + "&maxDays=10");
    request.getVars().clear();
    request.setWhereClause("");
    request.setHttpMethod(HttpMethod.GET);
    
    requests.add(request);

    System.out.println("    Load Pending Invoices request created.");
    
    // *** Create Load Pending Orders Request ***
    
    request = new SciiRequest();
    request.setTag("Load Pending Orders");
    request.setUrlExtension("ws/com.spiritsci.spimo.getPendingOrdersByUserId?userId=" + userData.getUserServerId());
    request.getVars().clear();
    request.setWhereClause("");
    request.setHttpMethod(HttpMethod.GET);
    
    requests.add(request);

    System.out.println("    Load Pending Orders request created.");

    return requests;
  }
 
  public void processSubResponse(SciiResponse response)
  {
    
    Node xmlDoc = response.getResponseEntity().getBody().getNode();
    
    // *** Process Load Companies Response ***
    
    if (response.getTag().equals("Load Companies"))
    {
      
      NodeList xmlCompanyList = (NodeList)XmlHelper.readFromXml(xmlDoc, "//Organization", XPathConstants.NODESET);
      for(int i=0; i < xmlCompanyList.getLength(); i++)
      {
        
        Company company = new Company();
        
        company.setServerId((String)XmlHelper.readFromXml(xmlCompanyList.item(i), "id", XPathConstants.STRING));
        company.setName((String)XmlHelper.readFromXml(xmlCompanyList.item(i), "name", XPathConstants.STRING));
        company.setRuc((String)XmlHelper.readFromXml(xmlCompanyList.item(i), "organizationInformationList/OrganizationInformation/dndRuc", XPathConstants.STRING));
        
/*
        company.setServerId((String)XmlHelper.readFromXml(xmlCompany, "/id", XPathConstants.STRING));
        company.setName((String)XmlHelper.readFromXml(xmlCompany, "/name", XPathConstants.STRING));
        company.setRuc((String)XmlHelper.readFromXml(xmlCompany, "/ruc", XPathConstants.STRING));
*/
        userData.getCompanies().add(company);
      }
      
      long currentTime = new Date().getTime();
      long elapsedTime = currentTime - this.timeKeeper;
      this.timeKeeper = currentTime;

      System.out.println("    Load Companies request processed. " + elapsedTime + " ms.");
    }

    // *** Process Load Roles Response ***
 
    if (response.getTag().equals("Load Roles"))
    {
      
      NodeList xmlRoleList = (NodeList)XmlHelper.readFromXml(xmlDoc, "//Role", XPathConstants.NODESET);
      for(int i=0; i < xmlRoleList.getLength(); i++)
      {
        Role role = new Role();
        role.setServerId((String)XmlHelper.readFromXml(xmlRoleList.item(i), "Id", XPathConstants.STRING));
        role.setName((String)XmlHelper.readFromXml(xmlRoleList.item(i), "Name", XPathConstants.STRING));
        role.setOrganizationId((String)XmlHelper.readFromXml(xmlRoleList.item(i), "OrganizationId", XPathConstants.STRING));
        role.setDescription((String)XmlHelper.readFromXml(xmlRoleList.item(i), "Description", XPathConstants.STRING));
        role.setWarehouseId((String)XmlHelper.readFromXml(xmlRoleList.item(i), "WarehouseId", XPathConstants.STRING));
        role.setOrganizationList((String)XmlHelper.readFromXml(xmlRoleList.item(i), "OrganizationList", XPathConstants.STRING));
        
        // *** Load Routes for this Role
        NodeList xmlRouteList = (NodeList)XmlHelper.readFromXml(xmlRoleList.item(i), "Routes/Route", XPathConstants.NODESET);
        for(int j=0; j < xmlRouteList.getLength(); j++)
        {
          Route route = new Route();
          route.setServerId((String)XmlHelper.readFromXml(xmlRouteList.item(j), "Id", XPathConstants.STRING));
          route.setOrganizationId((String)XmlHelper.readFromXml(xmlRouteList.item(j), "OrganizationId", XPathConstants.STRING));
          route.setRoleId((String)XmlHelper.readFromXml(xmlRouteList.item(j), "RoleId", XPathConstants.STRING));
          route.setName((String)XmlHelper.readFromXml(xmlRouteList.item(j), "Name", XPathConstants.STRING));
          role.getRoutes().add(route);
        }
        userData.getRoles().add(role);
      }
      
      long currentTime = new Date().getTime();
      long elapsedTime = currentTime - this.timeKeeper;
      this.timeKeeper = currentTime;

      System.out.println("    Load Routes and Roles request processed. " + elapsedTime + " ms.");
    }
    
    // *** Process Load Customers Response ***
    
    if (response.getTag().equals("Load Customers"))
    {
      
      NodeList xmlCustomerList = (NodeList)XmlHelper.readFromXml(xmlDoc, "//Customer", XPathConstants.NODESET);
 
      for(int i=0; i < xmlCustomerList.getLength(); i++)
      {
        Customer customer = new Customer();
        customer.setServerId((String)XmlHelper.readFromXml(xmlCustomerList.item(i), "id", XPathConstants.STRING));
        customer.setName((String)XmlHelper.readFromXml(xmlCustomerList.item(i), "name", XPathConstants.STRING));
        customer.setClientId((String)XmlHelper.readFromXml(xmlCustomerList.item(i), "clientId", XPathConstants.STRING));
        customer.setOrganizationId((String)XmlHelper.readFromXml(xmlCustomerList.item(i), "organizationId", XPathConstants.STRING));
        customer.setPaymentMethodId((String)XmlHelper.readFromXml(xmlCustomerList.item(i), "paymentMethodId", XPathConstants.STRING));
        customer.setPaymentTermsId((String)XmlHelper.readFromXml(xmlCustomerList.item(i), "paymentTermsId", XPathConstants.STRING));
        customer.setPriceListId((String)XmlHelper.readFromXml(xmlCustomerList.item(i), "priceListId", XPathConstants.STRING));
        customer.setUpcean((String)XmlHelper.readFromXml(xmlCustomerList.item(i), "upcean", XPathConstants.STRING));
        customer.setDndRutaOrg((String)XmlHelper.readFromXml(xmlCustomerList.item(i), "dndRutaOrg", XPathConstants.STRING));
        
        // *** Load Locations for this Customer
   
        Node xmlLocationsRoot = (Node)XmlHelper.readFromXml(xmlCustomerList.item(i), "Locations", XPathConstants.NODE);
        NodeList xmlLocationList = (NodeList)XmlHelper.readFromXml(xmlLocationsRoot, "Location", XPathConstants.NODESET);
        for(int j=0; j < xmlLocationList.getLength(); j++)
        {
          Location location = new Location();
          location.setServerId((String)XmlHelper.readFromXml(xmlLocationList.item(j), "id", XPathConstants.STRING));
          location.setAddress((String)XmlHelper.readFromXml(xmlLocationList.item(j), "address", XPathConstants.STRING));
          customer.getLocations().add(location);
        }
     
        userData.getCustomers().add(customer);
      }
      
      long currentTime = new Date().getTime();
      long elapsedTime = currentTime - this.timeKeeper;
      this.timeKeeper = currentTime;

      System.out.println("    Load Customers request processed. " + elapsedTime + " ms.");
    }
    
    // *** Process Load Products Response ***
    
    if (response.getTag().equals("Load Products"))
    {
      
      NodeList xmlProductList = (NodeList)XmlHelper.readFromXml(xmlDoc, "//Product", XPathConstants.NODESET);
 
      for(int i=0; i < xmlProductList.getLength(); i++)
      {
        Product product = new Product();
        product.setServerId((String)XmlHelper.readFromXml(xmlProductList.item(i), "id", XPathConstants.STRING));
        product.setName((String)XmlHelper.readFromXml(xmlProductList.item(i), "name", XPathConstants.STRING));
        Double quantity = (Double)XmlHelper.readFromXml(xmlProductList.item(i), "quantityOnHand", XPathConstants.NUMBER);
        product.setQuantityOnHand(quantity.intValue());
        product.setProductOrganizationId((String)XmlHelper.readFromXml(xmlProductList.item(i), "productOrganizationId", XPathConstants.STRING));
        product.setStorageBinId((String)XmlHelper.readFromXml(xmlProductList.item(i), "storageBin/@id", XPathConstants.STRING));
        product.setStorageBinOrganizationId((String)XmlHelper.readFromXml(xmlProductList.item(i), "storageBin/organization/@id", XPathConstants.STRING));
        product.setTaxCategoryId((String)XmlHelper.readFromXml(xmlProductList.item(i), "taxCategoryId", XPathConstants.STRING));
        product.setuPCEAN((String)XmlHelper.readFromXml(xmlProductList.item(i), "uPCEAN", XPathConstants.STRING));

        // *** Load Pricing Lists for this Product ***
   
        NodeList xmlPriceList = (NodeList)XmlHelper.readFromXml(xmlProductList.item(i), "pricingProductPriceList/pricingProductPrice", XPathConstants.NODESET);
        for(int j=0; j < xmlPriceList.getLength(); j++)
        {
          ProductPrice productPrice = new ProductPrice();
          productPrice.setListVersionId((String)XmlHelper.readFromXml(xmlPriceList.item(j), "priceListVersion/@id", XPathConstants.STRING));
          productPrice.setStandardPrice((Double)XmlHelper.readFromXml(xmlPriceList.item(j), "standardPrice", XPathConstants.NUMBER));
          productPrice.setListPrice((Double)XmlHelper.readFromXml(xmlPriceList.item(j), "listPrice", XPathConstants.NUMBER));
          product.getProductPrices().add(productPrice);
        }
   
        // ****** Temporal *************************************************************
        ProductPrice productPrice = product.getProductPrices().getProductPrice().get(0);
        product.setStandardPrice(productPrice.getStandardPrice());
        // *****************************************************************************
        
        userData.getProducts().add(product);
      }
      
      long currentTime = new Date().getTime();
      long elapsedTime = currentTime - this.timeKeeper;
      this.timeKeeper = currentTime;

      System.out.println("    Load Products request processed. " + elapsedTime + " ms.");
    }

    // *** Process Pending Invoices Response ***
    
    if (response.getTag().equals("Load Pending Invoices"))
    {
      
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");

      NodeList xmlInvoiceList = (NodeList)XmlHelper.readFromXml(xmlDoc, "//Invoice", XPathConstants.NODESET);

      int i;
      for(i=0; i < xmlInvoiceList.getLength(); i++)
      {
                
        Invoice invoice = new Invoice();
        
        invoice.setoid(System.nanoTime());
        invoice.setserverId((String)XmlHelper.readFromXml(xmlInvoiceList.item(i), "Id", XPathConstants.STRING));
        invoice.setclientId((String)XmlHelper.readFromXml(xmlInvoiceList.item(i), "ClientId", XPathConstants.STRING));
        invoice.setorganizationId((String)XmlHelper.readFromXml(xmlInvoiceList.item(i), "OrganizationId", XPathConstants.STRING));
        invoice.setdocumentNumber((String)XmlHelper.readFromXml(xmlInvoiceList.item(i), "DocumentNo", XPathConstants.STRING));
        String creationDate = (String)XmlHelper.readFromXml(xmlInvoiceList.item(i), "CreationDate", XPathConstants.STRING);
        try
        {
          invoice.setcreationDate(sdf.parse(creationDate));
        } catch (ParseException e)
        {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        invoice.setcustomerId((String)XmlHelper.readFromXml(xmlInvoiceList.item(i), "BusinessPartnerId", XPathConstants.STRING));
        invoice.setpaymentTermsId((String)XmlHelper.readFromXml(xmlInvoiceList.item(i), "PaymentTermsId", XPathConstants.STRING));
                
        String pmServerId = (String)XmlHelper.readFromXml(xmlInvoiceList.item(i), "PaymentMethodId", XPathConstants.STRING);
        PaymentMethod pm = new PaymentMethod();
        pm.setserverId(pmServerId);
        
        invoice.setpaymentMethod(pm);
        invoice.setpriceListId((String)XmlHelper.readFromXml(xmlInvoiceList.item(i), "PriceListId", XPathConstants.STRING));
        
        invoice.settotal((Double)XmlHelper.readFromXml(xmlInvoiceList.item(i), "TotalAmount", XPathConstants.NUMBER));
        invoice.setsubTotal((Double)XmlHelper.readFromXml(xmlInvoiceList.item(i), "OutstandingAmount", XPathConstants.NUMBER));
               
        invoice.setstatus(Invoice.STATUS_LOADED_FROM_ERP);
        
        userData.getInvoices().add(invoice);
 
      }
      
      userData.getInvoices().setCount(i);
      
      long currentTime = new Date().getTime();
      long elapsedTime = currentTime - this.timeKeeper;
      this.timeKeeper = currentTime;

      System.out.println("    Load Pending Invoices request processed. " + elapsedTime + " ms.");
      
    }

    // *** Process Pending Orders Response ***
    
    if (response.getTag().equals("Load Pending Orders"))
    {
            
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");

      NodeList xmlOrderList = (NodeList)XmlHelper.readFromXml(xmlDoc, "//Order", XPathConstants.NODESET);

      int i;
      for(i=0; i < xmlOrderList.getLength(); i++)
      {
                
        Order order = new Order();
        
        order.setoid(System.nanoTime());
        order.setserverId((String)XmlHelper.readFromXml(xmlOrderList.item(i), "Id", XPathConstants.STRING));
        order.setclientId((String)XmlHelper.readFromXml(xmlOrderList.item(i), "ClientId", XPathConstants.STRING));
        order.setorganizationId((String)XmlHelper.readFromXml(xmlOrderList.item(i), "OrganizationId", XPathConstants.STRING));
        String creationDate = (String)XmlHelper.readFromXml(xmlOrderList.item(i), "CreationDate", XPathConstants.STRING);
        try
        {
          order.setcreationDate(sdf.parse(creationDate));
        } 
        catch (ParseException e)
        {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        
        order.setcustomerId((String)XmlHelper.readFromXml(xmlOrderList.item(i), "BusinessPartnerId", XPathConstants.STRING));
        order.setpaymentTermsId((String)XmlHelper.readFromXml(xmlOrderList.item(i), "PaymentTermsId", XPathConstants.STRING));
                
        String pmServerId = (String)XmlHelper.readFromXml(xmlOrderList.item(i), "PaymentMethodId", XPathConstants.STRING);
        PaymentMethod pm = new PaymentMethod();
        pm.setserverId(pmServerId);
        
        order.setpaymentMethod(pm);
        order.setpriceListId((String)XmlHelper.readFromXml(xmlOrderList.item(i), "PriceListId", XPathConstants.STRING));
        
        order.settotal((Double)XmlHelper.readFromXml(xmlOrderList.item(i), "TotalAmount", XPathConstants.NUMBER));
        //order.setsubTotal((Double)XmlHelper.readFromXml(xmlOrderList.item(i), "OutstandingAmount", XPathConstants.NUMBER));
               
        // ** Load OrderLines **
        NodeList xmlOrderLineList = (NodeList)XmlHelper.readFromXml(xmlOrderList.item(i), "//OrderLine", XPathConstants.NODESET);

        int j;
        for(j=0; j < xmlOrderLineList.getLength(); j++)
        {
          OrderLine ol = new OrderLine();
          ol.setlineNumber((j+1) * 10);
          ol.setnetAmount((Double)XmlHelper.readFromXml(xmlOrderLineList.item(j), "NetAmount", XPathConstants.NUMBER));
          ol.setOid(System.nanoTime());
          ol.setOrder(order);
          ol.setprice((Double)XmlHelper.readFromXml(xmlOrderLineList.item(j), "UnitPrice", XPathConstants.NUMBER));
          ol.setquantity(((Double)XmlHelper.readFromXml(xmlOrderLineList.item(j), "Quantity", XPathConstants.NUMBER)).intValue());
          ol.setproductId((String)XmlHelper.readFromXml(xmlOrderLineList.item(j), "ProductId", XPathConstants.STRING));
          ol.setstatus(Order.STATUS_LOADED_FROM_ERP);
          //ol.settaxRateId(taxRateId);
          order.getorderLine().add(ol);
        }
        
        order.setstatus(Order.STATUS_LOADED_FROM_ERP);
        
        userData.getOrders().add(order);
 
      }
      
      userData.getOrders().setCount(i);
      
      long currentTime = new Date().getTime();
      long elapsedTime = currentTime - this.timeKeeper;
      this.timeKeeper = currentTime;

      System.out.println("    Load Pending Orders request processed. " + elapsedTime + " ms.");
      
    }
  }

  public void validate() throws SciiException
  {
    super.validate();
    System.out.println("END - Transaction Validation.");
  }
  
}
