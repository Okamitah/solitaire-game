import java.util.List;
import java.util.Map;
import java.util.Arrays;

public class GameLogic {

    public static boolean canCardGetDragged(Card card) {
        return card.getIsFaceUp();
    }

    public static boolean canBeAddedToFoundation(Card card, Map<String,List<Card>> foundations) { 
        String suit = card.getSuit();
        List<Card> foundation = foundations.get(suit);
        if (foundation.isEmpty()) return card.getRank()=="A";
        else {
            Card lastCard = foundation.get(foundation.size()-1);
            return isCardRankOneUp(card, lastCard);
        }
    }
    
    public static boolean canBeAddedToPile(Card card, List<Card> pile) {
        if (pile.isEmpty()) {
            if (card.getRank() == "king") return true;
            else return false;
        } else {
            Card lastCard = pile.get(pile.size()-1);
            return (!sameColor(card, lastCard) && isCardRankOneUp(lastCard, card));
        }
    }

    public static boolean isCardRankOneUp(Card card1, Card card2) {
        int rank1 = card1.getIntRank();
        int rank2 = card2.getIntRank();
        return rank1 == rank2 - 1;
    }

    public static boolean sameColor(Card card1, Card card2) {
        List<String> red = Arrays.asList("hearts","diamonds");
        List<String> black = Arrays.asList("clubs","spades");

        return ((red.contains(card1.getSuit()) && red.contains(card2.getSuit())) || (black.contains(card1.getSuit()) && black.contains(card2.getSuit())));
    }
}
