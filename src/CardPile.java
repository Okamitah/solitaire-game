import java.util.ArrayList;
import java.util.List;

public class CardPile {

    private int spacing;
    
    private List<Card> cards;
    private List<Card> draggedCards;

    public CardPile(String type, boolean graphical) {
        this.cards = new ArrayList<>();
        switch (type) {
            case "F":
                this.spacing = 0;
                break;
            case "T":
                this.spacing = 30;
            default:
                break;
        }
    }

    public void addCards(ArrayList<Card> addedCards) {
        this.cards.addAll(addedCards);
    }

    public void fillAtFirst(int nbCards, ArrayList<Card> deck) {
        for (int i=0; i<nbCards; i++) {
            Card card = deck.remove(0);
            if (i==nbCards-1) card.flip();
            this.cards.add(card);
        }
    }

    public void removeCards(int removedCardsIndex) {
        Card topCard = cards.get(removedCardsIndex);
        if (topCard.getVis()) {
            for (int i=removedCardsIndex; i<cards.size(); i++) {
                Card card = cards.remove(removedCardsIndex); // I have to return to this later and check
                draggedCards.add(card);
            }
        }
    }

    public void drawPile() {
        for (Card card : cards) {
            System.out.println(card.getRank() + "_of_" + card.getSuit());
        }
    }
}
