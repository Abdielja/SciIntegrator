/**
 * 
 */
package com.sci.integrator.handlers;

import java.util.Date;
import java.util.Iterator;

import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.StaleObjectStateException;
import org.hibernate.StaleStateException;
import org.springframework.web.client.HttpServerErrorException;

import com.sci.integrator.domain.core.AppSettings;
import com.sci.integrator.domain.core.SciiException;
import com.sci.integrator.domain.core.SciiResult;
import com.sci.integrator.provider.IProvider;
import com.sci.integrator.services.ITransactionService;
import com.sci.integrator.transaction.Transaction;
import com.sci.integrator.transaction.TransactionError;
import com.sci.integrator.transaction.Transactions;

/**
 * @author Abdiel Jaramillo Ojedis
 * 
 */

public class TransactionHandler
{

  private final Log   logger = LogFactory.getLog(this.getClass());

  @Inject
  AppSettings appSettings;
  
  @Inject
  ITransactionService transactionService;

  @Inject
  IProvider           provider;

  public void process()
  {

    // logger.info(this.getClass().getName() + " Start");

    TransactionError trxError = new TransactionError();

    try
    {
      Transactions transactions = transactionService.getAllPending();
      Iterator<Transaction> iTrx = transactions.getTransaction().iterator();

      if (iTrx.hasNext())
      {
        System.out.println(transactions.getTransaction().size()
            + " Pending transactions found (" + new Date() + ")\n");
      }

      while (iTrx.hasNext())
      {

        Transaction trx = iTrx.next();

        SciiResult sr = trx.beforProcess();
        if (sr.getreturnCode() != Transaction.STATUS_VALIDATED)
        {
          trx.setstatus(Transaction.STATUS_FAILED);
         
          SciiException se = new SciiException(sr.getreturnMessage(), sr.getreturnCode());
          trxError.setMessage(se.getMessage());
          trx.setTrxError(trxError);
          throw se;
        }
        
        if (trx.getstatus() != Transaction.STATUS_PROCESSED && trx.getstatus() != Transaction.STATUS_MAX_RETRIES_REACHED)
        {

          System.out.println("Processing...");

          try
          {
            provider.getTransactionProviderGateway().processTransaction(trx);
            trx.setstatus(Transaction.STATUS_PROCESSED);
            
            //System.out.println("Processed.");

          } 
          catch (SciiException se)
          {
            System.out.println(se.getMessage());
            trx.setstatus(Transaction.STATUS_FAILED);
            trxError.setMessage(se.getMessage());
            trx.setTrxError(trxError);
            System.out.println("Failed.");
          } 
          catch (HttpServerErrorException hse)
          {
            System.out.println(hse.getResponseBodyAsString());
            trx.setstatus(Transaction.STATUS_FAILED);
            trxError.setMessage(hse.getResponseBodyAsString());
            trx.setTrxError(trxError);
            System.out.println("Failed.");
          } 
          catch (Exception e)
          {
            System.out.println(e.getMessage());
            trx.setstatus(Transaction.STATUS_FAILED);
            trxError.setMessage(e.getMessage());
            trx.setTrxError(trxError);
            System.out.println("Failed.");
          }
          finally
          {
            
            if (trx.getstatus() == Transaction.STATUS_FAILED)
            {
              if (trx.getRetries() >= appSettings.getMaxRetries())
              {
                trx.setstatus(Transaction.STATUS_MAX_RETRIES_REACHED);                
              }
              else
              {
                trx.setRetries(trx.getRetries() + 1);
              }
            }
            
            // *** Update Transaction ***
            transactionService.updateTransaction(trx);
          }

        }
      }
    } 
    catch (Exception e)
    {
      System.out.println(e.getMessage());
    }

    // logger.info(this.getClass().getName() + " Finish");

  }

  public Transaction process(Transaction trx)
  {

    // logger.info(this.getClass().getName() + " Start");

    SciiResult result = new SciiResult();

    if (trx.getstatus() != Transaction.STATUS_PROCESSED)
    {

      TransactionError trxError = new TransactionError();

      try
      {
        Transaction retTrx = provider.getTransactionProviderGateway().processTransaction(trx);

        trx.setstatus(Transaction.STATUS_PROCESSED);

        result.setreturnCode(SciiResult.RETURN_CODE_OK);
        
        result.setreturnMessage("OK");

      } 
      catch (SciiException se)
      {

        System.out.println(se.getMessage());
        trx.setstatus(Transaction.STATUS_FAILED);

        if (!se.getMessage().isEmpty())
        {
          trxError.setMessage(se.getMessage());
        }
        else
        {
          trxError.setMessage("SciiException: Unedentified Server Error");          
        }
        
        if (se.getCause() != null)
        {
          trxError.setCause(se.getCause().getMessage());
        }
        // trxError.setStackTrace(e.getStackTrace()[0].getClassName() + "." +
        // e.getStackTrace()[0].getMethodName() + " - Line " +
        // e.getStackTrace()[0].getLineNumber());
        trx.setTrxError(trxError);
      } 
      catch (HttpServerErrorException e)
      {

        System.out.println(e.getResponseBodyAsString());

        trx.setstatus(Transaction.STATUS_FAILED);
        trxError.setMessage(e.getResponseBodyAsString());
        trx.setTrxError(trxError);

        result.setreturnCode(SciiResult.RETURN_CODE_UNIDENTFIED_ERROR);
        result.setreturnMessage(e.getMessage());

      } 
      finally
      {
        transactionService.updateTransaction(trx);          
      }

    }

    return trx;

  }

}