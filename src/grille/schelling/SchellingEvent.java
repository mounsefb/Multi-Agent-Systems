import gui.GUISimulator;

public class SchellingEvent extends Event{
    private GUISimulator gui;
    private EventManager manager;
    private Schelling schelling;

    public SchellingEvent ( long date, GUISimulator gui,  EventManager manager, Schelling schelling) {
        super(date);
        this.manager=manager;
        this.schelling=schelling;
        this.gui=gui;
    }

    public void execute () {
        gui.reset();
        schelling.schellingStep();
        schelling.maj();
        manager.addEvent(new SchellingEvent(manager.getCurDate()+1, gui,  manager, schelling));
    }
}