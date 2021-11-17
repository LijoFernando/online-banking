<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en-us">
<head>
<meta charset="UTF-8">
<title>Account Form</title>
<link rel="stylesheet" href="./responsiveRegistration.css">
<style type="text/css">
* {
	box-sizing: border-box;
}

input[type=text], input[type=number], input[type=select], input[type=tel]
	{
	width: 20%;
	padding: 12px;
	border: 1px solid rgb(168, 166, 166);
	border-radius: 4px;
	resize: vertical;
}

h1 {
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

input[type=button] {
	background-color: #4CAF50;
	color: white;
	padding: 12px 10px;
	border-radius: 4px;
	float: left;
}

input[type=button]:hover {
	background-color: #32a336;
}

.container {
	border-radius: 5px;
	background-color: #f2f2f2;
	padding: 20px;
}

.col-10 {
	float: left;
	width: 10%;
	margin-top: 6px;
}

.col-90 {
	float: left;
	width: 90%;
	margin-top: 6px;
}

.row:after {
	content: "";
	display: table; 
	clear: both; 
}
</style>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script type="text/javascript">
$(documnet).ready(function(){
	
});
</script>
</head>

<body>
	<h1>Account Detail Form</h1>


	<form action="${pageContext.request.contextPath }/welcome?">
		<div class="container">
			<div class="row">

				<div class="col-10">
					<label for="mobile">Customer ID:</label>
				</div>
				<div class="col-90">
					<input type="text" id="cusId" name="cusId"
						placeholder="Enter your customer ID">
				</div>
			</div>
			<div class="row">
				<div class="col-10">
					<label for="mobile">Account Number:</label>
				</div>
				<div class="col-90">
					<input type="tel" id="accno" name="accno"
						placeholder="only numbers are allowed">
				</div>
			</div>
			<div class="row">
				<div class="col-10">
					<label for="balance">Account Balance:</label>
				</div>
				<div class="col-90">
					<input type="tel" id="accBalance" name="accBalance"
						placeholder="Account Balance">
				</div>
			</div>

			<div class="row">
				<div class="col-10">
					<label for="city">Branch Location:</label>
				</div>
				<div class="col-90">
					<input type="text" id="city" name="city"
						placeholder="Bank Branch Location">
				</div>
			</div>

			<div class="row">
				<!--  <input type="submit" value="Submit" onclick="SaveStudentDetails()"> -->
				<button type="submit" name="name" value="insertaccount">Submit</button>
				<button type="button" value="Back" onclick="history.back()">Back</button>
			</div>
		</div>
	</form>

</body>
</html>