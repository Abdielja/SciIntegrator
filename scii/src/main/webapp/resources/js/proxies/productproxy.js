/**
 * 
 */


// Objects

// ***** Use this object to avoid precision problems *****
var Money = function(amount) 
{
  this.amount = amount;
};

Money.prototype.valueOf = function() 
{
    return Math.round(this.amount*100)/100;
};
// *******************************************************

function Product() 
{
  this.oid = 0;
  this.name = "";
  this.price = 0.0;
  this.initialQuantity = 0;
  this.endQuantity = 0;
  this.invoicedQuantity = 0;
  this.differenceQuantity = 0;  
  this.invoicedAmount = 0;
  this.itbms = 0.0;
  this.amount = 0.0;
  this.totalAmount = 0.0;
};

function PaymentMethod()
{

  // Constants

  this.PAYMENT_METHOD_EFECTIVO = 1;
  this.PAYMENT_METHOD_CHEQUE = 2;
  this.PAYMENT_METHOD_BOLANTE = 3;
  this.PAYMENT_METHOD_CREDITO = 4;
  
  this.oid = 0;
  this.description = "Empty";
};

function unmarshallUserData(xmlUserData)
{

  console.log(xmlUserData);

  var tUserData = X2JS.xml2json(xmlUserData); 
  
  //console.log("testUserData: \n" + tUserData.userData.invoices.invoice_asArray[0].oid);
  
  var userData = new UserData();
  
  userData.oid = tUserData.userData.oid;
  userData.userOid = tUserData.userData.userOid;
   
   var products = $(xmlUserData).find("products");
   
   products.each(function()
   {

     var i = 0;
     $(this).find("product").each(function()
     {
       var product = new Product();
       product.oid = $(this).find("oid").text();
       product.name = $(this).find("name").text();
       product.price = parseFloat($(this).find("standardPrice").text());
       product.initialQuantity = parseInt($(this).find("quantityOnHand").text());
       product.invoicedQuantity = parseInt($(this).find("quantityInvoiced").text());
       product.invoicedAmount = parseFloat($(this).find("amountInvoiced").text());
       userData.products[i++] = product;       
     });

   });

   userData.totals.reset();
   
   var invoices = tUserData.userData.invoices.invoice_asArray;

   for(var i = 0; i < tUserData.userData.invoices.count; i++)
   {
     
     var invoice = new Invoice();
     invoice.oid = invoices[i].oid;
     invoice.invoicedAmount = new Money(parseFloat(invoices[i].total));
     invoice.paymentMethod.oid = invoices[i].paymentMethod.oid;
     invoice.paymentMethod.description = invoices[i].paymentMethod.description;
     invoice.status = invoices[i].status;
     
     userData.invoices[i] = invoice;
     
     if (invoice.status != 6)
     {
       userData.totals.totalInvoiced += invoice.invoicedAmount;       
       console.log("Invoice: "  + invoice.oid + ".Amount: " + invoice.invoicedAmount);
     }
     else
     {
       console.log("Reversed invoice: " + invoice.oid + ".Amount: " + invoice.invoicedAmount);       
     }
     
   }
     
   pdCash = new PaymentDetail();
   pdCash.paymentMethodOid = 1;
   pdCash.paymentMethodDescr = "Efectivo";
   pdCash.amount = 0.00;      
   userData.paymentsDetails[0] = pdCash;
   
   pdCheques = new PaymentDetail();
   pdCheques.paymentMethodOid = 2;
   pdCheques.paymentMethodDescr = "Cheques";
   pdCheques.amount = 0.00;      
   userData.paymentsDetails[1] = pdCheques;

   pdBolantes = new PaymentDetail();
   pdBolantes.paymentMethodOid = 3;
   pdBolantes.paymentMethodDescr = "Bolantes";
   pdBolantes.amount = 0.00;      
   userData.paymentsDetails[2] = pdBolantes;

   pdCredit = new PaymentDetail();
   pdCredit.paymentMethodOid = 4;
   pdCredit.paymentMethodDescr = "Credito";
   pdCredit.amount = 0.00;      
   userData.paymentsDetails[3] = pdCredit;

   var payments = tUserData.userData.payments.payment_asArray;

   for(var i = 0; i < tUserData.userData.payments.count; i++)
   {

     var payment = new Payment();
     payment.oid = payments[i].oid;
     payment.amount = parseFloat(payments[i].amount);
     payment.invoiceStatus = payments[i].invoice.status;
     payment.paymentMethod.oid = payments[i].paymentMethod.oid;
     payment.paymentMethod.description = payments[i].paymentMethod.description;

     userData.payments[i] = payment;

     // ** If payment's invoice has not been reversed **
     if (payment.invoiceStatus != 6)
     {

       var paymentMethodOid = payment.paymentMethod.oid;
       
       if (paymentMethodOid == 1)   // Efectivo
       {
         pdCash.amount += payment.amount;
       }
  
       if (paymentMethodOid == 2)   // Cheque
       {
         pdCheques.amount += payment.amount;
       }
       
       if (paymentMethodOid == 3)   // Bolantes
       {
         pdBolantes.amount += payment.amount;
       }
       
       if (paymentMethodOid == 4)   // Credito
       {
         pdCredit.amount += payment.amount;
         userData.totals.totalCredit += payment.amount;
       }

       userData.totals.totalCollected += payment.amount;       
     
     }
     else
     {
       userData.totals.totalCollected += 0;
     }
     
     userData.totals.total = userData.totals.totalInvoiced - userData.totals.totalCollected - userData.totals.totalCredit;

   }
   
   // *** Init Cash Details Rows ***
   
   row20 = new CashDetail();
   row20["valor"] = "100";
   row20["denominacion"] = "Billetes de 100";
   row20["monto"] = "0";
   userData.cashDetails[0] = row20;

   row21 = new CashDetail();
   row21["valor"] = "50";
   row21["denominacion"] = "Billetes de 50";
   row21["monto"] = "0";
   userData.cashDetails[1] = row21;
   
   row22 = new CashDetail();
   row22["valor"] = "20";
   row22["denominacion"] = "Billetes de 20";
   row22["monto"] = "0";
   userData.cashDetails[2] = row22;
   
   row23 = new CashDetail();
   row23["valor"] = "10";
   row23["denominacion"] = "Billetes de 10";
   row23["monto"] = "0";
   userData.cashDetails[3] = row23;

   row24 = new CashDetail();
   row24["valor"] = "5";
   row24["denominacion"] = "Billetes de 5";
   row24["monto"] = "0";
   userData.cashDetails[4] = row24;

   row25 = new CashDetail();
   row25["valor"] = "1";
   row25["denominacion"] = "Billetes de 1";
   row25["monto"] = "0";
   userData.cashDetails[5] = row25;
   
   row26 = new CashDetail();
   row26["valor"] = "1";
   row26["denominacion"] = "Monedas 1";
   row26["monto"] = "0";
   userData.cashDetails[6] = row26;

   row27 = new CashDetail();
   row27["valor"] = "0.50";
   row27["denominacion"] = "Monedas .50";
   row27["monto"] = "0";
   userData.cashDetails[7] = row27;

   row28 = new CashDetail();
   row28["valor"] = "0.25";
   row28["denominacion"] = "Monedas .25";
   row28["monto"] = "0";
   userData.cashDetails[8] = row28;

   row29 = new CashDetail();
   row29["valor"] = "0.10";
   row29["denominacion"] = "Monedas .10";
   row29["monto"] = "0";
   userData.cashDetails[9] = row29;

   row30 = new CashDetail();
   row30["valor"] = "0.05";
   row30["denominacion"] = "Monedas .05";
   row30["monto"] = "0";
   userData.cashDetails[10] = row30;

   row31 = new CashDetail();
   row31["valor"] = "0.01";
   row31["denominacion"] = "Monedas .01";
   row31["monto"] = "0";
   userData.cashDetails[11] = row31;

   return userData;
   
};

function marshallUserData(userData)
{
  var xml = "";
  
  return xml;
};