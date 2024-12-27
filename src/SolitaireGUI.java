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

        JLabel menu = new JLabel("Menu");
        menu.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton newGameButton = new JButton("New Game");
        JButton loadGameButton = new JButton("Load Game");
        JButton statsButton = new JButton("Statistics");
        JButton exitButton = new JButton("Exit");

        startMenu.add(menu);
        startMenu.add(newGameButton);
        startMenu.add(loadGameButton);
        startMenu.add(statsButton);
        startMenu.add(exitButton);

        JPanel gameScreen = new JPanel();
        gameScreen.setBackground(Color.red);
        JButton backButton = new JButton("Back to Menu");
        gameScreen.add(backButton, BorderLayout.SOUTH);


        mainPanel.add(startMenu, "Start Menu");
        mainPanel.add(gameScreen, "Game Screen");

        newGameButton.addActionListener(e -> cardLayout.show(mainPanel, "Game Screen"));
        exitButton.addActionListener(e -> System.exit(0));
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Start Menu"));

        frame.add(mainPanel);

        frame.setVisible(true);
    }

}
