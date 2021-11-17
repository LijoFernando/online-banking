<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<style type="text/css">
.transactHistory-container {
	display: block;
	position: relative;
	background-color: #ccddff;
	padding: 20px;
	text-align: center;
}

label {
	color: Black;
}

h3 {
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
	justify-content: space-between;
}

#selectAcc-container {
	display: flex;
	justify-content: center;
}

#selectedAccNo {
	margin-left: 5px;
	margin-right: 10px;
}

#downloadtransact-link {
	background-color: #4d88ff;
	padding: 5px;
	color: white;
	border-color: yellow;
}

#download-transact-container {
	background-color: white;
	position: absolute;
	width: 600px;
	height: 400px;
	z-index: 15;
	top: 50%;
	left: 40%;
	padding-top: 60px;
	margin: 50px 0 0 -150px;
}

#downloadtransact-link:hover {
	background-color: #1a66ff;
}

#downloadtransact-link:after ::selection {
	background-color: #4d88ff;
}

.download-list-item {
	font-family: serif;
	font-size: large;
	padding-bottom: 20px;
	display: flex;
	justify-content: space-between;
	margin-top: 10px;
	margin-left: 20%;
	margin-right: 20%;
	padding-bottom: 20px;
	padding-bottom: 20px
}

#downloadtransact-btn-container {
	margin-top: 10px;
	margin-bottom: 10px;
}

#downloadtransact-btn, #downloadtransact-cancel-btn {
	font-size: medium;
	background-color: #ccddff;
	width: auto;
	padding: 7 px;
}

#downloadtransact-btn:hover, #downloadtransact-cancel-btn:hover {
	background-color: #557cc0;
}

.download-list-item .typeselect {
	width: 180px;
}

.download-list-header-container {
	margin-bottom: 40px;
}

#downloaderrormsg {
	text-align: center;
}

