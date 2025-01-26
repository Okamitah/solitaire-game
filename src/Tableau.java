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
}
