package main;

import java.awt.*;
import java.awt.image.BufferedImage;

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

    public void drawInventory(Graphics2D g2d) {
        int frameSize = 48; // Size of each inventory frame
        int framePadding = 10; // Padding between frames
        int totalWidth = (frameSize * 3) + (framePadding * 2); // Total width of the inventory frames

        // Calculate the starting X position to center the inventory frames
        int startX = (gamePanel.getWidth() / 2) - (totalWidth / 2);
        int startY = gamePanel.getHeight() - frameSize - 20; // Position the frames at the bottom with some padding

        // Draw the 3 inventory frames
        for (int i = 0; i < 3; i++) {
            int x = startX + (i * (frameSize + framePadding));

            // Draw shadow
            g2d.setColor(new Color(0, 0, 0, 100));
            g2d.fillRoundRect(x + 3, startY + 3, frameSize, frameSize, 10, 10);

            // Draw gradient background
            GradientPaint gradient = new GradientPaint(x, startY, Color.LIGHT_GRAY, x, startY + frameSize, Color.DARK_GRAY);
            g2d.setPaint(gradient);
            g2d.fillRoundRect(x, startY, frameSize, frameSize, 10, 10);

            // Draw border
            g2d.setColor(Color.BLACK);
            g2d.drawRoundRect(x, startY, frameSize, frameSize, 10, 10);

            // Draw the item icon if available
            BufferedImage itemIcon = gamePanel.getInventory().getItemIcon(i);
            if (itemIcon != null) {
                int iconX = x + (frameSize - itemIcon.getWidth()) / 2;
                int iconY = startY + (frameSize - itemIcon.getHeight()) / 2;
                g2d.drawImage(itemIcon, iconX, iconY, null);
            }
        }
    }
}
