monsterApp.controller('LoginController', ['$scope', 'loggInnService', '$location',  function($scope, loggInnService, $location) {
    $scope.loggInn = function(){
        loggInnService.loggInn($scope.brukernavn).then(function(){
            $location.url('/butikken');
        })
    }
}]);