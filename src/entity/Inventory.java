package entity;

import main.GamePanel;
import object.SuperObject;

import java.awt.image.BufferedImage;


public class Inventory {

    GamePanel gamePanel;
    private int inventorySize;
    private SuperObject[] inventoryTab;
    private int nbCoins = 0;
    private int selectedSlot = 0;

    public Inventory(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        inventorySize = 3;
        inventoryTab = new SuperObject[inventorySize];
        selectedSlot = 0;
    }

    public void pickUp(int index) {
        if (gamePanel.getObjects().get(index) != null) {
            inventoryTab[selectedSlot] = gamePanel.getObjects().get(index); //Put object in inventory
            gamePanel.getObjects().remove(index); //remove object from the ground
        }
    }

    public BufferedImage getItemIcon(int index) {
        BufferedImage itemIcon = null;
        if (inventoryTab[index] != null) {
            itemIcon = inventoryTab[index].getImage();
        }
        return itemIcon;
    }

    public SuperObject[] getInventoryTab() {
        return inventoryTab;
    }

    public int getInventorySize() {
        return inventorySize;
    }


    public void setSelectedSlot(int selectedSlot) {
        this.selectedSlot = selectedSlot;
    }

    public SuperObject getSelectedObject() {
        return inventoryTab[selectedSlot];
    }

    public int getSelectedSlot() {
        return selectedSlot;
    }
}
