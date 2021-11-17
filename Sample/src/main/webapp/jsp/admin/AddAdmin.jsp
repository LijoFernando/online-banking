<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add Admin</title>
<style type="text/css">
.container {
	background-color: gray;
	padding: 40px;
	position: absolute;
	margin-left: 35%;
}

h2 {
	text-align: center;
	background-color: black;
	position: relative;
	color: white;
	padding: 20px;
}

input[type='text'], input[type='password'], input[type='email'], input[type='submit']
	{
	padding: 10px;
	border-radius: 3px;
}

button {
	padding: 15px;
	padding-bottom: 11px;
	border-radius: 3px;
}

#username {
	margin-left: 19px;
}

#password {
	margin-left: 23px;
}

#mail {
	margin-left: 52px;
}

.error {
	background-color: white;
}

label {
	font-family: sans-serif;
}

#addadmin {
	margin-left: 55px;
	font-family: serif;
	font-size: large;
	font-family: serif;
}
</style>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {

		$("#username").blur(function() {
			var usname = $(this).val();
			if (usname == "") {
				$("#unexistmsg").text("Enter Username");
				$("#username").focus();
			} else if (usname != "") {
				$("#message").text("");
				$.ajax({
					url : '/Sample/welcome',
					type : "POST",
					data : {
						name : "checkadminUsername",
						uname : usname,
					},
					success : function(response) {
						console.log(response);
						userNameExistResponse(response);
					}
				});
			}
		});

		//password Coparision Check
		$("#cnfpassword").blur(function() {
			comparePassword();
		});

		$("input[type='submit']").on('click', function() {

		});
		fillfeild();
	});

	//usernmae exists check
	function userNameExistResponse(response) {
		if (response == "true") {
			$("#unexistmsg").text("Username Available");
			$("#unexistmsg").css("color", "Green");

		} else if (response == "false") {
			$("#unexistmsg").text("Username AlreadyTaken");
			$("#unexistmsg").css("color", "red");
			$("input[type='submit']").attr("disabled", "disabled");
			$("#username").focus();
			return false;
		}
	}

	function comparePassword() {
		var pass = $("#password").val();
		var cnfpass = $("#cnfpassword").val();
		if (pass != "" && cnfpass != "") {
			if (pass != cnfpass) {
				$("#password,#cnfpassword").css("border-color", "red");
				$("#passmessage").text("Password MisMatch");
				$("input[type='submit']").attr("disabled", "disabled");
				$("#password").focus();
			} else {
				$("#password,#cnfpassword").css("border-color", "black");
				$("#passmessage").text("");
				$("input[type='submit']").removeAttr("disabled");
			}
		} else {
			$("#passmessage").text("Fill Password or Confirm Password feild ");
		}
	}

	function fillfeild() {
		if ($("#username").val() != "" && $("#password").val() != ""
				&& $("#cnfpassword").val() != "" && $("#mail").val() != "") {

			$("input[type='submit']").removeAttr("disabled");

		} else {
			$("input[type='submit']").attr("disabled", "disabled");
		}
	}
</script>
</head>
<body>
	<div>
		<h2>
			<a>Add New Admin</a>
		</h2>
	</div>
	<div class="container">
		<form action="${pageContext.request.contextPath }/welcome?"
			method="POST">
			<div>
				<label for="username">Enter UserName</label> <input type="text"
					name="uname" placeholder="User Name" id="username">
			</div>
			<br>
			<div>
				<label for="password">Enter Password</label> <input type="password"
					name="password" placeholder="Enter Password" id="password">
			</div>
			<br>
			<div>
				<label for="cnfpassword">Re-enter Password</label> <input
					type="password" placeholder="Re-enter Password" id="cnfpassword">

			</div>
			<br>
			<div>
				<label for="mail">Enter Email</label> <input type="email"
					name="mail" placeholder="Enter Email" id="mail">
			</div>
			<br>
			<div>
				<input type="submit" name="name" value="addnewAdmin" id="addadmin">
				<button name="name" value="adminHome" id="adminhome-btn">Home</button>
			</div>

		</form>
		
		<div class="error">

			<a id="unexistmsg">${unameexist}</a> <br> <a id="passmessage"></a>
			<br> <a id="message"></a> <a>${submittedStatus}</a>
		</div>

	</div>

</body>
</html>