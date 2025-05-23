
# Guide d'utilisation du Makefile

Ce Makefile a été conçu pour simplifier la compilation et l'exécution de différents tests de simulateurs, tels que des automates cellulaires et des simulations de comportements d'entités (boids, balles, etc.).

## Prérequis

Assurez-vous d'avoir les éléments suivants installés sur votre système :
- Java Development Kit (JDK) version 1.8

## Génération de la documentation (Javadoc)

La documentation du code source peut être générée automatiquement à l'aide de l'outil **Javadoc**.  
Pour ce faire, exécutez la commande suivante à la racine du projet :

```
javadoc -d doc -sourcepath src -subpackages gui simulator events
```

Cela créera la documentation dans un répertoire `doc/`, que vous pourrez consulter en ouvrant le fichier `doc/index.html` dans votre navigateur préféré.

> Vous pouvez adapter la liste des sous-packages (`gui`, `simulator`, etc.) en fonction de l’organisation exacte de votre code source.

## Structure du Projet

Le projet est organisé comme suit :
- `src/`: Contient les fichiers source Java.
- `lib/`: Contient le fichier JAR (`gui.jar`) nécessaire pour la partie graphique et les différentes images utilisées.
- `bin/`: C'est le répertoire où seront générés les fichiers compilés.

## Utilisation du Makefile

Pour compiler et exécuter chaque test individuellement, des commandes spécifiques sont prévues :

```
make boids
make balls
make conway
make immigration
make schelling
make invader
```

### Quitter une simulation

**IMPORTANT** 

NE QUITTEZ PAS UNE SIMULATION EN FAISANT UN CTRL+C DANS LE TERMINAL.  
Cela empêchera le nettoyage automatique des fichiers générés lors de l'exécution.

À la place, quittez la simulation en :
- Appuyant sur le bouton "QUITTER" de l'interface graphique
- Ou en utilisant le bouton de fermeture de fenêtre en haut à droite

Si vous avez quitté avec un CTRL+C, vous pouvez nettoyer manuellement le dépôt avec la commande :
```
make clean
```
Cela supprimera le répertoire `bin/` ainsi que tout fichier généré.

## Remarques

- Assurez-vous que le fichier `lib/gui.jar` est correctement présent dans le répertoire `lib/`.
- Le Makefile utilise des règles génériques pour la compilation des fichiers Java, ce qui simplifie l'ajout de nouveaux tests.

C'est tout ! Vous êtes prêt à explorer et à tester les différents simulateurs en utilisant ce Makefile.
