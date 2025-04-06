import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;


public class Game extends Frame implements MouseListener, MouseMotionListener {

    private Tableau tableau;
    private Foundations foundations;
    private MovingPile movingPile;
    private DrawAndWastePile drawAndWastePile;
    private List<Card> drawPile;
    private List<Card> wastePile;
    private int clickX;
    private int clickY;
    private int pressX;
    private int pressY;
    private int sourcePileIndex;
    
    public Game() {
        setSize(900,600);
        setTitle("Solitaire");
        setResizable(false);
        setVisible(true);
        addMouseListener(this);
        addMouseMotionListener(this);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                dispose();
            }
        });
        
        tableau = new Tableau();
        foundations = new Foundations();
        List<Card> deck = tableau.getDeck();
        drawAndWastePile = new DrawAndWastePile(deck);
        drawPile = drawAndWastePile.getDrawPile();
        wastePile = drawAndWastePile.getWastePile();

    }

    @Override
    public void paint(Graphics g) {
        tableau.draw(g);
        foundations.draw(g);
        if (movingPile != null && !movingPile.draggedCards.isEmpty()) {
            movingPile.draw(g);
        }

        drawAndWastePile.draw(g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        clickX = e.getX();
        clickY = e.getY();
        Card toRemoveCard = null;
        if (clickY > 180) {
            for (CardPile stack : Tableau.getStacks()) {
                for (Card card : stack.getCards()) {
                    toRemoveCard = card.handleClick(clickX, clickY);
                }
                stack.removeCard(toRemoveCard);
            }
        } else {
            drawAndWastePile.getCard();
        }
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        pressX = e.getX();
        pressY = e.getY();
        movingPile = null;

        for (CardPile stack : Tableau.getStacks()) {

            List<Card> cards = stack.getCards();
            for (int i = 0; i < cards.size(); i++) {

                Card card = cards.get(i);

                if (e.getX() >= card.getX() && e.getX() <= card.getX() + 60 &&
                e.getY() >= card.getY() && e.getY() <= card.getY() + 80) {

                    List<Card> draggedCards = new ArrayList<>();
                    sourcePileIndex = stack.getIndex();

                    for (int j = i; j < cards.size(); j++) {
                        draggedCards.add(cards.remove(j--));
                    }

                    movingPile = new MovingPile();
                    movingPile.addCardsToDrag(draggedCards);
                    movingPile.setXY(e.getX(), e.getY());
                    break;
                }
            }
            if (movingPile != null) break;
        }
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (movingPile != null) {
            movingPile.setXY(e.getX(), e.getY());
            repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {
        if (movingPile != null && !movingPile.draggedCards.isEmpty()) {
            boolean dropped = false;
            for (CardPile pile : Tableau.getStacks()) {
                if (e.getX() >= pile.getX() && e.getX() <= pile.getX() + 60 &&
                e.getY() >= pile.getY() && e.getY() <= pile.getY() + 80 * pile.getCards().size()) {
                    if (GameLogic.canBeMovedToPile(movingPile.getCards(), pile)) {
                        pile.getCards().addAll(movingPile.draggedCards);
                        Card toFlipCard = Tableau.getStacks().get(sourcePileIndex).getCards().getLast();
                        if (!toFlipCard.getVis()) toFlipCard.flip();
                        dropped = true;
                        break;
                    } 
                }
            }
            if (!dropped) {
                Tableau.getStacks().get(sourcePileIndex).getCards().addAll(movingPile.draggedCards);
            }
        }
        movingPile = null;
        repaint();
    }

    @Override
    public void mouseExited(MouseEvent e) {}
}
