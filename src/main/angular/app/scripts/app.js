'use strict';

/**
 * @ngdoc overview
 * @name alfrescoDemoApp
 * @description
 * # alfrescoDemoApp
 *
 * Main module of the application.
 */
angular
  .module('alfrescoDemoApp', [
    'ngAnimate',
    'ngCookies',
    'ngResource',
    'ngRoute',
    'ngSanitize',
    'ngTouch',
    'ngTable',
    'ui.router'
  ])

.run([
  '$rootScope',
  '$state',
  '$stateParams',
  function($rootScope, $state, $stateParams) {
    $rootScope.$state = $state;
    $rootScope.$stateParams = $stateParams;
  }
])

.config([
  '$stateProvider',
  '$urlRouterProvider',
  function($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise('/');
  }
]);

/*
.config(function($routeProvider) {
  $routeProvider
    .when('/', {
      templateUrl: 'views/main.html',
      controller: 'MainCtrl'
    })
    .when('/about', {
      templateUrl: 'views/about.html',
      controller: 'AboutCtrl'
    })
    .when('/users', {
      templateUrl: 'views/users.html',
      controller: 'UsersCtrl'
    })
    .when('/groups', {
      templateUrl: 'views/groups.html',
      controller: 'GroupsCtrl'
    })
    .otherwise({
      redirectTo: '/'
    });
});
*/
