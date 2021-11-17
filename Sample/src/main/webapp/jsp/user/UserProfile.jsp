<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<style type="text/css">
h2 {
	text-align: center;
}

.profileInfo-container {
	position: fixed;
	text-align: left;
	padding: 40px;
	color: blue;
	background-color: white;
	margin-left: 20%;
}

.info-item {
	padding: 10px;
}

.chngpass-header {
	text-align: center;
	text-decoration: underline;
	font-family: sans-serif;
	font-size: larger;
}

#changePassword-items-container {
	display: list-item;
	list-style: none;
}

#changePassword-container {
	position: fixed;
	padding: 20px;
	margin-left: 45%;
	background-color: white;
	visibility: hidden;
	width: auto;
	height: 350px;
}

.editprof-items {
	display: flex;
	justify-content: space-between;
	flex-wrap: wrap;
	margin-left: 12%;
}

#editprofile-msg-container {
	background-color:
}

#edit-profile-conatiner {
	justify-content: center;
	width: 430px;
	height: 350px;
	padding: 20px;
	margin-left: 45%;
	background-color: white;
	visibility: hidden;
	position: fixed;
}

#change-btn-container {
	display: flex;
	justify-content: space-around;
}

#editprof-btn-container {
	display: flex;
	justify-content: center;
}

#chngepass-btn, #cancel-chngepass-btn {
	position: relative;
	width: auto;
	background-color: #ccddff;
	display: inline;
	padding: 5px;
	font-size: smaller;
}

#chngepass-btn:hover, #cancel-chngepass-btn:hover, .profile-button:hover
	{
	background-color: #6699ff;
	color: white;
}

.pass-item {
	padding-left: 10px;
	padding-top: 20px;
}

.profile-button, .editprof-button {
	padding: 5px;
	width: auto;
	background-color: #ccddff;
	font-size: smaller;
	margin-left: 10px;
}

label {
	font-family: sans-serif;
	font-size: large;
	padding-right: 0px;
}

input {
	margin-left: 20px;
}

