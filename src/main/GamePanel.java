package main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.plaf.basic.BasicTreeUI;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    private static final Logger logger = LogManager.getLogger(GamePanel.class);

    //Screen settings
    final int originalTileSize = 16;
    final int scale = 3;
    final int tileSize = originalTileSize * scale;
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol;
    final int screenHeight = tileSize * maxScreenRow;

    Thread gameThread;  //Thread better for performance
    //BasicTreeUI.KeyHandler keyH = new BasicTreeUI.KeyHandler()

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        // Game loop
        while (gameThread != null) {

            logger.trace("Game loop running");

            update();
            logger.trace("Finished updating game fields");

            repaint();
            logger.trace("Finished repainting sprites");
        }
    }

    public void update() {

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = ((Graphics2D) g);
        g2d.setColor(Color.WHITE);
        g2d.fillRect(100, 100, tileSize, tileSize);
        g2d.dispose();
    }
}
