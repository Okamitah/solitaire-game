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
    private long pressTime;
    private static final int CLICK_TIME_THRESHOLD = 500;
    private int clickX;
    private int clickY;
    private int pressX;
    private int pressY;
    private int sourcePileIndex;

    public Game() {
        setSize(900, 600);
        setTitle("Solitaire");
        setResizable(true);
        setBackground(new Color(0, 112, 0));
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
        if (movingPile != null && !movingPile.getCards().isEmpty()) {
            movingPile.draw(g);
        }
        drawAndWastePile.draw(g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        clickX = e.getX();
        clickY = e.getY();
        /*Card toRemoveCard = null;
        if (clickY > 180) {
            for (CardPile stack : Tableau.getStacks()) {
                if (clickY<stack.getPileLength()) {
                    for (Card card : stack.getCards()) {
                        toRemoveCard = card.handleClick(clickX, clickY);
                    }
                    stack.removeCard(toRemoveCard);
                }
            }
        } else*/ if (clickX > 50 && clickX < 110 && clickY < 130 && clickY > 50) {
            drawAndWastePile.getCard();
        } /*else if (clickX > 150 && clickX < 210 && clickY < 130 && clickY > 50) {
            Card lastCard = drawAndWastePile.getWastePile().getLast();
            CardPile targetFoundation = Foundations.getFoundations().get(lastCard.suitToInt());
            if (GameLogic.canBeAddedToFoundations(lastCard, targetFoundation)) {
                targetFoundation.addCards(lastCard);
                drawAndWastePile.getWastePile().remove(lastCard);
            }
        }*/
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        pressX = e.getX();
        pressY = e.getY();
        movingPile = null;

        if (pressY > 170) {

            for (CardPile stack : Tableau.getStacks()) {
                
                if (e.getY()<stack.getY()+stack.getPileLength() && e.getY()>stack.getY()) {
                    List<Card> cards = stack.getCards();
                    for (int i = 0; i < cards.size(); i++) {

                        Card card = cards.get(i);

                        if (e.getX() > card.getX() && e.getX() < card.getX() + 60 &&
                        e.getY() > card.getY() && e.getY() < card.getY() + 30 && card.getVis()) {

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
            }
        } else if (pressY > 50 && pressY < 130 && pressX > 150 && pressX < 210) {

            List<Card> draggedCards = new ArrayList<>();
            draggedCards.add(drawAndWastePile.getWastePile().removeLast());
            sourcePileIndex = 10;
            movingPile = new MovingPile();
            movingPile.addCardsToDrag(draggedCards);
            movingPile.setXY(e.getX(), e.getY());
        } else movingPile = new MovingPile();
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (movingPile != null) {
            movingPile.setXY(e.getX(), e.getY());
        }
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        boolean dropped = false;
        for (CardPile pile : Tableau.getStacks()) {
            if (e.getX()>pile.getX() && e.getX()<pile.getX()+60 && e.getY()>pile.getY()
            && e.getY()<pile.getPileLength()) {
                if (movingPile != null && !movingPile.getCards().isEmpty() && GameLogic.canBeMovedToPile(movingPile.getCards(), pile)) {
                    pile.getCards().addAll(movingPile.getCards());
                    if (sourcePileIndex != 10) {
                        List<Card> cardsPile = Tableau.getStacks().get(sourcePileIndex).getCards();
                        if (!cardsPile.isEmpty()) {
                            Card toFlipCard = cardsPile.getLast();
                            if (!toFlipCard.getVis())
                            toFlipCard.flip();
                        }
                    }
                    dropped = true;
                    break;
                }
            }
        }
        if (e.getX()>350 && e.getX()<750 && e.getY()<150 &&
        movingPile.getCards().size()==1 && 
        GameLogic.canBeAddedToFoundations(movingPile.getCards().get(0), Foundations.getFoundations().get(movingPile.getCards().get(0).suitToInt()))) {
            if (movingPile.getCards().size()==1 && GameLogic.canBeAddedToFoundations(movingPile.getCards().get(0), Foundations.getFoundations().get(movingPile.getCards().get(0).suitToInt()))) {
                Foundations.getFoundations().get(movingPile.getCards().get(0).suitToInt()).getCards().addAll(movingPile.getCards());
                if (sourcePileIndex != 10) {
                        List<Card> cardsPile = Tableau.getStacks().get(sourcePileIndex).getCards();
                        if (!cardsPile.isEmpty()) {
                            Card toFlipCard = cardsPile.getLast();
                            if (!toFlipCard.getVis())
                            toFlipCard.flip();
                        }
                }
                dropped = true;
            }
        }
        if (!dropped && movingPile != null) {
            if (sourcePileIndex != 10)
            Tableau.getStacks().get(sourcePileIndex).getCards().addAll(movingPile.getCards());
            else
            drawAndWastePile.getWastePile().addAll(movingPile.getCards());
        }

        movingPile = null;
        repaint();

        if (GameLogic.wonGame()) {
            setBackground(new Color(0, 0, 0));
            System.out.println("🎉🎉🎉 Congratulations! You've won the game! 🎉🎉🎉");
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
