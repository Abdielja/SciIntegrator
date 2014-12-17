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
import org.springframework.web.bind.annotation.RequestParam;

import com.sci.integrator.services.ITransactionService;
import com.sci.integrator.services.relational.TransactionServiceImpl;
import com.sci.integrator.transaction.Transactions;

/**
 * @author Abdiel Jaramillo Ojedis
 *
 */

/**
 * Handles requests for the processed transactions page.
 */

@Controller
public class TransactionController 
{

  @Inject 
  ITransactionService transactionService;

  private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

	@RequestMapping({"/transactions"})
//	public String showTransactionsPage(@RequestParam("userOid") String strUserOid, @RequestParam("trxType") String strTrxType, Locale locale, Model model)
  public String showTransactionsPage(@RequestParam("userOid") String strUserOid, Locale locale, Model model)
	{
	  
    logger.info("Transactions for user " + strUserOid + " made today. The client locale is " + locale.toString());
    
    Transactions transactions;
    
    long userOid;
    
    if (strUserOid == "")
    {
      userOid = 0;
    }
    else
    {
      userOid = Long.parseLong(strUserOid);     
    }
    
    if (userOid > 0)
    {
      transactions = transactionService.getByUserOidToday(userOid);            
    }
    else
    {
      transactions = transactionService.getAllToday();      
    }

    model.addAttribute("transactions", transactions.getTransaction());
    
    return "transactions";
	}
	
}