.prf-key {
	color: black;
}
</style>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script type="text/javascript">
	$(document).on('click', '#cancel-chngepass-btn', function() {
		//$('#changePassword-container').attr('hidden','true');
		$('#changePassword-container').attr('style', 'visibility : hidden');
	});

	$(document).on('click', '#chagepass-container-btn', function() {
		$('#edit-profile-conatiner').attr('style', 'visibility : hidden');
		$('#changePassword-container').attr('style', 'visibility : visible');
		clearFeild();
		$('#passwordMatch').text('');
	});

	$(document).on('click', '#editprofile-container-btn', function() {
		$('#edit-profile-conatiner').attr('style', 'visibility : visible');
		$('#changePassword-container').attr('style', 'visibility : hidden');
		clearFeild();
		$('#passwordMatch').text('');
		loadProfileToeditFeild();
		loadRestictDate();
	});
	function loadRestictDate() {
		var now = new Date(); //getting current date
		var currentY = now.getFullYear();
		var currentM = now.getMonth();
		var day = now.getDate();

		var now = new Date(currentY - 18, currentM, day);
		var updatedcurrentY = now.getFullYear();

		var dateString = updatedcurrentY + "-" + currentM + "-" + day;

		console.log(dateString);

		$('#dob').attr('max', dateString);

	}

	function loadProfileToeditFeild() {
		var fullname = $('#urFullName').text();
		var dob = $('#urDob').text();
		var address = $('#urAddress').text();
		//console.log(fullname+" "+dob+" "+address);
		$('#fullname').val(fullname);
		$('#dob').val(dob);
		$('#address').val(address);

	}

	$(document)
			.on(
					'keyup',
					'#newPass',
					function() {
						console.log("changed");
						var newPassLength = $("#newPass").val().length;
						if (newPassLength < 8) {
							$('#passwordLength')
									.text(
											" New Password Length Must Be Minimumm 8 character")
									.css('color', 'red');
							$("#newPass").focus();
						} else {
							$('#passwordLength').text("");
						}
					});
	$(document)
			.on(
					'keyup',
					'#cnfPass',
					function() {
						console.log("changed");
						var cnfPassLength = $("#cnfPass").val().length;
						if (cnfPassLength < 8) {
							$('#passwordLength')
									.text(
											" Confirm Password Length Must Be Minimumm 8 character")
									.css('color', 'red');
						} else {
							$('#passwordLength').text("");
						}
					});

	$(document)
			.on(
					'keyup',
					'#oldPass',
					function() {
						console.log("changed");
						//var oldPassLength = $("#oldPass").val().length;
						/* if (oldPassLength < 8) {
							$('#passwordLength').text("Old Password Length Must Be Minimumm 8 character")
									.css('color', 'red');
						} else {
							$('#passwordLength').text("");
						} */
						var isSame = newOldPasswordNotSame();
						if (isSame) {
							$('#notSame')
									.text(
											"Old Password and New Password are must be Different")
									.css('color', 'red');
						} else {
							$('#notSame').text("");
						}

					});

	$(document).on('click', '#chngepass-btn', function() {

		var newPassVal = $("#newPass").val();
		var cnfPassVal = $("#cnfPass").val();
		var oldPassVal = $("#oldPass").val();

		//null check
		validatenull(newPassVal, cnfPassVal, oldPassVal);

	});

	function validatenull(newPassVal, cnfPassVal, oldPassVal) {

		var newPassLength = newPassVal.length;
		var cnfPassLength = cnfPassVal.length;
		var oldPassLength = oldPassVal.length;
		var isSame = newOldPasswordNotSame();
		if (newPassVal == '' || newPassLength < 8) {
			$('#nullMessage').text("Enter New Password Properly");
		} else if (cnfPassVal == '' || cnfPassLength < 8) {
			$('#nullMessage').text("Enter Confirm Password Properly");
		} else if (oldPassVal == ''/* ||  oldPassLength < 8 */) {
			$('#nullMessage').text("Enter Current Password Properly");
		} else if (isSame) {
			$('#notSame').text(
					"Old Password and New Password are must be Different");
		} else if (newPassLength >= 8 && cnfPassLength >= 8 /* &&  oldPassLength >= 8 */) {
			console.log("validate correct");
			changePassword(cnfPassVal, oldPassVal);
		}

	}

	$(document).on(
			'blur',
			'.blurpass',
			function() {
				var newPassVal = $("#newPass").val();
				var cnfPassVal = $("#cnfPass").val();
				if (newPassVal != cnfPassVal && cnfPassVal.length >= 8) {
					$('#passwordMatch').text(
							"Password Mismatch Enter Correctly!!").css('color',
							'red');
				} else if (newPassVal == cnfPassVal) {
					$('#passwordMatch').text("Password Matched!!").css('color',
							'green');
				}
			});

	function changePassword(cnfPassVal, oldPassVal) {
		$.ajax({
			url : "welcome",
			type : "POST",
			data : {
				name : "userPasswordChange",
				newPassword : cnfPassVal,
				oldPassword : oldPassVal,
			},
			success : function(response) {
				console.log(response);
				responseStatus(response);
				clearFeild();
			}
		});
	}

	function responseStatus(response) {
		if (response == 1) {
			$("#passwordMatch").text("Password Changed Successfully");
		} else if (response == 808) {
			$("#passwordMatch").text(
					"New Password and Old Password must be different");
		}
	}

	function clearFeild() {
		$("#newPass").val("");
		$("#cnfPass").val("");
		$("#oldPass").val("");
	}

	function newOldPasswordNotSame() {
		var newPass = $("#cnfPass").val();
		var oldPass = $("#oldPass").val();
		if (newPass == oldPass) {
			return true;
		}
		return false;
	}

	$(document).on('click', '#editedsubmit-btn', function() {

		var fname = $('#fullname').val();
		var dob = $('#dob').val();
		var userAddress = $('#address').val();

		console.log("submited" + fname + "," + dob + "," + userAddress);

		if (isNotSame(fname, dob, userAddress) && isnotNull(fname, dob, userAddress)) {
			$.ajax({
				url : "welcome",
				type : "POST",
				data : {
					name : "submitProfileChanges",
					fulName : fname,
					dofB : dob,
					address : userAddress,
				},
				success : function(message) {
					console.log(message);
					profileFeid(fname, dob, userAddress);
					$('#profilemsg').text(message);

				}
			});
		}
	});

	function profileFeid(fname, dob, userAddress) {
		var fullname = $('#urFullName').text(fname);
		var doB = $('#urDob').text(dob);
		var address = $('#urAddress').text(userAddress);
	}
	function isnotNull(fname, dob, userAddress) {
		if (fname != "" && dob != "" && userAddress != "") {
			return true;
		}
		else{
		$('#profilemsg').text("Fill all the Feilds");
		return false;
		}
	}
	function isNotSame(fname, dob, userAddress) {
		var fullname = $('#urFullName').text();
		var doB = $('#urDob').text();
		var address = $('#urAddress').text();
		//console.log(fname+" == "+ fullname+","+ doB +"=="+ dob +","+ userAddress +" "+ address);
		if (fname == fullname && doB == dob && userAddress == address) {
			$('#profilemsg').text("Change the feild Before u Submit");
			return false;
		} else if (!isValidteDoB(dob)) {
			$('#profilemsg').text("Enter Valid Date of Birth: Minimum age is 18");
			return false;
		}
		return true;
	}
	
	function isValidteDoB(dob) {
		var maxDateVal = $('#dob').attr('max');
		var maxDate = new Date(maxDateVal);
		var selectDate = new Date(dob);
		console.log(maxDate + " , " + selectDate);
		if (selectDate <= maxDate) {
			return true;
		}
		return false;
	}
