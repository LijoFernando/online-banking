<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Account Page</title>
<style>
*
.topnav {
	color: #f2f2f2;
	padding: 14px 16px;
	font-size: 17px;
	text-align: center;
	background-color: #333;
	overflow: hidden;
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
	background-color: #008CBA; . column { float : left;
	width: 50%;
}

/* Clear floats after the columns */
.row:after {
	content: "";
	display: table;
	clear: both;
}
}
</style>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		//deletebutton
		$("#deletebtn").click(function() {
			var $boxes = $('input[name=selectacc]:checked');
			var length = $boxes.length;
			if (length == 0) {
				$("#message").html("SELECT Record to Delete");
				return false;
			} else {
				$('#accountListForm').attr('method', 'get');
				$("#message").html("Record Added Successfully");
				return true;
			}
		});

		//addAccount
		$("#addbtn").click(function() {
			$('#accountListForm').attr('method', 'post');
			$("#message").html("Record Added Successfully");
			return true;
		});
	});
</script>
</head>
<body>
	<div class="topnav">
		<h1>Account Info Page</h1>
	</div>
	<%@page import="java.util.*"%>

	<div class="row">
		<div class="column" role="table" aria-label="Students"
			aria-describedby="students_table_desc">
			<form id="accountListForm"
				action="${pageContext.request.contextPath }/welcome?" method="post" >
				<table>
					<tr>
						<td>Customer ID</td>
						<td>Account No</td>
						<td>Balance</td>
						<td>Account Branch</td>
						<!-- <td>Select</td> -->
					</tr>

					<c:forEach items="${account}" var="list">
						<tr>
							<td>${list.cusId}</td>
							<td>${list.accNo}</td>
							<td>${list.accBalance}</td>
							<td>${list.accBranch}</td>
							<%-- <td><input type="checkbox" name="selectacc"
								value="${list.cusId}"></td> --%>
						</tr>

					</c:forEach>
				</table>
				<button class="button" id="addbtn" name="name" value="addAccount">Add
					Account</button>
				<!-- <button class="button" id="deletebtn" name="name" value="deleteacc">delete</button> -->
				<input type="button" class="button" value="Back"
					onclick="history.back()"></input>
			</form>
			<div>
				<a id="message"></a>
			</div>
		</div>
	</div>
</body>
</html>