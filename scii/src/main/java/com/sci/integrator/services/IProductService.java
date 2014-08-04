/**
 * 
 */
package com.sci.integrator.services;

import com.sci.integrator.domain.core.Product;
import com.sci.integrator.domain.core.Products;
import com.sci.integrator.domain.core.SciiResult;

/**
 * @author Abdiel
 *
 */
public interface IProductService
{
  SciiResult save(Product product);
  void update(Product product);
  void delete(long oid);    
  Product getByOid(long oid);    
  Products getAll();
}
