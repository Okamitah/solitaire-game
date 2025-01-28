import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class CardPile extends JComponent {
    private List<Card> cards = new ArrayList<>();
    private int cardSpacing;
    private int cardWidth = 80;
    private int cardHeight = 120;
    private static Point globalDragPosition;
    private static CardPile draggingSource;
    private static List<Card> draggingCards = new ArrayList<>();
    private Point dragOffset;

    public CardPile(Tableau tableau, List<Card> deck, int order, char type) {
        this.cards = new ArrayList<>();
        this.cardSpacing = (type == 'F') ? 0 : 30;

        for (int i = 1; i <= order; i++) {
            Card card = deck.get(0);
            cards.add(card);
            deck.remove(0);
        }

        setupDragDrop(tableau);

        if (!cards.isEmpty()) {
            cards.get(cards.size() - 1).flip();
        }

        updateMouseListeners(tableau);
    }
   
    
    private void setupDragDrop(Tableau tableau) {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (!draggingCards.isEmpty()) {
                    Point screenPoint = e.getLocationOnScreen();
                    Point tableauPoint = new Point(screenPoint);
                    SwingUtilities.convertPointFromScreen(screenPoint, tableau);

                    
                    Component targetComponent = SwingUtilities.getDeepestComponentAt(
                        tableau, 
                        tableauPoint.x, 
                        tableauPoint.y
                    );

                    if (targetComponent instanceof CardPile) {
                        CardPile targetPile = (CardPile) targetComponent;
                        if (tableau.isValidMoveBetweenPiles(CardPile.this, targetPile, draggingCards)) {
                            tableau.moveCardsBetweenPiles(CardPile.this, targetPile, draggingCards);
                        }
                    }
                    
                    draggingSource = null;
                    draggingCards.clear();
                    repaint();
                }
            }
        });
        
    }

    public void removeCards(List<Card> cardsToRemove) {
        cards.removeAll(cardsToRemove);
        if (!cards.isEmpty() && !cards.getLast().getIsFaceUp()) {
            cards.getLast().flip();
        }
    }

    

    private void updateMouseListeners(Tableau tableau) {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int clickedIndex = e.getY() / cardSpacing;
                if (clickedIndex >= 0 && clickedIndex < cards.size()) {
                    draggingCards.clear();
                    for (int i = clickedIndex; i < cards.size(); i++) {
                        if (cards.get(i).getIsFaceUp()) {
                            draggingCards.add(cards.get(i));
                        } else {
                            break;
                        }
                    }
                    if (!draggingCards.isEmpty()) {
                        dragOffset = new Point(e.getX(), e.getY() - clickedIndex * cardSpacing);
                        draggingSource = CardPile.this;
                    }
                }
            }

        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (!draggingCards.isEmpty()) {
                    globalDragPosition = e.getLocationOnScreen();
                    repaint();
                }
            }
        });

    }

    private boolean isValidMove(CardPile targetPile) {
        if (targetPile.isEmpty()) {
            return draggingCards.get(0).getIntRank() == 13;
        } else {
            Card topCard = targetPile.getTopCard();
            return draggingCards.get(0).isOppositeColor(topCard) &&
                   draggingCards.get(0).getIntRank() == topCard.getIntRank() - 1;
        }
    }

    private void moveCardsToPile(CardPile targetPile) {
        draggingSource.cards.removeAll(draggingCards);
        targetPile.addCards(draggingCards);
        draggingSource.repaint();
        targetPile.repaint();
    }

    public void addCards(List<Card> newCards) {
        cards.addAll(newCards);
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    public Card getTopCard() {
        return cards.isEmpty() ? null : cards.get(cards.size() - 1);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(new Color(0, 100, 0));
        g.fillRect(0, 0, getWidth(), getHeight());

        int yOffset = 0;
        for (Card card : cards) {
            if (!draggingCards.contains(card)) {
                g.drawImage(card.getImg(), 0, yOffset, this);
            }
            yOffset += cardSpacing;
        }

        if (this == draggingSource && !draggingCards.isEmpty()) {
            Point mousePos = getMousePosition();
            if (mousePos != null) {
                int dragY = mousePos.y - dragOffset.y;
                for (int i = 0; i < draggingCards.size(); i++) {
                    g.drawImage(draggingCards.get(i).getImg(), 0, dragY + i * cardSpacing, this);
                }
            }
        }
    }

    @Override
    public Dimension getPreferredSize() {
        int height = cards.size() * cardSpacing + (cardHeight - cardSpacing);
        return new Dimension(cardWidth, height);
    }
}
