public class Poisson extends Boid {
    
    private String nomEspece = new String();
    static public enum ETAPE{EVEILLE, ENDORMI};
    
    public Poisson(Poisson b){
        super(b);
        nomEspece="Poisson";
    }

    public Poisson(int largeur, int hauteur){
        super(largeur, hauteur);
        nomEspece="Poisson";
    }

    public String getEspece(){
        return nomEspece;
    }
}