</script>
</head>
<body>
	<jsp:include page="UserPage.jsp" />


	<div class="chngpass-header">
		<h2>MY PROFILE</h2>
	</div>

	<div class="profileInfo-container">

		<!-- 	private int authId;
	private String userName;
	private String userPassword;
	private String userType;
	private String email;
	private Integer cusID;
	private String name;
	private String dofBirth;
	private String location;
	private String CusStatus; -->
		<div class="info-item">
			<label class="prf-key">User Name:</label> <label id="urUserName"
				class="prf-val">${profileInfo.userName}</label>
		</div>
		<div class="info-item">
			<label class="prf-key">Name:</label> <label id="urFullName"
				class="prf-val">${profileInfo.name}</label>
		</div>
		<div class="info-item">
			<label class="prf-key">Date of Birth:</label> <label id="urDob"
				class="prf-val">${profileInfo.dofBirth}</label>
		</div>
		<div class="info-item">
			<label class="prf-key">Address:</label> <label id="urAddress"
				class="prf-val">${profileInfo.location}</label>
		</div>
		<div>
			<button class="profile-button" id="chagepass-container-btn">Change
				Password</button>
			<button class="profile-button" id="editprofile-container-btn">Edit
				Profile</button>
			<!-- 		<button class="profile-button" name="name" value="userHome">Home</button> -->
		</div>
	</div>


	<!--   //change Password BLock -->
	<div id="changePassword-container">
		<div class="chngpass-header">
			<a>CHANGE PASSWORD</a>
		</div>
		<div id="changePassword-items-container">
			<div class="pass-item">
				<label for="newPass">New Password</label> <input id="newPass"
					class="blurpass" type="password" placeholder="New Password"
					required="required">
			</div>

			<div class="pass-item">
				<label for="cnfPass">Confirm Password</label> <input id="cnfPass"
					class="blurpass" type="password" placeholder="Re-enter Password"
					required="required">
			</div>

			<div class="pass-item">
				<label for="oldPass">Old Password</label> <input id="oldPass"
					type="password" placeholder="Current Password" required="required">
			</div>


		</div>
		<br>
		<div id="change-btn-container">
			<button id="chngepass-btn">Change Password</button>
			<button id="cancel-chngepass-btn">Cancel</button>
		</div>
		<div>
			<a id="passwordMatch"></a><a id="passwordLength"></a> <a
				id="nullMessage"></a> <a id="notSame"></a>
		</div>
	</div>

	<div id="edit-profile-conatiner">
		<div class="chngpass-header">
			<a>Edit Profile</a>
		</div>
		<br>
		<div class="editprof-items">
			<div class="pass-item">
				<label for="fullname">Full Name</label> <input id="fullname"
					class="blurpass" type="text" placeholder="Enter Full Name"
					required="required">
			</div>
			<br>
			<div class="pass-item">
				<label for="dob">Date of Birth</label> <input id="dob"
					class="blurpass" type="date" required="required"
					required="required">
			</div>
			<br>

			<div class="pass-item">
				<label for="address">Address</label> <input id="address" type="text"
					placeholder="Enter New Address" required="required">
			</div>
			<br>
		</div>
		<br>
		<div id="editprof-btn-container">
			<button id="editedsubmit-btn" class="editprof-button">Submit</button>
		</div>
		<div id="editprofile-msg-container">
			<a id="profilemsg"></a>
		</div>
	</div>




</body>
</html>