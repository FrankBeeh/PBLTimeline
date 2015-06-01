'use strict';

angular.module('productbacklogtimelineApp')
    .controller('ProductTimestampController', function ($scope, ProductTimestamp, Sprint) {
        $scope.productTimestamps = [];
        $scope.sprints = Sprint.query();
        $scope.loadAll = function() {
            ProductTimestamp.query(function(result) {
               $scope.productTimestamps = result;
            });
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            ProductTimestamp.get({id: id}, function(result) {
                $scope.productTimestamp = result;
                $('#saveProductTimestampModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.productTimestamp.id != null) {
                ProductTimestamp.update($scope.productTimestamp,
                    function () {
                        $scope.refresh();
                    });
            } else {
                ProductTimestamp.save($scope.productTimestamp,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            ProductTimestamp.get({id: id}, function(result) {
                $scope.productTimestamp = result;
                $('#deleteProductTimestampConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            ProductTimestamp.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteProductTimestampConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveProductTimestampModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.productTimestamp = {dateTime: null, name: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
