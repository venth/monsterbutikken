monsterApp.factory('handlekurvService',[ '$q', '$http', function($q, $http) {
    var handlekurv = {};
    return {
        getHandlekurv: function(){
            //returnerer nåværende tilstand på handlekurv
            return $http.get('/service/handlekurv/');
        },

        leggTilMonster: function(monsternavn){
            //legger til et monster i handlekurven
            return $http.post('/service/handlekurv/leggTil/' + monsternavn);
        },

        fjernMonster: function(monsternavn){
            //fjerner et monster fra handlekurven. Om dette resulterer at det er ingen av typen igjen fjernes monsteret.
            return $http.post('/service/handlekurv/fjern/' + monsternavn);
        },

        bekreftOrdre: function(){
            return $http.post('/service/handlekurv/bekreftOrdre');
        },

        handlekurvSum: function(){
            return $http.get('/service/handlekurv/sum');
        }

    };
}]);

monsterApp.factory('loggInnService',[ '$q', '$http', function($q, $http) {

    return {
        loggInn: function(brukernavn){
            //logger inn kunden. I monsterbutikken stoler vi på våre kunder, så det er ikke noe passord. Brukernavnet settes på session på serversiden.
            return $http.post('/service/autentisering/logginn/' + brukernavn)
        },

        innloggetBruker: function(){
            //logger inn kunden. I monsterbutikken stoler vi på våre kunder, så det er ikke noe passord. Returnerer true om innlogging gikk ok.
            return $http.get('/service/autentisering/innloggetBruker')
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