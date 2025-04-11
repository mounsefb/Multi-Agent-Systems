import gui.GUISimulator;
import gui.Oval;


import java.awt.Color;


public class BallsEvent extends Event {

    private GUISimulator gui;
    private Balls Balles;
    private double lastX, lastY;
    private int[] dx, dy;
    private int n;
    private EventManager manager;


    public BallsEvent ( long date, GUISimulator gui, Balls Balles, int[] dx, int[] dy, int n, EventManager manager) {
        super(date);
        this.gui = gui;
        this.Balles=Balles;
        this.dx=dx;
        this.dy=dy;
        this.n=n;
        this.manager=manager;
    }

    public void execute () {
        gui.reset();
        for (int i = 0; i < n; i++) {
            lastX = Balles.objet[i].getX();
            lastY = Balles.objet[i].getY();
            if (lastY >= gui.getPanelHeight()-100 || lastY<=0) {
                if (lastX>=gui.getPanelWidth() || lastX<=0){
                    dx[i] = -dx[i];
                    dy[i] = -dy[i];
                }
                else {
                    dy[i]=-dy[i];
                }
            }
            else if(lastX>=gui.getPanelWidth() || lastX<=0){
                dx[i] = -dx[i];
            }
            Balles.objet[i].translate(dx[i], dy[i]);
            gui.addGraphicalElement(
                new Oval((int) (Balles.objet[i].getX()), (int) (Balles.objet[i].getY()),
                        Color.decode("#1f77b4"), Color.decode("#1f77b4"), 10));
        };
        manager.addEvent(new BallsEvent(manager.getCurDate()+1, gui, Balles, dx, dy, n, manager));
    }
}
