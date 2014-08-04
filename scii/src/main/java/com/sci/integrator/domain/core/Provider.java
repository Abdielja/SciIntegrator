/**
 * 
 */
package com.sci.integrator.domain.core;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Abdiel Jaramillo Ojedis
 *
 */

@Entity
@Table(name="provider")
@XmlRootElement
public class Provider implements Serializable
{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  private long oid;
  private String name;
  
  // *** Oid ***
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name="oid")
  public long getOid()
  {
    return oid;
  }
  public void setOid(long oid)
  {
    this.oid = oid;
  }

  // *** Name ***
  @Column(name="name")
  public String getName()
  {
    return name;
  }
  public void setName(String name)
  {
    this.name = name;
  }
  
}
