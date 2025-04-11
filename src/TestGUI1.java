import java.awt.Color;
import gui.GUISimulator;
import gui.Rectangle;

public class TestGUI1 {
    public static void main(String[] args) {
        GUISimulator window = new GUISimulator(500, 500, Color.BLACK);
        for (int i = 100; i < 500; i += 100) {
            window.addGraphicalElement(new Rectangle(i, 250, Color.decode("#1f77b4"), Color.decode("#1f77b4"), 10));
        }
    }
}