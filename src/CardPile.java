import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CardPile {

    private int spacing;
    private int startY;
    private int startX;
    private List<Card> cards;
    private List<Card> draggedCards;
    private String content;

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

    public void removeCards(int removedCardsIndex) {
        Card topCard = cards.get(removedCardsIndex);
        if (topCard.getVis()) {
            for (int i=removedCardsIndex; i<cards.size(); i++) {
                Card card = cards.remove(removedCardsIndex); // I have to return to this later and check
                draggedCards.add(card);
            }
        }
    }

    public void removeCard(Card card) {
        this.cards.remove(card);
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
    
    public boolean isThisTheStack(int x) {
        if (startX==x) return true;
        return false;
    }

    public boolean isEmpty() {return this.cards.isEmpty();}

    public Card get(int index) {return this.cards.get(index);}
}
