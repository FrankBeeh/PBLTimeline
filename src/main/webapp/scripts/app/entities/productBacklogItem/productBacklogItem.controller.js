'use strict';

angular.module('productbacklogtimelineApp')
    .controller('ProductBacklogItemController', function ($scope, ProductBacklogItem, ProductTimestamp) {
        $scope.productBacklogItems = [];
        $scope.producttimestamps = ProductTimestamp.query();
        $scope.loadAll = function() {
            ProductBacklogItem.query(function(result) {
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

        $scope.save = function () {
            if ($scope.productBacklogItem.id != null) {
                ProductBacklogItem.update($scope.productBacklogItem,
                    function () {
                        $scope.refresh();
                    });
            } else {
                ProductBacklogItem.save($scope.productBacklogItem,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            ProductBacklogItem.get({id: id}, function(result) {
                $scope.productBacklogItem = result;
                $('#deleteProductBacklogItemConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            ProductBacklogItem.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteProductBacklogItemConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveProductBacklogItemModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.productBacklogItem = {pbiKey: null, title: null, description: null, estimate: null, state: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
