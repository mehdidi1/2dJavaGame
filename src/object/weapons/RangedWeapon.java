package object.weapons;

import main.DrawingUtils;
import main.GameConstants;
import main.GamePanel;
import object.SuperObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class RangedWeapon extends Weapon {
    private BufferedImage bulletImage;

    public RangedWeapon(int x, int y, GamePanel gamePanel) {
        super(x, y, gamePanel);
        loadBulletImage();
    }

    private void loadBulletImage() {
        try {
            bulletImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/objects/weapons/bullet.png")));
            bulletImage = DrawingUtils.scaleImage(bulletImage, GameConstants.TILE_SIZE / 4, GameConstants.TILE_SIZE / 4);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void performAttack() {
        Point mousePosition = gamePanel.getMousePosition();
        if (mousePosition != null) {
            int playerX = gamePanel.getPlayer().calculateScreenX();
            int playerY = gamePanel.getPlayer().calculateScreenY();
            int gunOffsetX = 10; // Adjust this value based on the gun's position relative to the player
            int gunOffsetY = 30; // Adjust this value based on the gun's position relative to the player
            double angle = Math.atan2(mousePosition.y - (playerY + gunOffsetY), mousePosition.x - (playerX + gunOffsetX));
            Bullet bullet = new Bullet(gamePanel.getPlayer().getWorldX() + gunOffsetX, gamePanel.getPlayer().getWorldY() + gunOffsetY, angle, bulletImage, gamePanel);
            gamePanel.getBullets().add(bullet);
        }
    }
}