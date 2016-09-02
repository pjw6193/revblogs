<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<title>Revature Blog</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-2.2.4.min.js" integrity="sha256-BbhdlvQf/xTY9gja0Dq3HiwQF8LaCRTXxZKRutelT44=" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
<link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro" rel="stylesheet">
<link href="resources/css/main.css" rel="stylesheet">

</head>

<body>
<jsp:include page="navbar.jsp"></jsp:include>
  <div class="container page-content">
    <div class="row">
      <div class="panel panel-default col-sm-8">
        <div class="hero-image-container">
          <div class="hero-image">
            <div class="dummy"></div>
          </div>
        </div>
		
        <div class="post-content" style="padding-top: 60px;">
          <div class="post-heading">
            <div class="post-meta">
              <span class="post-date">
              	<c:out value="${blog.publishDate}"></c:out>
              </span>
              <span class="post-author">
              	<c:out value="${blog.author.firstName}"></c:out> 
              	<c:out value="${blog.author.lastName}"></c:out>
              </span>
            </div>
            <h1 class="post-title">
            	<c:out value="${blog.blogTitle}"></c:out>
            </h1>
          </div>
          <div class="post-body">
          	<c:out value="${blog.blogContent}" escapeXml="false"></c:out>
          </div>
          <form action="/edit">
          	<button type="submit">Edit</button>
          </form>
        </div>
      </div>
      <div class="col-sm-4">
        <div class="panel panel-primary">
          <div class="panel-heading">
            <h3 class="panel-title">About the Author</h3>
          </div>
          <div class="panel-body">
            <div class="row author">
              <div class="col-xs-12 author-image">
              	<img src="${blog.author.profilePicture}" />
              </div>
              <div class="col-xs-12 author-name text-center">
              	<c:out value="${blog.author.firstName}"></c:out> 
              	<c:out value="${blog.author.lastName}"></c:out>
              </div>
            </div>
            <div class="row">
              <div class="col-xs-12 author-desc">
              	<c:out value="${blog.author.description}"></c:out>
              </div>
            </div>
          </div>
        </div>

        <div class="panel panel-primary">
          <div class="panel-heading">
            <h3 class="panel-title">Share this post</h3>
          </div>
          <div class="panel-body">
            <p>(Social sharing icons here)</p>
          </div>
        </div>

        <div class="panel panel-primary">
          <div class="panel-heading">
            <h3 class="panel-title">Learn more about Revature</h3>
          </div>
          <div class="panel-body">
            <ul class="list-unstyled">
              <li><a href="#">Revature</a></li>
              <li><a href="#">Life at Revature</a></li>
            </ul>
          </div>
        </div>
      </div>
    </div>
  </div>
  <div class="footer">
    <div class="container">
      <div class="row">
        <div class="col-sm-6">
          <img class="img-responsive" src="resources/img/rev-footer.png" />
        </div>
      </div>
    </div>
  </div>
</body>
</html>