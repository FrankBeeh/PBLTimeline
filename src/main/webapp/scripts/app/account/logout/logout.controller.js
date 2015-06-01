'use strict';

angular.module('productbacklogtimelineApp')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });
