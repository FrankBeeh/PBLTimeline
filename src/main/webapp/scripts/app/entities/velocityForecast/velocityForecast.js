'use strict';

angular.module('productbacklogtimelineApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('velocityForecast', {
                parent: 'entity',
                url: '/velocityForecast',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'productbacklogtimelineApp.velocityForecast.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/velocityForecast/velocityForecast.html',
                        controller: 'VelocityForecastController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('velocityForecast');
                        return $translate.refresh();
                    }]
                }
            });
    });
