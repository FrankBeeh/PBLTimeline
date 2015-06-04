'use strict';


angular.module('productbacklogtimelineApp').factory('ProductTimelineScope',
		function() {
			var selectedTimestamp;
			var referenceTimestamp;
			return {};
		});

angular.module('productbacklogtimelineApp').controller(
		'ProductTimelineController',
		function($scope, $state, ProductTimelineScope, ProductTimestamp) {
			$scope.producttimestamps = ProductTimestamp.query();
			$scope.timelineScope = ProductTimelineScope;
			$scope.reloadRoute = function() {
			   $state.reload();
			}
		});

