import java.util.Random;
import java.awt.Color;
import gui.GUISimulator;

public class Conway extends Grille {
    public Conway(GUISimulator gui, int ligne, int colonne) {
        super(gui,ligne, colonne);
        randomGenerate();
    }

    public void conwayStep(){
        int compt=0;
        for (int i = 0; i < ligne; i++) {
            for (int j = 0; j < colonne; j++) {
                compt=mat_actual[Math.floorMod(i-1,ligne)][Math.floorMod(j-1,colonne)];
                compt+=mat_actual[i][Math.floorMod(j-1,colonne)]+mat_actual[Math.floorMod(i+1,ligne)][Math.floorMod(j-1,colonne)]+mat_actual[Math.floorMod(i-1,ligne)][j]+mat_actual[i][Math.floorMod(j+1,colonne)]+mat_actual[Math.floorMod(i+1,ligne)][j]+mat_actual[Math.floorMod(i-1,ligne)][Math.floorMod(j+1,colonne)]+mat_actual[Math.floorMod(i+1,ligne)][Math.floorMod(j+1,colonne)];
                if(compt==3 && mat_actual[i][j]==0){
                    mat_next[i][j]=1;
                }
                else if((compt!=2 && compt!=3) && mat_actual[i][j]==1){
                    mat_next[i][j]=0;
                }
                else{
                    mat_next[i][j]=mat_actual[i][j];
                }
                if(mat_next[i][j]==1){
                    colorerCase(i, j, Color.decode("#1f77b4"));
                }
                else{
                    colorerCase(i, j, Color.decode("#ffffff"));
                }
                compt=0;
            }
        }
    }

    public void randomGenerate(){
        int tmp;
        Random random = new Random();
        for (int i = 0; i < ligne; i++) {
            for (int j = 0; j < colonne; j++) {
                tmp=random.nextInt(2);
                mat_actual[i][j]=tmp;
                mat_init[i][j]=tmp;
                mat_next[i][j]=0;
            }
        }
    }
}
