import gui.Simulable;
import gui.GUISimulator;
import gui.ImageElement;
import gui.Text;
import java.awt.Color;

public class ImmigrationSimulator extends Immigration implements Simulable{

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
        gui.addGraphicalElement(new Text(gui.getPanelWidth()/2, gui.getPanelHeight()/2-70, Color.decode("#000000"),
                new String("Simulateur du jeu de l'immigration")));   
        gui.addGraphicalElement(new Text(gui.getPanelWidth()/2, gui.getPanelHeight()/2+15-70, Color.decode("#000000"),
                new String("les règles sont :")));   
        gui.addGraphicalElement(new Text(gui.getPanelWidth()/2, gui.getPanelHeight()/2+30-70, Color.decode("#000000"),
                new String("-Chaque cellule peut être dans n états.")));   
        gui.addGraphicalElement(new Text(gui.getPanelWidth()/2, gui.getPanelHeight()/2+45-70, Color.decode("#000000"),
                new String("-Une cellule dans un état k passe à l’état k + 1( mod n) si ")));   
        gui.addGraphicalElement(new Text(gui.getPanelWidth()/2, gui.getPanelHeight()/2+60-70, Color.decode("#000000"),
                new String("et seulement si elle a trois voisines ou plus dans l’état k + 1.")));  
        gui.addGraphicalElement(new Text(gui.getPanelWidth()/2, gui.getPanelHeight()/2+80-70, Color.decode("#000000"),
                new String("Nombre de case : "+ligne +"x"+colonne+" avec une resolution de "+ gui.getPanelWidth()+"x"+ gui.getPanelHeight()))); 
        gui.addGraphicalElement(new ImageElement(gui.getPanelWidth()/2-350, gui.getPanelHeight()/2+100-70, "lib/image/immigration.png", 700, 200, gui));
        manager.addEvent(new ImmigrationEvent((long)0, gui, manager, this));
        //              Recommencer le même cas
        // reset();

        //              Regenerer un autre cas aléatoire
        randomGenerate();
    }

    public ImmigrationSimulator(GUISimulator gui, int ligne, int colonne, int n) {
        super(gui, ligne, colonne, n);
        this.gui=gui;
        gui.addGraphicalElement(new Text(gui.getPanelWidth()/2, gui.getPanelHeight()/2-70, Color.decode("#000000"),
                new String("Simulateur du jeu de l'immigration")));   
        gui.addGraphicalElement(new Text(gui.getPanelWidth()/2, gui.getPanelHeight()/2+15-70, Color.decode("#000000"),
                new String("les règles sont :")));   
        gui.addGraphicalElement(new Text(gui.getPanelWidth()/2, gui.getPanelHeight()/2+30-70, Color.decode("#000000"),
                new String("-Chaque cellule peut être dans n états.")));   
        gui.addGraphicalElement(new Text(gui.getPanelWidth()/2, gui.getPanelHeight()/2+45-70, Color.decode("#000000"),
                new String("-Une cellule dans un état k passe à l’état k + 1( mod n) si ")));   
        gui.addGraphicalElement(new Text(gui.getPanelWidth()/2, gui.getPanelHeight()/2+60-70, Color.decode("#000000"),
                new String("et seulement si elle a trois voisines ou plus dans l’état k + 1.")));  
        gui.addGraphicalElement(new Text(gui.getPanelWidth()/2, gui.getPanelHeight()/2+80-70, Color.decode("#000000"),
                new String("Nombre de case : "+ligne +"x"+colonne+" avec une resolution de "+ gui.getPanelWidth()+"x"+ gui.getPanelHeight()))); 
        gui.addGraphicalElement(new ImageElement(gui.getPanelWidth()/2-350, gui.getPanelHeight()/2+100-70, "lib/image/immigration.png", 700, 200, gui));


        manager.addEvent(new ImmigrationEvent((long)0, gui, manager, this));
    }
}