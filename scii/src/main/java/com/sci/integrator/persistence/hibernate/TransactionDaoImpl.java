/**
 * 
 */
package com.sci.integrator.persistence.hibernate;

import java.sql.SQLException;
import java.util.Iterator;

import javax.inject.Inject;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sci.integrator.domain.core.SciiResult;
import com.sci.integrator.domain.core.Transaction;
import com.sci.integrator.domain.core.TransactionOpen;
import com.sci.integrator.domain.core.Transactions;
import com.sci.integrator.domain.customer.TransactionCustomerExtra;
import com.sci.integrator.domain.incidence.TransactionIncidence;
import com.sci.integrator.domain.invoice.Invoice;
import com.sci.integrator.domain.invoice.InvoiceLine;
import com.sci.integrator.domain.invoice.TransactionInvoice;
import com.sci.integrator.domain.invoice.TransactionInvoiceReversal;
import com.sci.integrator.domain.order.Order;
import com.sci.integrator.domain.order.OrderLine;
import com.sci.integrator.domain.order.TransactionOrder;
import com.sci.integrator.domain.payment.Payment;
import com.sci.integrator.domain.payment.TransactionPayment;
import com.sci.integrator.domain.quotation.Quotation;
import com.sci.integrator.domain.quotation.QuotationLine;
import com.sci.integrator.domain.quotation.TransactionQuotation;
import com.sci.integrator.persistence.ITransactionDAO;

/**
 * @author Abdiel Jaramillo Ojedis
 *
 */

public class TransactionDaoImpl implements ITransactionDAO
{

  @Inject
  private UserDataDaoImpl userDataDao;
  
  private static final Logger logger = LoggerFactory.getLogger(TransactionDaoImpl.class);

  private SessionFactory sessionFactory;
  
  public void setSessionFactory(SessionFactory sessionFactory) 
  {
    this.sessionFactory = sessionFactory;
  } 
  
	private Session currentSession() 
	{
		return sessionFactory.getCurrentSession();
	}

	/*
	 * GetByOid(long _oid) 
	 * (non-Javadoc)
	 * @see com.sci.integrator.persistence.ITransactionDAO#getByOid(long)
	 */
	public Transaction getByOid(long _oid) 
	{
	  
	  System.out.println("TransactionDaoImpl.getByOid: BEGIN");
	  
    Transaction t = null;
    
	  try
	  {
	    t = (Transaction)currentSession().get(Transaction.class, _oid);	    
	  }
	  catch(Exception e)
	  {
	    System.out.println(e.getMessage());    
	  }
	  finally
	  {
	    System.out.println("TransactionDaoImpl.getByOid: END");
	  }
	  
	  return t;
    	  
	}
	
	@SuppressWarnings("unchecked")
  public Transactions getAll() 
	{
	  Transactions transactions = new Transactions();
	  
	  transactions.setTransaction(currentSession().createQuery( "FROM Transaction ORDER BY oid ASC LIMIT 10" ).list()); 
		return transactions;
	}

  @SuppressWarnings("unchecked")
  public Transactions getAllToday() 
  {
    Transactions transactions = new Transactions();
    
    //Calendar calendar = Calendar.getInstance();
    Query trxQuery = currentSession().createQuery( "FROM Transaction WHERE creationDate >= createdBy.lastBalanceDate ORDER BY oid ASC LIMIT 10" );    
    transactions.setTransaction(trxQuery.list()); 

    return transactions;
  }

  @SuppressWarnings("unchecked")
  public Transactions getByUserOidToday(long userOid)
  {
    Transactions transactions = new Transactions();
    Query trxQuery = currentSession().createQuery( "FROM Transaction WHERE creationDate >= createdBy.lastBalanceDate AND createdBy.oid = :userOid ORDER BY oid ASC LIMIT 10" );    
    trxQuery.setParameter("userOid", userOid);
    transactions.setTransaction(trxQuery.list()); 
    return transactions;
  }

  @SuppressWarnings("unchecked")
  public Transactions getByUserOidAndTypeToday(long userOid, int trxType)
  {
    Transactions transactions = new Transactions();
    Query trxQuery = currentSession().createQuery( "FROM Transaction WHERE creationDate >= createdBy.lastBalanceDate AND createdBy.oid = :userOid AND type = :trxType ORDER BY oid ASC LIMIT 10" );    
    trxQuery.setParameter("userOid", userOid);
    trxQuery.setParameter("trxType", trxType);
    transactions.setTransaction(trxQuery.list()); 
    return transactions;
  }

  @SuppressWarnings("unchecked")
  public Transactions getByTrxTypeToday(int trxType)
  {
    Transactions transactions = new Transactions();
    Query trxQuery = currentSession().createQuery( "FROM Transaction WHERE creationDate >= createdBy.lastBalanceDate AND type = :trxType ORDER BY oid ASC LIMIT 10" );    
    trxQuery.setParameter("trxType", trxType);
    transactions.setTransaction(trxQuery.list()); 
    return transactions;
  }
  
