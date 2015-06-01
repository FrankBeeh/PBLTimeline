'use strict';

angular.module('productbacklogtimelineApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


