package entity;

import main.GamePanel;

import java.awt.*;

public abstract class Npc extends Entity {

    public Npc(int x, int y, GamePanel gamePanel) {
        super(x, y, gamePanel);
    }

    @Override
    public void update() {
        updateDamageIndicators();
    }

    @Override
    public void draw(Graphics2D g2d) {
        drawDamageIndicators(g2d);
    }
}
