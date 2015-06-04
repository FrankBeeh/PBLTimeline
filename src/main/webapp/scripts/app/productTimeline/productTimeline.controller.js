'use strict';

angular.module('productbacklogtimelineApp').factory('ProductTimelineScope',
		function() {
			var selectedTimestamp;
			var referenceTimestamp;
			return {};
		});

var app = angular.module('productbacklogtimelineApp');
app.controller(
		'ProductTimelineController', 
		function($scope, $state, ProductTimelineScope, ProductTimestamp, Upload, Principal) {
			$scope.producttimestamps = ProductTimestamp.query();
			$scope.timelineScope = ProductTimelineScope;
	        Principal.identity(true).then(function(account) {
	            $scope.settingsAccount = account;
	        });

			$scope.reloadRoute = function() {
				$state.reload();
			}
			$scope.$watch('files', function() {
				$scope.upload($scope.files);
			});
			$scope.upload = function (files) {
		        if (files && files.length) {
		            for (var i = 0; i < files.length; i++) {
		                var file = files[i];
		                Upload.upload({
		                    url: 'api/upload',
		                    fields: {'username': $scope.settingsAccount.login},
		                    file: file
		                }).progress(function (evt) {
		                    var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
		                    console.log('progress: ' + progressPercentage + '% ' + evt.config.file.name);
		                }).success(function (data, status, headers, config) {
		                    console.log('file ' + config.file.name + 'uploaded. Response: ' + data);
		                });
		            }
		        }
		    };
		});
