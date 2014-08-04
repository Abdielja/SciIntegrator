/**
 * 
 */
package com.sci.integrator.controlers.view;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sci.integrator.domain.core.User;
import com.sci.integrator.services.IUserService;

/**
 * @author Abdiel Jaramillo OJEDIS
 *
 */
@Controller
public class IncidencesController
{

  private static final Logger logger = LoggerFactory.getLogger(UserBalanceController.class);

  private IUserService userService;
  
  @Inject
  public IncidencesController(IUserService userService)
  {
    this.userService = userService;
  }

  @RequestMapping({ "/userIncidences" })
  public String userIncidences(Locale locale, Model model)
  {
    logger.info("User Incidences " + locale.toString());
    
    List<User> users = userService.getAllActive();
    
    model.addAttribute("users", users);
    
    return "UserIncidences";
  }

}
