<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:og="http://ogp.me/ns#" xmlns:fb="http://www.facebook.com/2008/fbml">
<head>
<script src="https://code.jquery.com/jquery-2.2.4.min.js" integrity="sha256-BbhdlvQf/xTY9gja0Dq3HiwQF8LaCRTXxZKRutelT44=" crossorigin="anonymous"></script>
<script src="resources/js/img-mgmt.js"></script>
  <script src="${pageContext.servletContext.contextPath}/resources/tinymce/js/tinymce/tinymce.min.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<!-- HEADERS NEEDED TO PREVENT BACK BUTTON ON LOGOUT. DO NOT REMOVE ME! -->
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="Sat, 01 Dec 2001 00:00:00 GMT">
<meta property="og:url" content="http://dev.pjw6193:7001/pages/create-blog.jsp" />
<meta property="og:type"content="article" />
<meta property="og:title"content="Different" />
<meta property="og:description"content="this is only a testkhjkgkjhgkj" />
<meta property="og:image"content="http://weknowyourdreams.com/images/cheese/cheese-03.jpg" />
<meta property="og:image:width" content="450" />
<meta property="og:image:height" content="298" />
<meta property="fb:app_id" content="1070815552954243"/>

<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="bootstrap-social.css">
<script src="https://use.fontawesome.com/ebec39e24e.js"></script>
<link href="resources/css/main.css" rel="stylesheet">

<title>Blog Post</title>
</head>
<body>
<jsp:include page="navbar.jsp"></jsp:include>
<div class="container page-content">
<h2>Edit Post</h2>



<form:form action="add-blog.do" method="post" commandName="blog">
	Title
	<form:input path="blogTitle" value="${blog.blogTitle}" /><br />
	Subtitle
	<form:input path="blogSubtitle" value="${blog.blogSubtitle}" /><br />
	<br>
	<form:textarea path="blogContent" rows="30" cols="100" value="${blog.blogContent}" ></form:textarea>
	<br>
	<!-- <button type="button">Add Reference</button>
	<br>
	<textarea rows="5" cols="100"></textarea> -->
	<br>
 	Apply tags (separated by commas):
 	<br>
	<form:input path="blogTagsString" id="tagList" style="resize: none" style="width: 300px" value="${blog.blogTagsString}"></form:input>
	<br>
	
	<input type="submit" value="Preview" />
	<br/>
</form:form>
<br/>
</div>
<jsp:include page="footer.jsp"></jsp:include>
</body>
<script src="https://s3-us-west-2.amazonaws.com/dan-pickles-jar/content/resources/js/facebookConnection.js"></script>
<script src="${pageContext.servletContext.contextPath}/resources/js/tinyMCE.js"></script>
</html>