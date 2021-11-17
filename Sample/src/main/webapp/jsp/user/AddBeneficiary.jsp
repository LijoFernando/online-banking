<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add Beneficiary</title>
<style type="text/css">
.benefConatiner {
	background-color: white;
	padding: 50px 50px;
	position: fixed;
	margin-left: 35%;
	margin-top: 50px;
}

body {
	background-color: ccddff;
}

.container-elements {
	text-align: center;
	padding-top: 10px;
}

.beneficiaryHeader {
	text-align: center;
	text-decoration: underline;
	text-transform: uppercase;
	font-family: sans-serif;
}

.content-label {
	padding-left: 0px;
	font-family: serif;
	font-size: large;
	font-family: serif;
}

.content-feild {
	padding: 5px 5px;
	border-radius: 5px;
}

.benef-button {
	padding: 5px 10px;
	width: auto;
	background-color: #ccddff;
	text-decoration: nonee;
	color: black;
}

.benef-button:hover {
	background-color: #6699ff;
	color: white;
}

.benefsidenav {
	width: 130px;
	position: fixed;
	margin-top: 50px;
	left: 50px;
	background: #eee;
	padding: 8px 0;
	border-color: blue;
}

.benefsidenav a {
	padding: 10px 10px 10px;
	text-decoration: none;
	display: flex;
	width: auto;
}

.benefsidenav a:focus {
	color: white;
	background-color: #00cccc;
}

.benefsidenav a:hover {
	color: white;
	background-color: #00cccc;
}

#beneficiaryTableConatiner {
	display: inline-block;
	justify-content: center;
}

#viewBeneficiaryContainer {
	margin-left: 32%;
}

table {
	border-radius: 5px;
	background-color: #000066;
	color: white;
}

table tr td {
	padding-left: 10px;
}
</style>
</head>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$('#AddBenefMenu')[0].click();
		$('#AddBenefMenu').focus();
	});

	$(document).on('click', '#AddBenefMenu', function() {
		$('#viewBeneficiaryContainer').hide(function() {
			emptyFeild();
		});
		$('#addBenefContainer').toggle();
		$("#benefAddState").text("");
		$('#feildValidation').text("");
	});
	$(document).on('click', '#viewBenefMenu', function() {
		$('#addBenefContainer').hide();
		$('#viewBeneficiaryContainer').toggle();
		getBeneficierList();
	});
	$(document).on('click', '#addBenefButton', function() {
		nullCheck("#nickName", "#fullName", "#accNo", "#branchName");
	});

	$(document).on(
			'blur',
			'#nickName',
			function() {
				var feildval = $(this).val();
				var requiredSize = 6;
				var isValidSize = validateLength(this, requiredSize);
				if (isValidSize) {
					$('#feildValidation').text("");
				} else {
					$('#nickName').focus();
					$('#feildValidation').text(
							"Nick Name Must be Minimum 6 character").css(
							'color', 'red');
				}

			});

	$(document).on(
			'blur',
			'#nickName',
			function() {
				var feildval = $(this).val();
				var requiredSize = 6;
				var isValidSize = validateLength(this, 6);
				if (isValidSize) {
					$('#feildValidation').text("");
				} else {
					$('#nickName').focus();
					$('#feildValidation').text(
							"Nick Name Must be Minimum 6 character").css(
							'color', 'red');
				}

			});

	$(document).on(
			'blur',
			'#nickName',
			function() {
				var feildval = $(this).val();
				var isValidSize = validateLength(this, 6);
				if (isValidSize) {
					$('#feildValidation').text("");
				} else {
					$('#nickName').focus();
					$('#feildValidation').text(
							"Nick Name Must be Minimum 6 character").css(
							'color', 'red');
				}

			});

	function validateLength(feild, size) {
		var feildlength = $(feild).val().length;
		if (feildlength < size) {
			return false;
		}
		return true;
	}

	function nullCheck(feild1, feild2, feild3, feild4) {

		var f1val = $(feild1).val();
		var f2val = $(feild2).val();
		var f3val = $(feild3).val();
		var f4val = $(feild4).val();
		if (f1val != "" && f2val != "" && f3val != "" && f4val != "") {
			addBeneficiary(f1val, f2val, f3val, f4val);
			$("#benefAddState").text("");
		} else {
			nullfeildMessage();
		}
	}
	function nullfeildMessage() {
		$("#benefAddState").text("Filled all the feild, ").css('color', 'red');
	}

	function addBeneficiary(feild1, feild2, feild3, feild4) {
		$.ajax({
			url : "welcome",
			type : "POST",
			data : {
				name : "addNewBeneficiary",
				nickName : feild1,
				fullName : feild2,
				accNo : feild3,
				branchName : feild4
			},
			success : function(processState) {
				processStateHandler(processState);
			}
		});
	}

	function processStateHandler(processState) {
		if (processState == 1) {
			$("#benefAddState").text("Invalid Account Number, ").css('color',
					'red');
		} else if (processState == 200) {
			$("#benefAddState").text("Beneficiary Added	Successfully").css(
					'color', 'green');
			emptyFeild();
		}

	}

	function emptyFeild() {
		$("#nickName").val("");
		$("#fullName").val("");
		$("#accNo").val("");
		$("#branchName").val("");
	}

	function getBeneficierList() {
		$.ajax({
			url : "welcome",
			type : "POST",
			data : {
				name : "getUserBeneficiary"
			},
			success : function(response) {
				console.log("success");
				console.log(response);
				appendBenefList(response);
			},
		});
	}

	function appendBenefList(data) {
		var length = data.length;
		if (length == 0) {
			$('#benefList').text("No Beneficiary Record Found");
			$('#beneficiaryTableConatiner').hide();
		} else {
			$('#beneficiaryTableConatiner').show();
			console.log(length);
			$('#benefList').text("");
			$('#beneftable tr:not(:first)').remove();
			var sno = 0;
			$.each(data, function(index, item) {
				sno = sno + 1;
				var eachRow = "<tr><td>" + sno + "</td><td>"
						+ item.benefNickName + "</td><td>" + item.benefFullName
						+ "</td><td>" + item.benefAccNo + "</td><td>"
						+ item.benefBankBranch + "</td></tr>";
				$("#beneftable").append(eachRow);
			})
		}
	}
