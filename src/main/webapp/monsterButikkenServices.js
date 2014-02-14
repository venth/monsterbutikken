monsterApp.factory('handlekurvService',[ '$q', '$http', function($q, $http) {
    var handlekurv = {};
    return {
        getHandlekurv: function(brukernavn){
            //returnerer nåværende tilstand på handlekurv
            return $http.get('/service/handlekurv/' + brukernavn);
        },

        leggTilMonster: function(brukernavn, monsternavn){
            //legger til et monster i handlekurven
            return $http.post('/service/handlekurv/' + brukernavn + '/leggTil/' + monsternavn);
        },

        fjernMonster: function(brukernavn, monsternavn){
            //fjerner et monster fra handlekurven. Om dette resulterer at det er ingen av typen igjen fjernes monsteret.
            return $http.post('/service/handlekurv/' + brukernavn + '/fjern/' + monsternavn);
        },

        bekreftOrdre: function(){
            return $http.post('/service/handlekurv/' + brukernavn + '/bekreftOrdre' + monsternavn);
        },

        handlekurvSum: function(brukernavn){
            return $http.get('/service/handlekurv/' + brukernavn + '/handlekurvSum');
        }

    };
}]);

monsterApp.factory('loggInnService',[ '$q', function($q) {
    var bruker;
    return {
        loggInn: function(brukernavn){
            //logger inn kunden. I monsterbutikken stoler vi på våre kunder, så det er ikke noe passord. Returnerer true om innlogging gikk ok.
            this.bruker = brukernavn;
            var deferred = $q.defer();
            deferred.resolve(true);
            return deferred.promise;
        },

        innloggetBruker: function(){
            //logger inn kunden. I monsterbutikken stoler vi på våre kunder, så det er ikke noe passord. Returnerer true om innlogging gikk ok.
            var deferred = $q.defer();
            deferred.resolve(bruker);
            return deferred.promise;
        }
    }
}]);


monsterApp.factory('monsterService', ['$http', function($http) {
    return {
        getMonstre: function() {
            return $http.get('/service/monstre').error(function(){
                    console.log("klarte ikke hente monstre fra server, laster fra klient")
                    $scope.monstre = [
                        {navn: "Ao (skilpadde)", pris: 100000},
                        {navn: "Bakeneko", pris: 120000},
                        {navn: "Basilisk", pris: 175000},
                        {navn: "Det erymanthiske villsvin", pris: 100},
                        {navn: "Griff", pris: 100},
                        {navn: "Hamløper", pris: 100},
                        {navn: "Hippogriff", pris: 100},
                        {navn: "Hydra", pris: 100},
                        {navn: "Kentaur", pris: 100},
                        {navn: "Kerberos", pris: 100},
                        {navn: "Kraken", pris: 100},
                        {navn: "Mannbjørn", pris: 100},
                        {navn: "Mantikora", pris: 100},
                        {navn: "Margyge", pris: 100},
                        {navn: "Marmæle", pris: 100},
                        {navn: "Minotauros", pris: 100},
                        {navn: "Nekomusume", pris: 100},
                        {navn: "Rokk", pris: 100},
                        {navn: "Seljordsormen", pris: 100},
                        {navn: "Sfinks", pris: 100},
                        {navn: "Sirene", pris: 100},
                        {navn: "Sjøorm", pris: 100},
                        {navn: "Succubus", pris: 100},
                        {navn: "Valravn", pris: 100},
                        {navn: "Vampyr", pris: 100},
                        {navn: "Varulv", pris: 100}
                    ];
                }
            )
        }
    };
}]);