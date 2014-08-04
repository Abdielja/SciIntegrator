/**
 * 
 */
package com.sci.integrator.controlers.dto;

import java.io.Serializable;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Abdiel Jaramillo Ojedis
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class UserDtoList implements Serializable
{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  private int count;
  private ArrayList<UserDto> userDto;
  
  /**
   * @return the count
   */
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

  
  /**
   * @return the users
   */
  public ArrayList<UserDto> getUsers()
  {
    return userDto;
  }

  /**
   * @param users the users to set
   */
  public void setUsers(ArrayList<UserDto> users)
  {
    this.userDto = users;
  }

  /**
   * 
   */
  public UserDtoList()
  {
    userDto = new ArrayList<UserDto>();
  }

  public void add(UserDto user)
  {
    userDto.add(user);
  }
  
  public UserDto get(int index)
  {
    return userDto.get(index);
  }
}
