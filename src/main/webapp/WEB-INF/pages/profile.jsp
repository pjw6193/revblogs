<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page isELIgnored ="false" %>
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
<title>Profile</title>
</head>
<body>
<jsp:include page="navbar.jsp"></jsp:include>
<div class="container page-content">
	<div>
	<h3>Update Profile</h3><br />
		<form:form action="updateUser.do" method="post" commandName="updateUser">
		<table>
		<tr>
			<td>
				Email:&nbsp;&nbsp;&nbsp;
			</td>
			<td>
				<form:input path="email" class="form-control" value="${user.email}"/>
			</td>
		</tr>
		<tr>
			<td>
				First Name:&nbsp;&nbsp;&nbsp; 
			</td>
			<td>
				<form:input path="firstName" class="form-control" value="${user.firstName}"/>
			</td>
		</tr>
		<tr>
			<td>
				Last Name:&nbsp;&nbsp;&nbsp; 
			</td>
			<td>
				<form:input path="lastName" class="form-control" value="${user.lastName}"/>
			</td>
		</tr>
		<tr>
			<td>
				Job Title:&nbsp;&nbsp;&nbsp; 
			</td>
			<td>
				<form:input path="jobTitle" class="form-control" value="${user.jobTitle}"/>
			</td>
		</tr>
		<tr>
			<td>
				LinkedIn URL:&nbsp;&nbsp;&nbsp; 
			</td>
			<td>
				<form:input path="linkedInURL" class="form-control" value="${user.linkedInURL}"/>
			</td>
		</tr>
		<tr>
			<td>
				Description:&nbsp;&nbsp;&nbsp; 
			</td>
			<td>
				<input type="hidden" value="${user.description}" id="userDescription" />
				<form:textarea path="description" class="form-control" id="descriptionTextArea"/>
			</td>
		</tr>
		<tr>
			<td colspan=2>
				<input type="submit" class="btn btn-primary form-control" value="Update Profile" />
			</td>
		</tr>
		
		</table>
		</form:form>
	</div>
	<div>
	<hr>
	<h3>Update Password</h3>
	<br />
	<table>
		<tr>
			<td colspan=2>
				<a href="${pageContext.servletContext.contextPath}/password"><input type="button" value="Update Password" class="btn btn-primary form-control"></input></a>
			</td>
		</tr>
	</table>
	</div>
	<hr>
	<div>
		<h3>Upload Profile Picture</h3>
			<img src="${user.profilePicture}" />
			<input type="file" name="profilePicture" id="fileChooser" /><br />
			<input class="btn btn-primary form-control" id="fileUploadButton" type="submit" name="upload" value="Upload" />
	</div>
</div>
<jsp:include page="footer.jsp"></jsp:include>
</body>
<script type="text/javascript">
$(document).ready(function(){
	$("#fileUploadButton").click(function(e){
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
				alert("Profile Picture Updated!");
			}		
		});
		
		e.preventDefault();
	});

});

$(document).ready(function(){
	var description = $("#userDescription").val();
	
	$("#descriptionTextArea").val(description);
});

</script>
</html>