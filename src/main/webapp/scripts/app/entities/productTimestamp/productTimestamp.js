'use strict';

angular.module('productbacklogtimelineApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('productTimestamp', {
                parent: 'entity',
                url: '/productTimestamp',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'productbacklogtimelineApp.productTimestamp.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/productTimestamp/productTimestamps.html',
                        controller: 'ProductTimestampController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('productTimestamp');
                        return $translate.refresh();
                    }]
                }
            })
            .state('productTimestampDetail', {
                parent: 'entity',
                url: '/productTimestamp/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'productbacklogtimelineApp.productTimestamp.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/productTimestamp/productTimestamp-detail.html',
                        controller: 'ProductTimestampDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('productTimestamp');
                        return $translate.refresh();
                    }]
                }
            });
    });
