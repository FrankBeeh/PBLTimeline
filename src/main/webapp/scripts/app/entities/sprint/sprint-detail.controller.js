'use strict';

angular.module('productbacklogtimelineApp')
    .controller('SprintDetailController', function ($scope, $stateParams, Sprint, ProductTimestamp) {
        $scope.sprint = {};
        $scope.load = function (id) {
            Sprint.get({id: id}, function(result) {
              $scope.sprint = result;
            });
        };
        $scope.load($stateParams.id);
    });
