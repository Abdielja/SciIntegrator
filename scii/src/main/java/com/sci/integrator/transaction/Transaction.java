/**
 * 
 */
package com.sci.integrator.transaction;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.sci.integrator.domain.core.Provider;
import com.sci.integrator.domain.core.SciiException;
import com.sci.integrator.domain.core.SciiResult;
import com.sci.integrator.domain.core.User;
import com.sci.integrator.provider.adempiere.transaction.TransactionOpenAdempiere;
import com.sci.integrator.provider.openbravo.transaction.TransactionIncidence;
import com.sci.integrator.provider.openbravo.transaction.TransactionInvoice;
import com.sci.integrator.provider.openbravo.transaction.TransactionInvoiceReversal;
import com.sci.integrator.provider.openbravo.transaction.TransactionOpen;
import com.sci.integrator.provider.openbravo.transaction.TransactionOrder;
import com.sci.integrator.provider.openbravo.transaction.TransactionPayment;
import com.sci.integrator.provider.openbravo.transaction.TransactionQuotation;

/**
 * @author Abdiel Jaramillo Ojedis
 *
 */

@Entity
@Table(name = "transaction")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.INTEGER)
public abstract class Transaction implements ITransaction, Serializable
{

  // **** Public Constants *****

  public static final int   STATUS_VALIDATED           = -1;
  public static final int   STATUS_PENDING             = 0;
  public static final int   STATUS_FAILED              = 1;
  public static final int   STATUS_PROCESSED           = 2;
  public static final int   STATUS_REVERSED            = 3;
  public static final int   STATUS_MAX_RETRIES_REACHED = 7;
  public static final int   STATUS_INVOICE_PENDING     = 8;
  public static final int   STATUS_PAYMENT_PENDING     = 9;
  public static final int   STATUS_INVOICE_REVERSED    = 10;

  public static final int   TRANSACTION_GENERIC        = -1;
  public static final int   TRANSACTION_QUOTATION      = 0;
  public static final int   TRANSACTION_ORDER          = 1;
  public static final int   TRANSACTION_INVOICE        = 2;
  public static final int   TRANSACTION_PAYMENT        = 3;
  public static final int   TRANSACTION_SHIPMENT       = 4;
  public static final int   TRANSACTION_ORDER_REVERSAL = 5;
  public static final int   TRANSACTION_INCIDENCE      = 6;
  public static final int   TRANSACTION_CUSTOMER_EXTRA = 7;

  public static final int   TRANSACTION_OPEN           = 100;
  public static final int   TRANSACTION_CLOSE          = 101;

  public static final int   TRANSACTION_OPEN_ADEMPIERE = 200;
  public static final int   TRANSACTION_CLOSE_ADEMPIERE= 201;

  private static final long serialVersionUID           = 1L;

  private long              oid;
  private int               type;
  private String            trxType;
  private Date              creationDate;
  private User              createdBy;
  private int               status;
  private String            description;
  private Provider          provider;
  private String            asynchronous;

  private String            organizationId;
  private String            roleId;
  private String            warehouseId;
  private TransactionError  trxError;
  private int               retries;

  
  // ***** Properties *****

  @Transient
  protected long            timeKeeper;

