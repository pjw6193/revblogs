/**
 * 
 */

var enhancedSearchModule = angular.module('enhancedSearch', []);

enhancedSearchModule.controller('enhancedSearchCtrl', function($scope, $http) {
	
	$scope.searchText = '';
	$scope.suggestions = [];
	$scope.selectedSuggestion = -1;
	$scope.maxSuggestions = 6;
	
	$scope.keyup = function(event) {
		var keyCode = event.which || event.keyCode;
		if ( keyCode == 38 ) {
			$scope.upArrow();
		} else if ( keyCode == 40 ) {
			$scope.downArrow();
		} else if ( keyCode == 13 ) {
			$scope.enterKey();
		} else if ( keyCode == 27 ) {
			$scope.escapeKey();
		} else {
			$scope.unknownKey(keyCode);
		}
	}
	
	//////////////////////////////////
	///// Start Key-Up Callbacks /////
	//////////////////////////////////
	$scope.upArrow = function() {
		if ( $scope.searchText.length > 0 ) {
			if ( $scope.selectedSuggestion <= 0 ) {
				$scope.selectedSuggestion = 0;
			} else {
				$scope.selectedSuggestion -= 1;
			}
			$scope.selectSuggestion($scope.selectedSuggestion);
			$scope.setSuggestionsVisible(true);
		}
	}
	
	$scope.downArrow = function() {
		if ( $scope.searchText.length > 0 ) {
			if ( $scope.selectedSuggestion >= $scope.maxSuggestions-1 ) {
				$scope.selectedSuggestion = $scope.maxSuggestions-1;
			} else {
				$scope.selectedSuggestion += 1;
			}
			$scope.selectSuggestion($scope.selectedSuggestion);
			$scope.setSuggestionsVisible(true);
		}
	}
	
	$scope.enterKey = function() {
		$scope.submitSearch($scope.searchText);
		$scope.setSuggestionsVisible(false);
	}
	
	$scope.escapeKey = function() {
		$scope.setSuggestionsVisible(false);
	}
	
	$scope.unknownKey = function(keyCode) {
		$scope.setSuggestionsVisible($scope.searchText.length > 0);
	}
	////////////////////////////////
	///// End Key-Up Callbacks /////
	////////////////////////////////
	
	$scope.setSuggestionsVisible = function(visible) {
		if ( visible ) {
			document.getElementById("selections").style = "display: inline; z-index: 2; position: relative;";
		} else {
			document.getElementById("selections").style = "display: none; z-index: 2; position: relative;";
		}
	}
	
	$scope.setSearchText = function(newText) {
		$scope.searchText = newText;
	}
	
	$scope.searchLostFocus = function() {
		$scope.setSuggestionsVisible(false);
	}
	
	$scope.selectSuggestion = function(indexToSelect) {
		angular.forEach($scope.suggestions, function(suggestion, index) {
			if ( index == indexToSelect && suggestion.selected != "selected" ) {
				suggestion.selected = "selected";
				$scope.setSearchText(suggestion.text);
			} else if ( index != indexToSelect && suggestion.selected != "" ) {
				suggestion.selected = "";
			}
		});
	}
	
	$scope.searchTextChanged = function() {
		var userEnteredSearchText = $scope.searchText;
		$scope.generateSuggestions(userEnteredSearchText);
	}
	
	$scope.generateSuggestions = function(userEnteredSearchText) {
		console.log("Unimplemented Generate Suggestions Method: " + userEnteredSearchText);
		
		$scope.suggestions = $scope.posts.searchFills;
	}
	
	$scope.submitSearch = function(searchText) 
	{
		var fullUrl;
		var ulQuery = $scope.searchQuery.toLowerCase();
		$scope.savedQuery = $scope.searchQuery;
		
		fullUrl = $scope.appUrl + "/api/posts/?page=1&per_page=10&q=" + ulQuery;
		
		$http.get(fullUrl).success(
			    function(resp)
				{		
					$scope.suggestions = resp.searchFills;
				});
	}	
});
