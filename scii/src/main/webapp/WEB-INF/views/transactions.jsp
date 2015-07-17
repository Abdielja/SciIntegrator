<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="false"%>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Transaction Manager</title>

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
<script type="text/javascript" src="resources/js/jqwidgets/jqxbuttons.js"></script>
<script type="text/javascript" src="resources/js/jqwidgets/jqxnavigationbar.js"></script>
<script type="text/javascript" src="resources/js/jqwidgets/jqxwindow.js"></script>

<script type="text/javascript" src="resources/js/views/header.js"></script>


</head>

<body>

  <div id="wrapper">

    <script type="text/javascript">

    var selectedUserOid = 0;
    var selectedType = 0;
    
    <% int trxCount = 0; %>
 
    function onUsersLoaded(userList)
    {
      console.log(userList);
    }

    function userListChanged(_userList)
    {
      selectedUserOid = _userList.value;
      //alert(_userList.value);
    }
    
    function trxTypeListChanged(_trxTypeList)
    {
      selectedTrxType = _trxTypeList.value;
      //alert(_userList.value);
    }
    
    function btnSearchClicked()
    {
      var url = "transactions?userOid=" + selectedUserOid;
      document.location.href = url;
    }
    
    $(document).ready
    (
      function() 
      {

        createMenu();

      }
      
    );
    
    </script>

    <header id="main_header">
      <figure id="main_logo">
        <img src="resources/images/logo_spirit.png">
      </figure>
      <hgroup>
        <h1>Spirit System Integrator</h1>
        <h2>Version 3.33</h2>
        <h3>(DND)</h3>
      </hgroup>
    </header>

    
    <div id="jqxWidget">
      <div id='jqxMenu'>
        <ul>
          
          <li><a href="home">Inicio</a></li>
          
          <li>
            Vendedores
            <ul>
              <li><a href="userMonitor">Monitor</a></li>
              <li><a href="userBalance">Proceso de Cuadre</a></li>
              <li><a href="userIncidences">Incidencias</a></li>
            </ul>
          </li>
          
          <li>
            Transacciones
            <ul>
              <li><a href="transactions?userOid=0">Busqueda Transacciones</a></li>
              <li><a href="transactions_failed">Transacciones con errores</a></li>
            </ul>            
          </li>
          
        </ul>
      </div>
    </div>

    <br />

    <div id="pageTitle" class="page_title">
      Transacciones
    </div>
    
    <div>
	    <form action="submit">
		    <table cellspacing="3" style="font-family: Verdana; font-size: 10pt;">

		     <tr>

		       <td>Usuario </td>
           <td>Tipo Transaccion </td>
           <td>
		        <input id="btnSearch" type="button" value="Buscar" onClick="btnSearchClicked(this);" style="width: 80px; height: 23px;">
		       </td>
		       
		     </tr>

		     <tr>
 
           <td>
             <Select id="userList" onChange="userListChanged(this);">
               <option value="0">Todos</option>
               <option value="1">Edgardo Natis</option>
               <option value="8">Alberto Chavarria</option>
               <option value="14">Raul Espinosa</option>
               <option value="11">Jorge Mackay</option>
               <option value="13">Raul Gutierrez</option>
               <option value="12">Laureano Ojo</option>
               <option value="2">Cesar Arboleda</option>
               <option value="7">Samuel Castillo</option>
               <option value="9">Nicolas Madero</option>
               <option value="6">Robinson Crespo</option>
               <option value="3">Alexander Martinez</option>
               <option value="5">Miguel Contini</option>
               <option value="4">Julio Cesar</option>
               <option value="10">Dionel de Icaza</option>
               <option value="16">Felix Villarreal</option>
             </Select>
           </td>

           <td>
             <Select id="trxTypeList" onChange="trxTypeListChanged(this);">
               <option value="-1">Todas</option>
                <option value="0">Cotizacion</option>
                <option value="1">Pedido</option>
                <option value="2">Factura</option>
                <option value="3">Cobro</option>
                <option value="5">Reversion Factura</option>
                <option value="6">Incidencia</option>
                <option value="100">Apertura</option>
                <option value="101">Cierre</option>
             </Select>
           </td>

		     </tr>

		     <tr>
		     
           <td id="trxCounter" colspan="3" style="font-weight: bold; color: Gray;">
           </td>
 
		     </tr>

		    </table>
		  </form>
    </div>
    
    <br />
    
    <div class="main_section">

	<br />

	<table cellspacing="3" style="padding: 3px; font-family: Verdana; border-color: DarkGray; border-width: 1px; border-style: solid">

		<tr valign="top"
			style="background: silver; font-size: 10pt; font-weight: bold">
			<td></td>
			<td>Provedor</td>
			<td>Tipo</td>
			<td>Oid</td>
			<td>Fecha</td>
			<td>Usuario</td>
			<td>Status</td>
		</tr>

		<c:forEach items="${transactions}" var="transaction">

      <c:if test="${transaction.status != 3 && transaction.status != 7}">
      
				<tr valign="top" style="font-size: 10pt">
					<td>v</td>
					<td><c:out value="${transaction.provider.name}" /></td>
					<td><c:out value="${transaction.trxType}" /></td>
					<td><c:out value="${transaction.oid}" /></td>
					<td><c:out value="${transaction.creationDate}" /></td>
					<td><c:out value="${transaction.createdBy.name}" /></td>
					
					<c:if test="${transaction.status == 0}">
					  <td><c:out value="Pendiente" /></td>
					</c:if>
				
	        <c:if test="${transaction.status == 1}">
	          <td><c:out value="Error" /></td>
	        </c:if>
	      
	        <c:if test="${transaction.status == 2}">
	          <td><c:out value="Procesado" /></td>
	        </c:if>
	      
				</tr>
	
	      <c:if test="${transaction.trxType == 'Open Transaction'}">
	        <tr>
	          <td colspan="7">
	            <table
	              style="font-size: 10pt; margin-left: 20px; border-style: solid; border-color: DarkGray; border-width: 1px">
	              <tr>
	                <td colspan="4" style="color: Gray; font-weight: bold">Opening Transaction</td>
	              </tr>
	            </table>
	            <br />
	          </td>
	        </tr>
	      </c:if>
	
				<c:if test="${transaction.trxType == 'Create Invoice'}">
					<tr>
						<td colspan="7">
							<table 
								style="font-size: 10pt; margin-left: 20px; border-style: solid; border-color: DarkGray; border-width: 1px">
								<tr>
									<td colspan="6" style="color: Gray; font-weight: bold">Invoice</td>
								</tr>
	              <tr>
	                <td style="color: Gray">Oid</td>
	                <td>${transaction.invoice.oid}</td>
	                <td style="color: Gray">ServerId</td>
	                <td colspan="3">${transaction.invoice.serverId}</td>
	              </tr>
								<tr>
									<td style="color: Gray">Cliente</td>
									<td colspan="5">${transaction.invoice.customerId}</td>
								</tr>
								<tr>
									<td style="color: Gray">Fecha</td>
									<td>${transaction.invoice.creationDate}</td>
									<td style="color: Gray">Monto</td>
									<td>${transaction.invoice.total}</td>
	                <td style="color: Gray">Status</td>
	                <td>${transaction.invoice.status}</td>
								</tr>
							</table>
							<br />
						</td>
					</tr>
				</c:if>
	
				<c:if test="${transaction.trxType == 'Pay Invoice'}">
					<tr>
						<td colspan="7">
							<table
								style="font-family: Verdana; font-size: 10pt; margin-left: 20px; border-style: solid; border-color: DarkGray; border-width: 1px">
								<tr>
									<td colspan="6" style="color: Gray; font-weight: bold">
									Invoice Payment
									</td>
								</tr>
								<tr>
	                <td style="color: Gray">Oid</td>
	                <td>${transaction.payment.oid}</td>
									<td style="color: Gray">ServerId</td>
									<td>${transaction.payment.serverId}</td>
								</tr>
								<tr>
	                <td style="color: Gray">Invoice Oid</td>
	                <td>${transaction.payment.invoice.oid}</td>
									<td style="color: Gray">InvoiceId</td>
									<td colspan="3">${transaction.payment.invoice.serverId}</td>
								</tr>
								<tr>
									<td style="color: Gray">Cliente</td>
									<td colspan="5">${transaction.payment.invoice.customerId}</td>
								</tr>
								<tr>
									<td style="color: Gray">Fecha</td>
									<td>${transaction.payment.creationDate}</td>
									<td style="color: Gray">Monto</td>
									<td>${transaction.payment.amount}</td>
	                <td style="color: Gray">Status</td>
	                <td>${transaction.payment.status}</td>
								</tr>
							</table>
	            <br />
						</td>
					</tr>
				</c:if>
	
	      <c:if test="${transaction.trxType == 'Add Incidence'}">
	        <tr>
	          <td colspan="7">
	            <table
	              style="font-size: 10pt; margin-left: 20px; border-style: solid; border-color: DarkGray; border-width: 1px">
	              <tr>
	                <td colspan="4" style="color: Gray; font-weight: bold">Add Incidence</td>
	              </tr>
	              <tr>
	                <td style="color: Gray">Oid</td>
	                <td>${transaction.incidence.oid}</td>
	              </tr>
	              <tr>
	                <td style="color: Gray">Motivo</td>
	                <td>${transaction.incidence.incidenceType.description}</td>
	              </tr>
	              <tr>
	                <td style="color: Gray">Descripcion</td>
	                <td>${transaction.incidence.description}</td>
	              </tr>
	            </table>
	            <br />
	          </td>
	        </tr>
	      </c:if>
	
        <% trxCount++; %>
    
      </c:if>
    
		</c:forEach>

	</table>

  </div>

    <br />
    <footer class="main_footer">Copyright Spirit Consulting, Inc.
      2010-2013 </footer>

</div>

    <script type="text/javascript">
	    var trxCount = "<%=trxCount%>";
	    console.log("Counter - " + trxCount);
	    document.getElementById('trxCounter').innerHTML = trxCount + " Transacciones encontradas.";
    </script>

</body>
</html>