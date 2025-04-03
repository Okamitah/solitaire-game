import java.awt.*;
import java.util.List;
import java.util.ArrayList;


public class Game extends Frame {

    private Tableau tableau;
    private List<Card> drawPile;
    private List<Card> discardPile;
    
    public Game() {
        setSize(900,600);
        setTitle("Solitaire");
        setResizable(false);
        setVisible(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                dispose();
            }
        });
        
        drawPile = new ArrayList<>();
        discardPile = new ArrayList<>();
        tableau = new Tableau();

        for (int i = 0; i < 24; i++) {
            drawPile.add(new Card("SPADES", "A", true));
        }
    }

    @Override
    public void paint(Graphics g) {
        // Draw the tableau
        tableau.draw(g);

        // Draw the draw pile
        if (!drawPile.isEmpty()) {
            drawPile.get(0).draw(g, 50, 500); // Example position
        }

        // Draw the discard pile
        if (!discardPile.isEmpty()) {
            discardPile.get(discardPile.size() - 1).draw(g, 200, 500);
        }
    }
}
