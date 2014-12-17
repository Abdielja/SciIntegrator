/**
 * 
 */
package com.sci.integrator.services.relational;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sci.integrator.domain.core.SciiException;
import com.sci.integrator.domain.core.SciiResult;
import com.sci.integrator.domain.core.TransactionOpen;
import com.sci.integrator.domain.core.User;
import com.sci.integrator.domain.core.UserData;
import com.sci.integrator.domain.invoice.Invoice;
import com.sci.integrator.domain.payment.Payment;
import com.sci.integrator.persistence.ITransactionDAO;
import com.sci.integrator.persistence.IUserDao;
import com.sci.integrator.persistence.IUserDataDao;
import com.sci.integrator.persistence.hibernate.UserDataDaoImpl;
import com.sci.integrator.provider.openbravo.transaction.TransactionInvoice;
import com.sci.integrator.provider.openbravo.transaction.TransactionInvoiceReversal;
import com.sci.integrator.provider.openbravo.transaction.TransactionPayment;
import com.sci.integrator.services.IInvoiceService;
import com.sci.integrator.services.ITransactionService;
import com.sci.integrator.transaction.Transaction;
import com.sci.integrator.transaction.TransactionError;
import com.sci.integrator.transaction.Transactions;

/**
 * @author Abdiel Jaramillo Ojedis
 *
 */

@Service("transactionService")
public class TransactionServiceImpl implements ITransactionService 
{

  @Inject IInvoiceService invoiceService;
  
  private ITransactionDAO transactionDao;
  private IUserDao userDao;
  
  public void setTransactionDao(ITransactionDAO transactionDao)
  {
    this.transactionDao = transactionDao;
  }
  
  public void setUserDao(IUserDao userDao)
  {
    this.userDao = userDao;
  }


  @Transactional
	public SciiResult saveTransaction(Transaction transaction) throws SciiException 
	{
    SciiResult result;
    
    Date currDate = new Date();
    
    System.out.println("\n TransactionServiceImpl.saveTransaction: BEGIN");
    System.out.println("\n  Transaction type: " + transaction.gettrxType());
    System.out.println("  Creation date: " + transaction.getcreationDate());
    System.out.println("  Server date: " + currDate);
    System.out.println("  Created by: " + transaction.getcreatedBy().getname() + "\n");
   
    /*
     * Este Cambio se hizo por motivos de sincronizacion, ya que algunas veces
     * los moviles se descargaban y los quedaban con fechas erroneas que
     * se le asignaban a las transacciones que se enviaban.
     * 
     *  Cambio solicitado por Jose, a principios de 2014
     */
    
    // ***** Use server date instead of mobile's to avoid conflicts *****
    //transaction.setcreationDate(currDate);
    
    /*
     * Julio 28 2014. Jose pidio eliminar el cambio a la fecha de creacion antes 
     * solicitado, ya que algunos usuarios envian las transacciones al dia siguiente
     * y estas deben ser aplicadas con la fecha que se hicieron no con la fecha 
     * del servidor al momento de recibirlas. Por eso esta en comentario la linea arriba
     * de este comentario.
     */
    
    try
    {
      result = transactionDao.save(transaction);
    }
    catch(DataIntegrityViolationException dive)
    {
      throw new SciiException(dive.getMessage());      
    }
    catch(Exception e)
    {      
      throw new SciiException(e.getMessage());
    }
    
    System.out.println("\n TransactionServiceImpl.saveTransaction: END");

    return result;
	}

  @Transactional
  public void updateTransaction(Transaction transaction) 
  {
    
    transactionDao.update(transaction);
    
    if (transaction.getClass() == TransactionInvoiceReversal.class)
    {
    
      // ** Set Invoice status tu REVERSED **
      TransactionInvoiceReversal trxRev = (TransactionInvoiceReversal)transaction; 
      Invoice invoice = trxRev.getinvoice();
      invoiceService.updateInvoice(invoice);
      
    }

  }

  @Transactional
	public Transaction getTransactionByOid(long oid) 
	{

    System.out.println("\n TransactionServiceImpl.getTransactionByOid: BEGIN");
    
	  Transaction trx = transactionDao.getByOid(oid);

	  System.out.println("\n TransactionServiceImpl.getTransactionByOid: END");

	  return trx;
	
	}

  @Transactional
	public void deleteTransaction(long id) 
  {
		// TODO Auto-generated method stub
		
	}

  @Transactional
  public Transactions getAllTransactions()
  {       
    Transactions transactions = transactionDao.getAll();
    return transactions;
  }

