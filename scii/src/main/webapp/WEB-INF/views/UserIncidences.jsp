<!DOCTYPE HTML>
<html>

<head>

<title>Spirit Systems Integrator - User Incidences</title>

<link rel="stylesheet" href="resources/scii.css" />

<link rel="stylesheet" href="resources/js/jqwidgets/styles/jqx.base.css"
	type="text/css" />
<link rel="stylesheet"
	href="resources/js/jqwidgets/styles/jqx.shinyblack.css" type="text/css" />
  <link rel="stylesheet" href="resources/js/jqwidgets/styles/jqx.ui-sunny.css" type="text/css" />

<script type="text/javascript" src="resources/js/jquery-1.9.1.min.js"></script>

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
<script type="text/javascript"
	src="resources/js/jqwidgets/jqxbuttons.js"></script>
<script type="text/javascript"
	src="resources/js/jqwidgets/jqxnavigationbar.js"></script>
<script type="text/javascript" src="resources/js/jqwidgets/jqxwindow.js"></script>

<!-- ***** Import X2JS library ***** -->

<script type="text/javascript" src="resources/js/xml2json.js"></script>

<!-- ***** import proxies ***** -->

<script type="text/javascript" src="resources/js/proxies/userproxy.js"></script>
<script type="text/javascript" src="resources/js/proxies/incidenceproxy.js"></script>

<!-- ***** import scii scripts ***** -->

<script type="text/javascript" src="resources/js/views/header.js"></script>
<script type="text/javascript" src="resources/js/views/userincidences.js"></script>

</head>

<body>

   <script type="text/javascript">
   
	   // Create XmlToJson library instance 
	   var X2JS = new X2JS();

     $(document).ready(function() 
     {

       createMenu();
       createNavigationBar();
       createIncidencesGrid();

       console.log("Create users list");
       createUsersList();

       console.log("Get all users from webservice");
       getAllUsers(onLoadUsersSuccess);

    });
     
   </script>

	<div id="wrapper">

		<header id="main_header">
			<figure id="main_logo">
				<img src="resources/images/logo_spirit.png">
			</figure>
			<hgroup>
				<h1>Spirit System Integrator</h1>
				<h2>Version 3.3</h2>
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

		<div id="pageTitle" class="page_title">Incidencias de Vendedores</div>

		<div class="main_section">

        <div class="left_section">
          <div id="jqxUserListWidget">
            <div id="userListSection">
              <div class="section_title">Lista de Vendedores</div>
              <div id="jqxUserListBox"></div>
            </div>
          </div>
        </div>

			<div class="right_section">
				<div id="jqxIncidencesWidget">
					<div id="incidencesSection">
						<div class="section_title">Incidencias</div>
						<div id="jqxIncidencesGrid"></div>
					</div>
				</div>
			</div>

    </div>

		<footer class="main_footer">Copyright Spirit Consulting, Inc. 2010-2013</footer>

		<!-- Define aditional windows -->
		<!--  -->

  </div>
			
</body>
</html>
