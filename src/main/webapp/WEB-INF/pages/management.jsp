<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
<link rel="shortcut icon" type="image/png" href="/content/resources/img/favicon.png"/>
<style type="text/css">
.scroll{
	height: 422px;
	overflow-y: auto;
}
tr.header
{
    cursor:pointer;
}
.header .sign:after{
  content:"+";
  display:inline-block;      
}
.header.expand .sign:after{
  content:"-";
}
</style>
</head>
<body>
<jsp:include page="navbar.jsp"></jsp:include>
<div class="overall-container">
<div class="container page-content scroll content-padding">
	<table>
		<tr class="header"><th colspan="2">Pages<span class="sign"></span></th></tr>
		<tr><th>File</th><th>Delete</th></tr>
		<c:forEach items="${list}" var="name">
			<form:form action="${pageContext.servletContext.contextPath}/deleteFile" method="get" commandName="blog">
				<tr>
					<td><a href="http://blogs.pjw6193.tech/${name}"><c:out value="${name}"></c:out></a></td>
					<td><form:hidden path="blogTitle" value="${name}"/><form:button name="delete">Delete</form:button></td>
				</tr>
			</form:form>
		</c:forEach>
		<tr class="header"><th colspan="2">Pictures/Videos<span class="sign"></span></th></tr>
		<tr><th>File</th><th>Delete</th></tr>
		<c:forEach items="${elist}" var="name">
			<form:form action="${pageContext.servletContext.contextPath}/deleteFile" method="get" commandName="blog">
				<tr>
					<td><a href="http://blogs.pjw6193.tech/${name}"><c:out value="${name}"></c:out></a></td>
					<td><form:hidden path="blogTitle" value="${name}"/><form:button name="delete">Delete</form:button></td>
				</tr>
			</form:form>
		</c:forEach>
		<tr class="header"><th colspan="2">Profile Images<span class="sign"></span></th></tr>
		<tr><th>File</th></tr>
		<c:forEach items="${prlist}" var="name">
			<form:form action="${pageContext.servletContext.contextPath}/deleteFile" method="get" commandName="blog">
				<tr>
					<td><a href="http://blogs.pjw6193.tech/${name}"><c:out value="${name}"></c:out></a></td>
				</tr>
			</form:form>
		</c:forEach>
		<tr class="header"><th colspan="2">Resources<span class="sign"></span></th></tr>
		<tr><th>File</th></tr>
		<c:forEach items="${rlist}" var="name">
			<tr>
				<td><a href="http://blogs.pjw6193.tech/${name}"><c:out value="${name}"></c:out></a></td>
			</tr>
		</c:forEach>
		<tr class="header"><th colspan="2">Tests<span class="sign"></span></th></tr>
		<tr><th>File</th><th>Delete</th></tr>
		<c:forEach items="${tlist}" var="name">
			<form:form action="${pageContext.servletContext.contextPath}/deleteFile" method="get" commandName="blog">
				<tr>
					<td><a href="http://blogs.pjw6193.tech/${name}"><c:out value="${name}"></c:out></a></td>
					<td><form:hidden path="blogTitle" value="${name}"/><form:button name="delete">Delete</form:button></td>
				</tr>
			</form:form>
		</c:forEach>
	</table>
</div>
<div class="footer2">
<jsp:include page="footer.jsp"></jsp:include>
</div>
</div>
</body>
<script type="text/javascript" src="resources/js/ui.js"></script>
<script type="text/javascript">
$(document).ready(function() {
    $("table").each(function() {
         $("tr").each(function() {
             if ($(this).hasClass('header')) {}
              else {$(this).toggle(50);}});
       });
    $('.header').click(function(){
        $(this).toggleClass('expand').nextUntil('tr.header').slideToggle(100);
    });
});
</script>
</html>