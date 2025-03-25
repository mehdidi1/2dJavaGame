package entity;

import main.DrawingUtils;
import main.GameConstants;
import main.GamePanel;
import object.SuperObject;
import object.weapons.Gun;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class Scammer extends Npc {

    private static final Logger logger = LogManager.getLogger(Scammer.class);
    private SuperObject superObject;

    public Scammer(int x, int y, GamePanel gamePanel, SuperObject superObject) {
        super(x, y, gamePanel);
        this.superObject = superObject;
        loadScammerImage();
        super.collider = new Rectangle(0, 0, GameConstants.TILE_SIZE, GameConstants.TILE_SIZE);
        super.health = 100;
    }

    private void loadScammerImage() {
        try {
            for (int i = 0; i < 8; i++) {
                idleFrames[i] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/npc/frame_" + i + "_delay-0.05s.png")));
                idleFrames[i] = DrawingUtils.scaleImage(idleFrames[i], GameConstants.TILE_SIZE, GameConstants.TILE_SIZE);
                currentAnimationFrames = idleFrames;
            }
        } catch (Exception e) {
            logger.error(e);
        }
    }

    @Override
    public void update() {
        super.update();
        updateFrame();
    }

    @Override
    public void draw(Graphics2D g2d) {
        super.draw(g2d);
        int screenX = DrawingUtils.calculateScreenX(getWorldX(), getGamePanel());
        int screenY = DrawingUtils.calculateScreenY(getWorldY(), getGamePanel());

        // Draw the scammer
        g2d.drawImage(currentAnimationFrames[getCurrentAnimationFrameIndex()], screenX, screenY + 20, null);

        // Draw the panel above the scammer
        drawItemPanel(g2d, screenX, screenY);
        g2d.setColor(Color.RED);
        g2d.draw(super.collider);
    }

    private void drawItemPanel(Graphics2D g2d, int x, int y) {
        int panelWidth = 120;
        int panelHeight = 70;
        int padding = 10;

        // Draw panel background
        g2d.setColor(new Color(0, 0, 0, 150));
        g2d.fillRoundRect(x - panelWidth / 2, y - panelHeight, panelWidth, panelHeight, 10, 10);

        // Draw item image
        BufferedImage itemImage = superObject.getImage();
        g2d.drawImage(itemImage, x - panelWidth / 2 + padding, y - panelHeight + padding, null);

        // Draw item name
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 12));
        g2d.drawString(superObject.getName(), x - panelWidth / 2 + padding + itemImage.getWidth() + 5, y - panelHeight + padding + 12);

        // Draw item price
        g2d.drawString("Price: " + superObject.getPrice(), x - panelWidth / 2 + padding + itemImage.getWidth() + 5, y - panelHeight + padding + 28);
    }

    public void interact(Player player) {
        if (player.getInventory().getNbCoins() >= superObject.getPrice()) {
            player.getInventory().setNbCoins(player.getInventory().getNbCoins() - superObject.getPrice());
            dropItem();
        }
    }

    private void dropItem() {
        superObject.setWorldX(getWorldX());
        superObject.setWorldY(getWorldY());
        getGamePanel().getObjects().add(superObject);
        superObject = new Gun(0,0,getGamePanel()); // Remove the item from the scammer
    }
}