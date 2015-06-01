'use strict';

angular.module('productbacklogtimelineApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('productBacklog', {
                parent: 'entity',
                url: '/productBacklog',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'productbacklogtimelineApp.productBacklog.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/productBacklog/productBacklogs.html',
                        controller: 'ProductBacklogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('productBacklog');
                        return $translate.refresh();
                    }]
                }
            });
    });
