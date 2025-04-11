import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import gui.GUISimulator;

/**
 * Représente un Boid dans une simulation.
 */
public class Boid{

    static public enum ETAT{EN_VIE, MORT,NAISSANCE};
    private Boid nearestPrey=null;
    static public enum ETAPE{REPOS, CHASSE};
    private ETAPE etape=ETAPE.REPOS;

    private Point position;
    private Point positionInit;
    private ETAT etat=ETAT.EN_VIE;
    private Point velocity;
    private Point velocityInit;
    private ArrayList<Boid> voisinage = new ArrayList<Boid>();
    private int tempsmort;
    private int tempsnouveaune;

     /**
     * Constructeur d'un nouveau Boid en copiant un autre Boid.
     *
     * @param b le Boid à copier
     */
    public Boid(Boid b){
        velocity = new Point(b.getVelocity());
        if(b.getVelocityInit()==null){
            velocityInit= new Point(velocity);
        }
        else    velocityInit= new Point(b.getVelocityInit());
        position = new Point(b.getPosition());
        if(b.getPositionInit()==null){
            positionInit = new Point(position);
        }
        else    positionInit = new Point(b.getPositionInit());
    }


     /**
     * Constructeur de base qui génère aléatoirement les caractéristiques d'un nouveau Boid.
     * Ce Boid sera positionné à l'intérieur de l'écran, loin des bords,
     * avec des composantes axiales de vitesse comprises entre -10 et +10 blocs/pas.
     *
     * @param largeur la largeur de l'écran
     * @param hauteur la hauteur de l'écran
     */
    public Boid(int largeur, int hauteur){
        int larg, haut, orientX, orientY, veloX, veloY;
        Random rand=new Random();
        larg=rand.nextInt(largeur-200)+100;
        haut=rand.nextInt(hauteur-200)+100;
        // larg=largeur/2;
        // haut=hauteur/2;
        position = new Point(larg,haut);
        positionInit = new Point(larg,haut);
        // System.out.println(position);
        // System.out.println(positionInit);
        orientX =rand.nextInt(2);
        orientY =rand.nextInt(2);
        veloX=rand.nextInt(9)+1;
        veloY=rand.nextInt(9)+1;
        velocityInit = new Point((int)Math.pow(-1, orientX) * veloX, (int)Math.pow(-1, orientY) *veloY);
        // velocityInit = new Point((int)veloX, (int)veloY);

        velocity = new Point((int)Math.pow(-1, orientX) * veloX, (int)Math.pow(-1, orientY) *veloY);
        // velocity = new Point((int)veloX, (int)veloY);

        tempsmort = -1;
    }

    // Accesseurs aux attributs du boid : 
    // velocité actuelle, vélocité initiale, position actuelle, position initiale, et liste du voisinage

    public Point getVelocity(){
        return velocity;
    }

    public Point getPosition(){
        return position;
    }

    public Point getPositionInit(){
        return positionInit;
    }

    public Point getVelocityInit(){
        return velocityInit;
    }

    public ArrayList<Boid> getVoisinage(){
        return voisinage;
    }

    public boolean getBoidVivant(){
        if (etat==ETAT.MORT) return false;
        return true;
    }

    public boolean getBoidNouveauNe(){
        if (etat==ETAT.NAISSANCE) return true;
        return false;
    }

    public String getEspece(){
        return "pas de nom d'espece a ce stade";
    }

    public int getTempsnouveaune(){
        return tempsnouveaune;
    }

    public int getTempsmort(){
        return tempsmort;
    }

    public Boid getNearest(){
        return nearestPrey;
    }

    public int getEtape() {
        switch (etape) {
            case REPOS:
                return 0;
            case CHASSE:
                return 1;
            default:
                return -1;
        }
    }

    // Mutateurs des attributs (auxquels on donne accès) du boid : 
    // velocité actuelle et position actuelle
    // une fois la simulation, les attributs initiaux ne sont pas censé être changé

    public void setVelocity(Point v){
        velocity=new Point(v);
    }

    public void setPosition(Point p){
        position=new Point(p);
    }

    public void setNaissance(){
        etat=ETAT.NAISSANCE;
        tempsnouveaune=0;
        tempsmort=-1;
    }

    public void setBoidMort(){
        etat=ETAT.MORT;
        tempsmort=0;
        tempsnouveaune=-1;
    }
    
    public void setBoidVivant(){
        etat=ETAT.EN_VIE;
        tempsmort=-1;
        tempsnouveaune=-1;
    }

