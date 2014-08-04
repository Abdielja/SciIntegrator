package com.sci.integrator.persistence;

import com.sci.integrator.domain.core.Product;
import com.sci.integrator.domain.core.Products;
import com.sci.integrator.domain.core.SciiResult;

public interface IProductDao
{
  SciiResult save(Product product);
  void update(Product product);
  Product getByOid(long _oid);  
  Products getAll();
  void deleteAll();
  void deleteByOid(long userOid);
  public void deleteListByOid(long productListOid);
}
