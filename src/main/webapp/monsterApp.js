var monsterApp = angular.module("monsterButikken", ['ngRoute', 'ui.bootstrap'])
    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider
            .when('/',
            {
                controller: 'LoginController',
                templateUrl: 'login.html'
            })
            .when('/butikken',
            {
                controller: 'MonsterController',
                templateUrl: 'butikken.html'
            })
}]);