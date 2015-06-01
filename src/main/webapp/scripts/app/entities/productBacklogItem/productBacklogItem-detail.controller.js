'use strict';

angular.module('productbacklogtimelineApp')
    .controller('ProductBacklogItemDetailController', function ($scope, $stateParams, ProductBacklogItem, ProductTimestamp) {
        $scope.productBacklogItem = {};
        $scope.load = function (id) {
            ProductBacklogItem.get({id: id}, function(result) {
              $scope.productBacklogItem = result;
            });
        };
        $scope.load($stateParams.id);
    });
