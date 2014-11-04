'use strict';

var UsersCtrl = function($scope, ngTableParams, $http) {

  var url = '/alfresco/service/redpill/angular-demo/users';

  $scope.tableParams = new ngTableParams({
    page: 1,
    count: 5
  }, {
    counts: [],
    total: 1,
    getData: function($defer, params) {
      $http.get(url).success(function(result) {
        params.total(result.total);
        $defer.resolve(result.data);
      });
    }
  });

};

UsersCtrl.$inject = ['$scope', 'ngTableParams', '$http'];

angular.module('alfrescoDemoApp')

.config([
  '$stateProvider',
  function($stateProvider) {
    $stateProvider
      .state('users', {
        url: '/users',
        templateUrl: 'views/users.html',
        controller: 'UsersCtrl'
      });
  }
])

.controller('UsersCtrl', UsersCtrl);
