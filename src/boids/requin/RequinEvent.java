import gui.GUISimulator;
import gui.ImageElement;
import java.awt.Point;
import java.util.Random;

public class RequinEvent extends Event {
    private GUISimulator gui;
    private EventManager manager;
    private Boids Boids;
    private Boids boidsActual;
    private Boid[] boidsNext;
    private int rayonBoid;
    static public enum ETAPE{FAIM, CHASSE, REPOS};
    
    private String[] orientationChasse = { "lib/image/couleurs/rouge/ouest.png", "lib/image/couleurs/rouge/est.png", "lib/image/couleurs/rouge/nord.png", "lib/image/couleurs/rouge/sud.png", "lib/image/couleurs/rouge/nordest.png", "lib/image/couleurs/rouge/sudouest.png",
    "lib/image/couleurs/rouge/sudest.png", "lib/image/couleurs/rouge/nordouest.png" };
    private String[] orientationRepos = { "lib/image/couleurs/vert/ouest.png", "lib/image/couleurs/vert/est.png", "lib/image/couleurs/vert/nord.png", "lib/image/couleurs/vert/sud.png", "lib/image/couleurs/vert/nordest.png", "lib/image/couleurs/vert/sudouest.png",
    "lib/image/couleurs/vert/sudest.png", "lib/image/couleurs/vert/nordouest.png" };

    public RequinEvent(long date, GUISimulator gui, EventManager manager,
            Boids Boids, Boids boidsActual,
            Boid[] boidsNext,
            int rayonBoid) {
        super(date);
        this.manager = manager;
        this.Boids = Boids;
        this.boidsActual = boidsActual;
        this.boidsNext = boidsNext;
        this.rayonBoid = rayonBoid;
        this.gui = gui;
    }

   

    public void chasse(Requin Requin, int i){
        Point v1;
        Point tmp = boidsActual.reglePredateur((Requin)Requin, gui);
        if (tmp==null){
            Random rand=new Random();
            int switchState = rand.nextInt(10);
        
            if (switchState <5){
                 v1 = new Point(rand.nextInt(3)-1, rand.nextInt(3)-1);
            }
            else{
                v1 = new Point(0, 0);
            }
            int dodo = rand.nextInt(100);
            if(dodo==1){
                Requin.setEtape(0);
            }
        }
        else {
            v1 = new Point(tmp);
        }
        boidsNext[i].getVelocity().translate((int)(v1.getX()),(int)(v1.getY()));

        if (tmp==null){
            boidsActual.regle5(boidsNext[i], 10);
        }
        else {
            boidsActual.regle5(boidsNext[i], 30);
        }
        boidsNext[i].getPosition().setLocation(
                Math.floorMod((int) (boidsActual.getBoids()[i].getPosition().getX() + boidsActual.getBoids()[i].getVelocity().getX()), gui.getPanelWidth()),
                Math.floorMod((int) (boidsActual.getBoids()[i].getPosition().getY() + boidsActual.getBoids()[i].getVelocity().getY()), gui.getPanelHeight()));
                int img = boidsNext[i].getImg();
        gui.addGraphicalElement(new ImageElement((int) (boidsNext[i].getPosition().getX()),
            (int) (boidsNext[i].getPosition().getY()), orientationChasse[img - 1], 20, 20, gui));
    }

     public void repos(Requin Requin, int i){
        Point v1,v4;
        Random rand=new Random();
        int switchState = rand.nextInt(50);
        
        if (10<=switchState && switchState<=20){
            v1 = new Point(rand.nextInt(3)-1, rand.nextInt(3)-1);
        }
        else{
            if(switchState==1){
                Requin.setEtape(1);
            }
            v1 = new Point(0, 0);
        }
        v4 = new Point(boidsActual.regle4(Requin, 50, gui.getPanelWidth()-50, 50, gui.getPanelHeight()-50));

        boidsNext[i].getVelocity().translate((int)(v1.getX()+v4.getX()),(int)(v1.getY()+v4.getX()));
        boidsActual.regle5(boidsNext[i], 5);
        boidsNext[i].getPosition().setLocation(
                Math.floorMod((int) (boidsActual.getBoids()[i].getPosition().getX() + boidsActual.getBoids()[i].getVelocity().getX()), gui.getPanelWidth()),
                Math.floorMod((int) (boidsActual.getBoids()[i].getPosition().getY() + boidsActual.getBoids()[i].getVelocity().getY()), gui.getPanelHeight()));
                int img = boidsNext[i].getImg();
        gui.addGraphicalElement(new ImageElement((int) (boidsNext[i].getPosition().getX()),
            (int) (boidsNext[i].getPosition().getY()), orientationRepos[img - 1], 20, 20, gui));
    }

    public void execute(){
        int distanceMax = 50;
        for (int i = 0; i < boidsActual.getNbre(); i++) {
            if (boidsActual.getBoids()[i] instanceof Requin && boidsActual.getBoids()[i].getBoidVivant()) {
                boidsActual.getBoids()[i].etablissementVoisinage(distanceMax, gui, boidsNext.length, boidsActual.getBoids());
                switch (boidsActual.getBoids()[i].getEtape()) {
                    case 0:
                        repos(((Requin)(boidsActual.getBoids()[i])), i);
                        break;
                    case 1:
                        chasse(((Requin)(boidsActual.getBoids()[i])), i);
                        break;
                    default:
                        throw new IllegalArgumentException("Problème");
                }
                
            }
        }
        boidsActual.setBoids(boidsNext);
        boidsActual.resetVoisinage();
        manager.addEvent(
                new RequinEvent(manager.getCurDate() + 1, gui, manager, Boids, boidsActual, boidsNext, rayonBoid));
    }
}