package entity;

import main.GamePanel;
import object.Coin;

import java.awt.*;

public abstract class Enemy extends Entity {

    public Enemy(int x, int y, GamePanel gamePanel) {
        super(x, y, gamePanel);
    }

    @Override
    public void update() {
        if (isDead()) {
            dropCoins();
        }
        updateDamageIndicators();
    }

    @Override
    public void draw(Graphics2D g2d) {
        drawDamageIndicators(g2d);
    }

    private void dropCoins() {
        int coinCount = (int) (Math.random() * 3) + 1; // Drop 1 to 3 coins
        for (int i = 0; i < coinCount; i++) {
            int offsetX = (int) (Math.random() * 20 - 10); // Random offset for coin position
            int offsetY = (int) (Math.random() * 20 - 10);
            Coin coin = new Coin(getWorldX() + offsetX, getWorldY() + offsetY, getGamePanel());
            getGamePanel().getObjects().add(coin); // Add the coin to the game panel's objects
        }
    }
}