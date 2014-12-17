/**
 * 
 */
package com.sci.integrator.persistence.hibernate;

import java.util.Iterator;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sci.integrator.domain.core.Route;
import com.sci.integrator.domain.core.SciiResult;
import com.sci.integrator.domain.customer.Customer;
import com.sci.integrator.domain.customer.Customers;
import com.sci.integrator.domain.quotation.Quotation;
import com.sci.integrator.domain.quotation.QuotationLine;
import com.sci.integrator.persistence.ICustomersDao;
import com.sci.integrator.provider.openbravo.transaction.TransactionQuotation;
import com.sci.integrator.transaction.TransactionStatus;

/**
 * @author Abdiel
 *
 */
public class CustomersDaoImpl implements ICustomersDao
{

  // ********** Constants **********
  
  private static final Logger logger = LoggerFactory.getLogger(TransactionStatusDaoImpl.class);

  // ********** properties **********
  
  private SessionFactory sessionFactory;

  
  // ********** Constructors **********
  
  public CustomersDaoImpl()
  {
    // TODO Auto-generated constructor stub
  }


  // ********** Helper Methods **********
  
  public void setSessionFactory(SessionFactory sessionFactory) 
  {
    this.sessionFactory = sessionFactory;
  } 
  
  private Session currentSession() 
  {
    return sessionFactory.getCurrentSession();
  }

  // ********** ICustomerDao Implementation Methods **********
  
  public SciiResult save(Customers customers)
  {
    SciiResult result = new SciiResult();
    
    // *** Save Customer list header first ***
   
    Long customersOid = (Long)currentSession().save(customers);
    customers.setOid(customersOid);
    result.setaffectedObjectOid(customers.getOid());
    
    // *** Save Customer instances ***
    Iterator<Customer> iCustomer = customers.getCustomer().iterator();
    while (iCustomer.hasNext())
    {
      Customer customer = iCustomer.next();
      customer.setCustomerList(customers);
      currentSession().save(customer);
    }
    
    return result;
  }

  public void update(Customers customers)
  {
    currentSession().update(customers);
  }

  public Customers getByOid(long _oid)
  {
    Customers obj = (Customers)currentSession().get(TransactionStatus.class, _oid);
    return obj;
  }

}
