package main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;

import java.awt.*;

/**
 * Class the game window.
 * Contains the main parameters of the game and the game loop.
 */

public class GamePanel extends JPanel implements Runnable {

    private static final Logger logger = LogManager.getLogger(GamePanel.class);

    //Player settings
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 5;

    //Screen settings
    final int originalTileSize = 16;
    final int scale = 3;
    final int tileSize = originalTileSize * scale;
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol;
    final int screenHeight = tileSize * maxScreenRow;
    double fps = 60;

    Thread gameThread;  //Thread better for performance
    InputHandler inputHandler = new InputHandler();

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(inputHandler);
        this.setFocusable(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    /*
     * Game loop that updates the game and repaints the sprites using delta method.
     * fps value can be changed.
     */
    public void run() {
        double drawInterval = 1000000000 / fps; // Time interval between frames in nanoseconds
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;
        int lastDrawCount = 60; // Keep track of performance variation

        //GAME LOOP
        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                update(); // Update game state
                repaint(); // Render the game
                delta--;
                drawCount++;
            }

            if (timer >= 1000000000) { // If one second has passed
                if (drawCount != lastDrawCount) {
                    logger.info("Fps variation: {}", drawCount);
                }
                drawCount = 0; // Reset frame count
                timer = 0; // Reset timer
            }
        }
    }

    /**
     * This function must contain everything that need to be constantly updated inside the game loop
     */
    public void update() {
        if (inputHandler.isDownPressed()) {
            playerY += playerSpeed;
        }
        if (inputHandler.isUpPressed()) {
            playerY -= playerSpeed;
        }
        if (inputHandler.isLeftPressed()) {
            playerX -= playerSpeed;
        }
        if (inputHandler.isRightPressed()) {
            playerX += playerSpeed;
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = ((Graphics2D) g);
        g2d.setColor(Color.WHITE);
        g2d.fillRect(playerX, playerY, tileSize, tileSize);
        g2d.dispose();
    }
}
