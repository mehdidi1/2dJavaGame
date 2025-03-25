package object;

import main.DrawingUtils;
import main.GameConstants;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class HealthPotion extends utilityObject {

    private int healAmount = 2; // Amount of health restored by the potion

    public HealthPotion(int x, int y, GamePanel gamePanel) {
        super(x, y, gamePanel);
        loadHealthPotionImage();
        super.name = "Health Potion";
        super.price = 5;
        super.collider = new Rectangle(0,0,GameConstants.TILE_SIZE,GameConstants.TILE_SIZE);
    }

    private void loadHealthPotionImage() {
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/tiles/tile_89.png")));
            image = DrawingUtils.scaleImage(image, GameConstants.TILE_SIZE / 2, GameConstants.TILE_SIZE / 2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Graphics2D g2d) {
        int screenX = DrawingUtils.calculateScreenX(getWorldX(), gamePanel);
        int screenY = DrawingUtils.calculateScreenY(getWorldY(), gamePanel);
        g2d.drawImage(image, screenX, screenY, null);
    }


    @Override
    public void performAttack() {
        // Heal the player
        if (gamePanel.getPlayer().getHealth() < gamePanel.getPlayer().getMaxHealth()) {
            gamePanel.getPlayer().setHealth(gamePanel.getPlayer().getHealth() + healAmount);

            // Ensure health does not exceed max health
            if (gamePanel.getPlayer().getHealth() > gamePanel.getPlayer().getMaxHealth()) {
                gamePanel.getPlayer().setHealth(gamePanel.getPlayer().getMaxHealth());
            }

            // Remove the potion from the game
            gamePanel.getObjects().remove(this);
        }
    }
}