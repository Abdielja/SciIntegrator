/**
 * 
 */
package com.sci.integrator.controlers.rest;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sci.integrator.controlers.dto.IncidenceDto;
import com.sci.integrator.controlers.dto.IncidenceDtoList;
import com.sci.integrator.domain.incidence.Incidence;
import com.sci.integrator.services.IIncidenceService;

/**
 * @author Abdiel Jaramillo Ojedis
 *
 */

@Controller
@RequestMapping("/rest/incidences")
public class IncidenceRestController
{

  @Inject
  private IIncidenceService incidenceService;
  
  /**
   * Constructors
   */
  public IncidenceRestController()
  {
    // TODO Auto-generated constructor stub
  }

  /**
   * Public Methods 
   */
  @RequestMapping(value = "/getByUserTodayDto", method = RequestMethod.GET)
  public @ResponseBody IncidenceDtoList getByUserTodayDto(@RequestParam(value = "userOid", required = true) int userOid, Model model)
  {
    
    List<Incidence> incidences = incidenceService.getByUserOidToday(userOid);  
    IncidenceDtoList incidenceDtos = new IncidenceDtoList();

    incidenceDtos.setCount(incidences.size());
    
    for (int i = 0; i < incidences.size(); i++)
    {
      IncidenceDto iDto = new IncidenceDto(incidences.get(i));
      incidenceDtos.add(iDto);
    }
    
    return incidenceDtos;
   
  }
  
}
