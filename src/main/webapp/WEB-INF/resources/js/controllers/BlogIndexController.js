app.controller("BlogIndexController", ["$scope", "$http", function($scope, $http) 
{
	$scope.doSearch()
	{
		$scope.getFilter();
		$scope.changeView($scope.loadedPosts[0][0]);
	}
	
	$scope.getFilter = function()
	{
		$('#postsDiv').css('visibility', 'hidden');
		$("#loading").show();
		window.scrollTo(0, $('#postsDiv').offsetTop + 100);
		var ulQuery = $scope.searchQuery.toLowerCase().replace('\s', '+');
		$scope.savedQuery = $scope.searchQuery;
		
		$scope.appUrl +=  "&q=" + ulQuery;
		
		$http.get($scope.appUrl).success(
			    function(resp)
				{
			    	var page = 1;
			    	
					$scope.searchPosts = resp;
					
					for (var i = 0; i < $scope.searchPosts.posts.length/$scope.postsPerPage; i++) 
					{
						$scope.loadedPosts[i][0] = page + i;
						
						for (var j = 0; j < $scope.postsPerPage; j++) 
						{
							$scope.loadedPosts[i][j+1] = $scope.searchPosts.posts[j];
						}
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
		
		$http.get($scope.appUrl).success(
			    function(resp)
				{
					$scope.posts = resp;
					
					for (var i = 0; i < $scope.posts.posts.length/$scope.postsPerPage; i++) 
					{
						$scope.loadedPosts[i][0] = page + i;
						
						for (var j = 0; j < $scope.postsPerPage; j++) 
						{
							$scope.loadedPosts[i][j+1] = $scope.posts.posts[j];
						}
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

	$scope.changeView = function(page)
	{
		var pageCheck = false;
		var displayIndex;
		
		if(!$scope.isLoading)	
		{
			if(!$scope.searchPage)
			{
				$scope.isLoading = true;	
				
				for (var i = 0; i < $scope.loadedPosts.length; i++) 
				{
					if (page === $scope.loadedPosts[i][0]) 
					{
						pageCheck = true;
						displayIndex = i;
					}
				}
				
				if(pageCheck)
				{
					$scope.displayPosts = $scope.loadedPosts[displayIndex];
					
					if(displayIndex === $scope.loadedPosts.length)		
					{				
						var pageToLoad = page - (MAX_PP/$scope.postsPerPage/2);
						
						if(pageToLoad < 1)
						{
							pageToLoad = 1;
						}
						
						getPage(pageToLoad, $scope.postsPerPage)
	
				        window.scrollTo(0, $('#postsDiv').offsetTop + 100);
					}
				}
				
				else
				{			
					var pageToLoad = page - (MAX_PP/$scope.postsPerPage/2);
					
					if(pageToLoad < 1)
					{
						pageToLoad = 1;
					}
					
					getPage(pageToLoad, $scope.postsPerPage)
					
					changeView(pageToLoad + (MAX_PP/$scope.postsPerPage/2));
	
			        window.scrollTo(0, $('#postsDiv').offsetTop + 100);
				}
				
				$scope.isLoading = false;
			}
			
			else
			{
				
			}
		}
	}
	
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
	const MAX_PP = 20;
	$scope.loadedPosts = [[]];
	$scope.displayPosts = [];
	$scope.searchQuery = "";
	$scope.savedQuery = "";
	$scope.searchPage = false;
	$scope.curPage = 1;
	$scope.postsPerPage = 10;
	$scope.isLoading = false;
	$scope.author = 0;
	$scope.category = sessionStorage.tag;
	$scope.appUrl = "https://dev.pjw6193.tech:7002/revblogs?page=" + $scope.curPage
														+ "&per_page=" + $scope.postsPerPage
														+ "&author=" + $scope.author
														+ "&category=" + $scope.category;
	$scope.getPage($scope.curPage, $scope.postsPerPage);
	$scope.changeView(1);
}]);