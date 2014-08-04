<!DOCTYPE HTML>
<html>

<head>

	<title>Spirit Systems Integrator - Home</title>
	
	<link rel="stylesheet" href="resources/scii.css" />
	<link rel="stylesheet" href="resources/js/jqwidgets/styles/jqx.base.css" type="text/css" />
	<link rel="stylesheet" href="resources/js/jqwidgets/styles/jqx.shinyblack.css" type="text/css" />
  <link rel="stylesheet" href="resources/js/jqwidgets/styles/jqx.ui-sunny.css" type="text/css" />
	
	<script type="text/javascript" src="resources/js/jquery-1.10.2.min.js"></script>
	
	<script type="text/javascript" src="resources/js/jqwidgets/jqxcore.js"></script>
	<script type="text/javascript" src="resources/js/jqwidgets/jqxdata.js"></script>
	<script type="text/javascript"
	  src="resources/js/jqwidgets/jqxbuttons.js"></script>
	<script type="text/javascript"
	  src="resources/js/jqwidgets/jqxscrollbar.js"></script>
	<script type="text/javascript" src="resources/js/jqwidgets/jqxmenu.js"></script>
	<script type="text/javascript"
	  src="resources/js/jqwidgets/jqxlistbox.js"></script>
	<script type="text/javascript"
	  src="resources/js/jqwidgets/jqxdropdownlist.js"></script>
	<script type="text/javascript" src="resources/js/jqwidgets/jqxgrid.js"></script>
	<script type="text/javascript"
	  src="resources/js/jqwidgets/jqxgrid.selection.js"></script>
	<script type="text/javascript"
	  src="resources/js/jqwidgets/jqxgrid.columnsresize.js"></script>
	<script type="text/javascript"
	  src="resources/js/jqwidgets/jqxgrid.filter.js"></script>
	<script type="text/javascript"
	  src="resources/js/jqwidgets/jqxgrid.sort.js"></script>
	<script type="text/javascript"
	  src="resources/js/jqwidgets/jqxgrid.pager.js"></script>
	<script type="text/javascript"
	  src="resources/js/jqwidgets/jqxgrid.grouping.js"></script>
	<script type="text/javascript" src="resources/js/jqwidgets/jqxbuttons.js"></script>
	<script type="text/javascript" src="resources/js/jqwidgets/jqxnavigationbar.js"></script>
	<script type="text/javascript" src="resources/js/jqwidgets/jqxwindow.js"></script>

  <script type="text/javascript" src="resources/js/views/header.js"></script>

</head>

<body>

  <script type="text/javascript">

    console.log("Start - UserBalance Initialization");

	  $(document).ready(function() 
	  {
	
	    createMenu();
	
	  });
	 
  </script>

	<div id="wrapper">

	  <header id="main_header">
	    <figure id="main_logo">
	      <img src="resources/images/logo_spirit.png">
	    </figure>
	    <hgroup>
	      <h1>Spirit System Integrator</h1>
	      <h2>Version 3.0</h2>
        <h3>(DND)</h3>
	    </hgroup>
	  </header>

    <div id="jqxWidget">

      <div id='jqxMenu'>
        <ul>

          <li><a href="home">Inicio</a></li>

          <li>Vendedores
            <ul>
              <li><a href="userMonitor">Monitor</a></li>
              <li><a href="userBalance">Proceso de Cuadre</a></li>
              <li><a href="userIncidences">Incidencias</a></li>
            </ul>
          </li>

          <li>Transacciones
            <ul>
              <li><a href="transactions?userOid=0">Busqueda Transacciones</a></li>
              <li><a href="transactions_failed">Transacciones con
                  errores</a></li>
            </ul>
          </li>

        </ul>
      </div>
    </div>

    <br />

    <div id="pageTitle" class="page_title">
      Inicio
    </div>
    
    <div class="main_section">

      <div class="left_section">
      </div>

      <div class="right_section">
      </div>
      
    </div>

		<footer id="main_footer">Copyright Spirit Consulting, Inc. 2010-2013 </footer>

	</div>

</body>
</html>
