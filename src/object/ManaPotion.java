package object;

import main.DrawingUtils;
import main.GameConstants;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class ManaPotion extends utilityObject {

    private int restoreAmount = 20; // Amount of mana restored by the potion

    public ManaPotion(int x, int y, GamePanel gamePanel) {
        super(x, y, gamePanel);
        loadManaPotionImage();
        super.name = "Mana Potion";
        super.price = 5;
        super.collider = new Rectangle(0,0,GameConstants.TILE_SIZE,GameConstants.TILE_SIZE);
    }

    private void loadManaPotionImage() {
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/tiles/tile_97.png")));
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
        // Restore the player's mana
        if (gamePanel.getPlayer().getEnergy() < gamePanel.getPlayer().getMaxEnergy()) {
            gamePanel.getPlayer().setEnergy(gamePanel.getPlayer().getEnergy() + restoreAmount);

            // Ensure mana does not exceed max mana
            if (gamePanel.getPlayer().getEnergy() > gamePanel.getPlayer().getMaxEnergy()) {
                gamePanel.getPlayer().setEnergy(gamePanel.getPlayer().getMaxEnergy());
            }

            // Remove the potion from the game
            gamePanel.getObjects().remove(this);
        }
    }
}