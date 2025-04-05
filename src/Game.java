import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;


public class Game extends Frame implements MouseListener, MouseMotionListener {

    private Tableau tableau;
    private Foundations foundations;
    private List<Card> drawPile;
    private List<Card> discardPile;
    private int clickX;
    private int clickY;
    private int pressX;
    private int pressY;
    
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
        
        drawPile = new ArrayList<>();
        discardPile = new ArrayList<>();
        tableau = new Tableau();
        foundations = new Foundations();
        List<Card> deck = tableau.getDeck();

        for (int i = 0; i < 24; i++) {
            Card card = deck.removeLast();
            drawPile.add(card);
        }
    }

    @Override
    public void paint(Graphics g) {
        tableau.draw(g);
        foundations.draw(g);

        if (!drawPile.isEmpty()) {
            drawPile.get(0).draw(g, 50, 50);
        }

        if (!discardPile.isEmpty()) {
            discardPile.get(discardPile.size() - 1).draw(g, 200, 50);
        }
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
            drawPile.get(0).flip();
        }
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        pressX = e.getX();
        pressY = e.getY();
        for (CardPile stack : Tableau.getStacks()) {
            for (Card card : stack.getCards()) {
                card.handlePress(pressX, pressY);
            }
        }
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int dragX = e.getX();
        int dragY = e.getY();
        for (CardPile stack : Tableau.getStacks()) {
            for (Card card : stack.getCards()) {
                card.handleDrag(dragX, dragY);
            }
        }

        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
