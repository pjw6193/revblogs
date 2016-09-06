<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<title>Create User</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
</head>
<body>
	<div class="container">
		<h3> Create a User </h3>
		<hr/>
	</div>
	<div class="container">
		<form action="createAccount.do" method="post">
			Email: <input name="email" type="text" class="form-control"/><br />
			Role: 
			<select name="role" class="btn btn-primary form-control">
				<c:forEach var="t" items="${roleDropDown}">
			 		<option value="${t.role}" label="${t.role}"/>
			 	</c:forEach>
			 </select>
			<button type="submit" class="btn btn-primary form-control">Add User</button>
		</form>
		<hr/>
	</div>
</body>
</html>