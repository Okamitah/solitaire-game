import javax.swing.*;
import java.awt.*;

public class SolitaireGUI {


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

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;

        JPanel aboveTableauPanel = new JPanel();
        aboveTableauPanel.setBackground(new Color(0, 153, 0));
        aboveTableauPanel.setLayout(new GridLayout(1,2));
        aboveTableauPanel.setOpaque(false);
        
        JPanel foundationPanel = new JPanel();
        foundationPanel.add(new JLabel("Foundation", SwingConstants.CENTER));
        aboveTableauPanel.add(foundationPanel);

        JPanel trashPanel = new JPanel();
        trashPanel.add(new JLabel("Trash", SwingConstants.CENTER));
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
                for (int j = 0; j < i+1; j++) {
                    Card card = new Card("Hearts", String.valueOf(j+1), "cardsimgs/" + (j + 1) + "_of_spades.png");
                    JLabel cardLabel;

                    if (j == i) {
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
        
        aboveTableauPanel.setPreferredSize(new Dimension(1000, 60));
        aboveTableauPanel.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
        tableauPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
        

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
        String imgPath = isFaceUp ? card.getImgPath() : "cardsimgs/back.png";
        ImageIcon icon = new ImageIcon(imgPath);
        Image scaledImage = icon.getImage().getScaledInstance(80, 120, Image.SCALE_SMOOTH); // Resize image
        icon = new ImageIcon(scaledImage);
        JLabel cardLabel = new JLabel(icon);
        cardLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        return cardLabel;
    }


    private static JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(200, 40));
        return button;
    }

}

