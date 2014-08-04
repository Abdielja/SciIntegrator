/**
 * 
 */
package com.sci.integrator.services.relational;

import org.springframework.transaction.annotation.Transactional;

import com.sci.integrator.domain.core.Product;
import com.sci.integrator.domain.core.Products;
import com.sci.integrator.domain.core.SciiResult;
import com.sci.integrator.persistence.IProductDao;
import com.sci.integrator.services.IProductService;

/**
 * @author Abdiel Jaramillo Ojedis
 *
 */
public class ProductServiceImpl implements IProductService
{

  IProductDao productDao;

  public void setProductDao(IProductDao productDao)
  {
    this.productDao = productDao;
  }
  
  public ProductServiceImpl()
  {
    // TODO Auto-generated constructor stub
  }

  public SciiResult save(Product product)
  {
    // TODO Auto-generated method stub
    return null;
  }

  public void update(Product product)
  {
    // TODO Auto-generated method stub
    
  }

  public void delete(long oid)
  {
    // TODO Auto-generated method stub
    
  }

  @Transactional
  public Product getByOid(long oid)
  {
    Product product = productDao.getByOid(oid);
    return product;
  }

  public Products getAll()
  {
    // TODO Auto-generated method stub
    return null;
  }

}
