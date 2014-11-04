'use strict';

describe('Controller: GroupsCtrl', function() {

  // load the controller's module
  beforeEach(module('alfrescoDemoApp'));

  var GroupsCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function($controller, $rootScope) {
    scope = $rootScope.$new();
    GroupsCtrl = $controller('GroupsCtrl', {
      $scope: scope
    });
  }));
});
