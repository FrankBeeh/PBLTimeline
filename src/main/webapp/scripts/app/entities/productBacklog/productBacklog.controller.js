'use strict';

angular.module('productbacklogtimelineApp')
    .controller('ProductBacklogController', function ($scope, ProductBacklog, ProductTimestamp) {
        $scope.productBacklogItems = [];
        $scope.producttimestamps = ProductTimestamp.query();
        $scope.loadAll = function() {
            ProductBacklog.query(function(result) {
               $scope.productBacklogItems = result;
            });
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            ProductBacklogItem.get({id: id}, function(result) {
                $scope.productBacklogItem = result;
                $('#saveProductBacklogItemModal').modal('show');
            });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveProductBacklogItemModal').modal('hide');
            $scope.clear();
        };
    });
