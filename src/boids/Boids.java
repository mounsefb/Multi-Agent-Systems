import java.awt.Point;
import java.util.Iterator;

import gui.GUISimulator;
import gui.ImageElement;

/**
 * La classe Boids représente un ensemble de boids avec des comportements spécifiques.
 */
public class Boids{
    private int nbreBoid;
    private Boid[] boids;
    private int[] nbreMort=new int[3];
    private int[] mort=new int[3];
    private int[] naissance=new int[2];


    /**
     * Constructeur d'un ensemble de boids.
     *
     * @param nbreBoid Le nombre de boids.
     * @param largeur  La largeur de l'écran.
     * @param hauteur  La hauteur de l'écran.
     */    
    public Boids(int nbreBoid, int largeur, int hauteur){
        this.nbreBoid=nbreBoid;
        boids=new Boid[nbreBoid];
        // boucle for pas optimisée pour 1 Requin mais permet de changer le nombre de Requin 
        // rapidement juste avec une condition if
        for (int i=0; i<nbreBoid; i++){
            if(i<nbreBoid-15){
                boids[i]=new Poisson(largeur, hauteur);
            }
            else if (i<nbreBoid-5){
                boids[i]=new Dauphin(largeur, hauteur);
            }
            else{
                boids[i]=new Requin(largeur, hauteur);
            }
        }
    }

    // Accesseurs : liste de boids et nombre de boids
    public Boid[] getBoids(){
        return boids;
    }

    public int getNbre(){
        return nbreBoid;
    }

    public int[] getNbreMort(){

        return nbreMort;
    }

    public int[] getNbreNaissance(){
        return naissance;
    }

    // Mutateur de la liste de boid sur les caractéristiques mutables des boids
    public void setBoids(Boid[] Init){
        for (int i=0; i<Init.length; i++){
            boids[i].setPosition(Init[i].getPosition());
            boids[i].setVelocity(Init[i].getVelocity());
        }
    }

    /**
     * Réinitialise tous les boids, les remettant à leur état initial et supprimant leur voisinage.
     */
    public void reInit() {
        for (int i = 0; i < boids.length; i++) {
            boids[i].resetVoisinage();
            boids[i].setVelocity(boids[i].getVelocityInit());
            boids[i].setPosition(boids[i].getPositionInit());
        }
    }

    public void resetMort(){
        for (int i = 0; i < 3; i++) {
            mort[i]=0;
            nbreMort[i]=0;
        }
    }

    public void resetNaissance(){
        for (int i = 0; i < 2; i++) {
            naissance[i]=0;
        }
    }

     // Réinitialisation du voisinage : vidage de toutes les listes de voisinage
    public void resetVoisinage(){
        for(int i=0; i<nbreBoid; i++){
            boids[i].resetVoisinage();
        }
    }

    /**
     * Affiche l'état actuel de l'ensemble de boids.
     *
     * @return Une représentation sous forme de chaîne de caractères de l'état actuel des boids.
     */
    @Override
    public String toString() {
        String s = "( ";
        for (int i = 0; i < nbreBoid; i++) {
            s+=boids[i].toString();
            if (i < boids.length - 1) {
                s += " | ";
            }
        }
        s += " )\n";
        return s;
    }

    public void nombreMortEtNaissance(GUISimulator gui){
        for (Boid b : boids){
            if ( b.getBoidVivant()==false && b.getBoidNouveauNe()==false){
                int t = b.getTempsmort();
                if(t<10 && t>=0){
                        gui.addGraphicalElement(new ImageElement((int) (b.getPosition().getX()),
                        (int) (b.getPosition().getY()), "lib/image/couleurs/mort.png", 20-t, 20-t, gui));
                        b.setTempsmort(t+1);
                }
            }
            if(b.getBoidNouveauNe()){
                int t = b.getTempsnouveaune();
                if(t<10 && t>=0){
                    gui.addGraphicalElement(new ImageElement((int) (b.getPosition().getX()),
                    (int) (b.getPosition().getY()), "lib/image/couleurs/coeur.png", 20-t, 20-t, gui));
                    b.setTempsnouveaune(t+1);
                }
                else {
                    b.setBoidVivant();
                }
            }
        }
    }

    

    public void addMortRequin(){
        mort[0]++;
        nbreMort[0]++;
    }
        
