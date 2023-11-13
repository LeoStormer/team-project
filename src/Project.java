import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import game.Game;
import game.InputReader;
import query.Circle;
import query.Rect;
import rendering.Camera;

public class Project extends Game {
	private static final long serialVersionUID = 1L;
	public Camera cam;
	public Rect[] r;
	public Circle c;
	public double cameraMoveX = 0;
	public double cameraMoveY = 0;
	public double speed = 100;
	
	public Project() {
		// TODO Auto-generated constructor stub
		init();
		setPreferredSize(new Dimension(1280, 800));
		setBackground(Color.WHITE);
		setDoubleBuffered(true);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (int i = 0; i < r.length; i++) {
			r[i].draw(g, cam);
		}
	}

	
	@Override
	public void initialize() {
		cam = new Camera();
		c = new Circle(50, 100, 30);
		r = new Rect[5];
		for (int i = 0; i < r.length; i++) {
			r[i] = new Rect(i * 100, i % 2 * 50, 50, 50);
		}
	}


	@Override
	public void processInputState(InputReader inputReader) {
		cameraMoveX = inputReader.isKeyPressed(_A) ? -1 : 0;
		cameraMoveX += inputReader.isKeyPressed(_D) ? 1 : 0;
		cameraMoveY = inputReader.isKeyPressed(_W) ? -1 : 0;
		cameraMoveY += inputReader.isKeyPressed(_S) ? 1 : 0;
	}


	@Override
	public void gameLoop(double deltaTime) {
//		System.out.println(deltaTime);
		cam.setPosition(cam.getPosition().add(cameraMoveX * speed * deltaTime, cameraMoveY * speed * deltaTime));
//		r[1].setPosition(r[1].getPosition().add(cameraMoveX, cameraMoveY));
	}
}
