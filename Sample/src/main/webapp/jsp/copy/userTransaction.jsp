<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>UserBankTransaction</title>
<style type="text/css">
.container {
	padding: 20px 10px;
	background-color: gray;
	position: fixed;
	border-radius: 20px;
	margin-left: 35%;'
}

body {
	padding-top: 0px;
}

input, select, button {
	border-radius: 5px;
}

#transact-btn {
	font-size: large;
}

#banktitle {
	border-radius: 5px;
	background-color: #b3b3ff;
	text-align: center;
	padding: 10px;
	font-weight: 5px;
}
</style>
</head>
<body>
	<jsp:include page="UserPage.jsp" />

	<div align="center" class="container">
		<h3>
			<a id="banktitle">BANK TO BANK TRANSFER</a>
		</h3>
		<br>
		<div class="row">
			<label for="selectAccno">Select Account</label> <select
				id="selectAccno">
				<option value="empty">Select AccountNo</option>
			</select>
		</div>
		<br>
		<div class="row">
			<label for="BenifaccNo">Enter Beneficiary Account Number</label> <input
				type="text" id="BenifaccNo">
		</div>
		<br>
		<div class="row">
			<label for="tranferAmount">Enter Amount</label> <input type="text"
				id="transactAmount">
		</div>
		<br>
		<div>
			<button id="transact-btn">Transact</button>
		</div>
	</div>

</body>
</html>