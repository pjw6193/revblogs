/**
 * 
 */

var createBlogModule = angular.module('createBlog', []);

createBlogModule.controller('createBlogCtrl', function($scope) {
	
	$scope.references = [ {id: 'reference1', name: '' } ];
	
	$scope.maxedReference = false;
	
	$scope.addNewChoice = function() {
	  var newItemNo = $scope.references.length+1;
	  $scope.references.push({'id':'reference'+newItemNo});
	  if ( newItemNo >= 20 ) {
		  $scope.maxedReference = true;
	  }
	};
	
	$scope.showAddChoice = function(reference) {
	  return reference.id === $scope.references[$scope.references.length-1].id;
	};
});
