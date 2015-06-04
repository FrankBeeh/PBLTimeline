'use strict';

angular.module('productbacklogtimelineApp')
    .factory('ProductBacklog', function ($resource) {
        return $resource('api/productBacklog', {}, {
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
        });
    });
