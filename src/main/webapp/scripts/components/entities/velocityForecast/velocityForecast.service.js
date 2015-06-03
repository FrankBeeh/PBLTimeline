'use strict';

angular.module('productbacklogtimelineApp')
    .factory('VelocityForecast', function ($resource, DateUtils) {
        return $resource('api/velocityForecast', {}, {
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
        });
    });