</script>
<body>
	<jsp:include page="UserPage.jsp" />

	<!-- //side NavBar for Beneficiary  -->
	<div class="benefsidenav">
		<a  id="AddBenefMenu">Add Beneficiary</a> <a
			id="viewBenefMenu">View Beneficiary</a>
	</div>


	<!-- //add beneficiary -->
	<div id="addBenefContainer" class="benefConatiner"
		style="display: none">
		<div id="addBenefHeader" class="beneficiaryHeader">
			<h2>ADD BENEFICIARY</h2>
		</div>
		<div class="container-elements">
			<label for="nickName" class="content-label">Nick Name</label> <input
				id="nickName" type="text" placeholder="Enter Nick Name"
				class="content-feild">

		</div>
		<div class="container-elements">
			<label for="fullName" class="content-label">Account Holder
				Name</label> <input id="fullName" type="text" placeholder="Enter Full Name"
				class="content-feild">

		</div>
		<div class="container-elements">
			<label for="accNo" class="content-label">Account Number</label> <input
				id="accNo" type="text" placeholder="Enter Account Number"
				class="content-feild">

		</div>

		<div class="container-elements">
			<label for="branchName" class="content-label">Branch Name</label> <input
				id="branchName" type="text" placeholder="Enter Branch Name"
				class="content-feild">
		</div>
		<div class="container-elements">
			<button id="addBenefButton" class="benef-button">submit</button>
		</div>
		<div class="container-elements">
			<a id="benefAddState"></a> <a id="feildValidation"></a>
		</div>
	</div>



	<!-- //View Beneficiary -->
	<div id="viewBeneficiaryContainer" class="benefConatiner"
		style="display: none">
		<div id="viewBenefHeader" class="beneficiaryHeader">
			<h3>Beneficiary List</h3>
		</div>
		<div id="beneficiaryTableConatiner">
			<table id="beneftable">
				<tr>
					<td>S.No</td>
					<td>Nick Name</td>
					<td>Full Name</td>
					<td>Account Number</td>
					<td>Branch</td>
				</tr>
			</table>
		</div>
		<div style="text-align: center;">
			<a id="benefList"></a>
		</div>
	</div>
</body>
</html>