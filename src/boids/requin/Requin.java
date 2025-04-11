public class Requin extends Boid {
    
    private String nomEspece = new String();
    private int nbrePredateur = 0;

    public Requin(Requin b){
        super(b);
        nomEspece="Requin";
    }

    public Requin(int largeur, int hauteur){
        super(largeur, hauteur);
        nomEspece="Requin";
    }

    public String getEspece(){
        return nomEspece;
    }

    public void addPredateur(){
        nbrePredateur++;
    }

    public void resetPredateur(){
        nbrePredateur=0;
    }

    public int getPredateur(){
        return nbrePredateur;
    }

}
