package ui;

import entity.Player;
import main.DrawingUtils;
import main.GameConstants;
import main.GamePanel;
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
        drawEnergyBar(g2d);
    }

    public void drawCoin(Graphics2D g2d) {
        g2d.setFont(font);
        g2d.setColor(Color.BLACK);
        g2d.drawString("x" + gamePanel.getPlayer().getInventory().getNbCoins(), 70, 185);
        g2d.drawImage(coinImage, 20, 150, null);
    }

    public void drawInventory(Graphics2D g2d) {
        int frameSize = 48; // Size of each inventory frame
        int framePadding = 10; // Padding between frames
        int totalWidth = (frameSize * 3) + (framePadding * 2); // Total width of the inventory frames

        // Calculate the starting X position to center the inventory frames
        int startX = (gamePanel.getWidth() / 2) - (totalWidth / 2);
        int startY = gamePanel.getHeight() - frameSize - 20; // Position the frames at the bottom with some padding

        int selectedItemIndex = gamePanel.getInventory().getSelectedSlot(); // Get the selected item index

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

            // Highlight the selected item
            if (i == selectedItemIndex) {
                g2d.setColor(Color.YELLOW);
                g2d.setStroke(new BasicStroke(3));
                g2d.drawRoundRect(x - 2, startY - 2, frameSize + 4, frameSize + 4, 10, 10);
                g2d.setStroke(new BasicStroke(1)); // Reset stroke
            }

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

    private void drawEnergyBar(Graphics2D g2d) {
        int barWidth = 200; // Width of the energy bar
        int barHeight = 20; // Height of the energy bar
        int x = 20; // X position of the energy bar
        int y = 120; // Y position of the energy bar

        Player player = gamePanel.getPlayer();
        int energy = player.getEnergy();
        int maxEnergy = player.getMaxEnergy();

        // Calculate the width of the filled portion of the bar
        int filledWidth = (int) ((double) energy / maxEnergy * barWidth);

        // Draw the background of the bar
        g2d.setColor(Color.DARK_GRAY);
        g2d.fillRect(x, y, barWidth, barHeight);

        // Draw the filled portion of the bar
        g2d.setColor(Color.BLUE);
        g2d.fillRect(x, y, filledWidth, barHeight);

        // Draw the border of the bar
        g2d.setColor(Color.BLACK);
        g2d.drawRect(x, y, barWidth, barHeight);
    }

    private void loadImages() {
        try {
            heartImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/UI/heart pixel art 254x254.png")));
            heartImage = DrawingUtils.scaleImage(heartImage, GameConstants.TILE_SIZE, GameConstants.TILE_SIZE);
            emptyHeartImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/UI/emptyHeart.png")));
            emptyHeartImage = DrawingUtils.scaleImage(emptyHeartImage, GameConstants.TILE_SIZE, GameConstants.TILE_SIZE);
            coinImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/objects/coin_1.png")));
            coinImage = DrawingUtils.scaleImage(coinImage, GameConstants.TILE_SIZE, GameConstants.TILE_SIZE);
            shieldImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/UI/shield.png")));
            shieldImage = DrawingUtils.scaleImage(shieldImage, GameConstants.TILE_SIZE, GameConstants.TILE_SIZE);
            emptyShieldImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/UI/emptyShield.png")));
            emptyShieldImage = DrawingUtils.scaleImage(emptyShieldImage, GameConstants.TILE_SIZE, GameConstants.TILE_SIZE);
        } catch (Exception e) {
            logger.error(e);
        }
    }
}
