package ui;

import java.awt.*;

public class DamageIndicator {
    private int x, y;
    private int damage;
    private long startTime;
    private static final int DURATION = 1000; // Duration in milliseconds
    private static final int RISE_SPEED = 1; // Speed at which the indicator rises

    public DamageIndicator(int x, int y, int damage) {
        this.x = x;
        this.y = y;
        this.damage = damage;
        this.startTime = System.currentTimeMillis();
    }

    public boolean isExpired() {
        return System.currentTimeMillis() - startTime > DURATION;
    }

    public void update() {
        y -= RISE_SPEED;

    }

    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.RED);
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        g2d.drawString("-" + damage, x, y);
    }
}