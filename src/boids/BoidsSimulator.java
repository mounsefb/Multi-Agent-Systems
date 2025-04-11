import gui.GUISimulator;
import java.awt.Color;
import gui.Text;
import gui.ImageElement;
import gui.Simulable;

public class BoidsSimulator implements Simulable {

    private EventManager manager = new EventManager();
    private Boids boidsActual;
    private GUISimulator gui;
    private Boid[] boidsNext;
    private int rayonBoid;

    @Override
    public void next() {
        gui.reset();
        manager.next();
        boidsActual.nombreMortEtNaissance(gui);
        gui.addGraphicalElement(new Text(gui.getPanelWidth()-100, 5, Color.WHITE, new String("Nombre de requin mort :"+boidsActual.getNbreMort()[0])));
        gui.addGraphicalElement(new Text(gui.getPanelWidth()-100, 25, Color.WHITE, new String("Nombre de dauphin mort :"+boidsActual.getNbreMort()[1])));
        gui.addGraphicalElement(new Text(gui.getPanelWidth()-100, 45, Color.WHITE, new String("Nombre de poisson mort :"+boidsActual.getNbreMort()[2])));
        gui.addGraphicalElement(new Text(gui.getPanelWidth()-100, 65, Color.WHITE, new String("Nombre de dauphin né :"+boidsActual.getNbreNaissance()[1])));
    }

    @Override
    public void restart() {
        gui.reset();
        boidsActual.reInit();
        manager.restart(); 
        boidsActual = new Boids(boidsActual.getNbre(), gui.getPanelWidth(), gui.getPanelHeight());
        boidsNext = new Boid[boidsActual.getNbre()];
        for (int i = 0; i < boidsActual.getNbre(); i++) {
            boidsNext[i] = new Boid(boidsActual.getBoids()[i]);
        }
        gui.addGraphicalElement(new ImageElement(0,0,"lib/image/fondBoids.jpg", gui.getWidth(),gui.getHeight()-70, gui));  
        boidsActual.resetMort();
        boidsActual.resetNaissance();
        manager.addEvent(new PoissonEvent(0, gui, manager, boidsActual, boidsActual, boidsNext, rayonBoid, null));
        manager.addEvent(new DauphinEvent(0, gui, manager, boidsActual, boidsActual, boidsNext, rayonBoid));
        manager.addEvent(new RequinEvent(0, gui, manager, boidsActual, boidsActual, boidsNext, rayonBoid));
    }

    public BoidsSimulator(GUISimulator gui, int nbreBoid) {
        boidsActual = new Boids(nbreBoid, gui.getPanelWidth(), gui.getPanelHeight());
        boidsNext = new Boid[boidsActual.getNbre()];
        for (int i = 0; i < nbreBoid; i++) {
            boidsNext[i] = new Boid(boidsActual.getBoids()[i]);
            boidsNext[i].getPosition().translate((int)boidsActual.getBoids()[i].getVelocity().getX(),(int)boidsActual.getBoids()[i].getVelocity().getY());
        }
        this.gui = gui;
        rayonBoid = 1;
        boidsActual.resetMort();
        boidsActual.resetNaissance();
        gui.addGraphicalElement(new ImageElement(0,0, "lib/image/fondBoids.jpg", gui.getWidth(),gui.getHeight()-70, gui)); 
        manager.addEvent(new RequinEvent(0, gui, manager, boidsActual, boidsActual, boidsNext, rayonBoid));
        manager.addEvent(new PoissonEvent(0, gui, manager, boidsActual, boidsActual, boidsNext, rayonBoid, null));
        manager.addEvent(new DauphinEvent(0, gui, manager, boidsActual, boidsActual, boidsNext, rayonBoid));
    }
}
