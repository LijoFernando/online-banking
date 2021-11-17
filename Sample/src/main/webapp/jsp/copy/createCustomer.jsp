<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<title>Add Customer</title>
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.4.1/css/all.css"
	integrity="sha384-5sAR7xN1Nv6T6+dT2mhtzEpVJvfS3NScPQTrOxhwjIuvcA67KV2R5Jz6kr4abQsz"
	crossorigin="anonymous">
<link
	href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700"
	rel="stylesheet">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<style>
html, body {
	min-height: 100%;
}

body, div, form, input, select, p {
	padding: 0;
	margin: 0;
	outline: none;
	font-family: Roboto, Arial, sans-serif;
	font-size: 16px;
	color: #eee;
}

body {
	background-size: cover;
}

h1, h2 {
	text-transform: uppercase;
	font-weight: 400;
}

h2 {
	margin: 0 0 0 8px;
}

.main-block {
	display: flex;
	flex-direction: column;
	justify-content: center;
	align-items: center;
	height: 50%;
	padding-right: 280px;
	padding-left: 280px;
	background: rgba(0, 0, 0, 0);
	padding-left: 280px;
}

form {
	padding: 25px;
}

.fa-graduation-cap {
	font-size: 72px;
}

form {
	background: rgba(0, 0, 0, 1);
}

.title {
	display: flex;
	align-items: center;
	margin-bottom: 20px;
}

.info {
	display: flex;
	flex-direction: column;
}

input, select {
	padding: 5px;
	margin-bottom: 30px;
	background: transparent;
	border: none;
	border-bottom: 1px solid #eee;
}

input::placeholder {
	color: #eee;
}

.btn-item, button {
	padding: 10px 5px;
	margin-top: 20px;
	border-radius: 5px;
	border: none;
	background: #26a9e0;
	text-decoration: none;
	font-size: 15px;
	font-weight: 400;
	color: #fff;
}

.btn-item {
	display: inline-block;
	margin: 20px 5px 0;
	width: 100%;
}

button {
	width: 100%;
}

button:hover, .btn-item:hover {
	background: #85d6de;
}

@media ( min-width : 568px) {
	html, body {
		height: 100%;
	}
	.main-block {
		flex-direction: row;
		height: calc(100% - 50px);
	}
	.left-part, form {
		flex: 1;
		height: auto;
	}
}
</style>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script type="text/javascript">
	$(document)
			.ready(
					function() {

						/* $("#addCustomer").click(function() {
							var name = "";
							name = $('input[name=fname]').val();
							var location = $('input[name=location]').val();
							
						}); */
						$("#username")
								.blur(
										function() {
											var uname = $(
													'input[name=username]')
													.val();
											//alert(uname);
											$
													.ajax({
														url : "welcome",
														method : "POST",
														data : {
															name : "isusernameexist",
															username : uname,
														},
														success : function(data) {
															var value = $(data)
																	.find(
																			'#hiddenstatus')
																	.val();
															if (value != null) {
																if (value == "true") {
																	$(
																			"#username")
																			.focus();
																	$(
																			"#message")
																			.text(
																					"username already Exists");
																} else if (value == "false") {
																	$(
																			"#message")
																			.css(
																					"color",
																					"green")
																			.text(
																					"username Available");
																}
															}
														},

													});
										});

						$('input[type="date"]').blur(function() {

							var inputDate = new Date(this.value);
						});

					});
</script>
</head>
<body>
	<div class="main-block">

		<form action="${pageContext.request.contextPath }/welcome?"
			method="get">
			<div class="title">
				<h2>Add New Customer</h2>

			</div>
			<div class="info">
				<input id="username" type="text" name="username"
					placeholder="user name" required> <input id="password"
					type="password" name="password" placeholder="password" required>
					<input id="email"
					type="text" name="email" placeholder="email: example@gmail.com" required>
				<input id="cusname" type="text" name="fname" placeholder="Full name"
					required> <input id="dob" type="date" name="dob"
					placeholder="Date of Birth" required> <input type="text"
					name="location" placeholder="Location" required>
			</div>

			<button class="btn-item" id="addCustomer" name="name"
				value="insertCustomer">Submit</button>
			<button class="btn-item" type="button" onclick="history.back()">Cancel</button>

		</form>

	</div>
	<div>
		<input type="hidden" id="hiddenstatus" value="${status}"> <a
			id="message" style="color: red;">${message}</a>
	</div>
</body>
</html>