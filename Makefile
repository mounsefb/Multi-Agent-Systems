compileTestInvader:
	javac -d bin -classpath lib/gui.jar src/invader/TestInvader.java

runTestInvader: compileTestInvader
	java -classpath bin:lib/gui.jar TestInvader

ballsDeplace:
	mv src/balls/*.java src

grilleDeplace:
	mv src/grille/Grille.java src

conwayDeplace: grilleDeplace
	mv src/grille/conway/*.java src
	
immigrationDeplace: grilleDeplace
	mv src/grille/immigration/*.java src

schellingDeplace: grilleDeplace
	mv src/grille/schelling/*.java src

eventDeplace:
	mv src/event/*.java src

boidsDeplace:
	mv src/boids/*.java src
	mv src/boids/poisson/*.java src
	mv src/boids/dauphin/*.java src
	mv src/boids/requin/*.java src
	
BIN_DIR := bin
ifdef BIN_DIR
    DIR_EXISTS := $(shell test -d $(BIN_DIR))
else
    DIR_EXISTS :=
endif

compile: check_bin
	javac -d $(BIN_DIR) -classpath lib/gui.jar src/*.java

check_bin:
ifdef DIR_EXISTS
else
	mkdir -p $(BIN_DIR)
endif

eventReplace: 
	mv src/Event*.java src/event
	mv src/TestEvent*.java src/event
	
grilleReplace:
	mv src/Grille.java src/grille/
	
conwayReplace: grilleReplace
	mv src/*Conway*.java src/grille/conway
	
immigrationReplace: grilleReplace
	mv src/*Immigration*.java src/grille/immigration

schellingReplace: grilleReplace
	mv src/*Schelling*.java src/grille/schelling

ballsReplace:
	mv src/*Balls*.java src/balls
	
boidsReplace:
	mv src/Boid.java src/boids
	mv src/*Boids*.java src/boids
	mv src/*Poisson*.java src/boids/poisson
	mv src/*Dauphin*.java src/boids/dauphin
	mv src/*Requin*.java src/boids/requin

boidsCompileandRun: boidsDeplace eventDeplace compile boidsReplace eventReplace
	java -classpath bin:lib/gui.jar TestBoidsSimulator 

boids: boidsCompileandRun clean

ballsCompileandRun: ballsDeplace eventDeplace compile ballsReplace eventReplace
	java -classpath bin:lib/gui.jar TestBallsSimulator 

balls: ballsCompileandRun clean

conwayCompileandRun: conwayDeplace eventDeplace compile conwayReplace eventReplace
	java -classpath bin:lib/gui.jar TestConwaySimulator 

conway: conwayCompileandRun clean

immigrationCompileandRun: immigrationDeplace eventDeplace compile immigrationReplace eventReplace
	java -classpath bin:lib/gui.jar TestImmigrationSimulator 

immigration: immigrationCompileandRun clean

schellingCompileandRun: schellingDeplace eventDeplace compile schellingReplace eventReplace
	java -classpath bin:lib/gui.jar TestSchellingSimulator

schelling: schellingCompileandRun clean

invader: runTestInvader clean

clean:
	rm -rf bin docs
