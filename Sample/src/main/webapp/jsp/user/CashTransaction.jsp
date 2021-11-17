<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Cash Transaction</title>
<style type="text/css">
.cashTransactionContainer {
	background-color: white;
	position: absolute;
	padding: 30px;
	justify-content: center;
	margin-left: 38%;
	margin-top: 50px;
}

h2 {
	text-align: center;
	text-decoration: underline;
}

#cashtrasactbtn, #cashClearfeild {
	width: auto;
	padding: 7px;
	background-color: #ccddff;
}

#message {
	text-align: center;
}

#cashtrasactbtn:hover, #cashClearfeild:hover {
	background-color: #6699ff;
	color:white;
}

.cashtransactbtn {
	display: inline;
	margin-left: 120px;
}

label, select {
	padding: 10px;
	font-family: sans-serif;
	font-size: large;
}
</style>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script type="text/javascript">
	$(document).on('click', '#cashtrasactbtn', function() {
		transactbtnClick();
	});

	$(document).on(
			'click',
			'#cashClearfeild',
			function() {
				resetfeild("#selectAccount", "#transactType", "#transactFund");
				$("#message").text("");
				$("#selectAccount", "#transactType", "#transactFund").css(
						"border-color", "black");
			});

	function transactbtnClick() {
		var senderAccNo = $("#selectAccount").val();
		var transactType = $("#transactType").val();
		var fund = $("#transactFund").val();

		//alert(senderAccNo + ", " + transactType + ", " + fund);

		feildnullcheck(senderAccNo, transactType, fund);
	}

	//feild Empty Check
	function feildnullcheck(senderAccNo, transactType, fund) {
		if (senderAccNo == "empty") {
			$("#selectAccount").focus().css("border-color", "red");
			$("#message").text("select Account").css("color", "red");

		} else if (transactType == "empty") {
			$("#transactType").focus().css("border-color", "red");
			$("#message").text("select transactType").css("color", "red");

		} else if (fund == "") {
			//alert("enter amount");
			$("#transactFund").focus().css("border-color", "red");
			$("#message").text("enter Amount").css("color", "red");

		} else if (senderAccNo != "empty" && transactType != "empty"
				&& fund != "") {
			dobankTransaction(senderAccNo, transactType, fund);
		}
	}

	//do bankTransact
	function dobankTransaction(senderAccNo, transactType, fund) {
		$.ajax({
			url : "welcome",
			type : "POST",
			data : {
				name : "doUserCashTransaction",
				accNo : senderAccNo,
				transactType : transactType,
				transferFund : fund,
			},
			success : function(response) {
				console.log(response);
				$("#message").text(response).css("color", "red");
				console.log(response[0]);
				if (response[0] == "T") {
					$("#message").text(response).css("color", "green");
					resetfeild("#selectAccount", "#transactType",
							"#transactFund");
				}
			}
		});
		//resetfeild("#selectAccno","#BenifaccNo" , "#transactFund");
	}

	function resetfeild(selectfeild, inputfeild2, inputfeild3) {
		$(selectfeild).val($("" + selectfeild + " option:first").val());
		$(inputfeild2).val($("" + inputfeild2 + " option:first").val());
		$(inputfeild3).val("");
	}
</script>
</head>
<body>
	<jsp:include page="UserPage.jsp" />
	<div class="cashTransactionContainer">

		<div id="cashhead">
			<h2>
				<a>CASH TRANSACTION</a>
			</h2>
		</div>
		<br>
		<div>
			<label for="selectAccount">Select Account</label> <select
				id="selectAccount">
				<option value="empty">Select Account</option>
				<c:forEach items="${acountslist}" var="item">
					<option value="${item}">${item}</option>
				</c:forEach>
			</select>
		</div>
		<br>
		<div>
			<label for="transactType">Select Transact Type</label> <select
				id="transactType">
				<option value="empty">Select Type</option>
				<option value="deposit">Deposit</option>
				<option value="withdraw">Withdraw</option>
			</select>
		</div>
		<br>
		<div>
			<label for="transactFund">Enter Amount</label> <input type="number"
				placeholder="Enter Fund to Transfer" id="transactFund">
		</div>
		<br>
		<div>
			<a id="message"></a>
		</div>
		<div class="cashtransactbtn">
			<button id="cashtrasactbtn">Transact</button>
			<button id="cashClearfeild">Clear</button>
		</div>
		<br>

	</div>

</body>
</html>