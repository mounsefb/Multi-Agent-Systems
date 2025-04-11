import gui.GUISimulator;
import gui.Rectangle;

import java.awt.Color;

/**
 * La classe Grille représente une grille pour la simulation.
 */
public class Grille {
    protected int[][] mat_actual;
    protected int[][] mat_next;
    protected int[][] mat_init;
    protected GUISimulator gui;
    protected int ligne, colonne;
    protected String[] colors;

     /**
     * Constructeur de la classe Grille.
     *
     * @param gui     L'interface graphique.
     * @param ligne   Le nombre de lignes de la grille.
     * @param colonne Le nombre de colonnes de la grille.
     */
    public Grille(GUISimulator gui, int ligne, int colonne) {
        this.mat_actual = new int[ligne][colonne];
        this.mat_next = new int[ligne][colonne];
        this.mat_init = new int[ligne][colonne];
        this.gui = gui;
        this.ligne = ligne;
        this.colonne = colonne;
    }

     /**
     * Réinitialise la grille.
     */
    public void reset() {
        for (int i = 0; i < ligne; i++) {
            for (int j = 0; j < colonne; j++) {
                mat_actual[i][j] = mat_init[i][j];
                mat_next[i][j] = 0;
                if (mat_actual[i][j] >= 1) {
                    colorerCase(i, j, Color.decode(colors[mat_actual[i][j]]));
                } else {
                    colorerCase(i, j, Color.decode("0xffffff"));
                }
            }
        }
    }

    /**
     * Colorie une case de la grille avec une couleur spécifiée.
     *
     * @param i L'indice de la ligne.
     * @param j L'indice de la colonne.
     * @param c La couleur à appliquer à la case.
     */
    public void colorerCase(int i, int j, Color c) {
        gui.addGraphicalElement(new Rectangle((gui.getPanelWidth() / colonne) * (j + 1), (gui.getPanelHeight() / ligne) * (i + 1), Color.BLACK, c,
                (gui.getPanelHeight() / ligne), (gui.getPanelWidth() / colonne)));
    }

    /**
     * Met à jour la grille avec les valeurs de la prochaine génération.
     */
    public void maj() {
        for (int i = 0; i < ligne; i++) {
            for (int j = 0; j < colonne; j++) {
                mat_actual[i][j] = mat_next[i][j];
            }
        }
    }
}
