import java.util.ArrayList;
import javax.swing.*;
import java.util.List;
import java.awt.*;
import java.awt.event.*;

class CardPile extends JComponent {
    
    private List<Card> cards = new ArrayList<>();
    private int cardSpacing;
    private int cardWidth = 80;
    private int cardHeight = 120;
    private List<Card> selectedCards = new ArrayList<>();
    private int cardIndex = -1;

    
    public CardPile(JPanel tableau, List<Card> deck, int order, char type) {
        
        cardSpacing = (type == 'F') ? 0 : 30;

        for (int i=1; i<=order; i++) {
            Card card = deck.get(0);
            cards.add(card);
            deck.remove(0);
        }
        
        if (!cards.isEmpty()) cards.getLast().flip();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardIndex = e.getY() / cardSpacing;
                if (cardIndex >= 0 && cardIndex <= cards.size()) {
                    Card card = cards.get(cardIndex);
                    //flipCard(cardIndex);
                    selectCards();
                }
            }

            //@Override
            //public void mouse
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int yOffset = 0;

        for (Card card : cards) {
            Image image = card.getImg();
            g.setColor(Color.WHITE); 
            g.fillRect(0, yOffset, cardWidth, cardHeight);
            g.setColor(Color.BLACK);
            g.drawImage(image, 0, yOffset, this);
            yOffset += cardSpacing;
        }
    }

    @Override
    public Dimension getPreferredSize() {
        int height = cards.size() *  cardSpacing + (cardHeight - cardSpacing);
        return new Dimension(cardWidth, height);
    }

    private void flipCard(int index) {
        if (index >= 0 && index < cards.size()) {
            cards.get(index).flip();
            repaint();
        }
    } 

    private void selectCards() {
        selectedCards.clear();
        for (int i=cardIndex; i<cards.size(); i++) {
            Card card = cards.get(i);
            if (card.getIsFaceUp()) {
                selectedCards.add(card);
                System.out.println(card);
            }
            else {break;}
        }
    }
}
