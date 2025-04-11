import gui.Simulable;
import gui.GUISimulator;
import gui.Text;
import java.awt.Color;

public class SchellingSimulator extends Schelling implements Simulable{

    private EventManager manager = new EventManager();
    private GUISimulator gui;

     @Override
    public void next() {
        manager.next();
    }

    @Override
    public void restart() {
        gui.reset();
        manager.restart();
        manager.addEvent(new SchellingEvent((long)0, gui, manager, this));
        //              Recommencer le même cas
        // reset();
        //              Generer un autre cas aléatoire
        randomGenerate();
        gui.addGraphicalElement(new Text(gui.getPanelWidth()/2, gui.getPanelHeight()/2, Color.decode("#000000"),
                new String("Le modèle de Schelling")));   
        gui.addGraphicalElement(new Text(gui.getPanelWidth()/2, gui.getPanelHeight()/2+15, Color.decode("#000000"),
                new String("les règles sont :")));   
        gui.addGraphicalElement(new Text(gui.getPanelWidth()/2, gui.getPanelHeight()/2+30, Color.decode("#000000"),
                new String("-Chaque cellule de la grille représente une habitation.")));   
        gui.addGraphicalElement(new Text(gui.getPanelWidth()/2, gui.getPanelHeight()/2+45, Color.decode("#000000"),
                new String("-Une habitation peut être soit vacante, soit habitée par une famille de couleur c")));   
        gui.addGraphicalElement(new Text(gui.getPanelWidth()/2, gui.getPanelHeight()/2+60, Color.decode("#000000"),
                new String("-Si une famille de couleur c a plus de K voisins de couleur différente (K est un seuil qui est un paramètre de la simulation),")));  
        gui.addGraphicalElement(new Text(gui.getPanelWidth()/2, gui.getPanelHeight()/2+75, Color.decode("#000000"),
                new String("alors la famille déménage, c’est-à-dire qu’elle va chercher une habitation vacante et s’y installer.")));   
        gui.addGraphicalElement(new Text(gui.getPanelWidth()/2, gui.getPanelHeight()/2+90, Color.decode("#000000"),
                new String("L’habitation qu’elle occupait jusqu’ici devient alors vacante.")));   
        gui.addGraphicalElement(new Text(gui.getPanelWidth()/2, gui.getPanelHeight()/2+110, Color.decode("#000000"),
                new String("Nombre de case : "+ligne +"x"+colonne+" avec une resolution de "+ gui.getPanelWidth()+"x"+ gui.getPanelHeight()))); 
    }

    public SchellingSimulator(GUISimulator gui, int ligne, int colonne,  int nbrC, int seuil) {
        super(gui, ligne, colonne, nbrC, seuil);
        this.gui=gui;
        gui.addGraphicalElement(new Text(gui.getPanelWidth()/2, gui.getPanelHeight()/2, Color.decode("#000000"),
                new String("Le modèle de Schelling")));   
        gui.addGraphicalElement(new Text(gui.getPanelWidth()/2, gui.getPanelHeight()/2+15, Color.decode("#000000"),
                new String("les règles sont :")));   
        gui.addGraphicalElement(new Text(gui.getPanelWidth()/2, gui.getPanelHeight()/2+30, Color.decode("#000000"),
                new String("-Chaque cellule de la grille représente une habitation.")));   
        gui.addGraphicalElement(new Text(gui.getPanelWidth()/2, gui.getPanelHeight()/2+45, Color.decode("#000000"),
                new String("-Une habitation peut être soit vacante, soit habitée par une famille de couleur c")));   
        gui.addGraphicalElement(new Text(gui.getPanelWidth()/2, gui.getPanelHeight()/2+60, Color.decode("#000000"),
                new String("-Si une famille de couleur c a plus de K voisins de couleur différente (K est un seuil qui est un paramètre de la simulation),")));  
        gui.addGraphicalElement(new Text(gui.getPanelWidth()/2, gui.getPanelHeight()/2+75, Color.decode("#000000"),
                new String("alors la famille déménage, c’est-à-dire qu’elle va chercher une habitation vacante et s’y installer.")));   
        gui.addGraphicalElement(new Text(gui.getPanelWidth()/2, gui.getPanelHeight()/2+90, Color.decode("#000000"),
                new String("L’habitation qu’elle occupait jusqu’ici devient alors vacante.")));   
        gui.addGraphicalElement(new Text(gui.getPanelWidth()/2, gui.getPanelHeight()/2+110, Color.decode("#000000"),
                new String("Nombre de case : "+ligne +"x"+colonne+" avec une resolution de "+ gui.getPanelWidth()+"x"+ gui.getPanelHeight()))); 
        manager.addEvent(new SchellingEvent((long)0, gui, manager, this));
    }
}