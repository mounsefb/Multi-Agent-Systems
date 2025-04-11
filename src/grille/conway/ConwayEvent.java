import gui.GUISimulator;

public class ConwayEvent extends Event{
    private GUISimulator gui;
    private EventManager manager;
    private Conway conway;

    public ConwayEvent ( long date, GUISimulator gui,  EventManager manager, Conway conway) {
        super(date);
        this.manager=manager;
        this.conway=conway;
        this.gui=gui;
    }

    public void execute () {
        gui.reset();
        conway.conwayStep();
        conway.maj();
        manager.addEvent(new ConwayEvent(manager.getCurDate()+1, gui,  manager, conway));
    }
}