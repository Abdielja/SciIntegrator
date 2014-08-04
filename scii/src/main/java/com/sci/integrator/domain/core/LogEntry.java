/**
 * 
 */
package com.sci.integrator.domain.core;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author Abdiel
 *
 */

@Entity
@Table(name="log")
@XmlRootElement(name="LogEntry")
public class LogEntry implements Serializable
{

  private long oid;
  private int level;
  private User user;
  private Date date;
  private String description;
  
  /**
   * 
   */
  private static final long serialVersionUID = -4485393466172858323L;

  /**
   * @return the oid
   */
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
   * @return the level
   */
  @Column(name="level_log")  
  public int getLevel()
  {
    return level;
  }

  /**
   * @param level the level to set
   */
  public void setLevel(int level)
  {
    this.level = level;
  }

  /**
   * @return the user
   */
  @ManyToOne
  @JoinColumn(name = "user_oid")
  @XmlTransient
  public User getUser()
  {
    return user;
  }

  /**
   * @param user the user to set
   */
  public void setUser(User user)
  {
    this.user = user;
  }

  /**
   * @return the date
   */
  @Column(name="creation_date")  
  public Date getDate()
  {
    return date;
  }

  /**
   * @param date the date to set
   */
  public void setDate(Date date)
  {
    this.date = date;
  }

  /**
   * @return the description
   */
  @Column(name="description_log")  
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
   * 
   * Default Constructor
   */
  public LogEntry()
  {
    // TODO Auto-generated constructor stub
  }

}
