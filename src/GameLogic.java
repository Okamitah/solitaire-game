public class GameLogic {

    public GameLogic() {

    }

    public boolean canBeAddedToFoundations(Card card, Foundations foundations) {
        String cardSuit = card.getSuit();
        int cardRank = card.rankToInt();
        CardPile targetFoundation = foundations.getFoundations().get(card.suitToInt());
        if (!targetFoundation.isEmpty()) {
            Card foundationsLastCard = targetFoundation.getCards().getLast();
            if (cardRank == foundationsLastCard.rankToInt()+1) return true;
        } else {
            if (cardRank == 1) return true;
        }
        return false;
    }
}
