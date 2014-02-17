Monsterbutikken
===============

Monsterbutikken er det perfekte sted å kjøpe nye monstre til din hemmelige base. Alle typer monstre til salgs!


Denne versjonen av monsterbutikken har en back end implementert i Akka persistence. Følgende domeneobjekter benyttes:

* Monster: Produkt til salgs i monsterbutikken.
* Ordrelinje: Et kjøp av et monster. Antall beskriver hvor mange monstre inngår.
* Handlekurv: Foreløpig, ubekreftet ordre. Har en eller flere ordrelinjer.
* Ordre: Et bekreftet kjøp av en eller flere monstre.
