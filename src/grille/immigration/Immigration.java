import java.util.Random;
import java.awt.Color;
import gui.GUISimulator;

public class Immigration extends Grille {
    private int n;
    protected String[] colors= { "0xFFFFFF", "#00BFFF", "#1E90FF", "#000080" };

    public Immigration(GUISimulator gui, int ligne, int colonne, int n) {
        super(gui, ligne, colonne);
        this.n = n;
        randomGenerate();
    }

    public void immigrationStep() {
        int compt = 0;
        int val;
        for (int i = 0; i < ligne; i++) {
            for (int j = 0; j < colonne; j++) {
                val = (mat_actual[i][j] + 1) % n;
                if (mat_actual[Math.floorMod(i - 1, ligne)][Math.floorMod(j - 1, colonne)] == val) {
                    compt++;
                }
                if (mat_actual[i][Math.floorMod(j - 1, colonne)] == val) {
                    compt++;
                }
                if (mat_actual[Math.floorMod(i + 1, ligne)][Math.floorMod(j - 1, colonne)] == val) {
                    compt++;
                }
                if (mat_actual[Math.floorMod(i - 1, ligne)][j] == val) {
                    compt++;
                }
                if (mat_actual[i][Math.floorMod(j + 1, colonne)] == val) {
                    compt++;
                }
                if (mat_actual[Math.floorMod(i + 1, ligne)][j] == val) {
                    compt++;
                }
                if (mat_actual[Math.floorMod(i - 1, ligne)][Math.floorMod(j + 1, colonne)] == val) {
                    compt++;
                }
                if (mat_actual[Math.floorMod(i + 1, ligne)][Math.floorMod(j + 1, colonne)] == val) {
                    compt++;
                }
                if (compt >= 3) {
                    mat_next[i][j] = (mat_actual[i][j] + 1) % n;
                } else {
                    mat_next[i][j] = mat_actual[i][j];
                }
                if (mat_next[i][j] >= 1) {
                    colorerCase(i, j, Color.decode(colors[mat_next[i][j]]));
                } else {
                    colorerCase(i, j, Color.decode("#ffffff"));
                }
                compt = 0;
            }
        }
    }

    public void randomGenerate() {
        int tmp;
        Random random = new Random();
        for (int i = 0; i < ligne; i++) {
            for (int j = 0; j < colonne; j++) {
                tmp = random.nextInt(4);
                mat_actual[i][j] = tmp;
                mat_init[i][j] = tmp;
                mat_next[i][j] = 0;
            }
        }
    }
}
