
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>UserBankTransaction</title>
<style type="text/css">
.bankTransactcontainer {
	background-color: white;
	position: absolute;
	padding: 30px;
	margin-left: 32%;
	margin-top: 50px;
}

body {
	padding-top: 0px;
}

#transactbtn, #clearfeild1 {
	font-size: medium;
	background-color: #ccddff;
	width: auto;
	padding: 7px;
}

#getBalance {
	color: blue;
	text-decoration: underline;
}

#transactbtn:hover, #clearfeild1:hover {
	background-color: #6699ff;
	color: white;
}

#banktitle {
	border-radius: 5px;
	text-decoration: underline;
	text-align: center;
	padding: 10px;
	font-weight: 5px;
}
</style>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script type="text/javascript">
	$(document).on('click', '#transactbtn', function() {
		transactbtnClick();
	});

	$(document).on(
			'click',
			'#clearfeild1',
			function() {
				resetfeild("#selectAccno", "#BenifaccNo", "#transactFund");
				$("#message").text("");
				$('#yourBalance').text(" ");
				$("#selectAccno", "#BenifaccNo", "#transactFund").css(
						"border-color", "black");

			});
	$(document).keyup('#cussearchbeneficiary', function() {
		var keyword = $("#cussearchbeneficiary").val();
		//console.log(keyword);
		//console.log("search");
		if (keyword != "") {
			$.ajax({
				url : "welcome",
				type : "POST",
				data : {
					name : "getUserBeneficiary"
				},
				success : function(data) {
					console.log("success");
					//console.log(data);
					searchByKeyword(data, keyword);
				},
			});
		}
	});


	function searchByKeyword(data, keyword) {
		$('#suggestList').empty();
		//console.log(keyword);
		var keywordLike = new RegExp(keyword, "i");
		//console.log(keywordLike);
		$.each(data, function(index, item) {
			var index = item.benefNickName.search(keywordLike);
			//var accNo = item.benefAccNo.search(keywordLike);
			console.log(index);
			if (index != -1) {
				console.log(item.benefNickName);
				var benefdata = "<option value="+item.benefNickName+" label="+item.benefAccNo+">";
				$('#suggestList').append(benefdata);

			} else {
				console.log("not found");
			}
		});
	}
	
	 $(document).on('change', '#cussearchbeneficiary', function () {
         $("#BenifaccNo").val($("#suggestList option[value='" + $("#cussearchbeneficiary").val() + "']").attr("label"));
         $("#BenifaccNo").focus();
      });
	
	function transactbtnClick() {
		var senderAccNo = $("#selectAccno").val();
		var benfAccNo = $("#BenifaccNo").val();
		var fund = $("#transactFund").val();

		//	alert(senderAccNo + ", " + benfAccNo + ", " + fund);

		feildnullcheck(senderAccNo, benfAccNo, fund);
	}

	//feild Empty Check
	function feildnullcheck(senderAccNo, benfAccNo, fund) {
		if (senderAccNo == "empty") {
			$("#selectAccno").focus().css("border-color", "red");
			$("#message").text("Select Account").css("color", "red");
		} else if (benfAccNo == "") {
			$("#selectAccno").focus().css("border-color", "black");
			$("#BenifaccNo").focus().css("border-color", "red");
			$("#message").text("Enter Beneficiary Account No").css("color",
					"red");
		} else if (fund == "") {
			//alert("enter amount");
			$("#BenifaccNo").focus().css("border-color", "black");
			$("#transactFund").focus().css("border-color", "red");
			$("#message").text("Enter Amount").css("color", "red");
		} else if (senderAccNo != "empty" && benfAccNo != "" && fund != "") {
			$("#transactFund").focus().css("border-color", "black");
			dobankTransaction(senderAccNo, benfAccNo, fund);
		}
	}

	//do bankTransact
	function dobankTransaction(senderAccNo, benfAccNo, fund) {
		$.ajax({
			url : "welcome",
			type : "POST",
			data : {
				name : "doUserBankTransaction",
				sender : senderAccNo,
				benfAcc : benfAccNo,
				transferFund : fund,
			},
			success : function(response) {
				console.log(response);
				$("#message").text(response).css("color", "red");
				console.log(response[0]);
				if (response[0] == "T") {
					$("#message").text(response).css("color", "green");
					resetfeild("#selectAccno", "#BenifaccNo", "#transactFund");
				}
			}
		});
		//resetfeild("#selectAccno","#BenifaccNo" , "#transactFund");
	}

	function resetfeild(selectfeild, inputfeild2, inputfeild3) {
		$(selectfeild).val($("" + selectfeild + " option:first").val());
		$(inputfeild2).val("");
		$(inputfeild3).val("");
	}

	$(document).on('click', '#getBalance', function() {
		var selected = $('#selectAccno').val();
		if (selected == 'empty') {
			$('#yourBalance').text(" ");
			$('#message').text("Select Account Number").css('color', 'red');
		} else {
			$('#message').text("");
			$('#yourBalance').css('display', 'block');
			getBalance(selected);
		}
	});

	function getBalance(selected) {
		$.ajax({
			url : "welcome",
			type : 'POST',
			data : {
				name : "getAccBalance",
				accNo : selected,
			},
			success : function(response) {
				console.log(response);
				getBalanceResponse(response);
			}
		});
	}

	function getBalanceResponse(response) {
		$('#yourBalance').text("Your Account Balance is Rs: " + response);
	}
</script>
</head>
<body>
	<jsp:include page="UserPage.jsp" />

	<div align="center" class="bankTransactcontainer">
		<h3>
			<a id="banktitle">BANK TO BANK TRANSFER</a>
		</h3>
		<br>
		<div class="row">
			<label for="selectAccno">Select Account</label> <select
				id="selectAccno">
				<option value="empty">Select AccountNo</option>
				<c:forEach items="${acountslist}" var="item">
					<option value="${item}">${item}</option>
				</c:forEach>
			</select> <a id="getBalance">View Balance</a>
		</div>
		<br>
		<div>
			<a id="yourBalance" style="display: none;">Your Current Balance
				is </a>
		</div>
		<br>
		<div>
			<label for="cussearchbeneficiary">Search Beneficiary</label> <input
				type="text" list="suggestList" id="cussearchbeneficiary"
				placeholder="Enter name">
			<datalist id="suggestList">
			</datalist>
		</div>
		<br>
		<div class="row">
			<label for="BenifaccNo">Enter Beneficiary Account Number</label> <input
				name="benifAccNo" type="number" id="BenifaccNo"
				placeholder="Enter AccountNo">
		</div>
		<br>
		<div class="row">
			<label for="tranferAmount">Enter Amount</label> <input type="number"
				placeholder="Enter Fund to Transfer" id="transactFund">
		</div>
		<br>
		<div>
			<a id="message"></a>
		</div>
		<br>
		<div>
			<button id="transactbtn" class="userTrasactBtn">Transact</button>
			<button id="clearfeild1" class="userTrasactBtn">Clear</button>
		</div>
	</div>

</body>
</html>