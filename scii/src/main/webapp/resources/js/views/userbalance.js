/**
 * 
 */


// *** Initialization ***

  // Instantiate XmlToJson library instance 
    var X2JS = new X2JS();

    var userData = new UserData();

    var dataSourceProducts;
    var dataSourceInvoiced;
    var dataSourceCashDetails;
    
    console.log("Start - UserBalance Initialization");

    // *******************************************
    // ************* Create Widgets **************
    // *******************************************

    // ********** Create 'Previous' Button **********    

    function createPreviousButton()
    {
      // Create Back button
      $("#btnPrevious").jqxButton(
      {
        theme : theme
      });
      $("#btnPrevious").on('click', function()
      {
        console.log("Previous button clicked");
        $("#pageViewer").jqxScrollView('back');
      });
    }

    // ********** Create 'Next' Button **********    

    function createNextButton()
    {
      // Create Next button
      $("#btnNext").jqxButton(
      {
        theme : theme
      });
      
      $("#btnNext").on('click', function()
      {

        console.log("Next button clicked");
 
        var currentPage = $("#pageViewer").jqxScrollView("currentPage");
        
        if (currentPage == 0)
        {
        
         userData.computeDifferences();
         
         $("#jqxgridResultProducts").jqxGrid("updatebounddata");
         $("#jqxGridPaymentsDetailsResult").jqxGrid("updatebounddata");

        }
        
        if (currentPage == 1)
        {
          $("#txtInvoicedAmount").jqxNumberInput('setDecimal',userData.totals.totalInvoiced);        
          $("#txtCollectedAmount").jqxNumberInput('setDecimal',userData.totals.totalCollected);        
          $("#txtCreditAmount").jqxNumberInput('setDecimal',userData.totals.totalCredit);        
          $("#txtMissingAmount").jqxNumberInput('setDecimal',userData.totals.totalMissing);        
        }
        
        $("#pageViewer").jqxScrollView('forward');
        
      });
    }

    // ------------------- Page 1 Widgets ------------------

    // ********** Create User ListBox **********    
    function createUsersList()
    {

      source = [];

      $("#jqxUserListBox").jqxListBox(
      {
        theme : theme,
        source : source,
        width : 230,
        height : 350,
      });

      console.log("Users List Created");

      console.log("Create User List onClick Handler");
      $('#jqxUserListBox').on('select', function(event)
      {
        var args = event.args;
console.log(args.index);
        var item = $('#jqxUserListBox').jqxListBox('getItem', args.index);
        if (item != null)
        {
          getUserData(onLoadUserDataSuccess, item.value);
        }
      });

    }

    // ********** Create Product Grid **********    
    function createProductsGrid()
    {

      dataSourceProducts =
      {
        localdata : userData.products,
        datatype : "array"
      };

      var daProducts = new $.jqx.dataAdapter(dataSourceProducts,
      {
        crossDomain : true,
      });

      $("#jqxgrid").jqxGrid(
      {
        editable : true,
        selectionmode : 'singlecell',
        theme : theme,
        width : 250,
        height : 350,
        source : daProducts,
        columns : [
        {
          text : 'Producto',
          datafield : 'name',
          width : 150
        },
        {
          text : 'Cantidad',
          datafield : 'endQuantity',
          width : 80,
          cellsalign : 'right'
        } ]
      });

      console.log("Products Grid Created");

    }

    // ********** Create Invoiced Grid **********    

    function createInvoicedGrid()
    {

      dataSourceInvoiced =
      {
        localdata : userData.paymentsDetails,
        datatype : "array"
      };

      var dataAdapterInvoiced = new $.jqx.dataAdapter(dataSourceInvoiced,
      {
        crossDomain : true,
      });

      $("#jqxgridInvoiced").jqxGrid(
      {
        theme : theme,
        editable : true,
        selectionmode : 'singlecell',
        width : 230,
        height : 180,
        source : dataAdapterInvoiced,
        columns : [
        {
          text : 'Metodo de Pago',
          datafield : 'paymentMethodDescr',
          width : 150
        },
        {
          text : 'Monto',
          datafield : 'amountGiven',
          width : 80,
//          columntype : 'number',
//          cellsformat: 'c2',
          cellsalign : 'right'
        } ]
      });

      $("#txtCollectedAmountGiven").jqxNumberInput(
      {
        readOnly : true,
        height : 21,
        width : 110,
        inputMode : 'advanced',
        spinButtons : false,
        decimal : 0
      });

      $("#jqxgridInvoiced").on('cellvaluechanged', function()
      {
        // *** Compute total collected given amount ***
        var totalCollectedGiven = 0;
        for(var i=0; i < userData.paymentsDetails.length; i++)
        {
          var paymentDetail = userData.paymentsDetails[i];
          totalCollectedGiven += (parseFloat(paymentDetail.amountGiven));
        }

        $("#txtCollectedAmountGiven").jqxNumberInput(
        {
          decimal : totalCollectedGiven
        });  
      });
          
    }


    // ------------------- Page 2 Widgets ------------------

    // ********** Create Products Result Grid **********    

    function createProductsResultGrid()
    {

      var daProducts = new $.jqx.dataAdapter(dataSourceProducts,
      {
        crossDomain : true,
      });

      $("#jqxgridResultProducts").jqxGrid(
      {
        editable : false,
        theme : theme,
        width : 585,
        height : 350,
        source : daProducts,
        columns : [
        {
          text : 'Producto',
          datafield : 'name',
          width : 140
        },
        {
          text : 'Inicial',
          datafield : 'initialQuantity',
          columntype : 'number',
          width : 70
        },
        {
          text : 'Entregado',
          datafield : 'endQuantity',
          columntype : 'number',
          width : 70,
          cellsalign : 'right'
        },
        {
          text : 'Facturado',
          datafield : 'invoicedQuantity',
          columntype : 'number',
          width : 70,
          cellsalign : 'right'
        },
        {
          text : 'Diferencia',
          datafield : 'differenceQuantity',
          columntype : 'number',
          width : 70,
          cellsalign : 'right'
        },
        {
          text : 'Monto',
          datafield : 'invoicedAmount',
          columntype : 'number',
          cellsformat: 'c2',          
          width : 70,
          cellsalign : 'right'
        },
        {
          text : 'Monto Total',
          datafield : 'totalAmount',
          columntype : 'number',
          cellsformat: 'c2',
          width : 75,
          cellsalign : 'right'
        } ]
      });

      console.log("Products Result Grid Created");

    }

    // ********** Create Payments Details Result Grid **********    

    function createPaymentsDetailResultGrid()
    {
      
      var dataAdapterInvoiced = new $.jqx.dataAdapter(dataSourceInvoiced,
      {
        crossDomain : true,
      });

      $("#jqxGridPaymentsDetailsResult").jqxGrid(
      {
        theme : theme,
        width : 295,
        height : 150,
        source : dataAdapterInvoiced,
        columns : [
        {
          text : 'Metodo Pago',
          datafield : 'paymentMethod',
          width : 85
        },
        {
          text : 'Entregado',
          datafield : 'amountGiven',
          width : 70,
          columntype : 'number',
          cellsformat: 'c2',
          cellsalign : 'right'
        },        
        {
          text : 'Cobrado',
          datafield : 'amount',
          columntype : 'number',
          cellsformat: 'c2',
          width : 70,
          cellsalign : 'right'
        },
        {
          text : 'Diferencia',
          datafield : 'differenceAmount',
          columntype : 'number',
          cellsformat: 'c2',
          width : 70,
          cellsalign : 'right'
        }]
      });

    }
    
    // ------------------- Page 3 Widgets ------------------

    function createTotalsPanel()
    {
      $("#jqxTotalsPanel").jqxPanel(
      {
        theme : theme,
        width : 250,
        height : 250
      });

      $("#txtInvoicedAmount").jqxNumberInput(
      {
        readOnly : true,
        height : 21,
        width : 110,
        inputMode : 'advanced',
        spinButtons : false,
        decimal : userData.totals.totalInvoiced
      });

      $("#txtCollectedAmount").jqxNumberInput(
      {
        readOnly : true,
        height : 21,
        width : 110,
        inputMode : 'advanced',
        spinButtons : false,
        decimal : userData.totals.totalCollected
      });
      
      $("#txtCreditAmount").jqxNumberInput(
      {
        readOnly : true,
        height : 21,
        width : 110,
        inputMode : 'advanced',
        spinButtons : false,
        decimal : userData.totals.totalCredit
      });
          
      $("#txtMissingAmount").jqxNumberInput(
      {
        readOnly : true,
        height : 21,
        width : 110,
        inputMode : 'advanced',
        spinButtons : false,
        decimal : userData.totals.totalMissing
      });
                  
    }

    
    function createCashDetailGrid()
    {
      
      dataSourceCashDetails =
      {
        localdata : userData.cashDetails,
        datatype : "array"
      };

      var daCashDetail = new $.jqx.dataAdapter(dataSourceCashDetails,
      {
        loadComplete : function(data) 
        {
        },
        loadError : function(xhr, status, error) 
        {
        }
      });

      $("#jqxgridCashDetail").jqxGrid(
      {
        theme :theme,
        source : daCashDetail,
        width : 230,
        height : 350,
        editable : true,
        selectionmode : 'singlecell',
        columns : 
        [ 
          {
            text : 'Denominacion',
            datafield : 'denominacion',
            width : 150
          }, 
          {
            text : 'Cantidad',
            datafield : 'cantidad',
            width : 80,
            cellsalign : 'right'
          }
        ]                
      });

      $("#txtTotalCashDetail").jqxNumberInput(
      {
        readOnly : true,
        height : 21,
        width : 110,
        inputMode : 'advanced',
        spinButtons : false,
        decimal : 0
      });

      $("#jqxgridCashDetail").on('cellvaluechanged', function()
      {

        $("#txtTotalCashDetail").jqxNumberInput(
        {
          decimal : userData.getCashDetailsTotal()
        });  
      });
              
      
    }

    // ********** Create JScrollView (Page Container) **********    

    function createScrollView()
    {
      $("#pageViewer").jqxScrollView(
      {
        theme : theme,
        showButtons : false,
        width : 960,
        height : 450
      });

    }

    // ********** Create 'Previous' Button **********    

    function createClosePeriodButton()
    {
      // Create Back button
      $("#btnClosePeriod").jqxButton(
      {
        theme : theme
      });
      $("#btnClosePeriod").on('click', function()
      {
        console.log("Close Period button clicked");
        closeUserPeriod(userData);
      });
    }

    // *******************************************
    // ************* Helper Methods **************
    // *******************************************

    function updateUsersDataSource(users)
    {

      $("#jqxUserListBox").jqxListBox('clear');

      for ( var i = 0; i < users.count; i++)
      {

        var item =
        {
          label : users.user[i].name,
          value : users.user[i].oid
        };

        console.log(item);
        $("#jqxUserListBox").jqxListBox('addItem', item);
      }
      ;

      //$("#jqxNavigationBar").jqxListBox("refresh");

    }

    function updateProductsDataSource(products)
    {
      dataSourceProducts.localdata = products;
      $("#jqxgrid").jqxGrid("updatebounddata");
      $("#jqxgridResultProducts").jqxGrid("updatebounddata");
    }

    function updateInvoicedDataSource(paymentsDetails)
    {
      dataSourceInvoiced.localdata = paymentsDetails;
      $("#jqxgridInvoiced").jqxGrid("updatebounddata");
      $("#jqxGridPaymentsDetailsResult").jqxGrid("updatebounddata");
    }
    
    function updateCashDetailDataSource(cashDetails)
    {
      dataSourceCashDetails.localdata = cashDetails;
      $("#jqxgridCashDetail").jqxGrid("updatebounddata");
    }

    // *******************************************
    // ************* AJAX functions **************
    // *******************************************

    //*** AJAX callback functions ***

    //*** User list loaded *** 
    function onLoadUsersSuccess(xml)
    {

      // *** Parse XML response to User objects list ***
      var users = parseUsers(xml);
      console.log(users);
      updateUsersDataSource(users);
      console.log("All users loaded from webservice");
    }

    //*** User data loaded *** 
    function onLoadUserDataSuccess(xmlUserData)
    {

      if(xmlUserData == '')
      {
        alert("Error: UserProxy.unmarshallData().\nNo se encontro datos para este usuario en el servidor.");
        return;
      }

      userData = unmarshallUserData(xmlUserData);
      updateProductsDataSource(userData.products);
      updateInvoicedDataSource(userData.paymentsDetails);
      updateCashDetailDataSource(userData.cashDetails);
      console.log("UserData loaded from webservice");
      console.log(userData);
    }

console.log("END - UserBalance Initialization");