    /**
     * Règle de cohésion.
     *
     * @param bj          Le boid actuel.
     * @param distanceMax La distance maximale.
     * @param gui         L'interface graphique.
     * @return un vecteur de cohésion.
     */
    public Point regle1(Boid bj, int distanceMax, GUISimulator gui){
        Point pcj=new Point(0,0);
        Iterator<Boid> iterator = bj.getVoisinage().iterator();
        int N=0;
        while (iterator.hasNext()){
            Boid element = iterator.next();
            if(element.getClass()==bj.getClass()){
                pcj.translate((int)element.getPosition().getX(), (int)element.getPosition().getY());
                N++; 
            }  
        }
        if(N==0){return pcj;}
        pcj.setLocation(pcj.getX()/N, pcj.getY()/N);
        pcj.setLocation((pcj.getX()-bj.getPosition().getX())/15, (pcj.getY()-bj.getPosition().getY())/15);
        return pcj;
    }

    /**
     * Règle spécifique aux prédateurs.
     *
     * @param bj  Le boid actuel.
     * @param gui L'interface graphique.
     * @return La règle spécifique pour la chasse d'une proie.
     */
    public Point reglePredateur(Boid bj, GUISimulator gui) {
        Point pcj = new Point(0, 0);
        Boid nearest = bj.getNearest();
        if (nearest == null) {
            nearest = findNearestNeighbor(bj);
            bj.setNearest(nearest);
            if (bj.getNearest()==null) return null;
        } else {
            double distance = Math.sqrt(Math.pow(bj.getNearest().getPosition().getX()-bj.getPosition().getX(),2) 
                + Math.pow(bj.getNearest().getPosition().getY()-bj.getPosition().getY(),2));
            if(nearest.getPosition().getX()<0 || nearest.getPosition().getY()<0){
                bj.setNearest(null);
                return null;
            }
            if (distance < 10) {
                bj.setEtape(0);
                nearest.setBoidMort();
                if (nearest instanceof Dauphin){
                    mort[1]++;
                    nbreMort[1]++;

                }
                else if (nearest instanceof Poisson){
                    mort[2]++;
                    nbreMort[2]++;
                }
                bj.setNearest(null);
                return null;
            }
            else if (distance > 100 ) {
                bj.setNearest(null);
                return null;
            }
        }
        pcj.setLocation((bj.getNearest().getPosition().getX() - bj.getPosition().getX()) / 2, (bj.getNearest().getPosition().getY() - bj.getPosition().getY()) / 2);
        return pcj;
    }
    
    private Boid findNearestNeighbor(Boid bj) {
        Boid nearest = null;
        double minDistance = Double.MAX_VALUE;
        for (Boid element : bj.getVoisinage()) {
            if (element instanceof Dauphin && bj instanceof Requin || element instanceof Poisson && bj instanceof Dauphin ||
                element instanceof Requin && bj instanceof Poisson){
                double distance = Math.sqrt(Math.pow(element.getPosition().getX()-bj.getPosition().getX(),2) 
                    + Math.pow(element.getPosition().getY()-bj.getPosition().getY(),2));
                if (distance < minDistance && (0<element.getPosition().getY() && 0<element.getPosition().getX())) {
                    nearest = element;
                    minDistance = distance;
                }
            }
        }
        return nearest;
    }
    

    /**
     * Règle de séparation.
     *
     * @param bj Le boid actuel.
     * @return un vecteur de séparation.
     */    
    public Point regle2(Boid bj){
        Point c = new Point(0, 0);
        Iterator<Boid> iterator = bj.getVoisinage().iterator();
        double positionX=bj.getPosition().getX(), positionY=bj.getPosition().getY(), distanceEntreBetBj;
        while (iterator.hasNext()){
            Boid element = iterator.next();
            distanceEntreBetBj=Math.sqrt(Math.pow(element.getPosition().getX()-positionX,2) + Math.pow(element.getPosition().getY()-positionY,2));
            if ((element instanceof Requin && bj instanceof Dauphin) || (element instanceof Dauphin && bj instanceof Poisson)){
                if (distanceEntreBetBj<30){
                    c.translate((int)(-(element.getPosition().getX()-positionX)), (int)(-(element.getPosition().getY()-positionY)));
                }
            }
            else if (distanceEntreBetBj<10){
                c.translate((int)(-(element.getPosition().getX()-positionX)/30), (int)(-(element.getPosition().getY()-positionY)/30));
            }
        }
        return c;
    }

