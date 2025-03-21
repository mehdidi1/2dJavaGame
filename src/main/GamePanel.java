package main;

import entity.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

/**
 * Class the game window.
 * Contains the main parameters of the game and the game loop.
 */
public class GamePanel extends JPanel implements Runnable {

    private static final Logger logger = LogManager.getLogger(GamePanel.class);

    Thread gameThread;  //Thread better for performance
    InputHandler inputHandler = new InputHandler();
    TileManager tileManager = new TileManager(this);
    CollisionChecker collisionChecker = new CollisionChecker(this);

    //Player
    Player player = new Player(this, inputHandler, 100, 100);

    public GamePanel() {
        this.setPreferredSize(new Dimension(GameConstants.SCREEN_WIDTH, GameConstants.SCREEN_HEIGHT));
        this.setBackground(Color.BLUE);
        this.setDoubleBuffered(true);
        this.addKeyListener(inputHandler);
        this.setFocusable(true);
    }

    public void startGameThread() {
        try {
            gameThread = new Thread(this);
            gameThread.start();
        } catch (Exception e) {
            logger.error("Failed to start game thread: {}", e.getMessage());
        }
    }

    public void stopGameThread() {
        if (gameThread != null) {
            try {
                gameThread.join();
                gameThread = null;
            } catch (InterruptedException e) {
                logger.error("Error stopping game thread: {}", e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    /*
     * Game loop that updates the game and repaints the sprites using delta method.
     * fps value can be changed.
     */
    public void run() {
        double drawInterval = 1000000000 / GameConstants.FPS; // Time interval between frames in nanoseconds
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
        player.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        try {
            tileManager.draw(g2d);
            player.draw(g2d);
        } finally {
            g2d.dispose();
        }
    }

    public int getTileSize() {
        return GameConstants.TILE_SIZE;
    }

    public double getFps() {
        return GameConstants.FPS;
    }

    public Player getPlayer() {
        return player;
    }

    public TileManager getTileManager() {
        return tileManager;
    }

    public CollisionChecker getCollisionChecker() {
        return collisionChecker;
    }
}
