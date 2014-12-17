/**
 * 
 */
package com.sci.integrator.provider.openbravo;

import com.sci.integrator.domain.core.SciiException;
import com.sci.integrator.provider.BaseProvider;
import com.sci.integrator.transaction.Transaction;

/**
 * @author Abdiel Jaramillo Ojedis
 *
 */
public class ObProvider extends BaseProvider
{
  
  public ObProvider()
  {
    this.setName("OpenBravo Provider");
  }

  public Transaction processTransaction(Transaction trx) throws SciiException
  {

    Transaction retTrx = getTransactionProviderGateway().processTransaction(trx);
    
    return retTrx;
    
  }

  public void syncData()
  {
    
  }
  
}