  @Transactional
  public Transactions getAllToday()
  {       
    Transactions transactions = transactionDao.getAllToday();
    return transactions;
  }


  @Transactional
  public Transactions getAllPending()
  {
    Transactions transactions = transactionDao.getAllPending();
    return transactions;
  }


  @Transactional
  public Transactions getAllFailed()
  {
    Transactions transactions = transactionDao.getAllFailed();
    return transactions;
  }

  @Transactional
  public User validateUser(String userName, String password) throws SciiException
  {

    User user = null;
    
    user = userDao.getByUserName(userName);
    
    if (user != null)
    {
      if (!user.getpassword().equals(password))
      {
        throw new SciiException("SCII Error - Invalid User Credentials.");
      }
    }
    else
    {
      throw new SciiException("SCII Error - User Does not exists.");      
    }
    
    return user;
    
  }

  @Transactional
  public void validateTransaction(Transaction trx) throws SciiException
  {
    
    System.out.println("\n TransactionServiceImpl...");
    System.out.println("\n  Transaction type: " + trx.gettrxType());
    System.out.println("  Creation date: " + trx.getcreationDate());
    System.out.println("  Created by: " + trx.getcreatedBy().getname() + "\n");

    // *** Make sure user's last balance date is not null ***
    Object objDate = trx.getcreatedBy().getLastBalanceDate();
    
    // ***** Temporal *****
    if (objDate == null)
    {
      trx.getcreatedBy().setLastBalanceDate(new Date());
      userDao.update(trx.getcreatedBy());
    }
    
    // ***** Validate Invoice *****
    
    if (trx.getClass() == TransactionInvoice.class)
    {
    
      TransactionInvoice trxInvoice = (TransactionInvoice)trx; 

        Invoice invoice = invoiceService.getByOid(trxInvoice.getinvoice().getoid());

        if (invoice != null)
        {
          String strError = "ERROR - Invoice with oid " + trxInvoice.getinvoice().getoid() + " already exists.";
          SciiException e = new SciiException(strError, SciiResult.RETURN_CODE_INVOICE_OID_ALREADY_EXISTS);
          throw e;
        }
    }

    // ***** Validate Payment *****
    
    if (trx.getClass() == TransactionPayment.class)
    {
    
      TransactionPayment trxPayment = (TransactionPayment)trx; 

      Payment payment = trxPayment.getpayment();
      long invoiceOid = payment.getinvoiceServerOid();
      
      Invoice invoice = invoiceService.getByOid(invoiceOid);

      if (invoice == null)
      {
        String strError = "ERROR - Payment with oid " + payment.getoid() + " references a non existent invoice.";
        SciiException e = new SciiException(strError, SciiResult.RETURN_CODE_NON_EXISTENT_INVOICE_OID);
        throw e;
      }
    }

    // ***** Validate Invoice Reversal *****
    
    if (trx.getClass() == TransactionInvoiceReversal.class)
    {
    
      TransactionInvoiceReversal trxRev = (TransactionInvoiceReversal)trx; 

      long invoiceOid = trxRev.getinvoice().getoid();
      
      Invoice invoice = invoiceService.getByOid(invoiceOid);

      if (invoice == null)
      {
        String strError = "ERROR - Invoice Reversal with oid " + trxRev.getoid() + " references a non existent invoice.";
        SciiException e = new SciiException(strError, SciiResult.RETURN_CODE_NON_EXISTENT_INVOICE_OID);
        throw e;
      }
    }
    
  }

  @Transactional
  public Transactions getAllPendingToday()
  {
    Transactions transactions = transactionDao.getAllPendingToday();
    return transactions;
  }

  @Transactional
  public Transactions getAllFailedToday()
  {
    Transactions transactions = transactionDao.getAllFailedToday();
    return transactions;
  }

  @Transactional
  public Transactions getAllWithRetriesReached()
  {
    Transactions transactions = transactionDao.getAllWithRetriesReached();
    return transactions;
  }
  
  @Transactional
  public Transactions getByUserOidToday(long userOid)
  {
    Transactions transactions = transactionDao.getByUserOidToday(userOid);
    return transactions;
  }

  public Transactions getByUserOidAndTypeToday(long userOid, int trxType)
  {
    Transactions transactions = transactionDao.getByUserOidAndTypeToday(userOid, trxType);
    return transactions;
  }

  public Transactions getByTrxTypeToday(int trxType)
  {
    Transactions transactions = transactionDao.getByTrxTypeToday(trxType);
    return transactions;
  }

}
