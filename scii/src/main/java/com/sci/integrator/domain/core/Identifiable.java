/**
 * 
 */
package com.sci.integrator.domain.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Abdiel
 *
 */


@MappedSuperclass
public abstract class Identifiable 
{

	/**
   * 
   */
  
  private long oid;
  
  // *** Oid ***
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name="oid")
  public long getoid() 
  {
    return oid;
  }

  public void setoid(long oid) 
  {
    this.oid = oid;
  }


	public long getOid() 
  {
		return oid;
	}

	public void setOid(long oid) 
	{
		this.oid = oid;
	}
	

	public Identifiable()
	{
	}
	
}
