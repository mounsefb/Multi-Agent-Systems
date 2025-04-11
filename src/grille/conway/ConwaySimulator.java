import gui.Simulable;
import gui.GUISimulator;
import gui.ImageElement;
import gui.Text;
import java.awt.Color;

public class ConwaySimulator extends Conway implements Simulable{

    private EventManager manager = new EventManager();
    private GUISimulator gui;
    
     @Override
    public void next() {
        // gui.reset();
        // ConwayStep();
        // Maj();
        manager.next();
    }

    @Override
    public void restart() {
        gui.reset();
        manager.restart();
        manager.addEvent(new ConwayEvent((long)0, gui, manager, this));
        //              Recommencer le même cas
        // Reset();
        gui.addGraphicalElement(new Text(gui.getPanelWidth()/2, gui.getPanelHeight()/2-100, Color.decode("#000000"),
                new String("Simulateur du jeu de la vie de Conway")));   
        gui.addGraphicalElement(new Text(gui.getPanelWidth()/2, gui.getPanelHeight()/2+15-100, Color.decode("#000000"),
                new String("les règles sont :")));   
        gui.addGraphicalElement(new Text(gui.getPanelWidth()/2, gui.getPanelHeight()/2+30-100, Color.decode("#000000"),
                new String("-Chaque cellule de la grille peut prendre deux états : vivant ou mort.")));   
        gui.addGraphicalElement(new Text(gui.getPanelWidth()/2, gui.getPanelHeight()/2+45-100, Color.decode("#000000"),
                new String("-Une cellule morte possédant exactement trois voisines vivantes devient vivante.")));   
        gui.addGraphicalElement(new Text(gui.getPanelWidth()/2, gui.getPanelHeight()/2+60-100, Color.decode("#000000"),
                new String("-Une cellule vivante possédant deux ou trois voisines vivantes le reste, sinon elle meurt.")));  
        gui.addGraphicalElement(new Text(gui.getPanelWidth()/2, gui.getPanelHeight()/2+80-100, Color.decode("#000000"),
                new String("Nombre de case : "+ligne +"x"+colonne+" avec une resolution de "+ gui.getPanelWidth()+"x"+ gui.getPanelHeight())));  
        gui.addGraphicalElement(new ImageElement(gui.getPanelWidth()/2-350, gui.getPanelHeight()/2+100-100, "lib/image/conway.png", 700, 200, gui));
        //              Regenerer un autre cas aléatoire
        randomGenerate();
    }

    public ConwaySimulator(GUISimulator gui, int ligne, int colonne) {
        super(gui,ligne, colonne);
        this.gui=gui;
        gui.addGraphicalElement(new Text(gui.getPanelWidth()/2, gui.getPanelHeight()/2-100, Color.decode("#000000"),
                new String("Simulateur du jeu de la vie de Conway")));   
        gui.addGraphicalElement(new Text(gui.getPanelWidth()/2, gui.getPanelHeight()/2+15-100, Color.decode("#000000"),
                new String("les règles sont :")));   
        gui.addGraphicalElement(new Text(gui.getPanelWidth()/2, gui.getPanelHeight()/2+30-100, Color.decode("#000000"),
                new String("-Chaque cellule de la grille peut prendre deux états : vivant ou mort.")));   
        gui.addGraphicalElement(new Text(gui.getPanelWidth()/2, gui.getPanelHeight()/2+45-100, Color.decode("#000000"),
                new String("-Une cellule morte possédant exactement trois voisines vivantes devient vivante.")));   
        gui.addGraphicalElement(new Text(gui.getPanelWidth()/2, gui.getPanelHeight()/2+60-100, Color.decode("#000000"),
                new String("-Une cellule vivante possédant deux ou trois voisines vivantes le reste, sinon elle meurt.")));  
        gui.addGraphicalElement(new Text(gui.getPanelWidth()/2, gui.getPanelHeight()/2+80-100, Color.decode("#000000"),
                new String("Nombre de case : "+ligne +"x"+colonne+" avec une resolution de "+ gui.getPanelWidth()+"x"+ gui.getPanelHeight())));  
        gui.addGraphicalElement(new ImageElement(gui.getPanelWidth()/2-350, gui.getPanelHeight()/2+100-100, "lib/image/conway.png", 700, 200, gui));
        manager.addEvent(new ConwayEvent((long)0, gui, manager, this));
    }
}