#download-list-header {
	font-family: sans-serif;
	font-size: x-large;
	text-decoration: underline;
}
</style>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script type="text/javascript">
	$(document).ready(
			function() {
				$("select[id='selectedAccNo'] option:eq(1)").attr("selected",
						"selected");
				$("#selectedAccNo").click();
			});

	function loadDownloadDatepickers() {
		var selectAccno = $('#selectedAccNo').val();
		if (selectAccno != "empty" && selectAccno != "") {
			console.log(selectAccno);
			$.ajax({
				url : "welcome",
				type : "POST",
				data : {
					name : "loadTransactDatePicker",
					accno : selectAccno
				},
				success : function(data) {
					console.log(data);
					if (data != null && data != "") {
						setDatefordownloadDatepicker(data);
					}
				}

			});
		}
	}
	function setDatefordownloadDatepicker(data) {

		var firstTime = data.firstTransactionTime.split(" ");
		var LastTime = data.LastTransactionTime.split(" ");
		console.log(firstTime);
		console.log(LastTime);

		setMinMaxFordatePicker('#beginDate', firstTime[0], LastTime[0]);
		setMinMaxFordatePicker('#endDate', firstTime[0], LastTime[0]);
	}

	function setMinMaxFordatePicker(datepickerid, first, last) {
		$(datepickerid).attr('min', first);
		$(datepickerid).attr('max', last);
	}

	$(document).on('click', '#selectedAccNo', function() {

		//alert("changed");

		validateSelection("#selectedAccNo");

	});
	$(document).on('change', '#selectedAccNo', function() {

		//alert("changed");
		$('.transact-table').show();
		$('#download-transact-container').hide();

	});

	function validateSelection(selectID) {

		var selectedAccNo = $(selectID).val();

		//alert(selectedAccNo);

		if (selectedAccNo != "empty") {
			//alert("select one");
			loadTransactionData(selectedAccNo);
		}

	}

	function loadTransactionData(selectedAccNo) {
		$.ajax({
			url : "welcome",
			type : "POST", //Use "PUT" for HTTP PUT methods
			data : {
				name : "getUserTransactionHistory",
				accNo : selectedAccNo,
			},
			success : function(data) {
				console.log(data);
				var length = data.length;
				console.log(data);
				if (length != 0) {
					$.each(data, function(index, item) {
						$("#historytable").show();
						var eachrow = "<tr>" + "<td>" + item.transactionId
								+ "</td>"

								+ "<td>" + item.modeOfTransfer + "</td>"

								+ "<td>" + item.receiverAccNo + "</td>"

								+ "<td>" + item.creditAmount + "</td>"

								+ "<td>" + item.debitAmount + "</td>"

								+ "<td>" + item.timeOfTransaction + "</td>"

								+ "</tr>";
						$('#historytable').append(eachrow);
					});
				} else {
					$("#historytable").hide();
				}
			},
			error : function(response) {
				$("#historytable").hide(); //console.log("error"+ response);
				$("#error").text("History Cannot Be load");
			},
		});
	}
	$(document).on('click', '#downloadtransact-link', function() {
		$('.transact-table').hide();
		$('#download-transact-container').show();
		loadDownloadDatepickers();
	});

	$(document).on('click', '#downloadtransact-cancel-btn', function() {
		$('.transact-table').show();
		$('#download-transact-container').hide();

	});
	$(document).on(
			'click',
			'#downloadtransact-btn',
			function() {
				var typeOfTransact = $('#transactType').val();
				var fromDate = $('#beginDate').val();
				var tillDate = $('#endDate').val();
				var selectAccno = $('#selectedAccNo').val();
				if (downloadFeildNullCheck()) {
					/* downloadTransactData(selectAccno, typeOfTransact, fromDate,
							tillDate); */
							appendDownloaddata(selectAccno, typeOfTransact, fromDate,
									tillDate);
				}
			});

	function downloadFeildNullCheck() {
		var typeOfTransact = $('#transactType').val();
		var fromDate = $('#beginDate').val();
		var tillDate = $('#endDate').val();
		var selectAccno = $('#selectedAccNo').val();
		console.log(typeOfTransact + ',' + fromDate + "," + tillDate);
		if (iSvalidateSelection('#selectedAccNo')) {
			if (fromDate != "" && tillDate != "") {
				downloadErrorMsg("");
				if (fromDate <= tillDate) {
					downloadErrorMsg("");
					return true;
				} else {
					downloadErrorMsg("invalid Date Selection");
				}
			} else {
				downloadErrorMsg("Choose Both Date From and Till");
			}
		} else {
			downloadErrorMsg("Select Account");
		}
		return false;
	}

	function downloadErrorMsg(msg) {
		$("#dterror").text(msg).css('color', 'red');
	}

	function downloadTransactData(selectAccno, typeOfTransact, fromDate,
			tillDate) {
	/* 	$.ajax({
					url : "welcome",
					type : "POST",
					data : {
						name : "dowloadTransactionsAsXsl",
						accNo : selectAccno,
						transactType : typeOfTransact,
						dateFrom : fromDate,
						dateTill : tillDate
					},
					success : function(data, textStatus, jqXHR) {

						var xhrType = jqXHR.getResponseHeader('Content-Type');

						console.log(data + "," + textStatus + "," + xhrType);
						var blob = new Blob(
								[ data ],
								{
									type : "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
								});
						var fileName = "transaction.xlsx";
						//Check the Browser type and download the File.

						var url = window.URL || window.webkitURL;
						link = url.createObjectURL(blob);
						var a = $("<a />");
						a.attr("download", fileName);
						a.attr("href", link);
						$("#form1").append(a);
						a[0].click();
					}
				}); */

		/* var form = document.creatElement("form");
		var element1 = document.createElement("input");
		form.method = "POST";
		form.action = "${pageContext.request.contextPath }/welcome";
		element1.value=" {name : dowloadTransactionsAsXsl, accNo :"+selectAccno+",
		+"	transactType :"+typeOfTransact+" , dateFrom : "+ fromDate +",dateTill : "+tillDate+"}"; //its a json string I need to pass to server.
		element1.name="data";
		element1.type = 'hidden';
		form.appendChild(element1);

		document.body.appendChild(form);
		form.submit(); 
		$("div#form1").append(
				// Creating Form Div and Adding <h2> and <p> Paragraph Tag in it.
				$("<form/>", {
					id : 'myform',
					action : '${pageContext.request.contextPath }/welcome?name=dowloadTransactionsAsXsl',
					method : 'POST'
				}).append(
						// Create <form> Tag and Appending in HTML Div form1.
						$("<input/>", {
							type : 'hidden',
							name : 'data',
							value : '{ accNo :' + selectAccno
									+ ', transactType :' + typeOfTransact
									+ ', dateFrom : ' + fromDate
									+ ', dateTill : ' + tillDate + '}'
						}) // Creating Input Element With Attribute.

						 $("<input/>", {
							type : 'submit',
							id : 'submit',
							name : 'name',
							hidden:'hidden',
							value : ''
						}) ));
		});
		
		
		$('#myform').submit(function() {
			alert("submited"); */

		/* 	$("div#form1")
					.append(

							$(
									"<form/>",
									{
										id : 'myform',
										action : '${pageContext.request.contextPath }/welcome',
										method : 'POST'
									}).append(

									/* $("<input/>", {
										type : 'hidden',
										name : 'data',
										value : '{ accNo :' + selectAccno
												+ ', transactType :'
												+ typeOfTransact + ', dateFrom : '
												+ fromDate + ', dateTill : '
												+ tillDate + '}'
									}) 								
									$("<input/>", {
										id:'submit',
										type : 'hidden',
										name : 'name',
										value : 'dowloadTransactionsAsXsl'
									})
									
				));
			$('#myform').submit(); */

	}
	function iSvalidateSelection(selectID) {

		var selectedAccNo = $(selectID).val();

		//alert(selectedAccNo);

		if (selectedAccNo != "empty" && selectedAccNo != "") {
			//alert("select one");
			return true;
		}
		return false;

	}
	function appendDownloaddata(selectAccno, typeOfTransact, fromDate,tillDate){
		var hrflink = $('#downloadData').attr('href');
		var appendlink = hrflink+"&accNo="+selectAccno+"&transactType="+typeOfTransact+"&dateFrom="+fromDate+"&dateTill="+tillDate;
		$('#downloadData').attr('href',appendlink);
		$('#form1 a').click();
		$('#downloadData').click();
		window.open(appendlink, "_self");
		console.log(appendlink);
		$('#downloadData').attr('href',hrflink);
	}
