/**
 * 
 */
package com.sci.integrator.controlers.rest;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sci.integrator.services.IProductService;

/**
 * @author Abdiel Jaramillo O.
 *
 */

@Controller
@RequestMapping("/rest/products")
public class ProductRestController
{

  private static final Logger logger = LoggerFactory.getLogger(ProductRestController.class);

  @Inject
  private IProductService productService;

  /**
   * 
   */
  public ProductRestController()
  {
    // TODO Auto-generated constructor stub
  }

}
