<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<!-- HEADERS NEEDED TO PREVENT BACK BUTTON ON LOGOUT. DO NOT REMOVE ME! -->
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="Sat, 01 Dec 2001 00:00:00 GMT">
<script src="${pageContext.servletContext.contextPath}/resources/js/validatePassword.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-2.2.4.min.js" integrity="sha256-BbhdlvQf/xTY9gja0Dq3HiwQF8LaCRTXxZKRutelT44=" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
<link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro" rel="stylesheet">
<link href="resources/css/main.css" rel="stylesheet">
<title>Change Password</title>
</head>
<body>
<jsp:include page="navbar.jsp"></jsp:include>
<div class="container page-content">
	<div>
		<div >
			<h3>Update Password</h3> <br />
			<form:form action="updatePassword.do" method="post" commandName="updatePassword">
			<table>
			<tr>
				<td>
					Old Password:&nbsp;&nbsp;&nbsp;
				</td>
				<td> 
					<form:hidden path="newUser" val="${user.newUser}"/>
					<form:password path="oldPassword" name="oldPassword" id="oldPassword" class="form-control" required />
				</td>
			</tr>
			<tr>
				<td>
					New Password:&nbsp;&nbsp;&nbsp;
				</td>
				<td>
					<form:password path="newPassword" name="newPassword" id="newPassword" class="form-control" required/>
				</td>
			</tr>
			<tr>
				<td>
					Confirm Password:&nbsp;&nbsp;&nbsp;
				</td>
				<td> 
					<form:password path="confirmPassword" name="confirmPassword" 
					class="form-control" id="confirmPassword" required
					onkeyup="validatePassword()"/> <span id="confirmMessage" />
				</td>
			</tr>
			<tr>
				<td colspan=2>
					<input  type="submit" 
							name="changePassword" 
							class="btn btn-primary form-control" 
							id="changePassword" 
							value="Update Password" />
				</td>
			</tr>
			</table>
			</form:form>
		</div>
	</div>
</div>
<jsp:include page="footer.jsp"></jsp:include>
</body>
<script type="text/javascript">
$(document).ready(function(){
	$(".fileUploadButton").click(function(e){
		var data = new FormData($("#fileChooser")[0].files);
		
		data.append("profilePicture",$("#fileChooser")[0].files[0]);
		$.ajax({
			url: "uploadProfilePicture",
			data: data,
			contentType: false,
			processData: false,
			type: "POST",
			cache: false,
			success: function(response){
				console.log(response);
			}		
		});
		
		e.preventDefault();
	});

});

$(document).ready(function(){
	$("#confirmPassword").keyup(function(){
		var newPass = $("#newPassword").val();
		var conPass = $("#confirmPassword").val();
		
		if(newPass !== conPass){
			
			$("#confirmMessage").text("Passwords do not match.");
			$("#confirmMessage").css("color", "red");
		}
		
		else{
			
			$("#confirmMessage").text("");
			$("#confirmMessage").css("color", "black");
			
		}
		
			
	});

})



</script>
</html>