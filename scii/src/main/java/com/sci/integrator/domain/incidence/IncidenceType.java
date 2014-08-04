/**
 * 
 */
package com.sci.integrator.domain.incidence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Abdiel Jaramillo Ojedis
 *
 */
@Entity
@Table(name="incidence_type")
public class IncidenceType implements Serializable
{

  // ***** Properties *****
  
  private static final long serialVersionUID = 1L;
  
  private long oid;
  private String description;
  
  /**
   * @return the oid
   */
  @Id
  @Column(name="oid")
  public long getOid()
  {
    return oid;
  }

  /**
   * @param oid the oid to set
   */
  public void setOid(long oid)
  {
    this.oid = oid;
  }

  /**
   * @return the description
   */
  @Column(name="description")
  public String getDescription()
  {
    return description;
  }

  /**
   * @param description the description to set
   */
  public void setDescription(String description)
  {
    this.description = description;
  }

  // ***** Constructors *****
  
  public IncidenceType()
  {
  }

}
