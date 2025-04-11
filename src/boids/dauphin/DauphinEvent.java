import gui.GUISimulator;
import gui.ImageElement;
import java.awt.Point;
import java.util.Random;


public class DauphinEvent extends Event {
    private GUISimulator gui;
    private EventManager manager;
    private Boids Boids;
    private Boids boidsActual;
    private Boid[] boidsNext;
    private int rayonBoid;


    private String[] orientation = { "lib/image/couleurs/violet/ouest.png", "lib/image/couleurs/violet/est.png", "lib/image/couleurs/violet/nord.png", "lib/image/couleurs/violet/sud.png", "lib/image/couleurs/violet/nordest.png", "lib/image/couleurs/violet/sudouest.png",
        "lib/image/couleurs/violet/sudest.png", "lib/image/couleurs/violet/nordouest.png" };

    public DauphinEvent(long date, GUISimulator gui, EventManager manager,
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

    public void chasse(Dauphin dauphin, int i){
        if(dauphin.getBoidVivant())
{       Point v1;
        Point tmp = boidsActual.reglePredateur((Dauphin)dauphin, gui);
        if (tmp==null){
            Random rand=new Random();
            int switchState = rand.nextInt(20);
        
            if (switchState <5){
                 v1 = new Point(rand.nextInt(3)-1, rand.nextInt(3)-1);
            }
            else if (switchState==15){
                dauphin.setEtape(1);
                v1 = new Point(0, 0);
            }
            else{
                v1 = new Point(0, 0);
            }
        }
        else if (tmp.getX()<0 || tmp.getY()<0){
            dauphin.setEtape(1);
            v1 = new Point(0, 0);
        }
        else{
            v1 = new Point(tmp);
        }
        boidsNext[i].getVelocity().translate((int)(v1.getX()),(int)(v1.getY()));

        if (tmp==null){
            boidsActual.regle5(boidsNext[i], 25);
        }
        else {
            boidsActual.regle5(boidsNext[i], 30);
        }}
    }

    public void repos(Dauphin dauphin, int i){
        if(dauphin.getBoidVivant())
        {Point v1,v2,v3,v4;
        int distanceMax = 50;
        Random rand=new Random();
        int switchState = rand.nextInt(100);
        if(switchState==1){
            dauphin.setEtape(1);
            v1 = new Point(0, 0);
            v2 = new Point(0, 0);
            v3 = new Point(0, 0);
        }
        else{
            Boid b = boidsActual.getBoids()[i];
            v1 = new Point(boidsActual.regle1(b, distanceMax, gui));
            v2 = new Point(boidsActual.regle2(b));
            v3 = new Point(boidsActual.regle3(b));
            boidsActual.regleAccouplement(b);
        }
        v4 = new Point(boidsActual.regle4(dauphin, 50, gui.getPanelWidth()-50, 50, gui.getPanelHeight()-50));

        boidsNext[i].getVelocity().translate((int)(v1.getX()+v2.getX()+v3.getX()+v4.getX()),
            (int)(v1.getY()+v2.getY()+v3.getY()+v4.getY()));
        boidsActual.regle5(boidsNext[i], 20);}
    }


    public void reposdenaissance(Dauphin dauphin, int i){
    if(dauphin.getBoidNouveauNe()){
        Point v1,v2,v3,v4;
        int distanceMax = 50;
        Boid b = boidsActual.getBoids()[i];
        v1 = new Point(boidsActual.regle1(b, distanceMax, gui));
        v2 = new Point(boidsActual.regle2(b));
        v3 = new Point(boidsActual.regle3(b));        
        v4 = new Point(boidsActual.regle4(dauphin, 50, gui.getPanelWidth()-50, 50, gui.getPanelHeight()-50));

        boidsNext[i].getVelocity().translate((int)(v1.getX()+v2.getX()+v3.getX()+v4.getX()),
            (int)(v1.getY()+v2.getY()+v3.getY()+v4.getY()));
        boidsActual.regle5(boidsNext[i], 20);
    
    }}


    public void execute(){
        int distanceMax = 50;
        for (int i = 0; i < boidsActual.getNbre(); i++) {
            if (boidsActual.getBoids()[i] instanceof Dauphin ) {
                boidsActual.getBoids()[i].etablissementVoisinage(distanceMax, gui, boidsNext.length, boidsActual.getBoids());
                switch (boidsActual.getBoids()[i].getEtape()) {
                    case 0:
                    if(boidsActual.getBoids()[i].getBoidVivant()){
                        repos(((Dauphin)(boidsActual.getBoids()[i])), i);}
                    if(boidsActual.getBoids()[i].getBoidNouveauNe()){
                        reposdenaissance(((Dauphin)(boidsActual.getBoids()[i])), i);}
                    
                        break;
                    case 1:
                    if(boidsActual.getBoids()[i].getBoidVivant()){
                        chasse(((Dauphin)(boidsActual.getBoids()[i])), i);}
                        break;
                    default:
                        throw new IllegalArgumentException("Problème");
                }
                
                
            }
        }
        for (int i = 0; i < boidsActual.getNbre(); i++) {
            if (boidsActual.getBoids()[i] instanceof Dauphin ) {
        boidsNext[i].getPosition().setLocation(
                        Math.floorMod((int) (boidsActual.getBoids()[i].getPosition().getX() + boidsActual.getBoids()[i].getVelocity().getX()), gui.getPanelWidth()),
                        Math.floorMod((int) (boidsActual.getBoids()[i].getPosition().getY() + boidsActual.getBoids()[i].getVelocity().getY()), gui.getPanelHeight()));
                        int img = boidsNext[i].getImg();
                if(boidsActual.getBoids()[i].getBoidVivant()||boidsActual.getBoids()[i].getBoidNouveauNe()){
                gui.addGraphicalElement(new ImageElement((int) (boidsNext[i].getPosition().getX()),
                    (int) (boidsNext[i].getPosition().getY()), orientation[img - 1], 14, 14, gui));}
        }
    }
        boidsActual.setBoids(boidsNext);
        boidsActual.resetVoisinage();
        manager.addEvent(
                new DauphinEvent(manager.getCurDate() + 1, gui, manager, Boids, boidsActual, boidsNext, rayonBoid));
    }
}