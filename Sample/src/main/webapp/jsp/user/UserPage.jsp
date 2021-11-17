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
.header {
	background-color: black;
	padding: 7px;
}

#onlinebankingheader {
	font-size: 25px;
	font-family: sans-serif;
	text-transform: uppercase;
	text-decoration: underline;
	display: inline;
	margin-left: 35%;
}

#logout-img {
	height: 100px;
	width: 100px;
	padding: 10px;
	overflow: hidden;
	font-size: small;
	overflow: hidden;
	font-size: small;
	margin: -40px -70px -40px -10px;
	width: 100px;
	font-size: small;
}

#banklogo {
	height: 50px;
	width: 50px;
	border-radius: 8px;

}

.menu-btn {
	display: inline;
	border: none;
	background-color: white;
	width: 10%;
	color: black;
	font-family: serif;
	font-size: medium;
	padding-top: 10px;
	padding-bottom: 10px;
}

.logout-btn {
	background-color: white;
	width: 10%;
	color: black;
	padding: 10px;
	position: fixed;
}

.logout-btn:hover {
	background-color: red;
	color: white;
}

.menu-btn:hover {
	background-color: black;
	color: white;
	display: inline;
}

.container1 {
	background-color: white;
}

form a {
	margin-left: 500px;
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
	$(document)
			.on(
					'click',
					'#logout',
					function() {
						$
								.ajax({
									url : "${pageContext.request.contextPath }/logoutservlet",
									type : "POST",
									success : function() {
										window.location.href = "${pageContext.request.contextPath }/SignInPage.jsp";
									}
								});
					});
</script>
</head>
<body style="background: #ccddff; color: black;">

	<div class="header">
		<a style="color: white"><img id="banklogo" alt="rlb-logo"
			src=".\images\rlbbanklogo.png">RLB</a> <a id="onlinebankingheader"
			style="color: white;">Online Banking</a>
	</div>

	<div class="container1">
		<form action="${pageContext.request.contextPath }/welcome?"
			method="POST">
			<!-- <button name="name" value="userHome">Home</button> -->

			<button class="menu-btn" name="name" value="getProfileInfo">Profile</button>
			<button id="accountInfo" class="menu-btn" name="name"
				value="userAccountInfo">Account Info</button>
			<button class="menu-btn" name="name" value="banktransaction">Bank
				Transfer</button>
			<button class="menu-btn" name="name" value="userCashTransaction">Cash
				Transfer</button>
			<button class="menu-btn" name="name" value="userTransactionHistory">
				Transactions</button>
			<button class="menu-btn" name="name" value="addBeneficiaryPage">Beneficiary</button>
			<a id="logout" class="logout-btn"> <img id="logout-img"
				src="https://svg-clipart.com/clipart/button/7A6q0oB-logoutbutton-clipart.png">
			</a>
		</form>

		<%-- <form action="${pageContext.request.contextPath }/logoutservlet"
			method="POST">
			<input type="submit" id="logout" class="logout-btn" value="logout">
		</form> --%>
	</div>
	<%-- <div>
		<h3>
			<a>My Session ID: <%=session.getId()%></a>
		</h3>
	</div> --%>


</body>
</html>