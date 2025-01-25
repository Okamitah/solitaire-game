import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.io.IOException;

public class Card {
    
    public static final String[] SUITS = {"hearts", "diamonds", "clubs", "spades"};
    public static final String[] RANKS = {"ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "jack", "queen", "king"};
    
    private String suit;
    private String rank;
    private String imgPath;
    private boolean isFaceUp;

    public Card(String suit, String rank, boolean isFaceUp) {
        this.suit = suit;
        this.rank = rank;
        this.isFaceUp = isFaceUp;
        updateImgPath();
    }
    
    public String getSuit() { return suit; }

    public int getIntRank() {
        switch (rank) {
            case "ace":
                return 1;
            case "jack":
                return 11;
            case "queen":
                return 12;
            case "king":
                return 13;
            default:
                return Integer.parseInt(rank);
        }
    }

    public String getRank() { return rank; }

    public String getImgPath() { return imgPath; }

    public BufferedImage getImg() {

        try {

            BufferedImage originalImage = ImageIO.read(getClass().getResource(imgPath));
            if (originalImage == null) {
                System.err.println("Failed to load image: " + imgPath);
                return null;
            }

            BufferedImage scaledImage = new BufferedImage(80, 120, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = scaledImage.createGraphics();
            g2d.drawImage(originalImage, 0, 0, 80, 120, null);
            g2d.dispose();
            
            return scaledImage;
        } catch (IOException e) {
            System.err.println("error: "+ e);
            return null;
        }
    }

    public boolean getIsFaceUp() { return isFaceUp; }

    public String getColor() { return (suit == "hearts" || suit == "diamonds") ? "red" : "black"; }

    public void setRank(String rank) { this.rank = rank; }
    
    private void updateImgPath() { this.imgPath = isFaceUp ? "cardsimgs/" + rank + "_of_" + suit + ".png" : "cardsimgs/back.png"; }

    public boolean isOppositeColor(Card card) { return (this.getColor() != card.getColor()); }

    public boolean isSameSuit(Card card) { return this.suit == card.suit; }

    public void setSuit(String suit) { this.suit = suit; }

    public void setImgPath(String image) { this.imgPath = image; }

    public void flip() {
        isFaceUp = !isFaceUp;
        updateImgPath();
    }
    

    @Override
    public String toString() { return rank + " of " + suit; }
}
