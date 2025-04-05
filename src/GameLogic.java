import java.util.List;

public class GameLogic {

    public GameLogic() {

    }

    public static boolean canBeAddedToFoundations(Card card, CardPile targetFoundation) {
        int cardRank = card.rankToInt();
        if (card.getVis()) {
            if (!targetFoundation.isEmpty()) {
                Card foundationsLastCard = targetFoundation.getCards().getLast();
                if (cardRank == foundationsLastCard.rankToInt()+1) return true;
            } else {
                if (cardRank == 1) return true;
            }
        }

        return false;
    }

    public static boolean canBeMovedToPile(List<Card> cardsToMove, CardPile targetPile) {
        Card topMovedCard = cardsToMove.get(0);
        int movedCardRank = topMovedCard.rankToInt();
        if (targetPile.isEmpty() && movedCardRank == 13) return true;
        else {
            Card bottomTargetCard = targetPile.getCards().getLast();
            if (canCardsMerge(topMovedCard, bottomTargetCard)) return true;
        }
        return false;
    }

    private static boolean canCardsMerge(Card movedCard, Card targetCard) {
        if (movedCard.rankToInt()==targetCard.rankToInt()-1 && movedCard.getColor()!=targetCard.getColor()) return true;
        return false;
    }

}
