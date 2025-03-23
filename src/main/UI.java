package main;

import java.awt.*;

public class UI {
    GamePanel gamePanel;
    private final Font font;

    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        font =  new Font("Silkscreen", Font.PLAIN, 20);
    }

    public void draw(Graphics2D g2d) {
        g2d.setFont(font);
        g2d.setColor(Color.black);
        g2d.drawString("Speed : " + gamePanel.getPlayer().getSpeed(),50, 50);
    }
}
