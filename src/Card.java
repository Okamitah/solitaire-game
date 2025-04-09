import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Card implements ImageObserver {

    private String rank;
    private String suit;
    private boolean isFaceUp;
    public static int height = 80;
    public static int width = 60;
    private boolean useImg;
    private String imgPath;
    private Image image;
    private int x;
    private int y;

    public Card(String rank, String suit, boolean visibility, boolean useImg) {
        this.rank = rank;
        this.suit = suit;
        this.isFaceUp = visibility;
        this.useImg = useImg;
    }

    public void flip() {
        this.isFaceUp = !isFaceUp;
    }

    public boolean getVis() {
        return isFaceUp;
    }

    public String getImgPath() {
        return imgPath;
    }

    public String getSuit() {
        return this.suit;
    }

    public String getRank() {
        return this.rank;
    }

    public void draw(Graphics g, int x, int y) {
        if (!useImg) {
            if (isFaceUp) {
                g.setColor(Color.white);
                g.fillRect(x, y, width, height/3);
                g.setColor(Color.black);
                g.drawRect(x, y, width, height);
                if (this.getColor().equals("red")) {
                    g.setColor(Color.red);
                } else {
                    g.setColor(Color.black);
                }
                g.fillRect(x, y + height/3, width, 2*height/3);
                g.drawString(rankToSubRank() + " " + suitToEmoji(), x + 10, y + 20);
            } else {
                g.setColor(Color.blue);
                g.fillRect(x, y, width, height);
                g.setColor(Color.black);
                g.drawRect(x, y, width, height);
            }
        } else {
            if (isFaceUp) {
                imgPath = "cardsimgs/" + rank + "_of_" + suit + ".png";
            } else {
                imgPath = "cardsimgs/back.png";
            }

            System.out.println("Loading image from: " + imgPath);

            try {
                image = ImageIO.read(new File(imgPath));
            } catch (IOException e) {
                System.out.println("Failed to load image: " + imgPath);
                e.printStackTrace();
                image = null;
            }

            g.setColor(Color.black);
            g.drawRect(x, y, width, height);

            if (image != null) {
                g.drawImage(image, x, y, width, height, this);
            } else {
                System.out.println("Image is null. Cannot draw.");
            }
        }
    }


    public int handlePress(int pressX, int pressY) {
        if (pressX > x && pressX < x + width && pressY > y && pressY < y + height) {
            int index = getIndex();
            return index;
        }
        return -1;
    }

    public void handleDrag(int dragX, int dragY) {
        if (dragX > x && dragX < x + width && dragY > y && dragY < y + height) {
            this.x = dragX;
            this.y = dragY;
        }
    }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int suitToInt() {
        switch (suit) {
            case "hearts":
                return 0;
            case "diamonds":
                return 1;
            case "spades":
                return 2;
            case "clubs":
                return 3;
            default:
                return -1;
        }
    }

    public String suitToEmoji() {
        switch (suit) {
            case "hearts":
                return "♡";
            case "diamonds":
                return "♢";
            case "spades":
                return "♤";
            case "clubs":
                return "♧";
            default:
                return "";
        }
    }

    public String rankToSubRank() {
        switch (rank) {
            case "king":
                return "K"; 
            case "queen":
                return "Q";
            case "jack":
                return "J";
            case "ace":
                return "A";
            default:
                return rank;
        }
    }

    public int rankToInt() {
        switch (rank) {
            case "king":
                return 13;
            case "queen":
                return 12;
            case "jack":
                return 11;
            case "ace":
                return 1;
            default:
                return Integer.parseInt(rank);
        }
    }

    public int getIndex() {
        return y / 50 - 4;
    }

    public String getColor() {
        return (this.suit.equals("hearts") || this.suit.equals("diamonds")) ? "red" : "black";
    }

    @Override
    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
        return (infoflags & (ALLBITS | ABORT)) == 0;
    }
}
