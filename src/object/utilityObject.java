package object;

import main.GamePanel;

public class utilityObject extends SuperObject{
    public utilityObject(int x, int y, GamePanel gamePanel) {
        super(x, y, gamePanel);
    }

    @Override
    public void performAttack() {
        gamePanel.getPlayer().fists.performAttack();
    }
}
