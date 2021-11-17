<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<style>
*
.topnav {
	background-color: #333;
	overflow: hidden;
	color: #f2f2f2;
	padding: 14px 16px;
	font-size: 17px;
	text-align: center;
}

table {
	font-family: arial, sans-serif;
	border-collapse: collapse;
	width: 50%;
}

td, th {
	border: 1px solid #dddddd;
	text-align: left;
	padding: 8px;
}

tr:nth-child(even) {
	background-color: #dddddd;
}

.button {
	border-radius: 5px;
	border : none;
	color: white;
	padding: 12px 32px;
	text-align: center;
	text-decoration: none;
	display: inline-block;
	font-size: 15px;
	margin: 4px 2px;
	cursor: pointer;
	background-color: #008CBA;
	border: none;
}

* {
	box-sizing: border-box;
}

input[type=text], input[type=number], input[type=select], select {
	width: 45%;
	padding: 12px;
	border: 1px solid rgb(168, 166, 166);
	border-radius: 4px;
	resize: vertical;
}

h2 {
	font-family: Arial;
	font-size: medium;
	font-style: normal;
	font-weight: bold;
	color: brown;
	text-align: center;
	text-decoration: underline;
}

label {
	padding: 12px 12px 12px 0;
	display: inline-block;
}

.container {
	border-radius: 5px;
	background-color: #f2f2f2;
	padding: 20px;
}

@media screen and (max-width: 600px) {
	input[type=submit] {
		width: 100%;
		margin-top: 0;
	}
}

.column {
	float: left;
	width: 50%;
}

/* Clear floats after the columns */
.row:after {
	content: "";
	display: table;
	clear: both;
}
</style>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script type="text/javascript">
	$(document)
			.ready(
					function() {
						$("#historytable").hide();
						$("#searchbtn")
								.click(
										function() {
											var value = $("#searchBox").val();
											if (value == "") {
												$("#message").html(
														"Enter Account Number");
												return false;
											} else {
								$				
														.ajax({
															url : "welcome",
															type : "POST", //Use "PUT" for HTTP PUT methods
															data : {
																name : "getTransactions",
																accNo : value,
															},
															success : function(
																	data) {
																console
																		.log(data);
																var length = data.length;
																//alert(data);
																if (length != 0) {
																	$('#historytable tr:not(:first)').remove();
																	$
																			.each(
																					data,
																					function(
																							index,
																							item) {
																						$(
																								"#historytable")
																								.show();
																						var eachrow = "<tr>"
																								+ "<td>"
																								+ item.transactionId
																								+ "</td>"
																								+ "<td>"
																								+ item.senderAccNo
																								+ "</td>"
																								+ "<td>"
																								+ item.receiverAccNo
																								+ "</td>"
																								+ "<td>"
																								+ item.creditAmount
																								+ "</td>"
																								+ "<td>"
																								+ item.debitAmount
																								+ "</td>"
																								+ "<td>"
																								+ item.timeOfTransaction
																								+ "</td>"
																								+ "</tr>";
																						$(
																								'#historytable')
																								.append(
																										eachrow);
																					});
																} else {
																	$(
																			"#historytable")
																			.hide();
																}
															},
															error : function(
																	response) {
																$(
																		"#historytable")
																		.hide();
																console
																		.log("error"
																				+ response);
																$(
																		"#requestform")
																		.html(
																				"error");
															},

														});
											}
										});
						$("#searchBox").keydown(function() {
							$('#historytable').DataTable().ajax.reload();
						});

					});
</script>

</head>
<body>

	<div class="topnav">
		<h1>Welcome to SearchTransaction Page</h1>

	</div>
	<div class="column">
		<div class="row">
			<h2>Search the Transaction for AccountNumber</h2>
			<label for="searchBox">Enter the AccountNumber</label> <input
				id="searchBox" type="number" name="searchbox"
				placeholder="Enter account number to Search">
			<button id="searchbtn" class="button" name="searchAccount">search</button>
		</div>
		<div class="row">
			<p id="${message}">${message}</p>
		</div>
		<div>
			<table id="historytable" hidden="true">
				<tr>
					<td>Transaction ID</td>
					<td>Sender AccNo</td>
					<td>Receiver AccNo</td>
					<td>Credit</td>
					<td>Debit</td>
					<td>TimeOfTransaction</td>
				</tr>
			</table>
		</div>
	</div>

</body>
</html>