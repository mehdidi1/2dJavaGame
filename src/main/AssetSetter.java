package main;

import entity.Scammer;
import entity.Soldier;
import object.Button;
import object.Key;
import object.SuperObject;
import object.weapons.Ak47;
import object.weapons.Gun;
import object.weapons.Knife;
import object.weapons.Sword;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class AssetSetter {
    protected GamePanel gamePanel;
    private Button button ;

    public AssetSetter(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setObjects() {
        Knife knifeObj = new Knife(300, 100, gamePanel);
        button = new Button(45*GameConstants.TILE_SIZE,30*GameConstants.TILE_SIZE,gamePanel);
        gamePanel.objects.add(knifeObj);
        gamePanel.objects.add(button);

    }

    public void setEntities() {

        gamePanel.entities.add(gamePanel.getPlayer());
        gamePanel.entities.add(new Scammer(8 * GameConstants.TILE_SIZE,6 * GameConstants.TILE_SIZE,gamePanel,new Key(300,300,gamePanel)));
        gamePanel.entities.add(new Soldier(12 * GameConstants.TILE_SIZE,4 * GameConstants.TILE_SIZE,gamePanel));
        gamePanel.entities.add(new Soldier(17 * GameConstants.TILE_SIZE,4 * GameConstants.TILE_SIZE,gamePanel));
        gamePanel.entities.add(new Scammer(25 * GameConstants.TILE_SIZE,6 * GameConstants.TILE_SIZE,gamePanel,new Gun(300,300,gamePanel)));
        gamePanel.entities.add(new Soldier(30 * GameConstants.TILE_SIZE,4 * GameConstants.TILE_SIZE,gamePanel));
        gamePanel.entities.add(new Soldier(35 * GameConstants.TILE_SIZE,4 * GameConstants.TILE_SIZE,gamePanel));
        gamePanel.entities.add(new Soldier(40 * GameConstants.TILE_SIZE,4 * GameConstants.TILE_SIZE,gamePanel));
        gamePanel.entities.add(new Soldier(45 * GameConstants.TILE_SIZE,4 * GameConstants.TILE_SIZE,gamePanel));
        gamePanel.entities.add(new Scammer(50 * GameConstants.TILE_SIZE,6 * GameConstants.TILE_SIZE,gamePanel,new Ak47(300,300,gamePanel)));
        gamePanel.entities.add(new Scammer(25 * GameConstants.TILE_SIZE,35 * GameConstants.TILE_SIZE,gamePanel,new Sword(300,300,gamePanel)));
        gamePanel.entities.add(new Soldier(30 * GameConstants.TILE_SIZE, 30* GameConstants.TILE_SIZE,gamePanel));
        gamePanel.entities.add(new Soldier(35 * GameConstants.TILE_SIZE,30 * GameConstants.TILE_SIZE,gamePanel));
        gamePanel.entities.add(new Soldier(40 * GameConstants.TILE_SIZE,30 * GameConstants.TILE_SIZE,gamePanel));
    }

    public Button getButton() {
        return button;
    }
}
