
import gui.GUISimulator;
import gui.ImageElement;
import java.awt.Point;
import java.util.Iterator;

public class PoissonEvent extends Event {
    private GUISimulator gui;
    private EventManager manager;
    private Boids Boids;
    private Boids boidsActual;
    private Boid[] boidsNext;
    private int rayonBoid;
    private Boid proie;


    private String[] orientation = { "lib/image/couleurs/bleu/ouest.png", "lib/image/couleurs/bleu/est.png", "lib/image/couleurs/bleu/nord.png", "lib/image/couleurs/bleu/sud.png", "lib/image/couleurs/bleu/nordest.png", "lib/image/couleurs/bleu/sudouest.png",
    "lib/image/couleurs/bleu/sudest.png", "lib/image/couleurs/bleu/nordouest.png" };

    public PoissonEvent(long date, GUISimulator gui, EventManager manager,
            Boids Boids, Boids boidsActual,
            Boid[] boidsNext,
            int rayonBoid, Boid proie) {
        super(date);
        this.manager = manager;
        this.Boids = Boids;
        this.boidsActual = boidsActual;
        this.boidsNext = boidsNext;
        this.rayonBoid = rayonBoid;
        this.gui = gui;
        this.proie=proie;
    }

    public Point reglePredateurPoisson(Poisson bj, GUISimulator gui) {
        if(manager.getCurDate()<200){
            return null;
        }
        if(proie.getEtape()==1) return null;
        if(proie.getBoidVivant()==false){
            bj.setEtape(0);
            return null;
        }

        Point pcj = new Point(0, 0);
        double distance = Math.sqrt(Math.pow(proie.getPosition().getX()-bj.getPosition().getX(),2) 
            + Math.pow(proie.getPosition().getY()-bj.getPosition().getY(),2));
        if (((Requin)proie).getPredateur()>=10){
            if (distance < 10 && proie.getBoidVivant()) {
                bj.setEtape(0);
                proie.setBoidMort();
                boidsActual.addMortRequin();
                proie.setTempsmort(0);
                
                return null;
            }
            else if (distance > 100) {
                bj.setNearest(null);
                return null;
            }
        }
        pcj.setLocation((proie.getPosition().getX() - bj.getPosition().getX()) / 2, (proie.getPosition().getY() - bj.getPosition().getY()) / 2);
        return pcj;
    }

    public void chasse(Poisson poisson, int i){
        Point v1;
        Point tmp = reglePredateurPoisson(poisson, gui);
        if (tmp==null){
            poisson.setEtape(0);
            v1 = new Point(0, 0);
        }
        else {
            v1 = new Point(tmp);
        }
        
        boidsNext[i].getVelocity().translate((int)(v1.getX()),(int)(v1.getY()));

        if (tmp==null){
            boidsActual.regle5(boidsNext[i], 20);
        }
        else {
            boidsActual.regle5(boidsNext[i], 5);
        }
    }

    public void repos(Poisson poisson, int i){
        int distanceMax = 50;
        Point v1,v2,v3,v4;
        boolean chasse = false;
        Iterator<Boid> iterator = poisson.getVoisinage().iterator();
        while (iterator.hasNext()){
            Boid element = iterator.next();
            if(element instanceof Requin && element.getEtape()==0){
                ((Requin)element).addPredateur();
                proie=element;
                chasse=true;
                break;
            }
        }
        if(chasse){
            poisson.setEtape(1);
            v1 = new Point(0, 0);
            v2 = new Point(0, 0);
            v3 = new Point(0, 0);
        }
        else{
            Boid b = boidsActual.getBoids()[i];
            v1 = new Point(boidsActual.regle1(b, distanceMax, gui));
            v2 = new Point(boidsActual.regle2(b));
            v3 = new Point(boidsActual.regle3(b));
        }
        v4 = new Point(boidsActual.regle4(poisson, 50, gui.getPanelWidth()-50, 50, gui.getPanelHeight()-50));

        boidsNext[i].getVelocity().translate((int)(v1.getX()+v2.getX()+v3.getX()+v4.getX()),
            (int)(v1.getY()+v2.getY()+v3.getY()+v4.getY()));
        boidsActual.regle5(boidsNext[i], 20);
    }

    public void execute(){
        int distanceMax = 50;
        for (int i = 0; i < boidsActual.getNbre(); i++) {
            if (boidsActual.getBoids()[i] instanceof Poisson && boidsActual.getBoids()[i].getBoidVivant()) {
                boidsActual.getBoids()[i].etablissementVoisinage(distanceMax, gui, boidsNext.length, boidsActual.getBoids());
                switch (boidsActual.getBoids()[i].getEtape()) {
                    case 0:
                        repos(((Poisson)(boidsActual.getBoids()[i])), i);
                        break;
                    case 1:
                        chasse(((Poisson)(boidsActual.getBoids()[i])), i);
                        break;
                    default:
                        throw new IllegalArgumentException("Problème");
                }
                boidsNext[i].getPosition().setLocation(
                        Math.floorMod((int) (boidsActual.getBoids()[i].getPosition().getX() + boidsActual.getBoids()[i].getVelocity().getX()), gui.getPanelWidth()),
                        Math.floorMod((int) (boidsActual.getBoids()[i].getPosition().getY() + boidsActual.getBoids()[i].getVelocity().getY()), gui.getPanelHeight()));
                        int img = boidsNext[i].getImg();
                gui.addGraphicalElement(new ImageElement((int) (boidsNext[i].getPosition().getX()),
                    (int) (boidsNext[i].getPosition().getY()), orientation[img - 1], 7, 7, gui));
            }
        }
        boidsActual.setBoids(boidsNext);
        boidsActual.resetVoisinage();
        manager.addEvent(
                new PoissonEvent(manager.getCurDate() + 1, gui, manager, Boids, boidsActual, boidsNext, rayonBoid, proie));
    }
}