<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%><%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!doctype html>
<html>
<head>
<title>Revature Blog</title>
<base href="/revblogs/" />
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-2.2.4.min.js" integrity="sha256-BbhdlvQf/xTY9gja0Dq3HiwQF8LaCRTXxZKRutelT44=" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
<link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro" rel="stylesheet">
<link href="resources/css/main.css" rel="stylesheet">
<script src="resources/js/angular.min.js"></script>
<script src="resources/js/app.js"></script>
<script src="resources/js/controllers/BlogIndexController.js"></script>

</head>

<body ng-app="app" ng-controller="BlogIndexController">
  <jsp:include page="navbar.jsp"></jsp:include>
  <script type="text/javascript">$("#navhome").addClass("active");</script>
  <div class="container visible-xs-block" style="margin-top: 80px">
  	<form>
       <div class="form-group input-group post-search">
         <input type="text" class="form-control" placeholder="Search">
         <span class="input-group-btn">
           <button class="btn btn-primary"><span class="glyphicon glyphicon-search"></span></button>
         </span>
       </div>
     </form>
  </div>
  
  <div class="container page-content">
  	<div class="row">
  		<div class="col-xs-12">
  			<h2>Latest Posts</h2>
  		</div>
  	</div>
    <div id="postsDiv" class="row">
      <div class="col-sm-8">
      	<div ng-repeat="post in posts.posts" ng-include src="'resources/js/templates/post-preview.html'"></div>
      </div>
      <div class="col-sm-4 hidden-xs">
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
  
  <!-- Customize posts to view -->
  <div id="postsPerPage">
  <label>Number of posts to show: </label>
  	<select>
  		<option ng-model="postsPerPage" ng:class="{true:'disabled', false:'enabled'}[postsPerPage == 10]" href="#here" ng-click="getPage(curPage)">10</option>
  		<option ng-model="postsPerPage"  ng:class="{true:'disabled', false:'enabled'}[postsPerPage == 25]" href="#here" ng-click="getPage(curPage)">25</option>
  		<option ng-model="postsPerPage"  ng:class="{true:'disabled', false:'enabled'}[postsPerPage == 50]" href="#here" ng-click="getPage(curPage)">50</option>
  	</select>
  </div>
  
  
  <!-- PAGINATION -->
  <nav id="pageNumsNav" aria-label="...">
  	<div>
  	<ul id="pageNums" class="pagination">
   	  <li ng:class="{true:'disabled', false:'enabled'}[curPage == 1 || isLoading]" ng-click="changeView(0)"><a id="previous" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
 		<li ng:class="{true:'active', false:''}[number == curPage]" ng-repeat="number in numOfPages" ng-if="number > 0"> <a ng-click="getPage(number)">{{number}}</a> </li>
   	  <li ng:class="{true:'disabled', false:'enabled'}[curPage == numOfPages || isLoading]" ng-click="changeView(1)"><a id="next" aria-label="Next"><span aria-hidden="true">&raquo;</span></a></li>
  	</ul>
  	</div>
  </nav>
  <!-- PAGINATION -->
  </div>
  
  <span>{{isLoading}}</span>
  
  <jsp:include page="footer.jsp"></jsp:include>
  
  <input type="hidden" ng-model="blogsPerPage">
  <input type="hidden" ng-model="curPage">
  <input type="hidden" ng-model="nextPagePosts">
  <input type="hidden" ng-model="prevPagePosts">
  
</body>

<script type="text/javascript">


</script>

</html>
