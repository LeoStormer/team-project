package com.game;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

public abstract class Game extends JPanel implements Runnable, InputReader {

	private static final long serialVersionUID = 1L;

	private Thread t;

	private InputListener inputListener;

	public void init() {
		setPreferredSize(new Dimension(1280, 800));
		setSize(getPreferredSize());
		setBackground(Color.WHITE);
		setDoubleBuffered(true);

		inputListener = new InputListener();
		setFocusable(true);

		addKeyListener(inputListener);
		addMouseListener(inputListener);
		addMouseMotionListener(inputListener);

		requestFocus();
		initialize();

		t = new Thread(this);
		t.start();
	}

	@Override
	public void run() {
		long previousTime = System.nanoTime();
		long currentTime;

		while (true) {
			currentTime = System.nanoTime();
			double deltaTime = (double) (currentTime - previousTime) * 1e-9;
			previousTime = currentTime;

			processInputState(inputListener);
			gameLoop(deltaTime);
			repaint();

			try {
				Thread.sleep(15);
			} catch (Exception e) {
			}
			;
		}
	}

	public void initialize() {
	}

	public void processInputState(InputListener inputListener) {
	}

	public abstract void gameLoop(double deltaTime);

}
