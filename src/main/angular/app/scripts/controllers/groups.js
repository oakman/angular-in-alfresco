'use strict';

var GroupsCtrl = function($scope, ngTableParams, $http) {

  var url = '/alfresco/service/api/groups';

  $scope.tableParams = new ngTableParams({
    page: 1,
    count: 100
  }, {
    counts: [],
    total: 1,
    getData: function($defer, params) {
      $http.get(url).success(function(result) {
        params.total(result.paging.totalItems);
        $defer.resolve(result.data);
      });
    }
  });

};

GroupsCtrl.$inject = ['$scope', 'ngTableParams', '$http'];

angular.module('alfrescoDemoApp')

.config([
  '$stateProvider',
  function($stateProvider) {
    $stateProvider
      .state('groups', {
        url: '/groups',
        templateUrl: 'views/groups.html',
        controller: 'GroupsCtrl'
      });
  }
])

.controller('GroupsCtrl', GroupsCtrl);
