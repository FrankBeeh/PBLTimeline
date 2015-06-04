'use strict';

angular.module('productbacklogtimelineApp')
    .controller('VelocityForecastController', function ($scope, VelocityForecast, ProductTimestamp, ProductTimelineScope) {
        $scope.velocityForecasts = [];
        $scope.producttimestamps = ProductTimestamp.query();
        $scope.loadAll = function() {
        	VelocityForecast.query({selectedTimestamp: ProductTimelineScope.selectedTimestamp, referenceTimestamp: ProductTimelineScope.referenceTimestamp}, function(result) {
               $scope.velocityForecasts = result;
            });
        };
        $scope.loadAll();
        
        $scope.refresh = function () {
            $scope.loadAll();
        };
    });