    public void setTempsmort(int t){
        tempsmort = t;
    }

    public void setTempsnouveaune(int t){
        tempsnouveaune = t;
    }

    public void setNearest(Boid nearestPrey){
        this.nearestPrey=nearestPrey;
    }

    public void setEtape(int ent) {
        ETAPE[] etapes = ETAPE.values();
        if (ent >= 0 && ent < etapes.length) {
            this.etape = etapes[ent];
        } else {
            // Gérer le cas où l'entier n'est pas valide, par exemple, en levant une exception
            throw new IllegalArgumentException("Valeur d'étape non valide : " + ent);
        }
    }

    /** Réinitialisation de la liste de voisinage */ 
    public void resetVoisinage(){
        voisinage.clear();
    }

    /**
     * Renvoie une représentation sous forme de chaîne du Boid, incluant sa position, sa vélocité, son image, etc.
     *
     * @return une représentation sous forme de chaîne du Boid
     */   
    @Override
    public String toString() {
        String s = "( ";
        s += "boid : " + position.getX() + " , " + position.getY();
        s+=" (sa position) ";
        s+=velocity.getX() + " , " + velocity.getY();
        s+=" (sa velocité) ";
        s+=getImg();
        s+=" (son image) ";
        s += " )";
        return s;
    }

    /**
     * Détermine et renvoie l'image correspondant à l'orientation actuelle du Boid.
     *
     * @return le code d'image représentant l'orientation du Boid
     */    
    public int getImg(){
        int img=1;
        double ypoint=velocity.getY(), xpoint=velocity.getX();
                    // System.out.println(ypoint);

        if ((ypoint)>0||(ypoint<0)){
            if ((((double)xpoint/(double)ypoint)>=0.5) || (((double)xpoint/(double)ypoint)<=(-0.5))){
                if((((double)xpoint/(double)ypoint)>=2) || (((double)xpoint/(double)ypoint)<=(-2)))
                    {
                        if (xpoint<0){
                            //orienté OUEST
                            img = 1;
                        }
                        else{
                            //orienté EST
                            img = 2;
                        }
                }
                else{
                    if(xpoint >=0 && ypoint >= 0){
                        //NORD EST
                        img=7;
                    }
                    if(xpoint <=0 && ypoint <= 0){
                        //SUD OUEST
                        img=8;
                    }
                    if(xpoint >=0 && ypoint <= 0){
                        //SUD EST
                        img=5;
                    }
                    if(xpoint <=0 && ypoint >= 0){
                        //NORD OUEST
                        img=6;
                    }
                }
            }
            else {
                if(ypoint<=0){
                    //orienté SUD
                    img = 3;
                }
                else{
                    //orienté NORD
                    img = 4;
                }
            }
        }
        else{
            // System.out.println("ypoint=0");
            if(xpoint<=0){ img=1;}
            else{img=2;}
        }

        return(img);
    }

    

