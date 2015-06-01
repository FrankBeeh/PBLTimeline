'use strict';

angular.module('productbacklogtimelineApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('productBacklogItem', {
                parent: 'entity',
                url: '/productBacklogItem',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'productbacklogtimelineApp.productBacklogItem.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/productBacklogItem/productBacklogItems.html',
                        controller: 'ProductBacklogItemController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('productBacklogItem');
                        return $translate.refresh();
                    }]
                }
            })
            .state('productBacklogItemDetail', {
                parent: 'entity',
                url: '/productBacklogItem/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'productbacklogtimelineApp.productBacklogItem.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/productBacklogItem/productBacklogItem-detail.html',
                        controller: 'ProductBacklogItemDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('productBacklogItem');
                        return $translate.refresh();
                    }]
                }
            });
    });
