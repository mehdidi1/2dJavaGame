package main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * This class takes of inputs pressed by the user.
 * Any new key that needs to be detected should be added here.
 * The input handler needs to be instantiated and attached to the window.
 * BE CAREFUL add gamePanel.requestFocusInWindow(); or else key detection won't work
 */

public class InputHandler implements KeyListener {

    private static final Logger logger = LogManager.getLogger(InputHandler.class);

    private boolean upPressed, downPressed, rightPressed, leftPressed,item1Pressed, item2Pressed, item3Pressed;


    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }


    @Override
    public void keyPressed(KeyEvent keyEvent) {
        logger.trace("Key pressed: {}", keyEvent.getKeyChar());
        int keyCode = keyEvent.getKeyCode();
        if (keyCode == KeyEvent.VK_Z) {
            upPressed = true;
        }
        if (keyCode == KeyEvent.VK_S) {
            downPressed = true;
        }
        if (keyCode == KeyEvent.VK_Q) {
            leftPressed = true;
        }
        if (keyCode == KeyEvent.VK_D) {
            rightPressed = true;
        }
        if (keyCode == KeyEvent.VK_1) {
            item1Pressed = true;
        }
        if (keyCode == KeyEvent.VK_2) {
            item2Pressed = true;
        }
        if (keyCode == KeyEvent.VK_3) {
            item3Pressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

        int keyCode = keyEvent.getKeyCode();
        if (keyCode == KeyEvent.VK_Z) {
            upPressed = false;
        }
        if (keyCode == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (keyCode == KeyEvent.VK_Q) {
            leftPressed = false;
        }
        if (keyCode == KeyEvent.VK_D) {
            rightPressed = false;
        }

        if (keyCode == KeyEvent.VK_1) {
            item1Pressed = false;
        }
        if (keyCode == KeyEvent.VK_2) {
            item2Pressed = false;
        }
        if (keyCode == KeyEvent.VK_3) {
            item3Pressed = false;
        }

    }

    public boolean isUpPressed() {
        return upPressed;
    }


    public boolean isDownPressed() {
        return downPressed;
    }


    public boolean isRightPressed() {
        return rightPressed;
    }


    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isItem1Pressed() {
        return item1Pressed;
    }

    public boolean isItem2Pressed() {
        return item2Pressed;
    }

    public boolean isItem3Pressed() {
        return item3Pressed;
    }
}
