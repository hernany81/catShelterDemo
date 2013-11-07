/**
 * Override default MainCrudCtrl defined in scaffolding.js
 */
var scaffoldingModule = angular.module('scaffolding');

scaffoldingModule.controller('MainCrudCtrl', function MainCrudCtrl($scope, $location, $resource, MainAppService, Flash) {
	$scope.appData = MainAppService;
	$scope.appData.breeds = [];
	$scope.appData.coats = [];
	
	var breedControllerUrl = $('#breedBaseUrlDiv').data('base-url');
	
	var breeds = $resource(breedControllerUrl + ':action', {}, {
		list: {method: 'GET', params: {action: 'list'}, isArray: true}
	});
	
	breeds.list(function(list, headers) {
		$scope.appData.breeds = list;
		$scope.message = Flash.getMessage();
	}, errorHandler.curry($scope, $location, Flash));
	
	var coatControllerUrl = $('#coatBaseUrlDiv').data('base-url');
	
	var coats = $resource(coatControllerUrl + ':action', {}, {
		list: {method: 'GET', params: {action: 'list'}, isArray: true}
	});
	
	coats.list(function(list, headers) {
		$scope.appData.coats = list;
		$scope.message = Flash.getMessage();
	}, errorHandler.curry($scope, $location, Flash));
});