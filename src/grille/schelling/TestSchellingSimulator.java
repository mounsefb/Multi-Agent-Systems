import java.awt.Color;
import gui.GUISimulator;

public class TestSchellingSimulator {
    public static void main(String[] args) {
        GUISimulator gui = new GUISimulator(1280, 720, Color.decode("#ffffff"));
        gui.setSimulable(new SchellingSimulator(gui, 36, 64, 5, 4));
    }
}