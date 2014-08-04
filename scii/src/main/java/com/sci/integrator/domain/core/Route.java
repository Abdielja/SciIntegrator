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
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Abdiel Jaramillo Ojedis
 *
 */

@Entity
@Table(name="route")
@XmlRootElement(name="Route")
public class Route implements Serializable
{
  
  // ***** Properties *****
  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  private long oid;
  private long role_oid;
  
  private String serverId;
  private String roleId;
  private String organizationId;
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

  // *** RoleOid ***
  @Column(name="role_oid")
  public long getRole_oid()
  {
    return role_oid;
  }

  /**
   * @param role_oid the role_oid to set
   */
  public void setRole_oid(long role_oid)
  {
    this.role_oid = role_oid;
  }

  // *** ServerId ***
  @Column(name="server_id")
  public String getServerId()
  {
    return serverId;
  }

  /**
   * @param serverId the serverId to set
   */
  public void setServerId(String serverId)
  {
    this.serverId = serverId;
  }

  // *** RoleId ***
  @Column(name="role_id")
  public String getRoleId()
  {
    return roleId;
  }

  public void setRoleId(String roleId)
  {
    this.roleId = roleId;
  }

  // *** OrganizationId ***
  @Column(name="organization_id")
  public String getOrganizationId()
  {
    return organizationId;
  }

  /**
   * @param organizationId the organizationId to set
   */
  public void setOrganizationId(String organizationId)
  {
    this.organizationId = organizationId;
  }

  // *** Name ***
  @Column(name="name")
  public String getName()
  {
    return name;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name)
  {
    this.name = name;
  }

  // ***** Constructors *****
  
  public Route()
  {
    // TODO Auto-generated constructor stub
  }

}
