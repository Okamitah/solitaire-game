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

        JPanel gameScreen = new JPanel();
        gameScreen.setBackground(new Color(0,102,0));
        JButton backButton = new JButton("Back to menu");
        JButton giveUpButton = new JButton("Give up");

        gameScreen.add(backButton, BorderLayout.SOUTH);
        gameScreen.add(giveUpButton, "Give up");


        mainPanel.add(startMenu, "Start Menu");
        mainPanel.add(gameScreen, "Game Screen");

        newGameButton.addActionListener(_ -> cardLayout.show(mainPanel, "Game Screen"));
        exitButton.addActionListener(_ -> System.exit(0));
        backButton.addActionListener(_ -> cardLayout.show(mainPanel, "Start Menu"));

        frame.add(mainPanel);

        frame.setVisible(true);
    }

    private static JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(200, 40));
        return button;
    }

}
