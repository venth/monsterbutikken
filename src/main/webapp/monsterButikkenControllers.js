monsterApp.controller('MonsterController', ['$scope', '$http', '$modal', 'monsterService', 'handlekurvService', 'loggInnService', function($scope, $http, $modal, monsterService, handlekurvService, loggInnService) {

    loggInnService.innloggetBruker().then(function(data){
        $scope.brukernavn = data;
    });

    $scope.handlekurvTom = true;

    $scope.$watch('handlekurv', function() {
        var handlekurvTom = true;
        for (var prop in $scope.handlekurv){
            if (handlekurv.hasOwnProperty(prop))
                $scope.handlekurvTom = false;
        }
        $scope.handlekurvTom = handlekurvTom;
    });

    $scope.leggTilMonster = function(monsternavn, e){
        $scope.takkForKjop = false;
        if (e) {
            e.preventDefault();
            e.stopPropagation();
        }
        handlekurvService.leggTilMonster($scope.brukernavn, monsternavn).then(function(){
            handlekurvService.getHandlekurv($scope.brukernavn).then(function(data){
                $scope.handlekurv = data;
            })
        });
    };

   $scope.fjernMonster = function(monsternavn){
        handlekurvService.fjernMonster($scope.brukernavn. monsternavn).then(function(){
            handlekurvService.getHandlekurv($scope.brukernavn).then(function(data){
                $scope.handlekurv = data;
            })
        });
    };

    $scope.$watch('handlekurv', function() {
        if ($scope.brukernavn){
            handlekurvService.handlekurvSum($scope.brukernavn).then(function(data){
                $scope.handlekurvSum =  data.sum();
            })
        }
    });

    $scope.betal = function(){
        if (!$scope.brukernavn)
            $scope.loggInn().then(function() {
                betal();
            });
        else
            betal();
    };

    function betal(){
        var modalInstance = $modal.open({
            templateUrl: 'kjopModal.html',
            controller: 'KjopModalCtrl',
            resolve: {
                handlekurv: function () {
                    return handlekurvService.getHandlekurv($scope.brukernavn);
                },
                sum: function () {
                    return handlekurvService.getHandlekurvSum();
                }
            }
        });

        modalInstance.result.then(function () {
            handlekurvService.betal().then(function(data){
                $scope.handlekurv = data;
                $scope.takkForKjop = true;
            });
        });
    }

    $scope.monstre = monsterService.getMonstre().success(function(data){
        $scope.monstre = data;
    });

}]);

monsterApp.controller('KjopModalCtrl', ['$scope', '$modalInstance', 'handlekurv', 'sum', function($scope, $modalInstance, handlekurv, sum) {
    $scope.handlekurv = handlekurv;

    $scope.sum = sum;

    $scope.bekreftKjop = function () {
        $modalInstance.close();
    };

    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };
}]);
