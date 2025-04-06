import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CardPile {

    private int spacing;
    private int startY;
    private int startX;
    private List<Card> cards;
    private MovingPile movingPile;
    private List<Card> draggingCards;
    private String content;
    private int tableauIndex;

    public CardPile(String type, boolean graphical) {
        this.cards = new ArrayList<>();
        switch (type) {
            case "FH":
                this.spacing = 0;
                this.startY = 50;
                content = "♡";          
                break;
            case "FD":
                this.spacing = 0;
                this.startY = 50;
                content = "♢";
                break;
            case "FS":
                this.spacing = 0;
                this.startY = 50;
                content = "♤";
                break;
            case "FC":
                this.spacing = 0;
                this.startY = 50;
                content = "♧";
                break;
            case "T":
                this.spacing = 50;
                this.startY = 200;
                content = "K";
            default:
                break;
        }
    }

    public void addCards(Card addedCards) {
        this.cards.add(addedCards);
    }

    public void fillAtFirst(int nbCards, ArrayList<Card> deck) {
        for (int i=0; i<nbCards; i++) {
            Card card = deck.remove(0);
            if (i==nbCards-1) card.flip();
            this.cards.add(card);
        }
    }

    public void removeCard(Card card) {
        this.cards.remove(card);
    }

    public void handleDrag(int dragX, int dragY) {
        int index = -1;
        movingPile = new MovingPile();
        for (Card card : cards) {
            if (card.handlePress(dragX, dragY)>=0) {
                index = card.handlePress(dragX, dragY);
        
                for (int i=index; i<cards.size(); i++) {
                    Card toAddCard = cards.remove(i);
                    draggingCards.add(toAddCard);
                }
                break;
            }
        }
        movingPile.addCardsToDrag(draggingCards);
    }

    public List<Card> getCards() {return this.cards;}

    public void drawPile(int x, Graphics g) {
        int y = startY;
        g.setColor(Color.black);
        g.setFont(new Font("Arial", Font.PLAIN, 15)); 
        g.drawString(content, x+10, y+10);
        g.setColor(Color.black);
        g.drawRect(x, y, 60, 80);
        for (Card card : cards) {
            card.draw(g, x, y);
            card.setXY(x, y);
            y += spacing;
        }
    }
    
    public void setX(int x) {this.startX = x;}

    public void setY(int y) {this.startY = y;}

    public void setIndex(int index) {this.tableauIndex=index;}

    public int getIndex() {return this.tableauIndex;}

    public int getX() {return this.startX;}

    public int getY() {return this.startY;}

    public int getPileLength() {
        int minCard = Math.max(this.getY()+80, this.getY()+80*cards.size());
        return minCard;
    }
    
    public boolean isThisTheStack(int x) {
        if (startX==x) return true;
        return false;
    }

    public boolean isEmpty() {return this.cards.isEmpty();}

    public Card get(int index) {return this.cards.get(index);}
}
