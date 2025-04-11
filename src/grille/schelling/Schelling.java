import java.util.Random;
import gui.GUISimulator;
import java.util.LinkedList;
import java.awt.Point;
import java.awt.Color;

public class Schelling extends Grille{
    private LinkedList<Point> habitationVacante = new LinkedList<Point>();
    private int nbrC, seuil;
    protected String[] colors;

    public Schelling(GUISimulator gui, int ligne, int colonne, int nbrC, int seuil) {
        super(gui,ligne, colonne);
        this.nbrC=nbrC;
        this.seuil=seuil;
        randomGenerate();
    }

    public void schellingStep(){
        int compt=0, val;
        Random random = new Random();
        for (int i = 0; i < ligne; i++) {
            for (int j = 0; j < colonne; j++) {
                val = mat_actual[i][j];
                if(val!=0){
                    if (mat_actual[Math.floorMod(i - 1, ligne)][Math.floorMod(j - 1, colonne)] != val && mat_actual[Math.floorMod(i - 1, ligne)][Math.floorMod(j - 1, colonne)] != 0) {
                        compt++;
                    }
                    if (mat_actual[i][Math.floorMod(j - 1, colonne)] != val && mat_actual[i][Math.floorMod(j - 1, colonne)] != 0) {
                        compt++;
                    }
                    if (mat_actual[Math.floorMod(i + 1, ligne)][Math.floorMod(j - 1, colonne)] != val && mat_actual[Math.floorMod(i + 1, ligne)][Math.floorMod(j - 1, colonne)] != 0) {
                        compt++;
                    }
                    if (mat_actual[Math.floorMod(i - 1, ligne)][j] != val && mat_actual[Math.floorMod(i - 1, ligne)][j] != 0) {
                        compt++;
                    }
                    if (mat_actual[i][Math.floorMod(j + 1, colonne)] != val && mat_actual[i][Math.floorMod(j + 1, colonne)] != 0) {
                        compt++;
                    }
                    if (mat_actual[Math.floorMod(i + 1, ligne)][j] != val && mat_actual[Math.floorMod(i + 1, ligne)][j] != 0) {
                        compt++;
                    }
                    if (mat_actual[Math.floorMod(i - 1, ligne)][Math.floorMod(j + 1, colonne)] != val && mat_actual[Math.floorMod(i - 1, ligne)][Math.floorMod(j + 1, colonne)] != 0) {
                        compt++;
                    }
                    if (mat_actual[Math.floorMod(i + 1, ligne)][Math.floorMod(j + 1, colonne)] != val && mat_actual[Math.floorMod(i + 1, ligne)][Math.floorMod(j + 1, colonne)] != 0) {
                        compt++;
                    }
                }
                
                if(compt>=seuil){
                    Point lieuDemenagement = habitationVacante.remove(random.nextInt(habitationVacante.size()));
                    Point aDemenager=new Point(i, j);
                    habitationVacante.add(aDemenager);
                    mat_next[(int)lieuDemenagement.getX()][(int)lieuDemenagement.getY()]=mat_actual[i][j];
                    mat_next[i][j]=0;
                }
                else if (val!=0){
                    mat_next[i][j]=mat_actual[i][j];                    
                }
                compt=0;
            }
        }
        for (int i = 0; i < ligne; i++) {
            for (int j = 0; j < colonne; j++) {
                colorerCase(i, j, Color.decode(colors[mat_next[i][j]]));
            }
        }
    }

    public void randomGenerate(){
        int tmp, tmpC, tmpParcourt=0xFFFFFF/(nbrC+1);
        Random random = new Random();
        colors = new String[nbrC+1];
        colors[0]="0xFFFFFF";            
        for (int i=1; i<nbrC+1; i++){
            colors[i]=Integer.toString(tmpParcourt);
            tmpParcourt+=tmpParcourt/(nbrC+1);
        }
        for (int i = 0; i < ligne; i++) {
            for (int j = 0; j < colonne; j++) {
                tmp=random.nextInt(4);
                if (tmp!=0){
                    tmpC=random.nextInt(nbrC+1);
                    mat_actual[i][j]=tmpC;
                    mat_init[i][j]=tmpC;
                    mat_next[i][j]=0; 
                }
                else {
                    habitationVacante.add(new Point(i, j));
                    mat_actual[i][j]=0;
                    mat_init[i][j]=0;
                    mat_next[i][j]=0;
                }
            }
        }
    }
}