  @SuppressWarnings("unchecked")
  public Transactions getAllPending()
  {    
    Transactions transactions = new Transactions();
    transactions.setTransaction(currentSession().createQuery("FROM Transaction WHERE (status = 0 OR status = 1) AND asynchronous = 'true' ORDER BY oid ASC").list()); 
    return transactions;
  }

  @SuppressWarnings("unchecked")
  public Transactions getAllPendingToday()
  {    
    Transactions transactions = new Transactions();
    transactions.setTransaction(currentSession().createQuery("FROM Transaction WHERE creationDate >= createdBy.lastBalanceDate AND (status = 0 OR status = 1) AND asynchronous = 'true' ORDER BY oid ASC").list()); 
    return transactions;
  }

  @SuppressWarnings("unchecked")
  public Transactions getAllFailed()
  {
    Transactions transactions = new Transactions();
    transactions.setTransaction(currentSession().createQuery("FROM Transaction WHERE (status = 1 OR status = 7) ORDER BY oid ASC").list()); 
    return transactions;
  }

  @SuppressWarnings("unchecked")
  public Transactions getAllFailedToday()
  {
    Transactions transactions = new Transactions();
    transactions.setTransaction(currentSession().createQuery("FROM Transaction WHERE creationDate >= createdBy.lastBalanceDate AND (status = 1 OR status = 7) ORDER BY oid ASC").list()); 
    return transactions;
  }

  @SuppressWarnings("unchecked")
  public Transactions getAllWithRetriesReached()
  {
    Transactions transactions = new Transactions();
    transactions.setTransaction(currentSession().createQuery("FROM Transaction WHERE status = 7 ORDER BY oid ASC").list()); 
    return transactions;
  }

