package object.weapons;

import entity.Entity;
import entity.Player;
import main.DrawingUtils;
import main.GamePanel;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

public abstract class MeleeWeapon extends Weapon {

    private boolean debug = true; // Enable or disable debug mode
    private int radius; // Width of the hitbox
    private int sweepAngle; // Height of the hitbox

    public MeleeWeapon(int x, int y, GamePanel gamePanel, int radius, int sweepAngle) {
        super(x, y, gamePanel);
        this.radius = radius;
        this.sweepAngle = sweepAngle;
    }

    @Override
    public void performAttack() {
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
            double angle = Math.atan2(mousePosition.y - centerY, mousePosition.x - centerX);

            // Create the arc-shaped attack area
            Path2D.Double sweepArea = new Path2D.Double();
            sweepArea.moveTo(centerX, centerY);

            // Line to start of arc
            sweepArea.lineTo(
                    centerX + radius * Math.cos(angle - sweepAngle / 2),
                    centerY + radius * Math.sin(angle - sweepAngle / 2)
            );

            // Approximate the arc with line segments
            int segments = 10;
            for (int i = 1; i <= segments; i++) {
                double segmentAngle = angle - sweepAngle / 2 + (sweepAngle * i / segments);
                sweepArea.lineTo(
                        centerX + radius * Math.cos(segmentAngle),
                        centerY + radius * Math.sin(segmentAngle)
                );
            }

            sweepArea.closePath();

            // Check collisions with enemies
            for (Entity entity : gamePanel.getEntities()) {
                if (entity != player) {
                    Rectangle entityCollider = entity.getCollider();

                    // Calculate the entity's collider position in world space
                    double entityColliderX = DrawingUtils.calculateScreenX(entity.getWorldX(),gamePanel) + entityCollider.x;
                    double entityColliderY = DrawingUtils.calculateScreenY(entity.getWorldY(),gamePanel) + entityCollider.y;

                    Rectangle absoluteEntityCollider = new Rectangle(
                            (int) entityColliderX,
                            (int) entityColliderY,
                            entityCollider.width,
                            entityCollider.height
                    );

                    System.out.println(absoluteEntityCollider.toString());
                    System.out.println(sweepArea.getBounds2D().toString());

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
}