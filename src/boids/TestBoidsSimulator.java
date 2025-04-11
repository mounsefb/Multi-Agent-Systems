import java.awt.Color;
import gui.GUISimulator;

public class TestBoidsSimulator {
    public static void main(String[] args) {
        GUISimulator gui = new GUISimulator(1280, 720, Color.decode("#000000"));
        gui.setSimulable(new BoidsSimulator(gui, 250));
    }
}
