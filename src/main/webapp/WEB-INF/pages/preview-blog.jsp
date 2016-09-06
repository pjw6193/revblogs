 <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<title>Revature Blog</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-2.2.4.min.js" integrity="sha256-BbhdlvQf/xTY9gja0Dq3HiwQF8LaCRTXxZKRutelT44=" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
<link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro" rel="stylesheet">
<link href="resources/css/main.css" rel="stylesheet">
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
<!-- LinkedIn Authenticator Token -->
<script src="//platform.linkedin.com/in.js">
    api_key:   77nvk5bz7r4mwj;
</script>
<script src="https://use.fontawesome.com/ebec39e24e.js"></script>
<link href="resources/css/bootstrap-social.css" rel="stylesheet">
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
            <h3 class="post-subtitle">
            	<c:out value="${blog.blogSubtitle}"></c:out>
            </h3>
          </div>
          <div class="post-body">
          	<c:out value="${blog.blogContent}" escapeXml="false"></c:out>
          </div>
          <form:form action="edit.do">
          	<button type="submit">Edit</button>
          </form:form>
          <form action="publish.do" method="post">
          	<button type="submit">Publish</button>
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
                   <div>Share:</div>
			<a class="btn btn-social-icon btn-linkedin" target="_blank" href="https://www.linkedin.com/shareArticle?mini=true&url=http://dev.pjw6193.tech/pages/create-blog.jsp">
				<span class="fa fa-linkedin"></span>	
			</a>
			<a class="btn btn-social-icon btn-facebook" target="_blank" id="shareBtn">
				<span class="fa fa-facebook"></span>
			</a>
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
<script src="${pageContext.servletContext.contextPath }/resources/js/facebookConnection.js"></script>
</html>