<!DOCTYPE HTML>
<html>

<head>

<title>Spirit Systems Integrator - User Balance</title>

<!-- ***** Link Style Sheets ***** -->

<link rel="stylesheet" href="resources/scii.css" />
<link rel="stylesheet" href="resources/js/jqwidgets/styles/jqx.base.css"
	type="text/css" />

<link rel="stylesheet"
	href="resources/js/jqwidgets/styles/jqx.shinyblack.css" type="text/css" />
<link rel="stylesheet"
	href="resources/js/jqwidgets/styles/jqx.ui-sunny.css" type="text/css" />

<!-- ***** import jqxWidgets ***** -->

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
<script type="text/javascript" src="resources/js/jqwidgets/jqxgrid.js"></script>
<script type="text/javascript"
	src="resources/js/jqwidgets/jqxgrid.selection.js"></script>
<script type="text/javascript"
	src="resources/js/jqwidgets/jqxgrid.edit.js"></script>
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
<script type="text/javascript" src="resources/js/jqwidgets/jqxwindow.js"></script>
<script type="text/javascript"
	src="resources/js/jqwidgets/jqxscrollview.js"></script>
<script type="text/javascript" src="resources/js/jqwidgets/jqxpanel.js"></script>
<script type="text/javascript" src="resources/js/jqwidgets/jqxnumberinput.js"></script>

<!-- ***** Import X2JS library ***** -->

<script type="text/javascript" src="resources/js/xml2json.js"></script>

<!-- ***** import proxies ***** -->

<script type="text/javascript" src="resources/js/proxies/userproxy.js"></script>
<script type="text/javascript" src="resources/js/proxies/productproxy.js"></script>

<!-- ***** import scii scripts ***** -->

<script type="text/javascript" src="resources/js/views/header.js"></script>
<script type="text/javascript" src="resources/js/views/userbalance.js"></script>

</head>

<body>

	<!-- ***** Custom scripts. (Abdiel) -->

	<script type="text/javascript">

    // ********************************************
    // ***** Document is ready. Start Scripts *****
    // ********************************************

    $(document).ready(function()
    {

      createMenu();

      console.log("Create Scroll View (Page container)");
      createScrollView();

      console.log("Create users list");
      createUsersList();

      console.log("Get all users from webservice");
      getAllUsers(onLoadUsersSuccess);

      console.log("Create Products Grid");
      createProductsGrid();
      createInvoicedGrid();

      createProductsResultGrid();
      createPaymentsDetailResultGrid();
      
      createTotalsPanel();
      createCashDetailGrid();
      createClosePeriodButton();
      
      createPreviousButton();
      createNextButton();

    });
  </script>

	<div id="wrapper">

		<header id="main_header">
			<figure id="main_logo">
				<img src="resources/images/logo_spirit.png">
			</figure>
			<hgroup>
				<h1>Spirit System Integrator</h1>
				<h2>Version 3.32a</h2>
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

		<!-- Sub Header -->
		<div class="sub_header">

			<!-- Page Title -->
			<div id="pageTitle" class="page_title">Cuadre de Vendedores</div>

			<!-- Button 'Previous' -->
			<input type="button" value="Anterior" id='btnPrevious'
				class="default_button" style="margin-bottom: 5px; margin-right: 5px" />

			<!-- Button 'Next' -->
			<input type="button" value="Siguiente" id='btnNext'
				class="default_button" style="margin-bottom: 5px" />

		</div>

		<!-- JScrollView -->
		<div id="pageViewer" style="background-color: White">

			<!-- Page 1 -->
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
					<div id="jqxQuantityWidget">
						<div id="quantitySection">
							<div class="section_title">Detalle de Inventario</div>
							<div id="jqxgrid"></div>
						</div>
					</div>
				</div>

				<div class="right_section">
					<div id="jqxInvoicedWidget">
						<div id="invoicedSection">
							<div class="section_title">Detalle de Cobros</div>
							<div id="jqxgridInvoiced"></div>
              <table class="section_total" width="230" border="0">
	              <tr>
		              <td>Total</td>
		              <td id="txtCollectedAmountGiven" align="right"></td>
	              </tr>
              </table>
						</div>
					</div>
				</div>

			</div>

			<!-- Page 2 -->

			<div class="main_section">

				<div class="right_section">
					<div id="jqxProductResultWidget">
						<div id="ProductResultSection">
							<div class="section_title">Balance de Inventario</div>
							<div id="jqxgridResultProducts"></div>
						</div>
					</div>
				</div>

        <div class="right_section">
          <div id="jqxPaymentsResultWidget">
            <div id="PaymentsResultSection">
              <div class="section_title">Balance de Cobros</div>
              <div id="jqxGridPaymentsDetailsResult"></div>
            </div>
          </div>
        </div>

			</div>

			<!-- Page 3 -->
			<div class="main_section">

				<div class="right_section">
					<div id="jqxTotalsPaneltWidget">
						<div id="TotalsSection">
							<div class="section_title">Totales Finales</div>
							<div id="jqxTotalsPanel">
								<table style="margin-top: 20px">

                  <!-- Total Invoiced -->
									<tr>
										<td>
										  <label class="default_label_bold" style="height:21px">Monto Facturado</label>
										</td>
										<td>
											<div id="txtInvoicedAmount">
											</div>
										</td>
									</tr>

                  <!-- Total Cobrado -->
                  <tr>
                    <td>
                      <label class="default_label_bold" style="height:21px">Monto Cobrado</label>
                    </td>
                    <td>
                      <div id="txtCollectedAmount">
                      </div>
                    </td>
                  </tr>

                  <!-- Total Credito -->
                  <tr>
                    <td>
                      <label class="default_label_bold" style="height:21px">Monto Credito</label>
                    </td>
                    <td>
                      <div id="txtCreditAmount">
                      </div>
                    </td>
                  </tr>

                  <!-- Difference -->
                  <tr>
                    <td>
                      <label class="default_label_bold" style="height:21px">Diferencia</label>
                    </td>
                    <td>
                      <div id="txtMissingAmount">
                      </div>
                    </td>
                  </tr>

								</table>
							</div>
						</div>
					</div>
				</div>

				<!-- Define cash detail grid -->

				<div class="right_section">

					<div id="jqxTotalsPaneltWidget">
						<div id="TotalsSection">
							<div class="section_title">Desglose de Efectivo</div>
							<div></div>
							<div id="jqxgridCashDetail"></div>
						</div>

            <table class="section_total" width="230" border="0">
              <tr>
                <td>Total</td>
                <td id="txtTotalCashDetail" align="right"></td>
              </tr>
            </table>

					</div>
				</div>

        <!-- Define Close Period Transaction Button -->

        <div class="right_section">
          <div id="jqxTotalsPaneltWidget">
            <div id="TotalsSection">
              <div class="section_title">Cerrar Periodo</div>
				      <!-- Button 'Previous' -->
				      <input type="button" value="Cerrar Periodo" id='btnClosePeriod' style="margin-left: 5px; padding-top: 30px; padding-bottom: 30px;  padding-left: 10px; padding-right: 10px;font-size: 18px;" />
            </div>

          </div>
        </div>

			</div>
		</div>

		<br>

		<footer class="main_footer">Copyright Spirit Consulting, Inc. 2010-2014</footer>

	</div>

</body>
</html>
