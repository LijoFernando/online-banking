<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<style type="text/css">
.cashTransact-container {
	display: block;
	position: relative;
	background-color: #c65353;
	padding: 20px;
	border-radius: 20px;
	padding: 20px;
	margin-left: 250px;
	text-align: center;
}

label {
	color: white;
}

h3 {
	color: white;
	text-align: center;
	padding: 0px, 0px;
	font-size: medium;
	align-items: center;
	text-decoration: underline;
}

td {
	padding: 20px;
	text-align: center;
}

table {
	border-radius: 10px;
	border: 20px;
	color: black;
	background-color: white;
	border: 20px;
}

.table-header {
	border-radius: 10px;
	background-color: #000066;
	color: white;
	background-color: #000066;
}

.transact-table {
	display: inline-block;
	justify-content: center;
}
</style>
</head>


<body>
	<jsp:include page="UserPage.jsp"></jsp:include>
	<div class="cashTransact-container">
		<h3>
			<a>TRANSACTION HISTORY</a>
		</h3>
		<div>
			<label for="selec-accNo"> Select Account Number</label> <select><option
					value="empty">Select Account</option>
					
			</select>
		</div>
		<br>
		<div class="transact-table">
			<table>
				<tr class="table-header">
					<td>TransactionID</td>
					<td>ProcessTo</td>
					<td>Credit</td>
					<td>Debit</td>
					<td>Time of Transaction</td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>