    /**
     * Méthode pour établir le voisinage du Boid.
     *
     * @param distanceMax la distance maximale pour considérer d'autres Boids comme voisins
     * @param gui         le GUISimulator pour la simulation
     * @param nbreBoid    le nombre total de Boids dans la simulation
     * @param boids       un tableau contenant tous les Boids de la simulation
     */
    public void etablissementVoisinage(double distanceMax, GUISimulator gui, int nbreBoid, Boid[] boids) {
        if(etat==ETAT.EN_VIE){
            // Boid this = this;
            double positionX1 = position.getX();
            double positionY1 = position.getY();
            double norme1 = Math.sqrt(Math.pow(positionX1,2) + Math.pow(positionY1,2));
            
            for (int j = 0; j < nbreBoid; j++) {
                Boid boid2 = boids[j];
                if (this != boids[j] && boids[j].getBoidVivant() && boids[j].getBoidNouveauNe()==false) {
                    if(((this instanceof Requin)==false)
                        || (((this instanceof Requin)==true) && boids[j].getClass() != this.getClass())){

                        double positionX2 = boid2.getPosition().getX();
                        double positionY2 = boid2.getPosition().getY();
                        double norme2 = Math.sqrt(Math.pow(positionX2,2) + Math.pow(positionY2,2));
                        double produitScalaire=positionX2*positionX1 + positionY2*positionY1;
                        double distance = Math.sqrt(Math.pow(positionX1-positionX2,2) + Math.pow(positionY1-positionY2,2));
                        if (((this instanceof Requin)==false && Math.floorMod((long)Math.acos(produitScalaire/(norme1*norme2)),(long)Math.PI)<Math.PI/2)
                            || (this instanceof Requin)==true){
                            if (distance <= distanceMax) {
                                // this.addVoisinage(boids[j]);
                                voisinage.add(boids[j]);
                            } else if (positionX1 < distanceMax && positionY1 < distanceMax) {
                                if (positionX2 > positionX1)
                                    positionX2 -= gui.getPanelWidth();
                                if (positionY2 > positionY1)
                                    positionY2 -= gui.getPanelHeight();
                                if (Math.sqrt(Math.pow(positionX1-positionX2,2) + Math.pow(positionY1-positionY2,2)) <= distanceMax) {
                                    if (boids[j] instanceof Requin){
                                        Requin newBoid = new Requin((Requin)boids[j]);
                                        newBoid.setPosition(new Point((int)positionX2, (int)positionY2));
                                        // this.addVoisinage(newBoid);
                                        voisinage.add(newBoid);

                                    }
                                    else{
                                        Boid newBoid = new Boid(boid2);
                                        newBoid.setPosition(new Point((int)positionX2, (int)positionY2));
                                        voisinage.add(newBoid);
                                        // this.addVoisinage(newBoid);
                                    }
                                }
                            } else if (positionX2 < distanceMax && positionY1 < distanceMax) {
                                if (positionX1 > positionX2)
                                    positionX1 -= gui.getPanelWidth();
                                if (positionY2 > positionY1)
                                    positionY2 -= gui.getPanelHeight();
                                if (Math.sqrt(Math.pow(positionX1-positionX2,2) + Math.pow(positionY1-positionY2,2)) <= distanceMax) {
                                    if (boids[j] instanceof Requin){
                                        Requin newBoid = new Requin((Requin)boids[j]);
                                        newBoid.setPosition(new Point((int)positionX2, (int)positionY2));
                                        // this.addVoisinage(newBoid);
                                        voisinage.add(newBoid);   
                                    }
                                    else{
                                        Boid newBoid = new Boid(boid2);
                                        newBoid.setPosition(new Point((int)positionX2, (int)positionY2));
                                        // this.addVoisinage(newBoid);
                                        voisinage.add(newBoid);
                                    }
                                }
                            } else if (positionX2 < distanceMax && positionY2 < distanceMax) {
                                if (positionX1 > positionX2)
                                    positionX1 -= gui.getPanelWidth();
                                if (positionY1 > positionY2)
                                    positionY1 -= gui.getPanelHeight();
                                if (Math.sqrt(Math.pow(positionX1-positionX2,2) + Math.pow(positionY1-positionY2,2)) <= distanceMax) {
                                    if (boids[j] instanceof Requin){
                                        Requin newBoid = new Requin((Requin)boids[j]);
                                        newBoid.setPosition(new Point((int)positionX2, (int)positionY2));
                                        // this.addVoisinage(newBoid);
                                        voisinage.add(newBoid);
                                    }
                                    else{
                                        Boid newBoid = new Boid(boid2);
                                        newBoid.setPosition(new Point((int)positionX2, (int)positionY2));
                                        // this.addVoisinage(newBoid);
                                        voisinage.add(newBoid);
                                    }
                                    // this.addVoisinage(new Boid(boid2,new Point((int)positionX2, (int)positionY2)));
                                }
                            } else if (positionX1 < distanceMax && positionY2 < distanceMax) {
                                if (positionX2 > positionX1)
                                    positionX2 -= gui.getPanelWidth();
                                if (positionY1 > positionY2)
                                    positionY1 -= gui.getPanelHeight();
                                if (Math.sqrt(Math.pow(positionX1-positionX2,2) + Math.pow(positionY1-positionY2,2)) <= distanceMax) {
                                    if (boids[j] instanceof Requin){
                                        Requin newBoid = new Requin((Requin)boids[j]);
                                        newBoid.setPosition(new Point((int)positionX2, (int)positionY2));
                                        // this.addVoisinage(newBoid);
                                        voisinage.add(newBoid);

                                    }
                                    else{
                                        Boid newBoid = new Boid(boid2);
                                        newBoid.setPosition(new Point((int)positionX2, (int)positionY2));
                                        // this.addVoisinage(newBoid);
                                        voisinage.add(newBoid);

                                    }
                                    // this.addVoisinage(new Boid(boid2,new Point((int)positionX2, (int)positionY2)));
                                }
                            }
                        }
                    }
                }   
            }
        }
    }

}