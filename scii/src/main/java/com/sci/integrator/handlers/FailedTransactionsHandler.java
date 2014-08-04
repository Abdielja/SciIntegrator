package com.sci.integrator.handlers;

import java.util.Date;
import java.util.Iterator;

import javax.inject.Inject;

import org.springframework.web.client.HttpServerErrorException;

import com.sci.integrator.domain.core.SciiException;
import com.sci.integrator.domain.core.Transaction;
import com.sci.integrator.domain.core.TransactionError;
import com.sci.integrator.domain.core.Transactions;
import com.sci.integrator.services.ITransactionService;

public class FailedTransactionsHandler
{

  @Inject
  ITransactionService transactionService;

  public FailedTransactionsHandler()
  {
    // TODO Auto-generated constructor stub
  }

  public void process()
  {

    // logger.info(this.getClass().getName() + " Start");

    TransactionError trxError = new TransactionError();

    try
    {
      Transactions transactions = transactionService.getAllWithRetriesReached();
      Iterator<Transaction> iTrx = transactions.getTransaction().iterator();

      int numTrx = transactions.getTransaction().size();
      
      if (numTrx > 0)
      {
        System.out.println(numTrx
            + " Transactions with max. number of retries found (" + new Date() + ")\n");
      }

      while (iTrx.hasNext())
      {

        Transaction trx = iTrx.next();

        trx.setstatus(Transaction.STATUS_FAILED);
        trx.setRetries(0);
        
        // *** Update Transaction ***
        transactionService.updateTransaction(trx);
      }

      if (numTrx > 0)
      {
        System.out.println(transactions.getTransaction().size()
            + " Transactions returned to failed transactions queue (" + new Date() + ")\n");
      }
      
    } 
    catch (Exception e)
    {
      System.out.println(e.getMessage());
    }

  }

  
}
