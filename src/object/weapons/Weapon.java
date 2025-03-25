package object.weapons;

import main.GamePanel;
import object.SuperObject;

public abstract class Weapon extends SuperObject {

    protected int damage;
    protected double attackCooldown; // Cooldown in seconds
    private long lastAttackTime = 0; // Time of the last attack in nanoseconds

    public Weapon(int x, int y, GamePanel gamePanel) {
        super(x, y, gamePanel);
    }

    public boolean canAttack() {
        long currentTime = System.nanoTime();
        return (currentTime - lastAttackTime) >= (attackCooldown * 1_000_000_000); // Convert seconds to nanoseconds
    }

    public void setLastAttackTime() {
        lastAttackTime = System.nanoTime();
    }

    public abstract void performAttack();
}