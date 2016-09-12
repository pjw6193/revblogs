<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html  xmlns="http://www.w3.org/1999/xhtml" xmlns:og="http://ogp.me/ns#" xmlns:fb="http://www.facebook.com/2008/fbml">
<head>
<script src="${pageContext.servletContext.contextPath}/resources/js/angular.min.js"></script>
<script src="${pageContext.servletContext.contextPath}/resources/js/img-mgmt.js"></script>
<script src="${pageContext.servletContext.contextPath}/resources/js/create-blog.js"></script>
<script src="${pageContext.servletContext.contextPath}/resources/tinymce/js/tinymce/tinymce.min.js"></script>
<script src="${pageContext.servletContext.contextPath}/resources/js/jquery-3.1.0.min.js"></script>
<script src="https://code.jquery.com/jquery-2.2.4.min.js" integrity="sha256-BbhdlvQf/xTY9gja0Dq3HiwQF8LaCRTXxZKRutelT44=" crossorigin="anonymous"></script>
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
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<script src="https://use.fontawesome.com/ebec39e24e.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
<link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro" rel="stylesheet">
<link rel="stylesheet" href="resources/css/main.css" >
<link rel="shortcut icon" type="image/png" href="/content/resources/img/favicon.png"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Blog Post</title>
</head>
<body ng-app="createBlog" ng-controller='createBlogCtrl'>

<jsp:include page="navbar.jsp"></jsp:include>

<div class="container page-content">
<h2>Create new blog post</h2>

<form:form action="add-blog.do" method="post" commandName="blog">
	<h3>Title</h3>
	<form:input name="ngTitle" ng-model="ngTitle" required path="blogTitle" />
<%-- 	<span class="validateHint" ng-show="ngTitle == null || ngTitle.length <= 0">A title is required!</span> --%>
	<br />
	<h4>Subtitle</h4>
	<form:input name="ngSubtitle" ng-model="ngSubtitle" path="blogSubtitle"/><br />
	<br>
	<form:textarea path="blogContent" rows="30" cols="100"></form:textarea>
	<br>
	<br />
	References:
	<c:forEach varStatus="vs" items="${blog.references}">
		<div id="ref${vs.index}" style="display: none;">
			[${vs.index+1}] - <form:input path="references[${vs.index}]"></form:input>
		</div>
	</c:forEach>
	<button id="addRefButton" type="button" ng-click="revealReference()">Add Another Reference</button>
<!-- 	<div margin="0" data-ng-repeat="reference in references"> -->
<!-- 		<input type="text" ng-model="reference.name" name="{{reference.id}}" placeholder="Reference"> -->
<!-- 		<span ng-hide="maxedReference"> -->
<!-- 			<button type="button" ng-show="showAddChoice(reference)" ng-click="addNewChoice()">Add another reference</button> -->
<!-- 		</span> -->
<!-- 	</div> -->
	<br /><br />
 	Apply tags (separated by commas):
 	<br>
	<form:input path="blogTagsString" id="tagList" style="resize:none;width:300px;"></form:input>
	<br />
	<span ng-show="ngTitle == null || ngTitle.length <= 0 || ngSubtitle == null || ngSubtitle.length <= 0"><br />Please provide a title and subtitle to proceed.</span>
	<input ng-show="ngTitle != null && ngTitle.length > 0 && ngSubtitle != null && ngSubtitle.length > 0" type="submit" value="Preview" />
	<br/>
</form:form>
<br/>
</div>
<jsp:include page="footer.jsp"></jsp:include>
</body>

<script type="text/javascript" src="${pageContext.servletContext.contextPath}/resources/js/ui.js"></script>

<script src="${pageContext.servletContext.contextPath}/resources/js/tinyMCE.js"></script>
</html>