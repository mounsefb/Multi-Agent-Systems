import gui.GUISimulator;

public class ImmigrationEvent extends Event{
    private GUISimulator gui;
    private EventManager manager;
    private Immigration immigration;

    public ImmigrationEvent ( long date, GUISimulator gui,  EventManager manager, Immigration immigration) {
        super(date);
        this.manager=manager;
        this.immigration=immigration;
        this.gui=gui;
    }

    public void execute () {
        gui.reset();
        immigration.immigrationStep();
        immigration.maj();
        manager.addEvent(new ImmigrationEvent(manager.getCurDate()+1, gui,  manager, immigration));
    }
}