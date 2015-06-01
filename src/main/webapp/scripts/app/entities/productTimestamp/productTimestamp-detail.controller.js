'use strict';

angular.module('productbacklogtimelineApp')
    .controller('ProductTimestampDetailController', function ($scope, $stateParams, ProductTimestamp, Sprint) {
        $scope.productTimestamp = {};
        $scope.load = function (id) {
            ProductTimestamp.get({id: id}, function(result) {
              $scope.productTimestamp = result;
            });
        };
        $scope.load($stateParams.id);
    });
