'use strict';

angular.module('productbacklogtimelineApp')
    .controller('SprintController', function ($scope, Sprint, ProductTimestamp) {
        $scope.sprints = [];
        $scope.producttimestamps = ProductTimestamp.query();
        $scope.loadAll = function() {
            Sprint.query(function(result) {
               $scope.sprints = result;
            });
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Sprint.get({id: id}, function(result) {
                $scope.sprint = result;
                $('#saveSprintModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.sprint.id != null) {
                Sprint.update($scope.sprint,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Sprint.save($scope.sprint,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Sprint.get({id: id}, function(result) {
                $scope.sprint = result;
                $('#deleteSprintConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Sprint.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteSprintConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveSprintModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.sprint = {name: null, startDate: null, endDate: null, capacityForecast: null, effortForecast: null, capacityDone: null, effortDone: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
