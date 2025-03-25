package object;

import main.DrawingUtils;
import main.GameConstants;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class Coin extends utilityObject {

    private BufferedImage coinImage;

    public Coin(int x, int y, GamePanel gamePanel) {
        super(x, y, gamePanel);
        loadCoinImage();
        super.collider = new Rectangle(0,0,GameConstants.TILE_SIZE,GameConstants.TILE_SIZE);
    }

    private void loadCoinImage() {
        try {
            coinImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/objects/coin_1.png")));
            coinImage = DrawingUtils.scaleImage(coinImage, GameConstants.TILE_SIZE / 2, GameConstants.TILE_SIZE / 2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Graphics2D g2d) {
        int screenX = DrawingUtils.calculateScreenX(getWorldX(), gamePanel);
        int screenY = DrawingUtils.calculateScreenY(getWorldY(), gamePanel);
        g2d.drawImage(coinImage, screenX, screenY, null);
    }


    @Override
    public void performAttack() {
        // Increase the player's coin counter
        gamePanel.getPlayer().getInventory().setNbCoins(gamePanel.getPlayer().getInventory().getNbCoins() + 1);

        // Remove the coin from the game
        gamePanel.getObjects().remove(this);
    }
}