package main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class UI {
    private static final Logger logger = LogManager.getLogger(UI.class);
    GamePanel gamePanel;
    private final Font font;
    private BufferedImage heartImage;
    private BufferedImage emptyHeartImage;
    private BufferedImage coinImage;
    private BufferedImage shieldImage;
    private BufferedImage emptyShieldImage;

    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        font = new Font("Silkscreen", Font.PLAIN, 30);
        loadImages();
    }

    public void draw(Graphics2D g2d) {
        drawCoin(g2d);
        drawInventory(g2d);
        drawHearts(g2d);
        drawShields(g2d);
    }

    public void drawCoin(Graphics2D g2d) {
        g2d.setFont(font);
        g2d.setColor(Color.BLACK);
        g2d.drawString("x" + gamePanel.getPlayer().getSpeed(), 70, 145);
        g2d.drawImage(coinImage, 20, 110, null);
    }

    public void drawInventory(Graphics2D g2d) {
        int frameSize = 48; // Size of each inventory frame
        int framePadding = 10; // Padding between frames
        int totalWidth = (frameSize * 3) + (framePadding * 2); // Total width of the inventory frames

        // Calculate the starting X position to center the inventory frames
        int startX = (gamePanel.getWidth() / 2) - (totalWidth / 2);
        int startY = gamePanel.getHeight() - frameSize - 20; // Position the frames at the bottom with some padding

        // Draw the 3 inventory frames
        for (int i = 0; i < 3; i++) {
            int x = startX + (i * (frameSize + framePadding));

            // Draw shadow
            g2d.setColor(new Color(0, 0, 0, 100));
            g2d.fillRoundRect(x + 3, startY + 3, frameSize, frameSize, 10, 10);

            // Draw gradient background
            GradientPaint gradient = new GradientPaint(x, startY, Color.LIGHT_GRAY, x, startY + frameSize, Color.DARK_GRAY);
            g2d.setPaint(gradient);
            g2d.fillRoundRect(x, startY, frameSize, frameSize, 10, 10);

            // Draw border
            g2d.setColor(Color.BLACK);
            g2d.drawRoundRect(x, startY, frameSize, frameSize, 10, 10);

            // Draw the item icon if available
            BufferedImage itemIcon = gamePanel.getInventory().getItemIcon(i);
            if (itemIcon != null) {
                int iconX = x + (frameSize - itemIcon.getWidth()) / 2;
                int iconY = startY + (frameSize - itemIcon.getHeight()) / 2;
                g2d.drawImage(itemIcon, iconX, iconY, null);
            }
        }
    }

    private void drawHearts(Graphics2D g2d) {
        int heartSize = 32; // Size of each heart
        int heartPadding = 10; // Padding between hearts
        int startX = 20; // Starting X position
        int startY = 20; // Starting Y position

        int health = gamePanel.getPlayer().getHealth(); // Assuming the Player class has a getHealth() method
        int maxHealth = gamePanel.getPlayer().getMaxHealth(); // Maximum health value

        // Draw hearts
        drawConsecutiveUI(g2d, heartSize, heartPadding, startX, startY, health, maxHealth, heartImage, emptyHeartImage);
    }

    private void drawShields(Graphics2D g2d) {
        int shieldSize = 32; // Size of each shield
        int shieldPadding = 10; // Padding between shields
        int startX = 20; // Starting X position
        int startY = 70; // Starting Y position (below the hearts)

        int shields = gamePanel.getPlayer().getShields(); // Assuming the Player class has a getShields() method
        int maxShields = gamePanel.getPlayer().getMaxShields(); // Maximum shield value

        // Draw shields
        drawConsecutiveUI(g2d, shieldSize, shieldPadding, startX, startY, shields, maxShields, shieldImage, emptyShieldImage);
    }

    private void drawConsecutiveUI(Graphics2D g2d, int shieldSize, int shieldPadding, int startX, int startY, int shields, int maxShields, BufferedImage shieldImage, BufferedImage emptyShieldImage) {
        for (int i = 0; i < maxShields; i++) {
            int x = startX + (i * (shieldSize + shieldPadding));
            if (i < shields) {
                g2d.drawImage(shieldImage, x, startY, null);
            } else {
                g2d.drawImage(emptyShieldImage, x, startY, null);
            }
        }
    }

    private void loadImages() {
        DrawingUtils drawingUtils = new DrawingUtils();
        try {
            heartImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/UI/heart pixel art 254x254.png")));
            heartImage = drawingUtils.scaleImage(heartImage, GameConstants.TILE_SIZE, GameConstants.TILE_SIZE);
            emptyHeartImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/UI/emptyHeart.png")));
            emptyHeartImage = drawingUtils.scaleImage(emptyHeartImage, GameConstants.TILE_SIZE, GameConstants.TILE_SIZE);
            coinImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/objects/coin_1.png")));
            coinImage = drawingUtils.scaleImage(coinImage, GameConstants.TILE_SIZE, GameConstants.TILE_SIZE);
            shieldImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/UI/shield.png")));
            shieldImage = drawingUtils.scaleImage(shieldImage, GameConstants.TILE_SIZE, GameConstants.TILE_SIZE);
            emptyShieldImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/UI/emptyShield.png")));
            emptyShieldImage = drawingUtils.scaleImage(emptyShieldImage, GameConstants.TILE_SIZE, GameConstants.TILE_SIZE);
        } catch (Exception e) {
            logger.error(e);
        }
    }
}
