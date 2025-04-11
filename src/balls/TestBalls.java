import java.awt.Point;

public class TestBalls {
    public static void main(String[] args) {
        Point[] Init = new Point[5];
        for (int i = 0; i < 5; i++) {
            Init[i] = new Point(i, i);
        }
        Balls Balles = new Balls(Init);
        System.out.println(Balles);
        Balles.translate(3, 3);
        System.out.println(Balles);
        Balles.reInit();
        System.out.println(Balles);
    }
}