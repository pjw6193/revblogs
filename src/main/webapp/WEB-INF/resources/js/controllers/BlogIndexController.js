$( function() {
  var availableTags = $scope.autofill;
   $scope.searchQuery.autocomplete({
   source: availableTags
  });
} );

/*$scope.autocompleteOptions.autocomplete(
{
    search: function () {},
    source: $scope.autofill,
    minLength: 2
});


var autocompleteApp = angular.module("autocompleteApp");
var autocompleteController = function($scope) {
  //http://jqueryui.com/autocomplete/
	var availableTags = $scope.autofill;
	$scope.autocompleteOptions = {
		source: availableTags
	}
};
autocompleteApp.('ui.config', {
    autocomplete: {
        minLength: 2,
        delay: 500
    }
});*/

app.controller("BlogIndexController", ["$scope", "$http", function($scope, $http) 
{	
	$scope.getFilter = function()
	{
		$('#postsDiv').css('visibility', 'hidden');
		$("#loading").show();
		window.scrollTo(0, $('#postsDiv').offsetTop + 100);
		var fullUrl = $scope.appUrl;
		
		if($scope.searchQuery != "")
		{
			var ulQuery = $scope.searchQuery.toLowerCase().replace('\s', '+');
			$scope.savedQuery = $scope.searchQuery;
			
			fullUrl = $scope.appUrl + "&q=" + ulQuery;
		}
		
		$http.get(fullUrl).success(
			    function(resp)
				{
					$scope.searchPosts = resp;
					
					$scope.autofill = $scope.searchPosts.searchFills;
					
					$scope.curPage = page;  //current page
					$scope.searchPage = true;
					
					$scope.numOfPages = [];
					$scope.numOfPages[0] = 1;
					
					for (var i = 1; i < $scope.searchPosts.total_pages+1; i++)
					{
						$scope.numOfPages[i - 1] = i;
					}

					$('#postsDiv').load();
					$("#loading").hide();
					$('#postsDiv').css('visibility', 'visible');
				}
			);
		}
		
		/*$scope.searchPosts.posts = [];
		var ulQuery = $scope.searchQuery.toLowerCase();
		$scope.savedQuery = $scope.searchQuery;
		$scope.searchPage = true;
		for (var i = 0; i < $scope.posts.posts.length; i++) 
		{
			var ulTitle = $scope.posts.posts[i].title.toLowerCase();
			var ulSubtitle = $scope.posts.posts[i].subtitle.toLowerCase();
			var ulName = $scope.posts.posts[i].author.name.toLowerCase();
			if (ulTitle.includes(ulQuery) || ulSubtitle.includes(ulQuery) || ulName.includes(ulQuery))
			{
				$scope.searchPosts.posts.push($scope.posts.posts[i]);
			}
		}
		console.log($scope.searchPosts.posts);
		return false;
	}*/
	
	$scope.clearSearch = function()
	{
		$scope.searchPosts.posts = [];
		$scope.searchPage = false;
	}
	
	$scope.getPage = function(page, postsPP)
	{
		$('#postsDiv').css('visibility', 'hidden');
		$("#loading").show();
		window.scrollTo(0, $('#postsDiv').offsetTop + 100);
		
		if (page > 1) 
		{
			$scope.pageToGet = $scope.curPage - MAX_PP/$scope.postsPerPage;
			
			if($scope.pageToGet < 1) $scope.pageToGet = 1;
		}
		
		
		
		$http.get(fullUrl).success(
			    function(resp)
				{
					$scope.posts = resp;
					
					$scope.autofill = $scope.posts.searchFills;
					
					$scope.curPage = page;  //current page
					$scope.searchPage = false;
					var prevPage = $scope.curPage;
					var nextPage = $scope.curPage;
					
					if($scope.curPage > 1)
					{
						prevPage = $scope.curPage - 1;
					}
					
					if($scope.curPage < $scope.posts.total_pages)
					{
						nextPage = $scope.curPage + 1;
					}
					
					$scope.numOfPages = [];
					$scope.numOfPages[0] = 1;
					
					for (var i = 1; i < $scope.posts.total_pages+1; i++)
					{
						$scope.numOfPages[i - 1] = i;
					}
					
					for (var i = (curPage-1)*$scope.postsPerPage; i < $scope.posts.length; i++) 
					{
						$scope.displayPosts[i] = $scope.posts[curPage*$scope.postsPerPage+i];
						endPost = i;
					}
					
					$('#postsDiv').load();
					$("#loading").hide();
					$('#postsDiv').css('visibility', 'visible');
				}
			);
		}
		
	$scope.getPageWithAuthor = function(page, authorid)
	{
		$scope.author = authorid;
		$scope.getPage(page,$scope.postsPerPage);
	}

	$scope.changeView = function(direction)
	{
		if(!$scope.isLoading)	
		{				
			$scope.isLoading = true;

			if(direction >= 1 & $scope.curPage < $scope.posts.total_pages)
			{
				$scope.curPage = $scope.curPage + direction;
			}
			
			else
			{
				if($scope.curPage > 1)
				{
					$scope.curPage = $scope.curPage - 1;
				}
			}
			
			preloadPage($scope.curPage - 1, $scope.postsPerPage);
			preloadPage($scope.curPage + 1, $scope.postsPerPage);

			var endPost = 0;
		
			for (var i = (curPage-1)*$scope.postsPerPage; i < $scope.posts.length; i++) 
			{
				$scope.displayPosts[i] = $scope.posts[curPage*$scope.postsPerPage+i];
				endPost = i;
			}

			if (endPost-$scope.posts.length <= $scope.postsPerPage)
			{
				getPage(curPage+1);
			}

			else if (endPost <= $scope.postsPerPage)
			{
				getPage(curPage-1);
			}

			$scope.isLoading = false;
			window.scrollTo(0, $('#postsDiv').offsetTop + 100);
		}
	};
	
	const MAX_PP = 20;
	$scope.posts = {
			page: 0,
			prev: null,
			next: null,
			posts:[],
			author: null,
			category: null,
			total_pages: 0,
			per_page: 0,
			total_posts: 0
	};
	$scope.searchPosts = {
			page: 0,
			prev: null,
			next: null,
			posts:[],
			author: null,
			category: null,
			total_pages: 0,
			per_page: 0,
			total_posts: 0
	};
	$scope.autofill = [];
	$scope.searchQuery = "";
	$scope.savedQuery = "";
	$scope.searchPage = false;
	$scope.curPage = 1;
	$scope.pageToGet = 1;
	$scope.postsPerPage = 10;
	$scope.isLoading = false;
	$scope.author = 0;
	$scope.category = sessionStorage.tag;
	$scope.getPage($scope.curPage, $scope.postsPerPage);
	$scope.appUrl = "https://dev.pjw6193.tech:7002/revblogs?page=" + $scope.pageToGet
																					          + "&per_page=" + $scope.postsPerPage
																					          + "&author=" + $scope.author 
																					          + "&category=" + $scope.category;
}]);