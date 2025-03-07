package main;

import javax.swing.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);

    private static final String WINDOW_TITLE = "2D Java Game";

    public static void main(String[] args) {

        //Create window with appropriate settings
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle(WINDOW_TITLE);
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        window.pack();

        gamePanel.startGameThread();
        gamePanel.requestFocusInWindow(); // Ensure GamePanel receives keyboard events
        logger.info("Game started");
    }
}