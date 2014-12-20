/**
 * 
 */
package com.sci.integrator.provider.adempiere;

import com.sci.integrator.domain.core.SciiException;
import com.sci.integrator.provider.ITransactionProviderGateway;
import com.sci.integrator.provider.SOAPBaseProviderGateway;
import com.sci.integrator.transaction.Transaction;

/**
 * @author Administrator
 *
 */
public class AdempiereTransactionProviderGateway extends SOAPBaseProviderGateway implements ITransactionProviderGateway 
{

	@Override
	public Transaction processTransaction(Transaction trx) throws SciiException {
		// TODO Auto-generated method stub
		return null;
	}

}