    /**
     * Règle d'allignement.
     *
     * @param bj Le boid actuel.
     * @return un vecteur d'allignement.
     */
    public Point regle3(Boid bj){
        Point pvj = new Point(0,0);
        Iterator<Boid> iterator = bj.getVoisinage().iterator();
        int N=0;
        while (iterator.hasNext()){
            Boid element = iterator.next();
            if(element.getClass()==bj.getClass()){
                pvj.translate((int)element.getVelocity().getX(), (int)element.getVelocity().getY());
                N++;
            }
        }
        if(N==0){return pvj;}
        pvj.setLocation(pvj.getX()/N, pvj.getY()/N);
        pvj.setLocation((pvj.getX()-bj.getVelocity().getX())/8, (pvj.getY()-bj.getVelocity().getY())/8);
        return pvj;
    }

    /**
     * Règle instaurant une répulsion par les bords.
     *
     * @param b Le boid actuel.
     * @param Xmin Bordure minimale en X du vecteur position
     * @param Xmax Bordure maximale en X du vecteur position
     * @param Ymin Bordure minimale en Y du vecteur position
     * @param Ymax Bordure maximale en Y du vecteur position
     * @return un vecteur de répulsion.
     */    
    public Point regle4(Boid b, int Xmin, int Xmax, int Ymin, int Ymax){
        Point v = new Point(0,0);
        int repulsion=5;
        if (b.getPosition().getX()<Xmin) v.translate(repulsion,0);
        else if (b.getPosition().getX()>Xmax) v.translate(-repulsion,0);
        if (b.getPosition().getY()<Ymin) v.translate(0,repulsion);
        else if (b.getPosition().getY()>Ymax) v.translate(0,-repulsion);
        return v;
    }


    /**
     * Règle d'accouplement qui permet de faire s'accoupler deux boids de même espece si les deux ne sont pas en chasse, assez proches
     *  et qu'il y a de la place pour un boid de cet espece.
     *
     * @param bj Le boid actuel.
     */
    public void regleAccouplement(Boid bj){
         Iterator<Boid> iterator = bj.getVoisinage().iterator();
        double positionX=bj.getPosition().getX(), positionY=bj.getPosition().getY(), distanceEntreBetBj;
        while (iterator.hasNext()){
            Boid element = iterator.next();
            distanceEntreBetBj=Math.sqrt(Math.pow(element.getPosition().getX()-positionX,2) + Math.pow(element.getPosition().getY()-positionY,2));
            if((bj.getBoidVivant() && element.getBoidVivant())&&((element instanceof Dauphin && bj instanceof Dauphin) || (element instanceof Requin && bj instanceof Requin)) && distanceEntreBetBj<10){
                if (mort[0]!=0 || mort[1]!=0){
                    int i=-1;
                    boolean ok=false;
                    while(i<boids.length-1 && ok==false){
                        i++;
                        if(boids[i].getBoidVivant()==false){
                            if(boids[i] instanceof Dauphin || boids[i] instanceof Requin){
                                ok=true;
                            }
                        }
                    }
                    if(ok==true){
                        boolean permission = false;
                        if (bj instanceof Requin){
                           if (mort[0]>naissance[0])
                            {

                             naissance[0]++;
                             mort[0]--;
                             permission = true;}
                        }
                        else if (bj instanceof Dauphin){
                            if (mort[1]>naissance[1])
                            {
                             naissance[1]++;
                             mort[1]--;
                             permission = true;}
                        }
                        if (permission){
                            boids[i].setEtape(0);           
                            boids[i].setPosition(new Point(element.getPosition()));
                            boids[i].setVelocity(new Point(element.getVelocity()));
                            boids[i].setNaissance();
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Règle d'accouplement qui permet de faire s'accoupler deux boids de même espece si les deux ne sont pas en chasse, assez proches
     *  et qu'il y a de la place pour un boid de cet espece.
     *
     * @param b Le boid actuel.
     * @param vlim 
     */ 
    public void regle5(Boid b, int vlim){
        double vitesseNormee = Math.sqrt(Math.pow(b.getVelocity().getX(),2)+Math.pow(b.getVelocity().getY(),2));
        if (vitesseNormee>vlim){
            b.getVelocity().setLocation(new Point((int)(vlim*b.getVelocity().getX()/(vitesseNormee)), (int)(vlim*b.getVelocity().getY()/(vitesseNormee))));
        }
    }

}
