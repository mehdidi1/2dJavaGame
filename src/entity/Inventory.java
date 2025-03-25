package entity;

import main.GamePanel;
import object.SuperObject;

import java.awt.image.BufferedImage;


public class Inventory {

    GamePanel gamePanel;
    private int inventorySize;
    private final SuperObject[] inventoryTab;
    private int nbCoins = 15;
    private int selectedSlot = 0;

    public Inventory(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        inventorySize = 3;
        inventoryTab = new SuperObject[inventorySize];
        selectedSlot = 0;
    }

    public void pickUp(int index) {
        if (gamePanel.getObjects().get(index) != null) {
            if (inventoryTab[selectedSlot] != null) {
                dropItem(selectedSlot); // Drop the item in the selected slot
            }
            inventoryTab[selectedSlot] = gamePanel.getObjects().get(index); // Put object in inventory
            gamePanel.getObjects().remove(index); // Remove object from the ground
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

    public void dropItem(int index) {
        SuperObject item = inventoryTab[index];
        inventoryTab[index] = null;
        item.setWorldX(gamePanel.getPlayer().getWorldX());
        item.setWorldY(gamePanel.getPlayer().getWorldY());
        gamePanel.getObjects().add(item);
    }

    public int getNbCoins() {
        return nbCoins;
    }

    public void setNbCoins(int i) {
        nbCoins = i;
    }
}
