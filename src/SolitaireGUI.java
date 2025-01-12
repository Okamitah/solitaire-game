import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;


public class SolitaireGUI {
    
    private String[] suits = {"hearts", "spades", "clubs", "diamonds"};
    private String[] ranks = {"ace","2","3","4","5","6","7","8","9","10","jack","queen","king"};


    public SolitaireGUI() {

        JFrame frame = new JFrame("Solitaire");
        frame.setSize(1000,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        CardLayout cardLayout = new CardLayout();
        JPanel mainPanel = new JPanel(cardLayout);

        JPanel startMenu = new JPanel();
        startMenu.setLayout(new BoxLayout(startMenu, BoxLayout.Y_AXIS));
        startMenu.setBackground(new Color(0,102,10));

        JLabel menu = new JLabel("Menu");
        menu.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton newGameButton = createButton("New Game");
        JButton loadGameButton = createButton("Load Game");
        JButton statsButton = createButton("Statistics");
        JButton exitButton = createButton("Exit");

        startMenu.add(menu);
        startMenu.add(newGameButton);
        startMenu.add(loadGameButton);
        startMenu.add(statsButton);
        startMenu.add(exitButton);

        // The game screen:
        
        JPanel gameScreen = new JPanel(new BorderLayout());
        gameScreen.setBackground(new Color(0,102,0));

        // Buttons panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonsPanel.setOpaque(false);
        JButton backButton = new JButton("Back to menu");
        JButton giveUpButton = new JButton("Give up");
        buttonsPanel.add(backButton);
        buttonsPanel.add(giveUpButton);
        gameScreen.add(buttonsPanel, BorderLayout.NORTH);
        
        // Cards panel
        JPanel cardsPanel = new JPanel();
        cardsPanel.setLayout(new GridBagLayout());
        cardsPanel.setOpaque(false);

        List<Card> deck = createDeck();
      
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;

        JPanel aboveTableauPanel = new JPanel(new GridLayout(1,2)); 
        aboveTableauPanel.setOpaque(false);
        
        JPanel foundationsPanel = new JPanel(new GridLayout(1,4));
        JPanel heartsFoundation = new JPanel();
        heartsFoundation.setMaximumSize(new Dimension(80, 120));
        
        JPanel diamondsFoundation = new JPanel();
        JPanel clubsFoundation = new JPanel();
        JPanel spadesFoundation = new JPanel();

        Border emptyBorder = new CompoundBorder(
            new LineBorder(Color.LIGHT_GRAY, 2, true),
            new EmptyBorder(10, 10, 10, 10)
        );

        heartsFoundation.setBorder(emptyBorder);
        diamondsFoundation.setBorder(emptyBorder);
        clubsFoundation.setBorder(emptyBorder);
        spadesFoundation.setBorder(emptyBorder);

        heartsFoundation.add(new JLabel("♥", SwingConstants.CENTER));
        diamondsFoundation.add(new JLabel("♦", SwingConstants.CENTER));
        clubsFoundation.add(new JLabel("♣", SwingConstants.CENTER));
        spadesFoundation.add(new JLabel("♠", SwingConstants.CENTER));


        heartsFoundation.setPreferredSize(new Dimension(80, 120));
        diamondsFoundation.setPreferredSize(new Dimension(80, 120));
        clubsFoundation.setPreferredSize(new Dimension(80, 120));
        spadesFoundation.setPreferredSize(new Dimension(80, 120));
   
        foundationsPanel.add(heartsFoundation);
        foundationsPanel.add(diamondsFoundation);
        foundationsPanel.add(clubsFoundation);
        foundationsPanel.add(spadesFoundation);
 
        aboveTableauPanel.add(foundationsPanel);

        JPanel trashPanel = new JPanel(new GridLayout(1,2));
        trashPanel.setOpaque(false);
        
        JPanel stockPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));
        stockPanel.setPreferredSize(new Dimension(80,120));
        stockPanel.setBorder(emptyBorder);

        for (int i=0; i<24; i++) {
            Card card = deck.remove(0);
            JLabel cardLabel = createCardLabel(card, false);
            stockPanel.add(cardLabel);
        }

        trashPanel.add(stockPanel);
        
        JPanel wastePanel = new JPanel();
        wastePanel.setPreferredSize(new Dimension(80,120));
        wastePanel.setBorder(emptyBorder);
        trashPanel.add(wastePanel);

        aboveTableauPanel.add(trashPanel);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 0.25;

        cardsPanel.add(aboveTableauPanel, gbc);
 
        JPanel tableauPanel = new JPanel(new GridLayout(1,9));
        tableauPanel.setBackground(new Color(100,0,0));
        tableauPanel.setOpaque(false);

        for (int i=0; i<9; i++) {
        
            JPanel pilePanel = new JPanel();
            pilePanel.setLayout(new OverlayLayout(pilePanel));
            pilePanel.setOpaque(false);
            pilePanel.setAlignmentY(0.0f);

            if (i==0 || i==8) tableauPanel.add(new JLabel(""));

            else {    

                for (int j=0; j<i; j++) {

                    Card card = deck.remove(0); 
                    JLabel cardLabel;

                    if (j==i-1) {
                        cardLabel = createCardLabel(card, true);
                    } else {
                        cardLabel = createCardLabel(card, false);
                    }

                    cardLabel.setAlignmentY(0.0f);

                    cardLabel.setBorder(BorderFactory.createEmptyBorder(j*20, 0, 0, 0));
                    pilePanel.add(cardLabel,0);    
                }
            }
            tableauPanel.add(pilePanel);
        }

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weighty = 0.75;

        cardsPanel.add(tableauPanel, gbc);
        
       
        gameScreen.add(cardsPanel, BorderLayout.CENTER);


        //cardsPanel.add(tableauPanel);

        // Timer panel
        JPanel timerPanel = new JPanel();
        timerPanel.setBackground(new Color(0,0,0));
        timerPanel.add(new JLabel("Timer: 00:00"));
        gameScreen.add(timerPanel, BorderLayout.SOUTH);

        // Main panel

        mainPanel.add(startMenu, "Start Menu");
        mainPanel.add(gameScreen, "Game Screen");

        newGameButton.addActionListener(e -> cardLayout.show(mainPanel, "Game Screen"));
        exitButton.addActionListener(e -> System.exit(0));
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Start Menu"));
        
        frame.add(mainPanel);

        frame.setVisible(true);
    }

    private JLabel createCardLabel(Card card, boolean isFaceUp) {

        String imgPath = isFaceUp ? "cardsimgs/" + card.getRank() + "_of_" + card.getSuit() + ".png" : "cardsimgs/back.png";
        ImageIcon icon = new ImageIcon(imgPath);

        Image scaledImage = icon.getImage().getScaledInstance(80, 120, Image.SCALE_SMOOTH);
        icon = new ImageIcon(scaledImage);

        JLabel cardLabel = new JLabel(icon);
        cardLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        return cardLabel;
    }

    private List<Card> createDeck() {

        List<Card> deck = new ArrayList<>();
        for (String suit : suits) {

            for (String rank : ranks) { 
                deck.add(new Card(suit, rank));
            }

        }

        Collections.shuffle(deck);

        return deck;
    }

    private static JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(200, 40));
        return button;
    }

}

