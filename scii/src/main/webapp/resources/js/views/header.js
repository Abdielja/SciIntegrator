// *** Initialization ***

console.log("Start - Template Initialization");

// Assign theme
var theme = "ui-sunny";

// *** View functions ***

function createMenu()
{
    // Create a jqxMenu
    $("#jqxMenu").jqxMenu({
      theme: theme,
      width : 960,
      height : 30
    });
}

