<%@page import="com.myWeb.model.pojo.Customer"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>CustomerPage</title>
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
	$(document).ready(function() {
		$("#deletebtn").click(function() {
			var $boxes = $('input[name=selectcusid]:checked');
			var length = $boxes.length;
			if (length == 0) {
				$('p[id=status]').text("Select Checkbox To Delete Record");
				return false;
			} else {
				$('#form1').attr('method', 'get');
				$('p[id=status]').text("Record Deleted SuccessFully");
			}
		});
		$("#activebtn").click(function() {
			var $activeBoxes = $('input[name=activatecusid]:checked');
			var length = $activeBoxes.length;
			if (length == 0) {
				$('p[id=status]').text("Select Checkbox To ActiveCustomer");
				return false;
			} else {
				$('p[id=status]').text("Customer Active SuccessFully");
			}

		});

	});
</script>
</head>
<body>

	<div class="topnav">
		<h1>Customer Information Page</h1>
	</div>
	<%@page import="java.util.*"%>
	<div class="row">
		<form id="form1" action="${pageContext.request.contextPath}/welcome?"
			method="post">
			<div class="column">
				<div>
					<h1>Active Customer List</h1>
					<div style="align-content: center">

						<button class="button" name="name" value="createCustomer">Add
							Customer</button>
						<button class="button" id="deletebtn" name="name"
							value="deletecustomer">Delete</button>
						<input type="button" class="button" name="cancel_button"
							value="Back" onclick="history.back()"></input>
					</div>
					<table>
						<tr>
							<td>Customer ID</td>
							<td>UserName</td>
							<td>Name</td>
							<td>Date Of Birth</td>
							<td>Location</td>
							<td>Select</td>

						</tr>

						<c:forEach items="${customer}" var="entry">
							<tr>
								<td>${entry.cusID}</td>
								<td>${entry.userName}</td>
								<td>${entry.name}</td>
								<td>${entry.dofBirth}</td>
								<td>${entry.location}</td>
								<td><input type="checkbox" name="selectcusid"
									value="${entry.cusID}"></td>
							</tr>

						</c:forEach>
					</table>
				</div>


				<p id="status" style="font-size: x-large; color: red;">
				<p>
			</div>
		</form>

		<div class="column">
			<form id="form2" action="${pageContext.request.contextPath}/welcome?"
				method="post">
				<div>
					<div class="row">
						<h1>InActive Customer List</h1>
						<button id="activebtn" class="button" name="name"
							value="activecustomer">Activate</button>
					</div>

					<table>
						<tr>
							<td>Customer ID</td>
							<td>UserName</td>
							<td>Name</td>
							<td>Date Of Birth</td>
							<td>Location</td>
							<td>Click</td>

						</tr>

						<c:forEach items="${inactiveCustomer}" var="entry">
							<tr>
								<td>${entry.cusID}</td>
								<td>${entry.userName}</td>
								<td>${entry.name}</td>
								<td>${entry.dofBirth}</td>
								<td>${entry.location}</td>
								<td><input type="checkbox" name="activatecusid"
									value="${entry.cusID}"></td>
							</tr>

						</c:forEach>
					</table>
				</div>

			</form>
		</div>
	</div>


</body>
</html>