  public SciiResult save(Transaction trx)
  {

    SciiResult result = new SciiResult();
    
    System.out.println("\n ITransactionDAO...");
    System.out.println("\n  Asynchronous " + trx.getasynchronous() + "\n");
    System.out.println("    CreatedBy " + trx.getcreatedBy() + "\n");
    System.out.println("    Creation date " + trx.getcreationDate() + "\n");
    System.out.println("    Description " + trx.getdescription() + "\n");
    System.out.println("   " + trx.getorganizationId() + "\n");
    System.out.println("   " + trx.getprovider().getOid() + "\n");
    System.out.println("   " + trx.getRetries() + "\n");
    System.out.println("   " + trx.getroleId() + "\n");
    System.out.println("   " + trx.getstatus() + "\n");
    //System.out.println("   " + trx.getTrxError().getOid() + "\n");
    System.out.println("   " + trx.getwarehouseId() + "\n");
    System.out.println("   ObjectId ");
    System.out.println("   " + trx.gettrxType() + "\n");
    System.out.println("   Oid " + trx.getoid() + "\n");

        
    // **** Save Open Transaction ****
    if (trx.getClass() == TransactionOpen.class)
    {
           
      TransactionOpen trxOpen = (TransactionOpen)trx;
      
      // *** Delete previous UserData Objects for this User ***      
      //userDataDao.deleteByUserOid(trxOpen.getcreatedBy().getoid());
      
      // *** Save new Transaction ***
      try
      {        
        result.settransactionOid((Long)currentSession().save(trxOpen));
        result.setreturnCode(SciiResult.RETURN_CODE_OK);
        System.out.println("TransactionOpen was created succesfully");
      }
      catch(Exception e)
      {
        System.out.println(e.getStackTrace());
        result.setreturnCode(SciiResult.RETURN_CODE_UNIDENTFIED_ERROR);
        result.setreturnMessage(e.getMessage());
      }
      
    }
   
    // **** Save Quotation Transaction ****
    if (trx.getClass() == TransactionQuotation.class)
    {
           
      // *** Save Quotation first ***
      TransactionQuotation trxQuotation = (TransactionQuotation)trx;      
      Quotation quotation = trxQuotation.getquotation();
      Long quotationOid = (Long)currentSession().save(quotation);
      quotation.setoid(quotationOid);
      result.setaffectedObjectOid(quotation.getoid());
      
      // *** Quotation lines ***
      Iterator<QuotationLine> iQuotationLines = trxQuotation.getquotation().getquotationLine().iterator();
      while (iQuotationLines.hasNext())
      {
        QuotationLine ql = iQuotationLines.next();
        ql.setQuotation(quotation);
        currentSession().save(ql);
      }

      // *** Finally, save transaction ***
      trxQuotation.setquotation(quotation);
      result.settransactionOid((Long)currentSession().save(trxQuotation));
      result.setreturnCode(SciiResult.RETURN_CODE_OK);

    }
   
    // **** Save Order Transaction ****
    if (trx.getClass() == TransactionOrder.class)
    {
           
      // *** Save Order first ***
      TransactionOrder trxOrder = (TransactionOrder)trx;      

      Order order = trxOrder.getorder();
      
      order.setCreatedBy(trxOrder.getcreatedBy());
      order.setcreationDate(trxOrder.getcreationDate());

      Long orderOid = (Long)currentSession().save(order);

      order.setoid(orderOid);
      result.setaffectedObjectOid(order.getoid());
      result.setAffectedObjectServerId(String.valueOf(order.getoid()));

      // *** Order lines ***
      Iterator<OrderLine> iOrderLines = trxOrder.getorder().getorderLine().iterator();
      while (iOrderLines.hasNext())
      {
        OrderLine ol = iOrderLines.next();
        ol.setOrder(order);
        currentSession().save(ol);
      }

      // *** Finally, save transaction ***
      trxOrder.setorder(order);
      result.settransactionOid((Long)currentSession().save(trxOrder));
      
      result.setreturnCode(SciiResult.RETURN_CODE_OK);

    }
   
    // **** Save InvoiceDO Transaction ****
    if (trx.getClass() == TransactionInvoice.class)
    {
           
      // *** Save InvoiceDO first ***
      TransactionInvoice trxInvoice = (TransactionInvoice)trx;
      
      Invoice invoice = trxInvoice.getinvoice();
      invoice.setCreatedBy(trxInvoice.getcreatedBy());
      invoice.setcreationDate(trxInvoice.getcreationDate());
      
      Long invoiceOid = (Long)currentSession().save(invoice);
      
      invoice.setoid(invoiceOid);
      result.setaffectedObjectOid(invoice.getoid());
      result.setAffectedObjectServerId(String.valueOf(invoice.getoid()));
      //result.setAffectedObjectServerId(invoice.getserverId());
      
      // *** Invoice lines ***
      Iterator<InvoiceLine> iInvoiceLines = trxInvoice.getinvoice().getinvoiceLine().iterator();
      while (iInvoiceLines.hasNext())
      {
        InvoiceLine il = iInvoiceLines.next();
        il.setInvoice(invoice);
        currentSession().save(il);
      }

      // *** Finally, save transaction ***
      trxInvoice.setinvoice(invoice);
      result.settransactionOid((Long)currentSession().save(trxInvoice));
      result.setreturnCode(SciiResult.RETURN_CODE_OK);

    }
   
    // **** Save Payment Transaction ****
    if (trx.getClass() == TransactionPayment.class)
    {
           
      // *** Save payment ***
      TransactionPayment trxPayment = (TransactionPayment)trx;      
      Payment payment = trxPayment.getpayment();
      payment.setCreatedBy(trxPayment.getcreatedBy());
      payment.setcreationDate(trxPayment.getcreationDate());

      Long paymentOid = (Long)currentSession().save(payment);
      payment.setoid(paymentOid);
      result.setaffectedObjectOid(payment.getoid());
      
      // *** Finally, save transaction ***
      trxPayment.setpayment(payment);
      result.settransactionOid((Long)currentSession().save(trxPayment));
      result.setreturnCode(SciiResult.RETURN_CODE_OK);

    }
   
    // **** Save Invoice Reversal Transaction ****
    if (trx.getClass() == TransactionInvoiceReversal.class)
    {           
      TransactionInvoiceReversal trxReversal = (TransactionInvoiceReversal)trx;      
      result.settransactionOid((Long)currentSession().save(trxReversal));
      result.setreturnCode(SciiResult.RETURN_CODE_OK);
    }
 
    // **** Save Incidence Transaction ****
    if (trx.getClass() == TransactionIncidence.class)
    {
           
      TransactionIncidence trxIncidence = (TransactionIncidence)trx;
      
      trxIncidence.getIncidence().setCreatedBy(trxIncidence.getcreatedBy());
      // *** Save new Transaction ***
      result.settransactionOid((Long)currentSession().save(trxIncidence));
      result.setreturnCode(SciiResult.RETURN_CODE_OK);
    }
   
    // **** Save CustomerExtra Transaction ****
    if (trx.getClass() == TransactionCustomerExtra.class)
    {
           
      TransactionCustomerExtra transactionCustomerExtra = (TransactionCustomerExtra)trx;
      
      transactionCustomerExtra.getCustomerExtra().setCreatedBy(transactionCustomerExtra.getcreatedBy());
      // *** Save new Transaction ***
      result.settransactionOid((Long)currentSession().save(transactionCustomerExtra));
      result.setreturnCode(SciiResult.RETURN_CODE_OK);
    }
   
    return result;

  }

  public void update(Transaction trx)
  {
    currentSession().update(trx);
  }

}
