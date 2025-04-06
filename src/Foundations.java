import java.util.List;
import java.util.ArrayList;
import java.awt.Graphics;

public class Foundations {

    private static List<CardPile> foundations;

    public Foundations() {
        foundations = new ArrayList<>();
        String[] suits = {"H","D","S","C"};
        for (int i=0; i<4; i++) {
            foundations.add(new CardPile("F"+suits[i],true));
        }
    }
    public void draw(Graphics g) {
        int x = 350;
        for (CardPile foundation : foundations) {
            foundation.drawPile(x,g);
            x += 100;
        }
    }

    public static List<CardPile> getFoundations() {return foundations;}

}
