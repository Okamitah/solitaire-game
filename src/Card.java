import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;


public class Card extends JLabel {
    
    public static final String[] SUITS = {"hearts", "diamonds", "clubs", "spades"};
    public static final String[] RANKS = {"ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "jack", "queen", "king"};
    
    private String suit;
    private String rank;
    private String imgPath;
    private boolean isFaceUp;
    private ImageIcon frontImg;
    private ImageIcon backImg;

    public Card(String suit, String rank, String frontImagePath, String backImagePath) {
        this.suit = suit;
        this.rank = rank;
        this.isFaceUp = false;

        this.frontImg = new ImageIcon(frontImagePath);
        this.backImg = new ImageIcon(backImagePath);

        setPreferredSize(new Dimension(80,120));
        updateAppearance();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                flip();
            }
        });
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

    public boolean getIsFaceUp() { return isFaceUp; }

    public String getColor() { return (suit == "hearts" || suit == "diamonds") ? "red" : "black"; }

    public void setRank(String rank) { this.rank = rank; }
    
    private void updateAppearance() {
        if (isFaceUp) {
            setIcon(frontImg);
        } else {
            setIcon(backImg);
        }
    }

    public boolean isOppositeColor(Card card) { return (this.getColor() != card.getColor()); }

    public boolean isSameSuit(Card card) { return this.suit == card.suit; }

    public void setSuit(String suit) { this.suit = suit; }

    public void flip() {
        isFaceUp = !isFaceUp;
        updateAppearance();
    }
    

    @Override
    public String toString() { return rank + " of " + suit; }
}
