import java.awt.Color;
import gui.GUISimulator;
// import gui.Rectangle;

public class TestConwaySimulator {
    public static void main(String[] args) {
        GUISimulator gui = new GUISimulator(1280, 780, Color.decode("#ffffff"));
        gui.setSimulable(new ConwaySimulator(gui, 27, 48));
    }
}