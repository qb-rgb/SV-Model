Quentin Baert  
Master MOCAD

# SV - Copasi

## Architecture

```
src
├── main
│   ├── java
│   ├── resources
│   └── scala
│       ├── model
│       │   ├── Reaction.scala
│       │   └── model.scala
│       └── sim
│           ├── Simulator.scala
│           ├── Test.scala
│           └── sim.scala
└── test
    ├── java
    ├── resources
    └── scala
```

* Le package `model` contient les classes qui permettent de modéliser un système de réactions chimiques.
* Le package `sim` contient les classes afin de simuler l'exécution d'un système de réactions chimiques grâce à l'algorithme de Gillespie.

## Exécution

Le code ayant été écrit en Scala, l'exécution du code nécessite l'outil SBT (http://www.scala-sbt.org/download.html).

La classe `Test.scala` contient le code necéssaire à l'exécution de la simulation d'un système de réactions chimiques. La modification du code présent dans cette classe permet de simuler un nouveau système.

Une fois que le code correspond au système à simuler, lancer les commandes suivantes depuis la racine du projet pour compiler et exécuter le code :
```
$ sbt compile
$ sbt run
```

Après l'exécution, deux fichiers sont crées : `sim.dat` et `sim.plot`. `sim.dat` contient les données de l'exécution alors que `sim.plot` est un script `gnuplot` qui permet de visualiser la simulation. Pour créer un fichier `.png` qui représente la simulation, exécuter la commande suivante depuis la racine du projet :
```
$ gnuplot sim.plot
```

Le script `clean.sh` permet de nettoyer la racine du projet des fichiers générés lors de l'exécution d'une simulation.
