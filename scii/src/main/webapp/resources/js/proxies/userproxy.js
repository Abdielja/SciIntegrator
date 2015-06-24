/**
 * 
 * userproxy.js
 * 
 *   Helper classes for calling Integrator's User Webservices 
 *   
 */

//***** Server Url *****
var serverURL = "http://190.141.100.170:8080";

console.log("BEGIN -- Declare User related classes");

console.log("  Declare User Class");

// ***** User Class *****

function User() 
{
	this.oid = 0;
	this.name = "";
	this.region = "";
};

function Invoice()
{  
  this.oid = 0;
  this.status = 0;
  this.paymentMethod = new PaymentMethod();
  this.invoicedAmount = 0.00;
  this.differenceAmount = 0.00;
};

function Payment()
{
  this.oid = 0;
  this.amount = 0;
  this.paymentMethod = new PaymentMethod();
  this.invoiceStatus = 0;
}

function PaymentDetail()
{
  this.paymentMethodOid = 0;
  this.paymentMethodDescr = "Empty";
  this.amount = 0.00;
  this.amountGiven = 0.00; 
  this.differenceAmount = 0.00;
};

function UserTotals()
{
  
  this.totalInvoiced = 0.00;
  this.totalCollected = 0.00;
  this.totalCredit = 0.00;
  this.total = 0.00;
  this.totalMissing = 0.00;

  this.reset = function()
  {
    this.totalInvoiced = 0.00;
    this.totalCollected = 0.00;
    this.totalCredit = 0.00;
    this.total = 0.00;
    this.totalMissing = 0.00;
  };

};

function CashDetail()
{
  this.valor = 0;
  this.denominacion = 0;
  this.cantidad = 0;
};

//***** UserData Class *****

function UserData()
{
  
  this.userOid = 0;
  this.products = new Array();
  this.invoices = new Array();
  this.payments = new Array();
  this.paymentsDetails = new Array();
  this.cashDetails = new Array();
  this.totals = new UserTotals();
  
  this.computeDifferences = function()
  {

    this.totals.totalMissing = 0;
    
    // *** Compute inventory differences ***
    for(var i=0; i < this.products.length; i++)
    {
      var product = this.products[i];
      product.differenceQuantity = (product.endQuantity) + product.invoicedQuantity - product.initialQuantity;
      userData.totals.totalMissing += (product.differenceQuantity * product.price.toFixed(2));        
    }

    // *** Compute amount collected details diferences ***
    for(var i=0; i < this.paymentsDetails.length; i++)
    {
      var paymentDetail = this.paymentsDetails[i];
      paymentDetail.differenceAmount = (parseFloat(paymentDetail.amountGiven.toFixed(2)) - parseFloat(paymentDetail.amount).toFixed(2));
    }
    
    console.log(userData.totals);

  };
  
  this.getCashDetailsTotal = function()
  {
    // *** Compute total collected from cash details ***
    var totalCashDetail = 0;
    for(var i=0; i < this.cashDetails.length; i++)
    {
      var cashDetail = this.cashDetails[i];
      totalCashDetail += (parseFloat(cashDetail.cantidad).toFixed(2) * parseFloat(cashDetail.valor).toFixed(2));
    }
    
    return totalCashDetail;
  };
  
};


//***** UserList Class *****

console.log("  Declare UserList Class");

function UserList()
{
	this.list = new Array();
};

UserList.prototype.count = function()
{
	return this.list.length;
};

UserList.prototype.get = function(index)
{
	return this.list[index]; 
};

UserList.prototype.add = function(user)
{
	this.list[this.list.length] = user; 
};


// ***** AJAX Functions *****

console.log("  Declare getAllUsers function");
function getAllUsers(onSuccess)
{

  $.get(serverURL + "/scii/rest/users").done(onSuccess)
      .fail(function()
      {
        alert("UserProxy.getAllUsers() error");
      });

}

console.log("  Declare User's Data function");
function getUserData(onSuccess, userOid)
{

  $.get(serverURL + "/scii/rest/users/getUserData?userOid=" + userOid).done(onSuccess).fail(function()
  {
    alert("Error: UserProxy.getUserData().");
  });

}

