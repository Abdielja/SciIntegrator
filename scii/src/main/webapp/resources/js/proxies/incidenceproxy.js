/**
 * 
 * incidenceproxy.js
 * 
 *   Helper classes for calling Integrator's Incidences Webservices 
 *   
 */

console.log("BEGIN -- Declare Incidence related classes");

console.log("  Declare User Class");

// ***** User Class *****


//***** AJAX Functions *****

console.log("  Declare getIncidencesByUserToday function");
function getIncidencesByUserToday(onSuccess, userOid)
{

  $.get("http://190.141.100.140:8080/scii/rest/incidences/getByUserTodayDto?userOid=" + userOid).done(onSuccess)
      .fail(function()
      {
        alert("Userincidence.getByUserTodayDto() error");
      });

}

//*** Helper functions ***

function parseIncidences(xml)
{

  var tIncidenceList = X2JS.xml2json(xml);

  return tIncidenceList.incidenceDtoList.incidenceDto;
   
};

console.log("END -- Declare Incidences related classes");

