'use strict';

angular.module('productbacklogtimelineApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('sprint', {
                parent: 'entity',
                url: '/sprint',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'productbacklogtimelineApp.sprint.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/sprint/sprints.html',
                        controller: 'SprintController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('sprint');
                        return $translate.refresh();
                    }]
                }
            })
            .state('sprintDetail', {
                parent: 'entity',
                url: '/sprint/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'productbacklogtimelineApp.sprint.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/sprint/sprint-detail.html',
                        controller: 'SprintDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('sprint');
                        return $translate.refresh();
                    }]
                }
            });
    });
