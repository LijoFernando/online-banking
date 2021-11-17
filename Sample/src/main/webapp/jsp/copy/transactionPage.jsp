<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>TransactionPage</title>

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
	border: none;
	color: white;
	padding: 15px 32px;
	text-align: center;
	text-decoration: none;
	display: inline-block;
	font-size: 16px;
	margin: 4px 2px;
	cursor: pointer;
	background-color: #008CBA;
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

ul {
	border-radius: 4px;
	background-color: white;
	text-align: center;
	list-style: none;
	padding-left: 0px;
	margin-top: 0px;
	position: absolute;
}

.suggest-down {
	width: 21%;
	margin-left: 96px;
}

.benf-suggest-down {
	width: 21%;
	margin-left: 170px;
}

li {
	padding-top: 10px;
	padding-bottom: 10px;
}

li:hover {
	color: white;
	background-color: black;
	border-radius: 4px;
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

input[type=submit] {
	background-color: #4CAF50;
	color: white;
	padding: 12px 20px;
	border: none;
	border-radius: 4px;
	cursor: pointer;
	float: left;
}

input[type=submit]:hover {
	background-color: #32a336;
}

.container {
	border-radius: 5px;
	background-color: #f2f2f2;
	padding: 20px;
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

.link-class:hover {
	background-color: #f1f1f1;
}
</style>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<script type="text/javascript">
	function validate() {

		alert("Enter Feild " + message + " ");
		return false;
	}
	$(document).on('click', '#transaction', function() {
		let accno = document.forms["f1"]["accNo"].value;
		let transacttype = document.forms["f1"]["transacttype"].value;
		let amount = document.forms["f1"]["amount"].value;
		let message = "Account Number";
		if (accno != 0) {
			message = "Transaction Feild";
			if (transacttype != null) {
				message = "Amount";
				if (amount != 0) {
					$.ajax({
						url : "welcome",
						type : "GET", //Use "PUT" for HTTP PUT methods
						data : {
							name : "doonlinetransaction"
						},
						success : function(response) {
							console.log("success");
						},
						error : function(response) {
							console.log(response);
						},
					});
				}
			}
		}

	});
	$(document).on('click', '#searchPage', function() {
		$.ajax({
			url : "welcome",
			type : "GET", //Use "PUT" for HTTP PUT methods
			data : {
				name : "searchtransaction"
			},
			success : function(response) {
				console.log(response);

			},
			error : function(response) {
				console.log(response);
			},
		});
	});

	//call for get usernameby keyword
	function suggestusernmesforkey(searchboxid, suggestUserNamelistId,
			dropdownId) {
		$(searchboxid).keyup(
				function() {
					$(suggestUserNamelistId).html('');
					//$("#accNolist").empty();
					$('' + dropdownId + ' option:not(:first)').remove();
					var searchField = $(searchboxid).val();

					//	var expression = new RegExp(searchField, "i");
					if (searchField == "") {
						$("#message").text("Enter the userName");
					} else if (searchField != "") {
						//alert(searchField);
						$("#message").text(" ");
						$.ajax({
							url : "welcome",
							type : "POST",
							data : {
								name : "getdataforusernamekeyword",
								uname : searchField
							},

							success : function getUserNames(data) {
								console.log("hai");
								var length = Object.keys(data).length;
								if (length == 0) {
									$("#message").text("Username Not Found")
								}
								if (length != 0) {
									//console.log(data);
									$(suggestUserNamelistId).html('');

									$.each(data, function(key, value) {

										$(suggestUserNamelistId).append(
												'<li id="usernames" value='+value+'>'
														+ key + '</li>');

									});
									//console.log("hello");

									//fill selected username in searchbox
									fillSearchBoxwithSelectedUsername(
											suggestUserNamelistId, dropdownId,
											searchboxid);

								}
							},
						});

					}
				});
	}

	////fill selected username in searchbox
	function fillSearchBoxwithSelectedUsername(suggestUserNamelistId,
			dropdownId, searchboxid) {
		$(suggestUserNamelistId).unbind().on('click', 'li',
				function userselected() {
					//console.log("hello2");
					var click_text = $(this).text();
					$(searchboxid).val($.trim(click_text));
					var CusID = $(this).val();
					console.log(CusID);
					
					loadaccNoToDropDown(CusID, dropdownId);
					$(suggestUserNamelistId).html('');
					$(searchboxid).html('');
				});
	}

	// load dropdownlist usernmaeforKeyword
	function loadaccNoToDropDown(cusID, dropdownId) {
		$.ajax({
			url : "welcome",
			type : "POST",
			data : {
				name : "getAccNumbers",
				customerId : cusID
			},
			success : function(data) {
				console.log(data);
				var length = data.length;
				for (i = 0; i < length; i++) {
					$(dropdownId).append(
							"<option value='"+data[i]+"'>" + data[i]
									+ "</option>");
				}
			}
		});

	}

	//doonlinetransactionByusername
	function doonlinetransactionByusername(senderAccNo, reciverAccNo, amount) {
		$.ajax({
			url : "welcome",
			type : "POST",
			data : {
				name : "doonlinetransaction",
				accno : senderAccNo,
				benifaccno : reciverAccNo,
				amount : amount
			},
			success : function(response) {
				console.log(response);
			},
			error : function(response) {
				console.log(response);
			}
		});
	}

	$(document).ready(
			function() {
				$.ajaxSetup({
					cache : false
				});
				var searchvalue = $("#searchname").val();
				/* var searchboxid = '#searchname';
				var suggestUserNamelistId =' #result'; */

				$("#searchname").on(
						'click',
						function() {

							var searchboxid = '#searchname';
							var suggestUserNamelistId = '#result';
							var dropdownId = "#accNolist";
							$(suggestUserNamelistId).attr('hidden', false);
							suggestusernmesforkey(searchboxid,
									suggestUserNamelistId, dropdownId);
						});
				$("#benifnamesearch").on(
						'click',
						function() {
							var searchboxid = '#benifnamesearch';
							var suggestUserNamelistId = '#benifsuggest';
							var dropdownId = "#BenifAccNolist"
							suggestusernmesforkey(searchboxid,
									suggestUserNamelistId, dropdownId);
						});

				$("#transactbtnbyusername")
						.on(
								'click',
								function() {
									var senderAccNo = $('#accNolist').val();
									var reciverAccNo = $('#BenifAccNolist')
											.val();
									var amount = $('#transactamount').val();
									console.log(senderAccNo + " "
											+ reciverAccNo + " +tP314");
									if (senderAccNo == "empty"
											|| reciverAccNo == "empty2") {
										$("#message").text(
												"select Account Number");
									} else if (senderAccNo == reciverAccNo) {
										console.log("both are equal");
										$("#message").text(
												"Account Could not be same");
									} else if (senderAccNo != "empty"
											&& reciverAccNo != "empty2"
											&& senderAccNo != reciverAccNo) {
										doonlinetransactionByusername(
												senderAccNo, reciverAccNo,
												amount);
									}

								});
			});
</script>
</head>

<body>

	<div class="topnav">
		<h1>Welcome to Transaction Page</h1>

	</div>
	<div class="row">
		<div class="column">
			<form action="${pageContext.request.contextPath }/welcome?"
				method="post">
				<table>

					<tr>
						<td>Transaction Id</td>
						<td>Mode Of Transaction</td>
						<td>Account No</td>
						<td>Transfer To</td>
						<td>Transact Type</td>
						<td>Time Stamp</td>
						<td>Amount</td>

					</tr>

					<!-- transaction list into transaction pojo's data -->
					<c:forEach items="${transaction}" var="list">
						<tr>
							<td>${list.transactionId}</td>
							<td>${list.modeOfTransfer}</td>
							<td>${list.accountNo}</td>
							<td>${list.benifAccountNo}</td>
							<td>${list.transactionType}</td>
							<td>${list.timeOfTransaction }</td>
							<td>${list.amount}</td>
							<%-- <td><input type="checkbox" name="selecttransact"
								value="${list.accountNo}"></td> --%>
						</tr>


					</c:forEach>
				</table>

				<button id="searchPage" class="button" name="name"
					value="searchTransaction">Search Transaction</button>
				<input type="button" class="button" value="back"
					onclick="history.back()"></input>
			</form>

		</div>

		<div class="column">
			<form name="f1" action="${pageContext.request.contextPath }/welcome?"
				method="post">
				<div class="container">
					<h2>Deposit/withdraw</h2>
					<hr>
					<!-- <div class="row">
						<label for="accNo"><b>Account Number</b></label> <input id="accNo"
							type="number" required="required"
							placeholder="Enter Account Number" name="accno">
					</div> -->
					<div class="row">
						<label for="benifaccNo"><b>Beneficiary Account Number</b></label>
						<input id="benifAccNo" type="number" required="required"
							placeholder="Enter Beneficiary Account Number" name="benifaccno">
					</div>
					<div class="row">
						<label for="transactType"><b>Select Transaction Type</b></label> <select
							id="transacttype" required="required" name="transactType">
							<option value="">SelectOne</option>
							<option value="deposit">Deposit</option>
							<option value="withdraw">Withdraw</option>
						</select>
					</div>
					<div class="row">
						<label for="amount"><b>Amount</b></label> <input id="amount"
							type="number" required="required" placeholder="Enter Amount"
							name="amount">
					</div>
					<hr>
					<button id="transactbtn" class="button" name="name"
						value="cashTransaction">Transact</button>
					<div>
						<a>${message}</a>
					</div>
				</div>
			</form>


			<!-- //transact by name Design -->


			<div class="container">
				<h2>Transact By Customer Name</h2>
				<hr>
				<div class="row">

					<label for="searchname">Enter Name</label> <input type="text"
						id="searchname">
					<ul class="suggest-down" id="result" hidden="true"></ul>

				</div>

				<div class="row">
					<label for="selectAccNo">Select Account No</label> <select
						id="accNolist">
						<option value="empty">select AccontNo</option>
					</select>
				</div>
				<div class="row">
					<label for="benifnamesearch">Enter Beneficiary Name</label> <input
						type="text" id="benifnamesearch">
					<ul class="benf-suggest-down" id="benifsuggest"></ul>

				</div>
				<div class="row">
					<label for="selectBenifAccNo">Select Beneficiary Account No</label>
					<select id="BenifAccNolist">
						<option value="empty2">select Beneficiary AccontNo</option>
					</select>
				</div>
				<div class="row">
					<label for="amount"><b>Amount</b></label> <input
						id="transactamount" type="text" required="required"
						placeholder="Enter Amount">
				</div>
				<hr>
				<button id="transactbtnbyusername" class="button">Transact</button>
				<div>
					<a id="message"></a> <a>${message}</a>
				</div>
			</div>
		</div>
	</div>

</body>
</html>