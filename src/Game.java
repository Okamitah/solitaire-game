import javax.swing.*;
import java.util.*;
import java.util.List;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

class Game {

    private String[] suits = {"hearts", "spades", "clubs", "diamonds"};
    private String[] ranks = {"ace","2","3","4","5","6","7","8","9","10","jack","queen","king"};
    private List<Card> stock = new ArrayList<>();
    private List<Card> waste = new ArrayList<>();
    private Map<String,List<Card>> foundationsLists = new HashMap<>();
    private List<Card> hearts = new ArrayList<>();
    private List<Card> diamonds = new ArrayList<>();
    private List<Card> spades = new ArrayList<>();
    private List<Card> clubs = new ArrayList<>();
    private Map<String,List<Card>> piles = new HashMap<>();

    public Game() {

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
        JButton newGameButton = new JButton("New Game");
        JButton loadGameButton = new JButton("Load Game");
        JButton statsButton = new JButton("Statistics");
        JButton exitButton = new JButton("Exit");
        JButton seePile = new JButton("see pile");

        startMenu.add(menu);
        startMenu.add(newGameButton);
        startMenu.add(loadGameButton);
        startMenu.add(statsButton);
        startMenu.add(exitButton);
        startMenu.add(seePile);

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

              
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;

        JPanel aboveTableauPanel = new JPanel(new GridLayout(1,2)); 
        aboveTableauPanel.setOpaque(false);
        
        JPanel foundationsPanel = new JPanel(new GridLayout(1,4));

        JPanel heartsFoundation = new JPanel();        
        JPanel diamondsFoundation = new JPanel();
        JPanel clubsFoundation = new JPanel();
        JPanel spadesFoundation = new JPanel();

        heartsFoundation.add(new JLabel("♥", SwingConstants.CENTER));
        diamondsFoundation.add(new JLabel("♦", SwingConstants.CENTER));
        clubsFoundation.add(new JLabel("♣", SwingConstants.CENTER));
        spadesFoundation.add(new JLabel("♠", SwingConstants.CENTER));


        Border emptyBorder = new CompoundBorder(
            new LineBorder(Color.LIGHT_GRAY, 2, true),
            new EmptyBorder(10, 10, 10, 10)
        );

        Map<String,JPanel> foundationsMap = new HashMap<>();

        foundationsMap.put("hearts", heartsFoundation);
        foundationsMap.put("diamonds", diamondsFoundation);
        foundationsMap.put("clubs", clubsFoundation);
        foundationsMap.put("spades", spadesFoundation);
        
        for (JPanel foundation : foundationsMap.values()) {
            foundation.setBorder(emptyBorder);
            foundation.setPreferredSize(new Dimension(80, 120));
            foundationsPanel.add(foundation);
        }

        aboveTableauPanel.add(foundationsPanel);

        JPanel trashPanel = new JPanel(new GridLayout(1,4));
        trashPanel.setOpaque(false);

        JPanel pl1 = new JPanel();
        pl1.setBackground(new Color(0,102,0));
        trashPanel.add(pl1);
        JPanel pl2 = new JPanel();
        pl2.setBackground(new Color(0,102,0));
        trashPanel.add(pl2);
        
        JPanel stockPanel = new JPanel();
        stockPanel.setLayout(new OverlayLayout(stockPanel));
        stockPanel.setBackground(new Color(0,102,0));
        stockPanel.setPreferredSize(new Dimension(80,120));
        stockPanel.setOpaque(false);

        List<Card> deck = createDeck();

        createStock(deck, stockPanel);

        
        trashPanel.add(stockPanel);
        
        JPanel wastePanel = new JPanel();
        wastePanel.setLayout(new OverlayLayout(wastePanel));
        wastePanel.setPreferredSize(new Dimension(80,120));
        wastePanel.setBackground(new Color(0,102,0));
        trashPanel.add(wastePanel);
        aboveTableauPanel.add(trashPanel);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 0.25;
        cardsPanel.add(aboveTableauPanel, gbc);
 
        JPanel tableauPanel = new JPanel(new GridLayout(1,9));
        tableauPanel.setBackground(new Color(100,0,0));
        tableauPanel.setOpaque(false);
        createTableau(tableauPanel, deck);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weighty = 0.75;
        cardsPanel.add(tableauPanel, gbc);
               
        gameScreen.add(cardsPanel, BorderLayout.CENTER);

        //
        //
        JPanel cardPile = new JPanel();
        JPanel tabPanel = new JPanel(new GridLayout(1,9));
        tabPanel.setBackground(new Color(100,0,0));
        tabPanel.setOpaque(false);

        cardPile.add(tabPanel);
        CardPile pile = new CardPile(tabPanel, createDeck(), 2, 't');
        cardPile.add(pile);
 
        // Timer panel
        JPanel timerPanel = new JPanel();
        timerPanel.setBackground(new Color(0,0,0));
        timerPanel.add(new JLabel("Timer: 00:00"));
        gameScreen.add(timerPanel, BorderLayout.SOUTH);
        // Main panel
        mainPanel.add(startMenu, "Start Menu");
        mainPanel.add(gameScreen, "Game Screen");

        mainPanel.add(cardPile, "CardPile");
        // Event listeners
        newGameButton.addActionListener(e -> cardLayout.show(mainPanel, "Game Screen"));
        exitButton.addActionListener(e -> System.exit(0));
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Start Menu"));
        seePile.addActionListener(e -> cardLayout.show(mainPanel, "CardPile"));
        stockPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (stockPanel.getComponentCount() > 0 && !stock.isEmpty()) {
                    Component cardComponent = stockPanel.getComponent(0);
                    stockPanel.remove(cardComponent);
                    Card drawnCard = stock.remove(0);
                    waste.add(drawnCard);
                    JLabel faceUpCard = createCardLabel(drawnCard, true);
                    wastePanel.add(faceUpCard,0); 
                    stockPanel.revalidate();
                    stockPanel.repaint();
                    wastePanel.revalidate();
                    wastePanel.repaint();
                }
            }
        });
        frame.add(mainPanel); 
        frame.setVisible(true);
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


    private void createStock(List<Card> deck, JPanel stockPanel) {
        for (int i=0; i<24; i++) {
            Card card = deck.remove(0);
            stock.add(card);
            JLabel cardLabel = createCardLabel(card, false);
            stockPanel.add(cardLabel);
            stockPanel.putClientProperty("label"+(i+1), cardLabel);
        }
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
    
    private void createTableau(JPanel tableauPanel, List<Card> deck) {
        
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
                    List<Card> pile = piles.get("pile"+i);
                    if (pile == null) {
                        pile = new ArrayList<>();
                        piles.put("pile"+i,pile);
                    } else {
                        pile.add(card);
                    }
                   
                    cardLabel.setBorder(BorderFactory.createEmptyBorder(j*30, 0, 0, 0));
                    pilePanel.add(cardLabel,0);   
                    
                }
                pilePanel.putClientProperty("pileNumber",i);
                
            }
            tableauPanel.add(pilePanel);
        }
    }

}
