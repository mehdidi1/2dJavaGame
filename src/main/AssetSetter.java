package main;

import object.Key;
import object.SuperObject;
import object.weapons.Gun;
import object.weapons.Knife;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class AssetSetter {
    protected GamePanel gamePanel;

    public AssetSetter(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setObjects() {
        Key keyObj = new Key(100, 100, gamePanel);
        gamePanel.objects.add(keyObj);
        Gun gunObj = new Gun(200, 100, gamePanel);
        gamePanel.objects.add(gunObj);
        Knife knifeObj = new Knife(300, 100, gamePanel);
        gamePanel.objects.add(knifeObj);
    }

}
