import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

class Tableau extends JComponent {

    private CardPile[] piles = new CardPile[7];
    private int cardWidth = 80;
    private int cardHeight = 120;
    private int spacing = 20;

    public Tableau(List<Card> deck) {
        setLayout(new FlowLayout(FlowLayout.LEFT, spacing, 0));
        setBackground(new Color(0,100,0));
        initializePiles(deck);
    }

    private void initializePiles(List<Card> deck) {
        for (int i = 0; i < 7; i++) {
            int cardsInPile = i + 1;
            piles[i] = new CardPile(this, deck, cardsInPile, 'T');
            add(piles[i]);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        int totalWidth = (cardWidth + spacing) * 7;
        return new Dimension(totalWidth, cardHeight * 4);
    }

    public boolean isValidMoveBetweenPiles(CardPile source, CardPile target, List<Card> draggedCards) {
        if (target.isEmpty()) {
            return draggedCards.get(0).getIntRank() == 13; // Only Kings on empty piles
        }
        
        Card targetTopCard = target.getTopCard();
        Card draggedBaseCard = draggedCards.get(0);
        
        return draggedBaseCard.isOppositeColor(targetTopCard) && 
               draggedBaseCard.getIntRank() == targetTopCard.getIntRank() - 1;
    }

    public void moveCardsBetweenPiles(CardPile source, CardPile target, List<Card> cards) {
        source.removeCards(cards);
        target.addCards(cards);
        repaint();
    }
}
