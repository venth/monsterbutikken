monsterApp.factory('handlekurvService',[ '$q', '$http', function($q, $http) {
    return {
        getHandlekurv: function(){
            //returnerer nåværende tilstand på handlekurv
            return $http.get('/service/handlekurv/');
        },

        leggTilMonster: function(monsternavn){
            //legger til et monster i handlekurven. Om det er en eksisterende ordrelinje økes antallet, ellers opprettes en ordrelinje.
            return $http.post('/service/handlekurv/leggTil/' + monsternavn);
        },

        fjernMonster: function(monsternavn){
            //fjerner et monster fra handlekurven. Om dette resulterer at det er ingen av typen igjen fjernes ordrelinjen.
            return $http.post('/service/handlekurv/fjern/' + monsternavn);
        },

        bekreftOrdre: function(){
            //oppretter en ordre basert på innholdet i handlekurven, og tømmer denne.
            return $http.post('/service/handlekurv/bekreftOrdre');
        },

        handlekurvSum: function(){
            //henter sum av pris * antall for alle ordrelinjer i handlekurven.
            return $http.get('/service/handlekurv/sum');
        }

    };
}]);

monsterApp.factory('autentiseringService',[ '$q', '$http', function($q, $http) {
    return {
        loggInn: function(kundenavn){
            //logger inn kunden. I monsterbutikken stoler vi på våre kunder, så det er ikke noe passord. Kundenavnet settes på session på serversiden.
            return $http.post('/service/autentisering/logginn/' + kundenavn)
        },

        loggUt: function(){
            //logger kunden ut, fjerner kundenavn fra session
            return $http.post('/service/autentisering/loggut')
        },

        innloggetBruker: function(){
            //henter kundenavnet til innlogget kunde
            return $http.get('/service/autentisering/innloggetKunde')
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