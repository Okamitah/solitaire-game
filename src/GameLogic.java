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
}
