/**
 * 
 */
package com.sci.integrator.domain.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Cascade;

/**
 * @author Abdiel Jaramillo Ojedis
 *
 */
@Entity
@Table(name="roles")
@XmlRootElement(name = "Roles")
public class Roles implements Serializable
{


  /**
   * 
   */
  private static final long serialVersionUID = -2724972025224240702L;
  
  
  private long oid;
  private int count;
  
  private List<Role> roles = new ArrayList<Role>();
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
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
   * @return the count
   */
  @Column(name="count")
  public int getCount()
  {
    return count;
  }

  /**
   * @param count the count to set
   */
  public void setCount(int count)
  {
    this.count = count;
  }

  @OneToMany(fetch = FetchType.EAGER)
  @JoinColumn(name="role_list_oid", referencedColumnName="oid")
  @Cascade({CascadeType.ALL})
  public List<Role> getRoles()
  {
    return roles;
  }

  public void setRoles(List<Role> roles)
  {
    this.roles = roles;
  }
  

  public void add(Role _role)
  {
    this.roles.add(_role);
  }

  
}
