package entity;

import main.GamePanel;
import object.SuperObject;

public class Inventory {

    GamePanel gamePanel;

    public Inventory(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void pickUp(int index) {
        if (gamePanel.getObjects().get(index) != null) {
            gamePanel.getObjects().remove(index);
        }
    }
}
