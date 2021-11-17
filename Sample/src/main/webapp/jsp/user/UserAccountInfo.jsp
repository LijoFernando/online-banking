<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Account Info</title>
<style type="text/css">
#accountInfoContainer {
	background-color: white;
	padding: 30px;
	width: 40%;
	position: fixed;
	margin-top: 100px;
	margin-left: 450px;
}

#viewAccountInfo {
	background-color: #f2f2f2;
	padding: 20px;
}

#accountInfoheader {
	text-decoration: underline;
	text-align: center;
	text-transform: uppercase;
}

#selectdropdown {
	padding: 10px;
}
</style>
</head>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script type="text/javascript">
	$(document).on('click', '#selectAccNo', function() {
		var selectval = $(this).val();
		if (selectval != "empty") {
			$('#viewAccountInfo').show();
			getAccountInfoData(selectval);
		} else {
			$('#viewAccountInfo').hide();
		}
	});
	
	$(document).ready(function() {
		
		$.ajax({
			url : "welcome",
			type : "POST",
			data : {
				name : "loadAccountList"
			},
			success : function(response) {
				console.log(response);
				loadtodropDown(response);
			}
		});
	});

	function loadtodropDown(data) {
		$.each(data, function(index, item) {
			$('#selectAccNo').append($('<option>').val(item).text(item));
		});
		$("select[id='selectAccNo'] option:eq(1)").attr("selected", "selected");
		$( "#selectAccNo" ).click();
	}

	function getAccountInfoData(selectval) {
		$.ajax({
			url : "welcome",
			type : "POST",
			data : {
				name : "getAccountInfo",
				selectAccNo : selectval
			},
			success : function(response) {
				console.log(response);
				loadAccountInfo(response);
			}
		});
	}

	function loadAccountInfo(data) {
		$("#accountInfoTable").empty();
		var dataSize = data.length;
		if (dataSize != 0) {
			var eachRow ="<tr><td>Account Number:</td><td>"+data.AccNo+"</td></tr><tr><td>Balance:</td><td>Rs."+data.AccBalance+"</td></tr><tr><td>Account Branch:</td><td>"+data.AccBranch+"</td></tr>";
			$("#accountInfoTable").append(eachRow);
		}

	}
</script>
<body>
	<jsp:include page="UserPage.jsp" />
	<div id="accountInfoContainer">
		<div id="selectdropdown">
			<label for="selectAccount">Your Accounts List</label> <select
				for="selectAccount" id="selectAccNo">
				<option value="empty">select One</option>
			</select>
		</div>
		<div id="viewAccountInfo" style="display: none;">
			<div id="accountInfoheader">
				<h3>Account Details</h3>
			</div>
			<table id="accountInfoTable">	
			</table>

		</div>
	</div>


</body>
</html>