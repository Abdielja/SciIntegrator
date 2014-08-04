package com.sci.integrator.controlers.view;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sci.integrator.domain.core.User;
import com.sci.integrator.services.IUserService;

/**
 * @author Abdiel Jaramillo Ojedis
 * 
 * Handles requests for the User Balance page.
 */

@Controller
public class UserBalanceController
{

  private static final Logger logger = LoggerFactory.getLogger(UserBalanceController.class);

  private IUserService userService;
  
  @Inject
  public UserBalanceController(IUserService userService)
  {
    this.userService = userService;
  }

  @RequestMapping({ "/userBalance" })
  public String userMonitor(Locale locale, Model model)
  {
    logger.info("User Balance " + locale.toString());
    
    List<User> users = userService.getAllActive();
    
    model.addAttribute("users", users);
    
    return "UserBalance";
  }
    
}
