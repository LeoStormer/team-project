package com.game;

import java.awt.MouseInfo;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputListener;

import com.query.Vector2;

public class InputReader implements KeyListener, MouseInputListener {

	private boolean[] keyboard;
	private boolean[] mouseButtons;
	private Vector2 mouseLocation;

	public InputReader() {
		keyboard = new boolean[KeyEvent.KEY_LAST + 1];
		mouseButtons = new boolean[MouseInfo.getNumberOfButtons()];
		mouseLocation = new Vector2();
	}

	public boolean isKeyPressed(int keyCode) {
		return keyboard[keyCode];
	}

	public boolean isMouseButtonPressed(int mouseButton) {
		return mouseButtons[mouseButton];
	}

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
