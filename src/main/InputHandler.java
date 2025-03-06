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

    private boolean upPressed, downPressed, rightPressed, leftPressed;


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


}
