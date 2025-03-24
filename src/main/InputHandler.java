package main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.event.*;

/**
 * This class handles both keyboard and mouse inputs.
 */
public class InputHandler implements KeyListener, MouseListener {
    private static final Logger logger = LogManager.getLogger(InputHandler.class);

    private boolean upPressed, downPressed, rightPressed, leftPressed;
    private boolean item1Pressed, item2Pressed, item3Pressed;
    private boolean attackPressed;

    @Override
    public void keyTyped(KeyEvent keyEvent) {
        // Not used
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        logger.trace("Key pressed: {}", keyEvent.getKeyChar());

        if (keyCode == KeyEvent.VK_Z) upPressed = true;
        if (keyCode == KeyEvent.VK_S) downPressed = true;
        if (keyCode == KeyEvent.VK_Q) leftPressed = true;
        if (keyCode == KeyEvent.VK_D) rightPressed = true;
        if (keyCode == KeyEvent.VK_1) item1Pressed = true;
        if (keyCode == KeyEvent.VK_2) item2Pressed = true;
        if (keyCode == KeyEvent.VK_3) item3Pressed = true;
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();

        if (keyCode == KeyEvent.VK_Z) upPressed = false;
        if (keyCode == KeyEvent.VK_S) downPressed = false;
        if (keyCode == KeyEvent.VK_Q) leftPressed = false;
        if (keyCode == KeyEvent.VK_D) rightPressed = false;
        if (keyCode == KeyEvent.VK_1) item1Pressed = false;
        if (keyCode == KeyEvent.VK_2) item2Pressed = false;
        if (keyCode == KeyEvent.VK_3) item3Pressed = false;
    }

    // MouseListener methods
    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            System.out.println("Left mouse button pressed - attacking!");
            attackPressed = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            attackPressed = false;
        }
    }

    // Other MouseListener methods (unused but required)
    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}

    // Getters
    public boolean isUpPressed() { return upPressed; }
    public boolean isDownPressed() { return downPressed; }
    public boolean isRightPressed() { return rightPressed; }
    public boolean isLeftPressed() { return leftPressed; }
    public boolean isItem1Pressed() { return item1Pressed; }
    public boolean isItem2Pressed() { return item2Pressed; }
    public boolean isItem3Pressed() { return item3Pressed; }
    public boolean isAttackPressed() { return attackPressed; }
}