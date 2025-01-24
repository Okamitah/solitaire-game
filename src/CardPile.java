import java.util.ArrayList;
import javax.swing.*;
import java.util.List;
import java.awt.Graphics;

class CardPile extends JComponent {
    
    private List<Card> cards = new ArrayList<>();
    private int cardSpacing;

    public CardPile(JPanel tableau, List<Card> deck, int order, char type) {
        
        //type == 'F' ? cardSpacing = 0 : cardSpacing = 30;

        for (int i=1; i<=order; i++) {
            Card card = deck.get(0);
            cards.add(card);
            deck.remove(0);
        }

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

    }
 
}
