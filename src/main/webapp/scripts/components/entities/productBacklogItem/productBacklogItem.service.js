'use strict';

angular.module('productbacklogtimelineApp')
    .factory('ProductBacklogItem', function ($resource, DateUtils) {
        return $resource('api/productBacklogItems/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