</script>
</head>


<body>
	<jsp:include page="UserPage.jsp"></jsp:include>
	<div class="transactHistory-container">
		<h3>
			<a>TRANSACTION HISTORY</a>
		</h3>
		<div id="selectAcc-container">
			<label for="selec-accNo">Account Number</label> <select
				id="selectedAccNo"><option value="empty">Select
					Account</option>

				<c:forEach items="${acountslist}" var="item">
					<option value="${item}">${item}</option>
				</c:forEach>
			</select> <a id="downloadtransact-link">Download Transactions</a>
		</div>
		<br>
		<div class="transact-table">
			<table id="historytable">
				<tr class="table-header">
					<td>TransactionID</td>
					<td>Mode Of Transfer</td>
					<td>ProcessTo</td>
					<td>Credit</td>
					<td>Debit</td>
					<td>Time of Transaction</td>
				</tr>
			</table>
			<div>
				<a id="error"></a>
			</div>
		</div>
		<div style="display: none;" id="download-transact-container">
			<div class="download-list-header-container">
				<a id="download-list-header">Download Transactions</a>
			</div>
			<div class="download-list-item">
				<label for="transactType">Select Transact Type</label><select
					class="typeselect" id="transactType"><option value="all">All</option>
					<option value="credit">Credit</option>
					<option value="debit">Debit</option>
				</select>
			</div>
			<div class="download-list-item">
				<label for="beginDate">Date From</label>
				<input id="beginDate" class="typeselect" type="date">
			</div>
			<div class="download-list-item">
				<label for="endDate">Date Till</label><input id="endDate"
					class="typeselect" type="date">
			</div>
			<div id="form1" style="display: none;">
				<a id="downloadData" href="${pageContext.request.contextPath }/welcome?name=dowloadTransactionsAsXsl">click</a>
			</div>


			<div id="downloadtransact-btn-container">
				<button id="downloadtransact-btn">Download</button>
				<button id="downloadtransact-cancel-btn">Cancel</button>
			</div>
			<a id="dterror"></a>
		</div>

	</div>
</body>
</html>