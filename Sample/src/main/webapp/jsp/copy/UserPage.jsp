<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<title>UserPage</title>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<style>
a {
	text-align: center;
}

.header {
	background-color: black;
	align-self: center;
	text-align: center;
	padding-top: 20px;
	padding-bottom: 20px;
}

ul, button {
	display: block;
	background-color: gray;
	width: 15%;
	list-style: none;
	padding-left: 0px;
}

li, button {
	font-family: serif;
	font-size: large;
	padding-top: 20px;
	padding-bottom: 20px;
	padding-top: 20px;
	text-align: center;
}

li:hover, button:hover {
	background-color: black;
	color: white;
	display: list-item;
}

.container1 {
	display: inline;
}
</style>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script type="text/javascript">
	$(document).on('click', '#profile', function() {
		//alert("profile call");
		var authId = $("#authId").val();
		alert(uname);
	});
</script>
</head>
<body style="background: #e6e6e6; color: black;">

	<div class="header">
		<h2>
			<a style="color: white;">MAIN PAGE OF USER</a>
		</h2>
		<form action="${pageContext.request.contextPath }/logoutservlet"
			method="POST">
			<input type="submit" id="logout" value="logout">
		</form>
		<input type="hidden" id="authId"
			value="<%=session.getAttribute("authId")%>">
	</div>
	<div>
		<h3>
			<a>My Session ID: <%=session.getId()%></a> <a>My AuthID: <%=session.getAttribute("authId")%></a>
		</h3>
	</div>
	<div class="container1">
		<ul>
			<li value="Home">Home</li>
			<li id="profile" value="userprofile">Profile</li>
			<li id="transact-li" value="transaction">Transactions</li>
			<li id="logout" value="logout">Logout</li>
		</ul>

	</div>

</body>
</html>