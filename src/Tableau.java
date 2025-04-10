import java.awt.Graphics;
import java.util.*;

public class Tableau {

    private static List<CardPile> stacks;
    static Tableau tableau = null;
    private ArrayList<Card> deck;
    private String[] suits = {"hearts", "diamonds", "spades", "clubs"};
    private String[] ranks = {"ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "jack", "queen", "king"};

    public Tableau() {
        stacks = new ArrayList<>();
        deck = createDeck();
        for (int i=0; i<7; i++) {
            stacks.add(new CardPile("T", true));
            stacks.get(i).fillAtFirst(i+1, deck);
            stacks.get(i).setIndex(i);
        }

    }

    public void draw(Graphics g) {
        int x = 50;
        for (CardPile stack : stacks) {
            stack.drawPile(x, g);
            stack.setX(x);
            x += 100;
        }
    }

    public ArrayList<Card> createDeck() {
        ArrayList<Card> deck = new ArrayList<>();
        for (String rank : ranks) {
            for (String suit : suits){
                deck.add(new Card(rank, suit, false, false));
            }
        }
        Collections.shuffle(deck);
        return deck;
    }

    public List<Card> getDeck() {return this.deck;}

    public static List<CardPile> getStacks() {return stacks;}

}
