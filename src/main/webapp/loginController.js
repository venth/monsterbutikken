monsterApp.controller('LoginController', ['$scope', 'loggInnService', '$location',  function($scope, loggInnService, $location) {
    $scope.loggInn = function(){
        loggInnService.loggInn($scope.kundenavn).then(function(){
            $location.url('/butikken');
        })
    }
}]);