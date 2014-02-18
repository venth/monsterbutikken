Monsterbutikken
===============

Monsterbutikken er det perfekte sted å kjøpe nye monstre til din hemmelige base. Alle typer monstre til salgs!


Denne versjonen av monsterbutikken har en back end implementert i Akka persistence. Følgende domeneobjekter benyttes:

* Monster: Et skummelt og dødelig vesen du kan kjøpe i monsterbutikken. Identifiseres av monsternavn.
* Ordrelinje: Et kjøp av ett eller flere eksemplarer av en monstertype. Antall beskriver hvor mange monstre inngår. Pris er prisen per monster.
* Handlekurv: Foreløpig, ubekreftet ordre. Har en eller flere ordrelinjer. En kunde kan bare ha en handlekurv om gangen.
* Ordre bekreftelse: Omdanner innholdet i handlekurven til en ordre og tømmer handlekurven.
* Ordre: Et bekreftet kjøp av en eller flere monstre.
* Kunde: En person som handler i monsterbutikken. Identifiseres med kundenavn.
