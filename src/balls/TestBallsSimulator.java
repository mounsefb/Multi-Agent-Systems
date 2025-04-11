import java.awt.Color;
import gui.GUISimulator;

public class TestBallsSimulator {
    public static void main(String[] args) {
        GUISimulator gui = new GUISimulator(1280, 720, Color.BLACK);
        gui.setSimulable(new BallsSimulator(gui, 50));
    }
}