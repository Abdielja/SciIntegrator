/**
 * 
 */
package com.sci.integrator.persistence.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.sci.integrator.domain.core.Transaction;
import com.sci.integrator.persistence.ITransactionDAO;

/**
 * 
 * @author Abdiel
 *
 */
public class TransactionDAOTest 
{

  private ITransactionDAO transactionDao;
  
  public void setTransactionDao(ITransactionDAO transactionDao)
  {
    this.transactionDao = transactionDao;
  }
  
	/**
	 * Test method for {@link com.sci.integrator.persistence.hibernate.TransactionDaoImpl#TransactionDAOImpl(org.hibernate.SessionFactory)}.
	 */
	@Test
	public void testTransactionDAOImpl() 
	{
		fail("Not yet implemented");
	}

	@Test
	public void testTransactionDaoNotNull()
	{
	  assert(transactionDao != null);
	}
	
	/**
	 * Test method for {@link com.sci.integrator.persistence.hibernate.TransactionDaoImpl#getByOid(long)}.
	 */
	@Test
	public void testGetByOidNotNull() 
	{
	  Transaction trx = transactionDao.getByOid(2);
		assert(trx != null);
	}

	/**
	 * Test method for {@link com.sci.integrator.persistence.hibernate.TransactionDaoImpl#getAll()}.
	 */
	@Test
	public void testGetAll() 
	{
		fail("Not yet implemented");
	}

}
