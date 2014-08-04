/**
 * 
 */
package com.sci.integrator.domain.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author Abdiel Jaramillo Ojedis
 *
 */

@Entity
@Table(name="role")
@XmlRootElement(name = "Role")
public class Role implements Serializable
{
 
  /**
   * 
   */
  private static final long serialVersionUID = -6556231954176750524L;
  
  private long oid;
  private String serverId;
  private String organizationId;
  private String name;
  private String description;
  private String warehouseId;
  private String organizationList;
  
  private Roles roleList = new Roles();
  private Routes routes;
  
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
   * @return the serverId
   */
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
  
  /**
   * @return the organizationId
   */
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
  
  /**
   * @return the name
   */
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
  /**
   * @return the warehouseId
   */
  @Column(name="warehouse_id")
  public String getWarehouseId()
  {
    return warehouseId;
  }
  /**
   * @param warehouseId the warehouseId to set
   */
  public void setWarehouseId(String warehouseId)
  {
    this.warehouseId = warehouseId;
  }
  /**
   * @return the organizationList
   */
  @Column(name="organization_list")
  public String getOrganizationList()
  {
    return organizationList;
  }
  /**
   * @param organizationList the organizationList to set
   */
  public void setOrganizationList(String organizationList)
  {
    this.organizationList = organizationList;
  }

  /**
   * @return the roleList
   */
  //@ManyToOne
  //@JoinColumn(name = "role_list_oid")
  @Transient
  @XmlTransient
  public Roles getRoleList()
  {
    return roleList;
  }
  /**
   * @param roleList the roleList to set
   */
  public void setRoleList(Roles roleList)
  {
    this.roleList = roleList;
  }
  /**
   * @return the routes
   */
  @Transient
  public Routes getRoutes()
  {
    return routes;
  }
  /**
   * @param routes the routes to set
   */
  public void setRoutes(Routes routes)
  {
    this.routes = routes;
  }
  
  
  public Role()
  {
    routes = new Routes();
  }
}
