<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>

<!DOCTYPE HTML>
<html>

<head>

<title>Spirit Systems Integrator - User Monitor</title>

<link rel="stylesheet" href="resources/scii.css" />

<link rel="stylesheet" href="resources/js/jqwidgets/styles/jqx.base.css"
	type="text/css" />
<link rel="stylesheet" href="resources/js/jqwidgets/styles/jqx.shinyblack.css" type="text/css" />
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

<script type="text/javascript" src="resources/js/views/header.js"></script>

</head>

<body>

	<div id="wrapper">

		<script type="text/javascript">
			$(document).ready(

			function() {

				createMenu();

				$(".user_box").click(function() {
					$('.right_section').show();
				});

				$(".user_box").mouseover(function() {
					//$('.right_section').show();
				});

				$(".user_box").mouseleave(function() {
					$('.right_section').hide();
				});

				// Create jqxQuery 
				var data = new Array();

				var row = {};
				row["product"] = "Digicel 2";
				row["price"] = "1.84";
				row["initialQuantity"] = "160";
				row["endQuantity"] = "95";
				row["invoicedQuantity"] = "65";
				row["itbms"] = "8.37";
				row["amount"] = "119.60";
				row["totalAmount"] = "127.97";
				data[0] = row;

				var source = {
					localdata : data,
					datatype : "array"
				};

				var dataAdapter = new $.jqx.dataAdapter(source, {
					loadComplete : function(data) {
					},
					loadError : function(xhr, status, error) {
					}
				});

				$("#jqxgrid").jqxGrid({
					theme : theme,
					width : 690,
					source : dataAdapter,
					columns : [ {
						text : 'Producto',
						datafield : 'product',
						width : 100
					}, {
						text : 'Price',
						datafield : 'price',
						width : 100
					}, {
						text : 'Cant Desp',
						datafield : 'initialQuantity',
						width : 80,
						cellsalign : 'right'
					}, {
						text : 'Cant Dev ',
						datafield : 'endQuantity',
						width : 80,
						cellsalign : 'right'
					}, {
						text : 'Cant Fact',
						datafield : 'invoicedQuantity',
						width : 80,
						cellsalign : 'right',
					}, {
						text : 'ITBMS',
						datafield : 'itmbs',
						width : 80,
						cellsalign : 'right',
						cellsformat : 'c2'
					}, {
						text : 'Sub Total',
						datafield : 'amount',
						width : 80,
						cellsalign : 'right',
						cellsformat : 'c2'
					}, {
						text : 'Total',
						datafield : 'totalAmount',
						width : 80,
						cellsalign : 'right',
						cellsformat : 'c2'
					} ]
				});

			});
		</script>

		<header id="main_header">
			<figure id="main_logo">
				<img src="resources/images/logo_spirit.png">
			</figure>
			<hgroup>
				<h1>Spirit System Integrator</h1>
				<h2>Version 3.32</h2>
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

		<div id="pageTitle" class="page_title">Monitor de Vendedores</div>

		<div class="main_section">
		
			<div class="left_section">

			</div>

			<div id="jqxWidget" class="right_section">
				<div id="jqxgrid"></div>
			</div>

		</div>

		<footer class="main_footer">Copyright Spirit Consulting, Inc. 2010-2013 </footer>

	</div>

</body>
</html>
