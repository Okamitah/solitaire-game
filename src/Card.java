public class Card {
    
    public static final String[] SUITS = {"Hearts", "Diamonds", "Clubs", "Spades"};
    public static final String[] RANKS = {"ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "jack", "queen", "king"};
    
    private String suit;
    private String rank;
    private String imgPath;
    private boolean isFaceUp;

    public Card(String suit, String rank) {
        this.suit = suit;
        this.rank = rank;
    }
    
    public String getSuit() {
        return suit;
    }

    public int getIntRank() {
        switch (rank) {
            case "A":
                return 1;
            case "J":
                return 11;
            case "Q":
                return 12;
            case "K":
                return 13;
            default:
                return Integer.parseInt(rank);
        }
    }

    public String getRank() {
        return rank;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    public void setImgPath(String image) {
        this.imgPath = image;
    }

    public void flip() {
        isFaceUp = !isFaceUp;
    }
    

    @Override
    public String toString() {
        return rank + " of " + suit;
    }

}
