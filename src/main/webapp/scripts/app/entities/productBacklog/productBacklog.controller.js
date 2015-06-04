'use strict';

angular.module('productbacklogtimelineApp')
    .controller('ProductBacklogController', function ($scope, ProductBacklog, ProductTimestamp, ProductTimelineScope) {
        $scope.productBacklogItems = [];
        $scope.producttimestamps = ProductTimestamp.query();
        $scope.loadAll = function() {
        	ProductBacklog.query({selectedTimestamp: ProductTimelineScope.selectedTimestamp, referenceTimestamp: ProductTimelineScope.referenceTimestamp}, function(result) {
               $scope.productBacklogItems = result;
            });
        };
        $scope.loadAll();
        
        $scope.refresh = function () {
            $scope.loadAll();
        };
    });
