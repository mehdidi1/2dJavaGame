package object.weapons;

import main.GamePanel;
import object.SuperObject;

public abstract class Weapon extends SuperObject {

    protected int damage;
    protected double attackCooldown;

    public Weapon(int x, int y, GamePanel gamePanel) {
        super(x, y, gamePanel);
    }

    public abstract void performAttack();
}
