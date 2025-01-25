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
    private Point dragOffset;

    
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
                    if (!selectedCards.isEmpty()) {
                        dragOffset = new Point(e.getX(), e.getY() - cardIndex * cardSpacing);
                    }
                }
            }

            /*@Override
            public void mouseReleased(MouseEvent e) {
                if (!selectedCards.isEmpty()) {
                    Component target = SwingUtilities.getDeepestComponentAt(
                        getTopLevelAncestor(), e.getXOnScreen(), e.getYOnScreen()
                    );
                    if (target instanceof CardPile) {
                        CardPile targetPile = (CardPile) target;
                        if (isValidMove(targetPile)) {
                            moveCardsToPile(targetPile);
                        }
                    }
                    selectedCards.clear();
                    repaint();
                }
            }*/
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (!selectedCards.isEmpty()) {
                    int x = e.getX() - dragOffset.x;
                    int y = e.getY() - dragOffset.y;
                    repaint();
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int yOffset = 0;

        for (Card card : cards) {
            if (!selectedCards.contains(card)){
                Image image = card.getImg();
                g.drawImage(image, 0, yOffset, this);
                yOffset += cardSpacing;
            }
        }

        if (!selectedCards.isEmpty()) {
            Point mousePos = getMousePosition();
            if (mousePos != null) {
                int dragY = mousePos.y - dragOffset.y;
                for (int i = 0; i < selectedCards.size(); i++) {
                    g.drawImage(selectedCards.get(i).getImg(), 0, dragY + i * cardSpacing, this);
                }
            }
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
