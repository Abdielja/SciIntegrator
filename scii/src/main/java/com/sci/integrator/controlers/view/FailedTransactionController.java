/**
 * 
 */
package com.sci.integrator.controlers.view;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sci.integrator.domain.core.Transactions;
import com.sci.integrator.services.ITransactionService;
import com.sci.integrator.services.relational.TransactionServiceImpl;

/**
 * @author Abdiel Jaramillo Ojedis
 *
 */

/**
 * Handles requests for the processed transactions page.
 */

@Controller
public class FailedTransactionController
{

  @Inject 
  ITransactionService transactionService;

  private static final Logger logger = LoggerFactory.getLogger(FailedTransactionController.class);

  /**
   * Simply selects the transactions view to render by returning its name.
   */
  @RequestMapping({"/transactions_failed"})
  public String showFailedTransactionsPage(Locale locale, Model model) 
  {
    logger.info("Transactions with errors . The client locale is " + locale.toString());
    
    Transactions transactions = transactionService.getAllFailedToday();
    model.addAttribute("transactions", transactions.getTransaction());
    
    return "transactions_failed";
  }
  
}
