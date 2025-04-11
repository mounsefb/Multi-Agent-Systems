import java.awt.Point;
import gui.Simulable;
import gui.GUISimulator;
import java.awt.Color;
import java.util.Random;
import gui.Text;

public class BallsSimulator implements Simulable {

    private EventManager manager = new EventManager();
    private Balls Balles;
    private GUISimulator gui;
    public int[] dx, dy;
    private int n;

    @Override
    public void next(){
        manager.next();
    }

    @Override
    public void restart() {
        gui.reset();
        gui.addGraphicalElement(new Text(gui.getWidth()/2, gui.getHeight()/2-20, Color.decode("#ffffff"),
                new String("Simulateur de Balles")));
        gui.addGraphicalElement(new Text(gui.getWidth()/2, gui.getHeight()/2, Color.decode("#ffffff"),
                new String("Les balles rebondissent sur les rebords ( resolution : 1280x720 )"))); 
        gui.addGraphicalElement(new Text(gui.getWidth()/2, gui.getHeight()/2+20, Color.decode("#ffffff"),
                new String("Velocites et positions des balles alatoire")));
        gui.addGraphicalElement(new Text(gui.getWidth()/2, gui.getHeight()/2+40, Color.decode("#ffffff"),
                new String("Nombre de balle : "+n)));           
        Balles.reInit();
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            dx[i]=(int)Math.pow(-1,random.nextInt(2))*10;
            dy[i]=(int)Math.pow(-1,random.nextInt(2))*10;
        }
        manager.restart();
        manager.addEvent(new BallsEvent(0, gui, Balles, dx, dy, n, manager));
    }

    public BallsSimulator(GUISimulator gui, int n) {
        this.n=n;
        this.gui = gui;
        Point[] Init = new Point[n];
        dx=new int[n];
        dy=new int[n];
        int randX, randY;
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            randX = random.nextInt(gui.getWidth());
            randY = random.nextInt(gui.getHeight());
            dx[i]=(int)Math.pow(-1,random.nextInt(2))*random.nextInt(10);
            dy[i]=(int)Math.pow(-1,random.nextInt(2))*random.nextInt(10);
            Init[i] = new Point(randX, randY);
        }
        this.Balles = new Balls(Init);
        gui.addGraphicalElement(new Text(gui.getWidth()/2, gui.getHeight()/2-40, Color.decode("#ffffff"),
                new String("Simulateur de Balles")));
        gui.addGraphicalElement(new Text(gui.getWidth()/2, gui.getHeight()/2, Color.decode("#ffffff"),
                new String("Les balles rebondissent sur les rebords ( resolution : 1280x720 )"))); 
        gui.addGraphicalElement(new Text(gui.getWidth()/2, gui.getHeight()/2+20, Color.decode("#ffffff"),
                new String("Velocites et positions des balles alatoire")));
        gui.addGraphicalElement(new Text(gui.getWidth()/2, gui.getHeight()/2+40, Color.decode("#ffffff"),
                new String("Nombre de balle : "+n)));
        
        manager.addEvent(new BallsEvent(0, gui, Balles, dx, dy, n, manager));
    }
}