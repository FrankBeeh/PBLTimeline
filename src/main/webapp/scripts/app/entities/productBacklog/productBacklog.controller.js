'use strict';

angular.module('productbacklogtimelineApp')
    .controller('ProductBacklogController', function ($scope, ProductBacklog, ProductTimestamp) {
        $scope.productBacklogItems = [];
        $scope.producttimestamps = ProductTimestamp.query();
        $scope.loadAll = function() {
        	ProductBacklog.query({selectedTimestamp: '2', referenceTimestamp: '1'}, function(result) {
               $scope.productBacklogItems = result;
            });
        };
        $scope.loadAll();
        
        $scope.refresh = function () {
            $scope.loadAll();
        };
    });
