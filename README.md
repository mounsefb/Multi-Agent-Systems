Guide d'utilisation du Makefile :
Ce Makefile a été conçu pour simplifier la compilation et l'exécution de différents tests de simulateurs, tels que des automates cellulaires et des simulations de comportements d'entités (boids, balles, etc.).

####################################################################################################

Prérequis
Assurez-vous d'avoir les éléments suivants installés sur votre système :

Java Development Kit (JDK) version 1.8

####################################################################################################

Documentation supplémentaire (Javadoc)

La documentation JavaDoc du code source est disponible dans le répertoire `additionalDoc`. Pour accéder à la documentation générée, ouvrez le fichier `index.html` dans votre navigateur préféré. Vous pouvez également naviguer directement vers le répertoire `additionalDoc` et ouvrir le fichier `index.html` à partir de là.

cd additionalDoc
open index.html (ou utilisez le navigateur de votre choix)

####################################################################################################

Structure du Projet
Le projet est organisé comme suit :

src/: Contient les fichiers source Java.
lib/: Contient le fichier JAR (gui.jar) nécessaire pour la partie graphique et les différentes images utilisées.
bin/: C'est le répertoire où seront générés les fichiers compilés.

####################################################################################################

Utilisation du Makefile

Pour compiler et exécuter chaque test individuellement des commandes sont prévues. Par exemple, pour le test des boids, utilisez la commande :

make boids

De même, pour les autres tests tels que balls, conway, immigration, schelling, et invader, utilisez les commandes respectives :

make balls
make conway
make immigration
make schelling
make invader

Quitter une simulation : 
    NE QUITTEZ PAS UNE SIMULAITON EN FAISANT UN CTRL+C DANS LE TERMINAL
    cela empêchera le nettoyage automatique des fichiers générés lors de l'éxecution
    à la place quittez la simulation en appuyant sur le bouton "QUITTER" de l'interface graphique
    ou à partir du bouton de fermeture de fenêtre en haut à droite

Si jamais vous avez quitté avec un CTRL+C vous pouvez nettoyez manuellement le dépôt avec la commande :

make clean

Cela supprimera le répertoire bin/ ainsi que tout fichier généré.

####################################################################################################

Remarques
Assurez-vous que le fichier lib/gui.jar est correctement présent dans le répertoire lib/.
Le Makefile utilise des règles génériques pour la compilation des fichiers Java, ce qui simplifie l'ajout de nouveaux tests.
C'est tout ! Vous êtes prêt à explorer et à tester les différents simulateurs en utilisant ce Makefile.