console.log("  Send User's Data function");
function closeUserPeriod(_userData)
{

  if (!validateCloseRequest(_userData))
  {
    return false;
  }

  console.log(_userData);
  
  var xmlUserPeriodData = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";
  xmlUserPeriodData += "<UserPeriodData>";
  xmlUserPeriodData += "<oid>" + _userData.userOid + "</oid>";

  // *** Totals to XML ***
  xmlUserPeriodData += "<totalInvoiced>" + _userData.totals.totalInvoiced + "</totalInvoiced>";
  xmlUserPeriodData += "<totalCollected>" + _userData.totals.totalCollected + "</totalCollected>";
  xmlUserPeriodData += "<totalCredit>" + _userData.totals.totalCredit + "</totalCredit>";
  xmlUserPeriodData += "<totalMissing>" + _userData.totals.totalMissing + "</totalMissing>";
  
  // *** Products to XML ***
  xmlUserPeriodData += "<Products>";
  var numProducts = _userData.products.length;
  for(var i=0; i < numProducts; i++)
  {
    var product = _userData.products[i];
    xmlUserPeriodData += "<Product>";
    xmlUserPeriodData += "<oid>" + product.oid +"</oid>";
    xmlUserPeriodData += "<initialQuantity>" + product.initialQuantity +"</initialQuantity>";
    xmlUserPeriodData += "<returnedQuantity>" + product.endQuantity +"</returnedQuantity>";
    xmlUserPeriodData += "</Product>";
    
  }
  xmlUserPeriodData += "</Products>";
    
  // *** Payment Details to XML ***
  xmlUserPeriodData += "<PaymentDetails>";
  var numPaymentDetails = _userData.paymentsDetails.length;
  for(var i=0; i < numPaymentDetails; i++)
  {
    var paymentDetail = _userData.paymentsDetails[i];
    xmlUserPeriodData += "<PaymentDetail>";
    xmlUserPeriodData += "<paymentMethodOid>" + paymentDetail.paymentMethodOid +"</paymentMethodOid>";
    xmlUserPeriodData += "<amount>" + paymentDetail.amountGiven +"</amount>";
    xmlUserPeriodData += "</PaymentDetail>";   
  }
  xmlUserPeriodData += "</PaymentDetails>";
    
  // *** Cash Details to XML ***
  xmlUserPeriodData += "<CashDetails>";
  var numCashDetails = _userData.cashDetails.length;
  for(var i=0; i < numCashDetails; i++)
  {
    var cashDetail = _userData.cashDetails[i];
    xmlUserPeriodData += "<CashDetail>";
    xmlUserPeriodData += "<value>" + cashDetail.valor +"</value>";
    xmlUserPeriodData += "<denomination>" + cashDetail.denominacion +"</denomination>";
    xmlUserPeriodData += "<quantity>" + cashDetail.cantidad + "</quantity>";
    xmlUserPeriodData += "</CashDetail>";   
  }
  xmlUserPeriodData += "</CashDetails>";

  xmlUserPeriodData += "</UserPeriodData>";

  console.log(xmlUserPeriodData);   


  $.ajax(
  {
    url: serverURL + "/scii/rest/users/closeUserPeriod",
    data: xmlUserPeriodData, 
    type: 'POST',
    contentType: "text/xml",
    dataType: "text",
    success : function(xhr, result)
    {
      console.log(xhr.status);          
      console.log(result);   
      alert("El periodo fue cerrado con exito.");
      $("#pageViewer").jqxScrollView({ currentPage: 0 });
    },
    error : function (xhr, ajaxOptions, thrownError)
    {  
      console.log(xhr.status);          
      console.log(thrownError);
    } 
  }
  );  

}

// *** Helper functions ***
function validateCloseRequest(_userData)
{
  
  // ** Confirmar cierre de periodo **
  var r = confirm("Cerrar periodo de ventas\npara el usuario " + _userData.userOid);
  if(r == false)
  {
    return false;
  }
  
  // ** Validar detalle de efectivo **
  if (_userData.getCashDetailsTotal() != _userData.paymentsDetails[0].amountGiven)
  {
    alert("La suma del desglose de efectivo debe \n ser igual al total de efectivo entregado");
    return false;
  }
  
  return true;
}

function parseUsers(xml)
{

  var tUserList = X2JS.xml2json(xml);

  console.log(tUserList);   
   
  return tUserList.users;
   
};

console.log("END -- Declare User related classes");
