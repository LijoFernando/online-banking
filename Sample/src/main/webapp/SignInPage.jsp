<%@ page language="java" contentType="text/html; charset=ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<title>Login Page</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<style type="text/css">
.top-bg {
	height: 250px;
	background-color: #1e5799;
	overflow: auto;
	text-align: center;
	text-decoration: underline;
	font-family: sans-serif;
	font-size: 30px;
	color: white;
}

.container {
	height: 450px;
	background-color: #eaeae1;
}

.signin-title {
	color: #1e5799;
	margin-top: 30px;
	text-align: center;
	text-decoration: underline;
}

.signin-feild {
	width: 220px;
	height: 35px;
	font-family: monospace;
	font-size: large;
	margin-left: 20%;
	margin-top: 8%;
	border: thin;
}

.bank-logo {
	height: 75px;
}

.login-container {
	top: 20%;
	height: 420px;
	width: 350px;
	border-radius: 10px;
	margin-left: 37%;
	background-color: white;
	position: absolute;
	justify-content: center;
	z-index: 3;
}

#showpassword {
	height: auto;
	width: auto;
	margin-top: 2%;
	height: auto;
}

#showpassword-lbl {
	font-family: sans-serif;
	font-size: smaller;
	color: #1e5799;
}

.sign-txtbx:focus {
	outline: 2px solid #1e5799;
}

#login-btn {
	border-radius: 3px;
	background-color: #1e5799;
	color: white;
}

#login-btn:hover {
	background-color: #113155;
	color: white;
}
?
</style>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script type="text/javascript">
	function validInput(username, password) {
		if (username != "" && password != "") {
			return true;
		}
		return false;
	}
	$(document).on('click', '#showpassword', function() {
		if ($(this).prop('checked') == true) {
			$('#password').attr('type', 'text');
		}if ($(this).prop('checked') == false) {
			$('#password').attr('type', 'password');
		}
	});
</script>
</head>
<body>
	<div class="top-bg">
		<h3>RBL BANK</h3>
	</div>


	<div class="container">
		<div class="login-container">
			<div class="signin-title">
				<img class="bank-logo" alt=""
					src="https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fwww.freeiconspng.com%2Fuploads%2Fbank-icon-9.png&f=1&nofb=1">
				<h2>
					<a>Sign in</a>
				</h2>
			</div>


			<form action="${pageContext.request.contextPath }/loginservlet"
				method="POST">

				<input id="username" type="text" name="username"
					class="signin-feild sign-txtbx" placeholder="Username" value="">
				<input id="password" type="password" name="password"
					class="signin-feild sign-txtbx" placeholder="Password"><a>${message}</a>
				<br> <input type="checkbox" id="showpassword"
					class="signin-feild "><label for="showpassword"
					id="showpassword-lbl">Show Password</label> <input id="login-btn"
					type="submit" value="Login" class="signin-feild">
			</form>
		</div>
	</div>



</body>
</html>