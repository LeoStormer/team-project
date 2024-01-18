package com.game;

import java.awt.MouseInfo;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputListener;

import com.query.Vector2;

/**
 * This class handles keyboard and mouse inputs.
 */
public class InputListener implements KeyListener, MouseInputListener {

    private boolean[] keyboard;

    private boolean[] mouseButtons;

    private Vector2 mouseLocation;

    public InputListener() {
        keyboard = new boolean[KeyEvent.KEY_LAST + 1];
        mouseButtons = new boolean[Math.max(0, MouseInfo.getNumberOfButtons())]; // to avoid NegativeArraySizeException
        mouseLocation = new Vector2();
    }

    /**
     * @param keyCode the integer code representing a key on a keyboard
     * @return Whether the key is currently pressed
     */
    public boolean isKeyPressed(int keyCode) {
        return keyboard[keyCode];
    }

    /**
     * @param mouseButton the integer code representing a key on a mouse
     * @return Whether the mouse button is currently pressed
     */
    public boolean isMouseButtonPressed(int mouseButton) {
        return mouseButtons[mouseButton];
    }

    /**
     * @return The location of the mouse in screen space
     */
    public Vector2 getMouseLocation() {
        return mouseLocation;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseButtons[e.getButton()] = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseButtons[e.getButton()] = false;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseLocation = new Vector2(e.getX(), e.getY());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseLocation = new Vector2(e.getX(), e.getY());
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keyboard[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keyboard[e.getKeyCode()] = false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

}
