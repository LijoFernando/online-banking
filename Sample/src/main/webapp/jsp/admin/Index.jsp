<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
if (session.getAttribute("name") == null) {
	response.sendRedirect(request.getContextPath() + "SignInPage.jsp");
}
%>
<!DOCTYPE html>
<html>
<head>
<title>This is Index Page</title>
<style>
body {
	background: #EEE;
	overflow-x: hidden;
}

.topnav {
	background-color: #333;
	overflow: hidden;
	color: #f2f2f2;
	padding: 14px 16px;
	font-size: 17px;
	text-align: center;
}

ul {
	list-style-type: none;
	margin: 40px;
	padding: 50px;
	width: 200px;
	text-align: center;
}

li a {
	background-color: navy;
	display: block;
	color: white;
	width: 200px;
	font-family: serif;
	font-size: large;
	padding: 8px 16px;
	text-decoration: none;
	display: block;
	margin: 2px;
	border-color: black;
	color: white;
}

li a:hover {
	background-color: black;
}
}
</style>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script type="text/javascript">
	$(document).on('click', "#logout", function() {
		alert("clicked");

	});
</script>

</head>

<body>
	<div class="topnav">
		<h1>WELCOME TO INDEX PAGE</h1>
		<h3>
			Hai,
			<%=session.getAttribute("name")%>
		</h3>
		<form action="${pageContext.request.contextPath }/logoutservlet"
			method="POST"> <input type="submit" id="logout" value="logout">
		</form>

	</div>
	<div class="container">
		<nav>
			<ul>
				<li><a href="#"> <i class="list"></i> <strong>Home</strong></a></li>
				<li><a
					href="${pageContext.request.contextPath }/welcome?name=customer">
						<i class="list"></i> <strong>Customers</strong>
				</a></li>
				<li><a
					href="${pageContext.request.contextPath }/welcome?name=Account">
						<i class="list"></i> <strong>Accounts</strong>
				</a></li>
				<li><a
					href="${pageContext.request.contextPath }/welcome?name=transact">
						<i class="list"></i> <strong>Transaction</strong>
				</a></li>
				<li><a
					href="${pageContext.request.contextPath }/welcome?name=addadminpage">
						<i class="list"></i> <strong>Add Admin</strong>
				</a></li>

			</ul>
		</nav>
	</div>
</body>
</html>
