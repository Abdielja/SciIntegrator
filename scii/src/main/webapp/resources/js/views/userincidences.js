// *******************************************
// ************ Global Variables *************
// *******************************************

var dataSourceIncidences;

// *******************************************
// ******** Widget Creation functions ********
// *******************************************

function createNavigationBar()
{
    // Create jqxNavigationBar
    $("#jqxNavigationBar").jqxNavigationBar({ width: 200, height: 350, theme: theme});    	
}

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
    var item = $('#jqxUserListBox').jqxListBox('getItem', args.index);
    console.log(item);
    if (item != null)
    {
      getIncidencesByUserToday(onLoadIncidencesSuccess, item.value);
    }
  });

  
}

function createIncidencesGrid()
{

  // Create jqxQuery 
  var data = new Array();

  dataSourceIncidences = 
  {
    localdata : data,
    datatype : "array"
  };

    var dataAdapter = new $.jqx.dataAdapter(dataSourceIncidences,
    {
      loadComplete : function(data) 
      {
      },
      loadError : function(xhr, status, error) 
      {
      }
    });

    $("#jqxIncidencesGrid").jqxGrid({
      theme: theme,
      width : 650,
      height : 300,
      source : dataAdapter,
      columns : [ 
      {
        text : 'Fecha',
        datafield : 'creationDate',
        columntype : 'datetimeinput',
        cellsformat : 'f',
        width : 100
      },
      {
        text : 'Cliente',
        datafield : 'customerName',
        width : 200
      },
      {
        text : 'Incidencia',
        datafield : 'incidenceType',
        width : 350,
      }]
    });
  
}

//*******************************************
//***** Widgets Event Handlers functions ****
//*******************************************


// *******************************************
// ************* AJAX functions **************
// *******************************************

//*** AJAX callback functions ***

//*** User list loaded *** 
function onLoadUsersSuccess(xml)
{

  // *** Parse XML response to User objects list ***
  var users = parseUsers(xml);
  updateUsersDataSource(users);
  console.log("All users loaded from webservice");
}

//*** Incidence list loaded *** 
function onLoadIncidencesSuccess(xml)
{

  // *** Parse XML response to User objects list ***
  var incidenceList = parseIncidences(xml);
  updateIncidencesDataSource(incidenceList);
  console.log("All user's incidences loaded from webservice");
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

}

function updateIncidencesDataSource(incidenceList)
{

  dataSourceIncidences.localdata = incidenceList;
  $("#jqxIncidencesGrid").jqxGrid("updatebounddata");
/*
  for ( var i = 0; i < users.count; i++)
  {
    
    var item =
    {
      label : incidenceList.user[i].name,
      value : incidenceList.user[i].oid
    };

    console.log(item);
    $("#jqxIncidencesGrid").jqxListBox('addItem', item);
  };
*/
  
  console.log(incidenceList);
}

