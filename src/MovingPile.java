import java.awt.Graphics;
import java.util.*;

public class MovingPile {

    private List<Card> draggedCards;
    private int draggedX;
    private int draggedY;

    public MovingPile() {
        draggedCards = new ArrayList<>();
    }

    public void addCardsToDrag(List<Card> cardsToAdd) {draggedCards.addAll(cardsToAdd);}

    public void dropCards(CardPile targetPile) {
        List<Card> targetCards = targetPile.getCards();
        targetCards.addAll(draggedCards);
        draggedCards.removeAll(draggedCards);
    }

    public void draw(Graphics g) {
        for (Card card : draggedCards) {
            card.draw(g, draggedX, draggedY);
            card.setXY(draggedX, draggedY);
            draggedY += 50;
        }
    }

    public void setXY(int x, int y) {
        this.draggedX = x;
        this.draggedY = y;
    }

    public int getX() {return this.draggedX;}

    public int getY() {return this.draggedY;}

    public List<Card> getCards() {return this.draggedCards;}
}