  // *** Oid ***
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "oid")
  public long getoid()
  {
    return oid;
  }

  public void setoid(long oid)
  {
    this.oid = oid;
  }

  /**
   * @return the type
   */
  @Column(name = "type", insertable = false, updatable = false)
  public int getType()
  {
    return type;
  }

  /**
   * @param type
   *          the type to set
   */
  public void setType(int type)
  {
    this.type = type;
  }

  // *** Type ***
  @Transient
  public String gettrxType()
  {
    if (this.getClass() == TransactionInvoice.class)
    {
      trxType = "Create Invoice";
    }
    else if (this.getClass() == TransactionPayment.class)
    {
      trxType = "Pay Invoice";
    }
    else if (this.getClass() == TransactionInvoiceReversal.class)
    {
      trxType = "Reverse Invoice";
    }
    else if (this.getClass() == TransactionQuotation.class)
    {
      trxType = "Create Quotation";
    }
    else if (this.getClass() == TransactionOrder.class)
    {
      trxType = "Create Order";
    }
    else if (this.getClass() == TransactionIncidence.class)
    {
      trxType = "Add Incidence";
    }
    else if (this.getClass() == TransactionOpen.class)
    {
      trxType = "Open Transaction Openbravo";
    }
    else if (this.getClass() == TransactionOpenAdempiere.class)
    {
      trxType = "Open Transaction Adempiere";
    }
    else
    {
      trxType = "Undefined";
    }

    return trxType;
  }

  // *** Creation Date ***
  @Column(name = "creation_date")
  public Date getcreationDate()
  {
    return creationDate;
  }

  public void setcreationDate(Date creationDate)
  {
    this.creationDate = creationDate;
  }

  // *** Created By ***
  @OneToOne(targetEntity = User.class)
  @JoinColumn(name = "created_by")
  public User getcreatedBy()
  {
    return createdBy;
  }

  public void setcreatedBy(User createdBy)
  {
    this.createdBy = createdBy;
  }

  // *** Status ***
  @Column(name = "status")
  public int getstatus()
  {
    return status;
  }

  public void setstatus(int status)
  {
    this.status = status;
  }

  // *** Description ***
  @Column(name = "description")
  public String getdescription()
  {
    return description;
  }

  public void setdescription(String description)
  {
    this.description = description;
  }

  // *** IProvider ***
  @OneToOne(targetEntity = Provider.class)
  @JoinColumn(name = "provider_id")
  public Provider getprovider()
  {
    return provider;
  }

  public void setprovider(Provider provider)
  {
    this.provider = provider;
  }

  /**
   * @return the strAsynchronous
   */
  @Column(name = "asynchronous")
  public String getasynchronous()
  {
    return asynchronous;
  }

  /**
   * @param strAsynchronous
   *          the strAsynchronous to set
   */
  public void setasynchronous(String strAsynchronous)
  {
    this.asynchronous = strAsynchronous;
  }

  /**
   * @return the isAsynchronous
   */

  /*
   * @Transient
   * 
   * public boolean isAsynchronous() {
   * 
   * if (asynchronous.contentEquals("true")) { return true; } else { return
   * false; }
   * 
   * }
   */

  // *** OrganizationId ***
  @Column(name = "organization_Id")
  public String getorganizationId()
  {
    return organizationId;
  }

  public void setorganizationId(String organizationId)
  {
    this.organizationId = organizationId;
  }

  // *** RoleId ***
  @Column(name = "role_Id")
  public String getroleId()
  {
    return roleId;
  }

  public void setroleId(String roleId)
  {
    this.roleId = roleId;
  }

  // *** Warehouse Id ***
  @Column(name = "warehouse_id")
  public String getwarehouseId()
  {
    return warehouseId;
  }

  public void setwarehouseId(String warehouseId)
  {
    this.warehouseId = warehouseId;
  }

  /**
   * @return the trxError
   */
  @OneToOne(targetEntity = TransactionError.class)
  @Cascade({ CascadeType.SAVE_UPDATE })
  @JoinColumn(name = "trx_error_oid")
  public TransactionError getTrxError()
  {
    return trxError;
  }

  /**
   * @param trxError
   *          the trxError to set
   */
  public void setTrxError(TransactionError trxError)
  {
    this.trxError = trxError;
  }

// *** Retries ***
  @Column(name = "retries")
  public int getRetries()
  {
    return retries;
  }

  /**
   * @param retries
   *          the retries to set
   */
  public void setRetries(int retries)
  {
    this.retries = retries;
  }

  // ***** Public Methods *****

  public void validate() throws SciiException
  {

    System.out.println("BEGIN - Transaction Validation");

    // ** Validate creation date **

    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    String strCreationDate = sdf.format(this.getcreationDate());
    Date lbDate = this.getcreatedBy().getLastBalanceDate();
    if (lbDate == null)
    {
      lbDate = new Date();
    }
    
    String strLastBalanceDate = sdf.format(lbDate);

    if (strCreationDate.compareTo(strLastBalanceDate) < 0)
    {
      throw new SciiException("Transaction Date Error '" + this.getcreationDate()
          + "' - Creation date must be greater than or equal to user's last balance date");
    }

  }

  public User Authenticate(User user)
  {

    // ***** Authentication code here *****

    return user;

  }

  public SciiResult beforProcess() throws SciiException
  {
    SciiResult sr = new SciiResult();
    sr.setreturnMessage("Transaction with oid " + this.getoid() + " has been validated.");
    sr.setreturnCode(Transaction.STATUS_VALIDATED);

    return sr;
  }

  public SciiResult process()
  {

    SciiResult result = null;

    Authenticate(getcreatedBy());

    return result;

  }

  public SciiResult afterProcess()
  {
    return null;
  }

}
