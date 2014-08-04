/**
 * 
 */
package com.sci.integrator.domain.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Abdiel
 *
 */
public class Companies implements Serializable
{

  /**
   * 
   */
  private static final long serialVersionUID = -7747667804467981632L;

  private List<Company> company = new ArrayList<Company>();

  /**
   * @return the company
   */
  public List<Company> getCompany()
  {
    return company;
  }

  /**
   * @param company the company to set
   */
  public void setCompany(List<Company> company)
  {
    this.company = company;
  }
  
  public void add(Company _company)
  {
    this.company.add(_company);
  }
}
