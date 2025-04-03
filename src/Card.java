import java.awt.Color;
import java.awt.Graphics;

public class Card {

    private String rank;
    private String suit;
    private boolean isFaceUp;
    private int height = 80;
    private int width = 60;
    private String imgPath;

    public Card(String rank, String suit, boolean visibility) {
        this.rank = rank;
        this.suit = suit;
        this.isFaceUp = visibility;
    }

    public void flip() {
        this.isFaceUp = !isFaceUp;
    }

    public boolean getVis() {return isFaceUp;}

    public String getImgPath() {
        return imgPath;
    }

    public String getSuit() {return this.suit;}

    public String getRank() {return this.rank;}

    public void draw(Graphics g, int x, int y) {
        if (isFaceUp) {
            g.setColor(Color.white);
            g.fillRect(x, y, width, height);
            g.setColor(Color.black);
            g.drawRect(x, y, width, height);
            g.drawString(rank + "_of_" + suit, x+10, y+20);
        } else {
            g.setColor(Color.red);
            g.fillRect(x, y, width, height);
        }
    }
}
