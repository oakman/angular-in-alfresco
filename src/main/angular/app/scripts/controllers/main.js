'use strict';

var MainCtrl = function($scope) {

  $scope.awesomeThings = ['Redpill Linpro', 'AngularJS', 'Alfresco'];

};

MainCtrl.$inject = ['$scope'];

angular.module('alfrescoDemoApp')

.config([
  '$stateProvider',
  function($stateProvider) {
    $stateProvider
      .state('main', {
        url: '/',
        templateUrl: 'views/main.html',
        controller: 'MainCtrl'
      });
  }
])

.controller('MainCtrl', MainCtrl);
