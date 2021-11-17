<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<title>The Login Form</title>
<style>
* {
	margin: 0;
	padding: 0;
	box-sizing: border-box;
}

html {
	height: 100%;
}

body {
	font-family: 'Segoe UI', sans-serif;;
	font-size: 1rem;
	line-height: 1.6;
	height: 150%;
}

.wrap {
	width: 100%;
	height: 100%;
	display: flex;
	justify-content: center;
	align-items: center;
	background: #fafafa;
}

.login-form {
	width: 500px;
	margin: 0 auto;
	border: 1px solid #ddd;
	padding: 2rem;
	background: #ffffff;
}

.form-input {
	background: #fafafa;
	border-bottom-color: red;
	border: 1px solid #eeeeee;
	padding: 12px;
	width: 100%;
}

.password-input {
	background: white;
	border: 1.4px solid #eeeeee;
	padding: 14px;
	width: 100%;
}

.blood-input {
	background: #fafafa;
	border: 1px solid #eeeeee;
	padding: 12px;
	width: 20%;
}

.form-group {
	margin-bottom: 1rem;
	border-top-color: red;
}

.form-button {
	background: #69d2e7;
	border: 1px solid #ddd;
	color: #ffffff;
	padding: 10px;
	width: 100%;
	text-transform: uppercase;
}

.form-button:hover {
	background: green;
}

.form-header {
	text-align: center;
	margin-bottom: 2rem;
}

.form-footer {
	text-align: center;
}
</style>
</head>
<body>
	<div class="wrap" style="background-color: tomato">

		<form class="login-form" action="welcome" method="post">
			<div class="form-header">
				<h3>Registration Form</h3>
				<p>Submit your Details</p>
			</div>
			<!--UserName Input-->
			<div class="form-group">
				<label for="username">User Name:</label><input type="text"
					name="username" class="form-input" placeholder="Username" required>
			</div>
			<fieldset class="password-input">
				<legend>Set Password</legend>
				<!--Password Input-->
				<div class="form-group">
					<label for="password">Password:</label><input type="password"
						class="form-input" placeholder="Password" required>
				</div>
				<!--Confirm Password Input-->
				<div class="form-group">
					<label for="cnf-Password">Confirm-Password:</label><input
						type="password" name="passwd" class="form-input"
						placeholder="Confirm password" required>
				</div>
			</fieldset>
			<!--DateofBirth Input-->
			<div class="form-group">
				<label for="DoB">Date Of Birth:</label><input type="date"
					class="form-input" placeholder="date" required>
			</div>
			<!--Gender Input-->
			<div class="form-group">
				<label for="Gender">Gender</label> <input type="radio" id="male"
					name="gender" value="Male"> <label for="male">Male</label>
				<input type="radio" id="female" name="gender" value="Female">
				<label for="female">Female</label> <label for="blood">Blood
					Group</label> <select name="blood" class="blood-input" id="blood" required><option
						value="o+ve">O+ve</option>
					<option value="b+ve">B+ve</option>
					<option value="a1+ve">A1+ve</option></select>
			</div>
			<!--Country Input-->
			<div class="form-group">
				<label for="country">Choose a Country:</label><select name="country"
					class="form-input" id="country" required>
					<option value="india">INDIA</option>
					<option value="canda">CANADA</option>
					<option value="usa">USA</option>
					<option value="germany">GERMANY</option>
				</select>
			</div>
			<!--Commment Input-->
			<div class="form-group">
				<label for="comment">Comment:</label>
				<textarea id="comment" name="comment" rows="4" cols="50" required>  </textarea>
			</div>
			<!--link Input-->
			<a type="link" href="_blank">Read user Aggrement </a>
			<!--Login Button-->
			<div class="form-group">
				<button class="form-button" type="submit">Login</button>
			</div>

		</form>
	</div>
	<!--/.wrap-->
</body>
</html>
