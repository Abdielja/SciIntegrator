<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<h1>Transaction</h1>

<table cellspacing="15">
  <s:url value="/rest/transactions/1" var="transaction_url" />
  <tr>
    <td>
      <small>${transaction.creationDate}</small> 
    </td>
    <td>
      <small>${transaction.comment}</small> 
    </td>
  </tr>
</table>
