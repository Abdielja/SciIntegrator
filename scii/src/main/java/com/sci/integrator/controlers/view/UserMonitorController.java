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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sci.integrator.domain.core.User;
import com.sci.integrator.services.IUserService;

/**
 * @author Abdiel Jaramillo Ojedis
 * 
 * Handles requests for the User Monitor page.
 */

@Controller
public class UserMonitorController
{

  private static final Logger logger = LoggerFactory
                                         .getLogger(UserMonitorController.class);

  private IUserService        userService;

  @Inject
  public UserMonitorController(IUserService userService)
  {
    this.userService = userService;
  }

  @RequestMapping({ "/userMonitor" })
  public String userMonitor(Locale locale, Model model)
  {
    logger.info("User Monitor " + locale.toString());

    List<User> users = userService.getAllActive();

    model.addAttribute("users", users);

    return "userMonitor";
  }
  
}
