'use strict';

angular.module('productbacklogtimelineApp')
    .factory('ProductTimeline', function ($resource) {
        return $resource('api/productTimestamps/:id', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
