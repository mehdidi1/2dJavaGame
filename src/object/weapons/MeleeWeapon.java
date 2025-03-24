package object.weapons;

import entity.Entity;
import entity.Player;
import main.DrawingUtils;
import main.GameConstants;
import main.GamePanel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.Objects;

public abstract class MeleeWeapon extends Weapon {

    private static final Logger logger = LogManager.getLogger(MeleeWeapon.class);

    private boolean debug = true; // Enable or disable debug mode
    private final int radius; // radius of the hitbox
    private final int sweepAngle; // angle of the hitbox
    public BufferedImage[] slashFrames = new BufferedImage[6];

    public MeleeWeapon(int x, int y, GamePanel gamePanel, int radius, int sweepAngle) {
        super(x, y, gamePanel);
        this.radius = radius;
        this.sweepAngle = sweepAngle;
        getSlashFrames();
    }

    @Override
    public void performAttack() {
        slashAnimation();
        slashCollider();
    }

    private void slashAnimation() {
        Player player = gamePanel.getPlayer();
        Point mousePosition = gamePanel.getMousePosition();

        if (mousePosition == null) {
            return; // Mouse position is not available
        }

        // Calculate the angle between the player and the mouse position
        double angle = Math.atan2(mousePosition.y - player.calculateScreenY(), mousePosition.x - player.calculateScreenX());

        new Thread(() -> {
            for (BufferedImage frame : slashFrames) {
                if (frame == null) {
                    continue; // Skip null frames
                }
                Graphics2D g2d = (Graphics2D) gamePanel.getGraphics();
                AffineTransform transform = new AffineTransform();
                double offsetX = Math.cos(angle) * (radius / 2); // Adjust the offset distance
                double offsetY = Math.sin(angle) * (radius / 2); // Adjust the offset distance
                transform.translate(player.calculateScreenX() + offsetX, player.calculateScreenY() + offsetY);
                transform.rotate(angle, frame.getWidth() / 2.0, frame.getHeight() / 2.0);
                g2d.drawImage(frame, transform, null);
                try {
                    Thread.sleep(100); // Adjust the delay between frames as needed
                } catch (InterruptedException e) {
                    logger.error("Slash animation interrupted", e);
                    Thread.currentThread().interrupt();
                }
            }
        }).start();
    }

    private void slashCollider() {
        Player player = gamePanel.getPlayer();
        Point mousePosition = gamePanel.getMousePosition();

        if (mousePosition != null) {
            // Get the player's screen position
            double playerX = player.calculateScreenX();
            double playerY = player.calculateScreenY();

            // Get the collider's RELATIVE offset (from player's position)
            Rectangle collider = player.getCollider();
            double colliderOffsetX = collider.getX(); // e.g., 10 pixels right from player's origin
            double colliderOffsetY = collider.getY(); // e.g., 5 pixels down from player's origin

            // Calculate the collider's CENTER in world coordinates
            double centerX = playerX + colliderOffsetX + (collider.getWidth() / 2);
            double centerY = playerY + colliderOffsetY + (collider.getHeight() / 2);

            // Calculate angle to mouse
            Path2D.Double sweepArea = getADouble(mousePosition, centerY, centerX);

            // Check collisions with enemies
            for (Entity entity : gamePanel.getEntities()) {
                if (entity != player) {
                    Rectangle entityCollider = entity.getCollider();

                    // Calculate the entity's collider position in world space
                    double entityColliderX = DrawingUtils.calculateScreenX(entity.getWorldX(), gamePanel) + entityCollider.x;
                    double entityColliderY = DrawingUtils.calculateScreenY(entity.getWorldY(), gamePanel) + entityCollider.y;

                    Rectangle absoluteEntityCollider = new Rectangle(
                            (int) entityColliderX,
                            (int) entityColliderY,
                            entityCollider.width,
                            entityCollider.height
                    );

                    if (sweepArea.intersects(absoluteEntityCollider)) {
                        System.out.println("Hit enemy!");
                        // entity.takeDamage(damage);
                    }
                }
            }

            // Debug rendering
            if (debug) {
                Graphics2D g2d = (Graphics2D) gamePanel.getGraphics();
                g2d.setColor(Color.GREEN);
                g2d.draw(sweepArea);
            }
        }
    }

    private Path2D.Double getADouble(Point mousePosition, double centerY, double centerX) {
        double angle = Math.atan2(mousePosition.y - centerY, mousePosition.x - centerX);

        // Create the arc-shaped attack area
        Path2D.Double sweepArea = new Path2D.Double();
        sweepArea.moveTo(centerX, centerY);

        // Line to start of arc
        sweepArea.lineTo(
                centerX + radius * Math.cos(angle - (double) sweepAngle / 2),
                centerY + radius * Math.sin(angle - (double) sweepAngle / 2)
        );

        // Approximate the arc with line segments
        int segments = 10;
        for (int i = 1; i <= segments; i++) {
            double segmentAngle = angle - (double) sweepAngle / 2 + ((double) (sweepAngle * i) / segments);
            sweepArea.lineTo(
                    centerX + radius * Math.cos(segmentAngle),
                    centerY + radius * Math.sin(segmentAngle)
            );
        }

        sweepArea.closePath();
        return sweepArea;
    }

    private void getSlashFrames() {
        try {
            for (int i = 0; i < 6; i++) {
                slashFrames[i] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/objects/weapons/slashAnimation/Slash_color5_frame" + (i + 4) + ".png")));
                slashFrames[i] = DrawingUtils.scaleImage(slashFrames[i], GameConstants.TILE_SIZE, GameConstants.TILE_SIZE);
            }
        } catch (Exception e) {
            logger.error(e);
        }
    }
}