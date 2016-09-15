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
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-2.2.4.min.js" integrity="sha256-BbhdlvQf/xTY9gja0Dq3HiwQF8LaCRTXxZKRutelT44=" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
<link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro" rel="stylesheet">
<link href="resources/css/main.css" rel="stylesheet">
<title>RevBlogs || Login</title>
<link rel="shortcut icon" type="image/png" href="/content/resources/img/favicon.png"/>
</head>
<body>
<jsp:include page="navbar.jsp"></jsp:include>
<div class="overall-container">
<div class="container page-content content-padding">

	<c:if test="${user.userRole.role eq 'ADMIN'}">
		<c:redirect url="/admin"/>
	</c:if>
	<c:if test="${user.userRole.role eq 'CONTRIBUTOR'}">
		<c:redirect url="/contributor"/>
	</c:if>

	<c:if test="${populate eq true}">
		<div>
			<button id="populateNow" class="btn btn-primary">Populate Database</button>
		</div>
	</c:if>
	<c:if test="${'fail' eq param.auth}">
		<div>
              <h3 id="failLogin">Failed to login. Please try again.</h3>
        </div>
	</c:if>
	<c:if test="${'logout' eq param.auth}">
		<div>
			<h3 id="logoutSuccess">You have successfully signed out.</h3>
		</div>
	</c:if>
	<br><br>
	<div id="login">
	<form id="form" name="loginForm" action="${pageContext.request.contextPath}/login" method="post">
	<table>	
		<tr>
			<td>
				Email&nbsp;&nbsp;&nbsp;
			</td> 
			<td>
				<input type="text" id="userAuth" name="username" class="form-control"/><span id="userMsg"></span><br/>
			</td>
		</tr>
		<tr>
			<td>
				Password&nbsp;&nbsp;&nbsp;
			</td>
			<td> 
				<input type="password" id="passAuth" name="password" class="form-control"/><span id="passMsg"></span>
			</td>
		</tr>
		<tr><td colspan=2><hr/></td></tr>
		<tr>
			<td></td>
			<td><button id="send" class="btn btn-primary form-control">Login</button></td>
		</tr>
		<tr><td colspan=2><br/><br/></td></tr>
	</table>	
	</form>
	</div>
</div>
<div class="footer2">
<jsp:include page="footer.jsp"></jsp:include>
</div>
</div>
</body>
<script type="text/javascript">
$(document).ready(function(){
	
	const HOST = "localhost";
	
	$("#send").click(function(){
	
		
		if(validateForm() === true){
			var email = $("#userAuth").val();

			$.get("http://" + HOST + ":7001/revblogs/bindUser?u=" + email, function(response){
			
				if(response === "Success"){
				
					$("#form").submit();
				
				} else {
				
					e.preventDefault();
				}
			});
		}
	});
	$('#form').keyup(function(e) {
		var keyCode = e.keyCode || e.which;
		if (keyCode === 13) { 
			e.preventDefault();
			if($("#passAuth").is(':focus')){
				if((validateForm() === true) ){
					var email = $("#userAuth").val();

					$.get("http://" + HOST + ":7001/revblogs/bindUser?u=" + email, function(response){
			
						if(response === "Success"){
			
							$("#form").submit();
			
						} else {
			
							e.preventDefault();
						}
					});
				}
			}
		}
	});
	
	$("#populateNow").click(function(){
	
		alert("Populating...");
	
		$.get("http://" + HOST + ":7001/revblogs/populate.do", function(response){
			
			alert(response)
		});
	});
});
function validateForm(){
	
	var element = document.querySelector("loginForm");

	var username = document.forms["loginForm"]["username"].value;
	var password = document.forms["loginForm"]["password"].value;
	var userError = document.getElementById("userMsg");
	var passError = document.getElementById("passMsg");
	
	if((username == null || username == "")||(password == null || password == "")){
			
		event.preventDefault();
		
		if(username == null || username == ""){
			userError.innerHTML = "Please enter a username.";
			userError.style.color = "red";
		} else{
			userError.innerHTML = "";
		}
		
		if(password == null || password == ""){
			passError.innerHTML ="Please enter a password.";
			passError.style.color = "red";

		} else{
			passError.innerHTML = "";
		}
		
		return false;
	}
	return true;
}
</script>
</html>