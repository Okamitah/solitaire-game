import java.util.ArrayList;
import java.util.List;
import java.awt.Graphics;

public class DrawAndWastePile {
    private List<Card> drawPile;
    private List<Card> wastePile;

    public DrawAndWastePile(List<Card> deck) {

        drawPile = new ArrayList<>();
        wastePile = new ArrayList<>();

        for (int i=0; i<24; i++) {
            drawPile.add(deck.removeLast());
        }
    }

    public void draw(Graphics g) {
        if (!drawPile.isEmpty()) {
            drawPile.getLast().draw(g, 50, 50);
        } else {
            //g.setColor(Color.black);
            g.drawArc(50, 50, Card.width, Card.height, 30, 30);
        }
        if (!wastePile.isEmpty()) wastePile.getLast().draw(g, 150, 50);
    }

    public void getCard() {
        if (!drawPile.isEmpty()) {
            Card gotCard = drawPile.remove(0);
            gotCard.flip();
            wastePile.add(gotCard);
        }
        else {
            drawPile.addAll(wastePile);
            wastePile.removeAll(wastePile);
            for (Card card : drawPile) card.flip();
        }
    }

    public List<Card> getDrawPile() {return this.drawPile;}

    public List<Card> getWastePile() {return this.wastePile;}
}
