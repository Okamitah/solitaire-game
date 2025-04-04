import java.awt.Color;
import java.awt.Graphics;

public class Card {

    private String rank;
    private String suit;
    private boolean isFaceUp;
    private int height = 80;
    private int width = 60;
    private String imgPath;
    private int x;
    private int y;
    private CardPile currentStack;

    public Card(String rank, String suit, boolean visibility) {
        this.rank = rank;
        this.suit = suit;
        this.isFaceUp = visibility;
    }

    public void flip() {this.isFaceUp = !isFaceUp;}

    public boolean getVis() {return isFaceUp;}

    public String getImgPath() {return imgPath;}

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

    public Card handleClick(int clickX, int clickY) {

        if (clickX>x && clickX<x+60 && clickY>y && clickY<y+80) {
            CardPile targetFoundation = Foundations.getFoundations().get(this.suitToInt());
            if (GameLogic.canBeAddedToFoundations(this,targetFoundation)) {
                targetFoundation.addCards(this);
                for (CardPile pile : Tableau.getStacks()) {
                    if (pile.isThisTheStack(x)) {
                        currentStack = pile;
                        int indice = currentStack.getCards().indexOf(this);
                        if (indice>0) {
                            Card prevCard = currentStack.get(indice-1);
                            prevCard.flip();
                        }
                    }
                } 
                return this;
            }
        }
        return null;
    }

    public void handlePress(int pressX, int pressY) {
        if (pressX>x && pressX<x+60 && pressY>y && pressY<y+80) {
            int index = getIndex();
            System.out.println(index);
        }
    }

    public void handleDrag(int dragX, int dragY) {
        if (dragX>x && dragX<x+60 && dragY>y && dragY<y+80) {
            this.x = dragX;
            this.y = dragY;
        }
    }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {return this.x;}

    public int getY() {return this.y;}

    public int suitToInt() {
        switch (suit) {
            case "hearts":
                return 0;
            case "diamonds":
                return 1;
            case "spades":
                return 2;
            case "clubs":
                return 3;
            default:
                return -1;
        }
    }

    public int rankToInt() {
        switch (rank) {
            case "K":
                return 13;     
            case "Q":
                return 12;
            case "J":
                return 11;
            case "A":
                return 1;
            default:
                return Integer.parseInt(rank);
        }
    }

    public int getIndex() {
        return y/50 - 4;
    }
}
