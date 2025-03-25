package entity;

import main.DrawingUtils;
import main.GameConstants;
import main.GamePanel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class Soldier extends Enemy {

    private static final Logger logger = LogManager.getLogger(Soldier.class);

    private static final int DETECTION_RADIUS = 200; // Radius within which the soldier detects the player
    private boolean isColliding = false; // Flag to indicate if the soldier is currently colliding

    public Soldier(int x, int y, GamePanel gamePanel) {
        super(x, y, gamePanel);
        getSoldierImage();
        super.collider = new Rectangle(8, 12, 32, 32);
        super.speed = 3;
    }

    private void getSoldierImage() {
        try {
            for (int i = 0; i < 8; i++) {
                idleFrames[i] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/enemies/soldierIdle/frame_" + i + "_delay-0.07s.png")));
                idleFrames[i] = DrawingUtils.scaleImage(idleFrames[i], GameConstants.TILE_SIZE, GameConstants.TILE_SIZE);
                walkingFrames[i] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/enemies/soldierWalk/frame_" + i + "_delay-0.07s.png")));
                walkingFrames[i] = DrawingUtils.scaleImage(walkingFrames[i], GameConstants.TILE_SIZE, GameConstants.TILE_SIZE);
            }
        } catch (Exception e) {
            logger.error(e);
        }
    }

    @Override
    public void update() {
        if (isColliding) {
            return; // Skip update if currently colliding
        }

        Player player = getGamePanel().getPlayer();
        double distanceToPlayer = Math.hypot(player.getWorldX() - getWorldX(), player.getWorldY() - getWorldY());

        if (distanceToPlayer < DETECTION_RADIUS) {
            moveToPlayer(player);
        } else {
            setWalking(false);
        }

        // Check for collisions with other entities
        Entity collidedEntity = getGamePanel().getCollisionChecker().checkEntityCollision(this);
        if (collidedEntity != null) {
            // Handle collision with another entity
            handleCollision();
        }

        updateAnimation();
        updateFrame();
    }

    private void moveToPlayer(Player player) {
        int playerX = player.getWorldX();
        int playerY = player.getWorldY();

        if (playerX < getWorldX()) {
            setDirection(Direction.LEFT);
            setFacingLeft(true);
            setWorldX(getWorldX() - getSpeed());
        } else if (playerX > getWorldX()) {
            setDirection(Direction.RIGHT);
            setFacingLeft(false);
            setWorldX(getWorldX() + getSpeed());
        }

        if (playerY < getWorldY()) {
            setDirection(Direction.UP);
            setWorldY(getWorldY() - getSpeed());
        } else if (playerY > getWorldY()) {
            setDirection(Direction.DOWN);
            setWorldY(getWorldY() + getSpeed());
        }

        setWalking(true);
    }

    private void handleCollision() {
        // Stop the soldier's movement
        setWalking(false);
        setSpeed(0);
        isColliding = true;

        // Reset speed after a short delay
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                setSpeed(3);
                isColliding = false;
            }
        }, 1000); // 1 second delay
    }

    @Override
    public void draw(Graphics2D g2d) {
        BufferedImage img = currentAnimationFrames[getCurrentAnimationFrameIndex()];
        boolean facingLeft = isFacingLeft();
        if (facingLeft) {
            img = flipImage(img);
        }

        int screenX = DrawingUtils.calculateScreenX(getWorldX(), getGamePanel());
        int screenY = DrawingUtils.calculateScreenY(getWorldY(), getGamePanel());

        // Draw the soldier
        g2d.drawImage(img, screenX, screenY, null);
    }
}