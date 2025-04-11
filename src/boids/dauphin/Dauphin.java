public class Dauphin extends Boid {
    
    private String nomEspece = new String();


    public Dauphin(Dauphin b){
        super(b);
        nomEspece="Dauphin";
    }

    public Dauphin(int largeur, int hauteur){
        super(largeur, hauteur);
        nomEspece="Dauphin";
    }

    public String getEspece(){
        return nomEspece;
    }

}
