<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<title>Revature Blog</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-2.2.4.min.js" integrity="sha256-BbhdlvQf/xTY9gja0Dq3HiwQF8LaCRTXxZKRutelT44=" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
<link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro" rel="stylesheet">
<link href="url to css style sheet" rel="stylesheet">

</head>

<body>
  <nav class="page-navigation navbar navbar-default navbar-fixed-top">
    <div class="container">
      <div class="navbar-header">
        <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
          <span class="sr-only">Toggle navigation</span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand" href="#"><img src="url to revature brand" /></a>
      </div>

      <!-- Collect the nav links, forms, and other content for toggling -->
      <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
        <ul class="nav navbar-nav">
          <li><a href="#">Home</a></li>
          <li><a href="#">About</a></li>
        </ul>
        <form class="navbar-form navbar-right">
          <div class="form-group input-group post-search">
            <input type="text" class="form-control" placeholder="Search">
            <span class="input-group-btn">
              <button class="btn btn-primary"><span class="glyphicon glyphicon-search"></span></button>
            </span>
          </div>
        </form>
      </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
  </nav>
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
              </span>
              <span class="post-author">
              </span>
            </div>
            <h1 class="post-title">
            </h1>
          </div>
          <div class="post-body">
          </div>
          <div class="post-references-heading">
          </div>
          <div class="post-references-body">
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
              </div>
              <div class="col-xs-12 author-name text-center">
              </div>
            </div>
            <div class="row">
              <div class="col-xs-12 author-desc">
              </div>
            </div>
          </div>
        </div>

        <div class="panel panel-primary">
          <div class="panel-heading">
            <h3 class="panel-title">Share this post</h3>
          </div>
          <div class="panel-body">
     
	<br>
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
          <img class="img-responsive" src="url to revature footer logo" />
        </div>
      </div>
    </div>
  </div>
</body>
</html>