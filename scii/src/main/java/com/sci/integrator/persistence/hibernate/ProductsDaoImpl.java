package com.sci.integrator.persistence.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sci.integrator.domain.core.Product;
import com.sci.integrator.domain.core.Products;
import com.sci.integrator.domain.core.SciiResult;
import com.sci.integrator.domain.core.UserData;
import com.sci.integrator.persistence.IProductDao;
import com.sci.integrator.transaction.TransactionStatus;

public class ProductsDaoImpl implements IProductDao
{

  // ********** Constants **********
  
  private static final Logger logger = LoggerFactory.getLogger(ProductsDaoImpl.class);

  // ********** properties **********
  
  private SessionFactory sessionFactory;

  // ********** Constructors **********
  
  public ProductsDaoImpl()
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

  // ********** IProductDao Implementation Methods **********
  
  public SciiResult save(Product product)
  {
    SciiResult result = new SciiResult();
    
    Long oid = (Long)currentSession().save(product);
    product.setOid(oid);
    result.setaffectedObjectOid(oid);
    result.setAffectedObjectServerId(product.getServerId());
      
    return result;
  }

  public void update(Product product)
  {
    currentSession().update(product);
  }

  public Product getByOid(long _oid)
  {
    Product obj = (Product)currentSession().get(Product.class, _oid);
    return obj;
  }

  @SuppressWarnings("unchecked")
  public Products getAll()
  {
    Products products = new Products();
    products.setProduct(currentSession().createQuery( "FROM product ORDER BY oid DESC LIMIT 10" ).list()); 
    return products;
  }

  public void deleteAll()
  {
    // TODO Auto-generated method stub
    
  }

  @SuppressWarnings("unchecked")
  public void deleteByOid(long productOid)
  {
    Query query = currentSession().createQuery("FROM Product WHERE oid = " + productOid);
    List<Product> productList = query.list();
    for(int i = 0; i < productList.size(); i++)
    {
      Product product = productList.get(i);
      currentSession().delete(product);
    }
  }

  @SuppressWarnings("unchecked")
  public void deleteListByOid(long productListOid)
  {
    Query query = currentSession().createQuery("FROM Products WHERE oid = " + productListOid);
    List<Products> productList = query.list();
    for(int i = 0; i < productList.size(); i++)
    {
      Products products = productList.get(i);
      currentSession().delete(products);
    }
  }

}
