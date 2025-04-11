import java.awt.Color;
import gui.GUISimulator;

public class TestImmigrationSimulator {
    public static void main(String[] args) {
        GUISimulator gui = new GUISimulator(1280, 720, Color.decode("#ffffff"));
        gui.setSimulable(new ImmigrationSimulator(gui, 36, 64, 4));
    }
}