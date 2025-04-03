import java.awt.Graphics;
import java.util.*;

public class Tableau {

    private List<List<Card>> stacks;
    public Tableau() {
        stacks = new ArrayList<>();
        for (int i=0; i<7; i++) {
            stacks.add(new CardPile("T", true));
            stacks.get(i).addCards(new Card("A", "hearts", true));
        }
    }

    public void draw(Graphics g) {
        int x = 50;
        for (List<Card> stack : stacks) {
            int y = 100;
            for (Card card : stack) {
                card.draw(g, x, y);
                y += 30;
            }
            x += 120;
        }
    }
}
