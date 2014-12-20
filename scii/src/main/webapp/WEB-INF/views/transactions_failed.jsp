<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="false"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Transacciones con Errores</title>

<link rel="stylesheet" href="resources/scii.css" />

<link rel="stylesheet" href="resources/js/jqwidgets/styles/jqx.base.css"
	type="text/css" />
<link rel="stylesheet"
	href="resources/js/jqwidgets/styles/jqx.shinyblack.css" type="text/css" />
<link rel="stylesheet"
	href="resources/js/jqwidgets/styles/jqx.ui-sunny.css" type="text/css" />

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

<script type="text/javascript" src="resources/js/views/header.js"></script>

</head>
<body>

	<div id="wrapper">

		<script type="text/javascript">
      function onUsersLoaded(userList)
      {
        console.log(userList);
      }

      $(document).ready(function()
      {
        createMenu();
      });
    </script>

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
							<li><a href="transactions?userOid=0">Busqueda
									Transacciones</a></li>
							<li><a href="transactions_failed">Transacciones con
									errores</a></li>
						</ul>
					</li>

				</ul>
			</div>
		</div>

		<br />

		<div id="pageTitle" class="page_title">Transacciones con Errores
		</div>

		<div class="main_section">

			<br />

			<table align="center" style="border-color: DarkGray; border-width: 1px; border-style: solid">

				<tr valign="top"
					style="background: silver; font-size: 10pt; font-weight: bold">
					<td></td>
					<td>Provedor</td>
					<td>Tipo</td>
					<td>Oid</td>
					<td>Fecha</td>
					<td>Usuario</td>
					<td>Status</td>
          <td>Retries</td>
				</tr>

				<c:forEach items="${transactions}" var="transaction">

					<tr valign="top" style="font-size: 10pt">
						<td>v</td>
						<td><c:out value="${transaction.provider.name}" /></td>
						<td><c:out value="${transaction.trxType}" /></td>
						<td><c:out value="${transaction.oid}" /></td>
						<td><c:out value="${transaction.creationDate}" /></td>
						<td><c:out value="${transaction.createdBy.name}" /></td>
						<td><c:out value="${transaction.status}" /></td>
            <td><c:out value="${transaction.retries}" /></td>
					</tr>

					<c:if test="${transaction.trxType == 'Create Invoice'}">
						<tr>
							<td colspan="7">
								<table
									style="font-size: 10pt; margin-left: 20px; border-style: solid; border-color: DarkGray; border-width: 1px">
									<tr>
										<td colspan="4" style="color: Gray; font-weight: bold">Invoice</td>
									</tr>
									<tr>
										<td style="color: Gray">ServerId</td>
										<td>${transaction.invoice.serverId}</td>
									</tr>
									<tr>
										<td style="color: Gray">Cliente</td>
										<td>${transaction.invoice.customerId}</td>
										<td style="color: Gray">Status</td>
										<td>${transaction.invoice.status}</td>
									</tr>
									<tr>
										<td style="color: Gray">Fecha</td>
										<td>${transaction.invoice.creationDate}</td>
										<td style="color: Gray">Monto</td>
										<td>${transaction.invoice.total}</td>
									</tr>
								</table>
							</td>
						</tr>
					</c:if>

					<c:if test="${transaction.trxType == 'Pay Invoice'}">
						<c:if test="${transaction.payment.amount > 0}">
	            <tr>
								<td colspan="7">
									<table
										style="font-size: 10pt; margin-left: 20px; border-style: solid; border-color: DarkGray; border-width: 1px">
										<tr>
											<td colspan="4" style="color: Gray; font-weight: bold">Invoice
												Payment</td>
										</tr>
										<tr>
											<td style="color: Gray">ServerId</td>
											<td>${transaction.payment.serverId}</td>
										</tr>
										<tr>
											<td style="color: Gray">InvoiceId</td>
											<td>${transaction.payment.invoice.serverId}</td>
										</tr>
										<tr>
											<td style="color: Gray">Cliente</td>
											<td>${transaction.payment.invoice.customerId}</td>
											<td style="color: Gray">Status</td>
											<td>${transaction.payment.status}</td>
										</tr>
										<tr>
											<td style="color: Gray">Fecha</td>
											<td>${transaction.payment.creationDate}</td>
											<td style="color: Gray">Monto</td>
											<td>${transaction.payment.amount}</td>
										</tr>
									</table>
								</td>
							</tr>
						</c:if>
					</c:if>

					<tr>
						<td colspan="8">
							<table
								style="font-size: 10pt; margin-left: 20px; border-style: solid; border-color: Red; border-width: 1px">
								<tr>
									<td colspan="4" style="color: Gray; font-weight: bold">Detalles de Error</td>
								</tr>
								<tr>
									<td style="color: Gray">Mensaje</td>
									<td style="color: Red">${transaction.trxError.message}</td>
								</tr>
								<tr>
									<td style="color: Gray">Causa</td>
									<td style="color: Red">${transaction.trxError.cause}</td>
								</tr>
								<tr>
									<td style="color: Gray">Stack Trace</td>
									<td style="color: Red">${transaction.trxError.stackTrace}</td>
								</tr>
							</table>
						</td>
					</tr>

				</c:forEach>

			</table>

		</div>

		<br />
		<footer class="main_footer">Copyright Spirit Consulting, Inc. 2010-2013 </footer>

	</div>

</body>
</html>