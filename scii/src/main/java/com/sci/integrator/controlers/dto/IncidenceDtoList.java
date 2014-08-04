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
 * @author Abdiel
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class IncidenceDtoList implements Serializable
{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private int count;
  private ArrayList<IncidenceDto> incidenceDto;
  
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
   * @return the incidenceDto
   */
  public ArrayList<IncidenceDto> getIncidenceDto()
  {
    return incidenceDto;
  }

  /**
   * @param incidenceDto the incidenceDto to set
   */
  public void setIncidenceDto(ArrayList<IncidenceDto> incidenceDto)
  {
    this.incidenceDto = incidenceDto;
  }

  /**
   * Constructors
   */
  public IncidenceDtoList()
  {
    incidenceDto = new ArrayList<IncidenceDto>();
  }

  
  
  public void add(IncidenceDto incidence)
  {
    incidenceDto.add(incidence);
  }
  
  public IncidenceDto get(int index)
  {
    return incidenceDto.get(index);
  }
  
}
