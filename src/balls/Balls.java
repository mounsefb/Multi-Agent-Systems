import java.awt.Point;

    /** Classe permettant de dessiner et simuler un ensemble de balles dans un espace fini*/

public class Balls {
    Point[] objet;
    Point[] objetInit;

            /** Translater la balle n°i d'un certain vecteur de coordonnees (dx,dy)   
             * @param i le numero de la balle
             * @param dx la translation sur l'axe des x
             * @param dy la translation sur l'axe des y
            */

    public void translateUne(int dx, int dy, int i){
        if (i>objet.length){
            throw new IllegalArgumentException("indice trop grand !");
        }
        objet[i].translate(dx, dy);
    }
        /** Translater toutes les balles d'un certain vecteur de coordonnees (dx,dy)
         * @param dx la translation sur l'axe des x
         * @param dy la translation sur l'axe des y
        */

    public void translate(int dx, int dy) {
        for (int i = 0; i < objet.length; i++) {
            objet[i].translate(dx, dy);
        }
    }
        /** Reinitialiser la position des balles */

    public void reInit() {
        for (int i = 0; i < objet.length; i++) {
            objet[i].setLocation(objetInit[i]);
        }
    }

        /** Créer un ensemble de balles 
         * @param n le nombre de balles
        */

    public Balls(int n) {
        this.objet = new Point[n];
        this.objetInit = new Point[n];
        for (int i = 0; i < n; i++) {
            this.objet[i] = new Point(0, 0);
            this.objetInit[i] = new Point(0, 0);
        }
    }

            /** Recopier un ensemble de balles
             * @param Init comporte les données de réference
            */

    public Balls(Point[] Init) {
        this.objet = new Point[Init.length];
        this.objetInit = new Point[Init.length];
        for (int i = 0; i < Init.length; i++) {
            this.objet[i] = new Point(Init[i]);
            this.objetInit[i] = new Point(Init[i]);
        }
    }

    
    @Override
    public String toString() {
        String s = "( ";
        for (int i = 0; i < this.objet.length; i++) {
            s += "objet n°" + i + " : " + this.objet[i].getX() + " , " + this.objet[i].getY();
            if (i < this.objet.length - 1) {
                s += " | ";
            }
        }
        s += " )";
        return s;
    }
}