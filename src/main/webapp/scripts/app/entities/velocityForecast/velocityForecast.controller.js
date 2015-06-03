'use strict';

angular.module('productbacklogtimelineApp')
    .controller('VelocityForecastController', function ($scope, VelocityForecast, ProductTimestamp) {
        $scope.velocityForecasts = [];
        $scope.producttimestamps = ProductTimestamp.query();
        $scope.loadAll = function() {
        	VelocityForecast.query({selectedTimestamp: '2', referenceTimestamp: '1'}, function(result) {
               $scope.velocityForecasts = result;
            });
        };
        $scope.loadAll();
        
        $scope.refresh = function () {
            $scope.loadAll();
        };
    });
