<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
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
<title>Logged In</title>
</head>
<body>
<jsp:include page="navbar.jsp"></jsp:include>
<div class="container page-content">
	<h1>${title}</h1>
	<h2>${message}</h2>
	
	<!-- place change form here. Have a post call, change it, 
	update it and the newUser parameter, and then redirect 
	to page with reload. -->
	
	<c:if test="${user.newUser eq true}">
		<c:redirect url="/password"/>
	</c:if>
	
	<sec:authorize access="hasRole('ADMIN')" >Welcome Admin!</sec:authorize>
	<c:if test="${user.userRole.role eq 'ADMIN'}">
		<table>
		<tr>
			<td>
				<a href="${pageContext.servletContext.contextPath}/profile"><span class="glyphicon glyphicon-wrench"></span>&nbsp;Update profile</a>
			</td>
		</tr>
		<tr>
			<td>
				<a href="${pageContext.servletContext.contextPath}/makeClientAccount"><span class="glyphicon glyphicon-user"></span>&nbsp;Add a Client</a>
			</td>
		</tr>
		<tr>
			<td>
				<a href="${pageContext.servletContext.contextPath}/create-blog"><span class="glyphicon glyphicon-pencil"></span>&nbsp;Create a Blog</a>
			</td>
		</tr>
		<tr>
			<td>
				<a href="${pageContext.servletContext.contextPath}/go-logout"><span class="glyphicon glyphicon-log-out"></span>&nbsp;Logout</a>
			</td>
		</tr>
		</table>
		
	</c:if>
	<sec:authorize access="hasRole('CONTRIBUTOR')" >Welcome Contributor!</sec:authorize>
	<c:if test="${user.userRole.role eq 'CONTRIBUTOR'}">
	<table>
	<tr>
		<td>
			<a href="${pageContext.servletContext.contextPath}/profile"><span class="glyphicon glyphicon-wrench"></span>&nbsp;Profile</a>
		</td>
	</tr>
	<tr>
		<td>
			<a href="${pageContext.servletContext.contextPath}/create-blog"><span class="glyphicon glyphicon-pencil"></span>&nbsp;Create a Blog</a>
		</td>
	</tr>
	<tr>
		<td>
			<a href="${pageContext.servletContext.contextPath}/"><span class="glyphicon glyphicon-book"></span>&nbsp;View Blogs</a>
		</td>
	</tr>
	<tr>
		<td>
			<a href="${pageContext.servletContext.contextPath}/go-logout"><span class="glyphicon glyphicon-log-out"></span>&nbsp;Logout</a>
		</td>
	</tr>
	</table>
	</c:if>
</div>
<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>