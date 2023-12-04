import java.awt.Dimension;
import java.awt.Graphics;

import game.Game;
import game.InputReader;
import query.Circle;
import query.Physics;
import query.Rect;
import rendering.Camera;

public class Project extends Game {
	private static final long serialVersionUID = 1L;
	public Camera cam;
	public Rect[] r;
	public Circle[] c;
	public double cameraMoveX = 0;
	public double cameraMoveY = 0;
	public double objectMoveX = 0;
	public double objectMoveY = 0;
	public double speed = 100;
	public Physics physics;
	public static int numTilesX = 200;
	public static int numTilesY = 200;
	public static int tileSize = 128;

	public Project() {
		init();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		physics.draw(g, cam);
	}

	
	@Override
	public void initialize() {
		Dimension size = getSize();
		cam = new Camera(size.width * -0.5, size.height * -0.5, size.width, size.height);
		int worldWidth = numTilesX * tileSize;
		int worldHeight = numTilesY * tileSize;
		physics = new Physics(1 / 60.0d, 0, 0, worldWidth, worldHeight, 128);
		int halfWidth = worldWidth / 2;
		int halfHeight = worldHeight / 2;
		
		c = new Circle[5000];
		for (int i = 0; i < c.length; i++) {
			double flipSign = Math.random() < 0.5 ? -1 : 1;
			double randomNumber = Math.random() * halfWidth;
			double x = randomNumber * flipSign;
			flipSign = Math.random() < 0.5 ? -1 : 1;
			randomNumber = Math.random() * halfHeight;
			double y = randomNumber * flipSign;

			c[i] = new Circle(x, y, 50);
		}

		r = new Rect[5000];
		for (int i = 0; i < r.length; i++) {
			double flipSign = Math.random() < 0.5 ? -1 : 1;
			double randomNumber = Math.random() * halfWidth;
			double x = randomNumber * flipSign;
			flipSign = Math.random() < 0.5 ? -1 : 1;
			randomNumber = Math.random() * halfHeight;
			double y = randomNumber * flipSign;
			
			r[i] = new Rect(x, y, 128, 128);
		}

		// c[0].setPosition(0, 0);
		r[0].setPosition(0, 0);

		c[1].setAnchored(true);;
		c[1].setPosition(100,0);
		// r[1].setAnchored(true);;
		// r[1].setPosition(128,0);
		
		physics.addColliders(r);
		physics.addColliders(c);
	}


	@Override
	public void processInputState(InputReader inputReader) {
		objectMoveX = inputReader.isKeyPressed(_A) ? -1 : 0;
		objectMoveX += inputReader.isKeyPressed(_D) ? 1 : 0;
		objectMoveY = inputReader.isKeyPressed(_W) ? -1 : 0;
		objectMoveY += inputReader.isKeyPressed(_S) ? 1 : 0;
		cameraMoveX = inputReader.isKeyPressed(LT) ? -1 : 0;
		cameraMoveX += inputReader.isKeyPressed(RT) ? 1 : 0;
		cameraMoveY = inputReader.isKeyPressed(UP) ? -1 : 0;
		cameraMoveY += inputReader.isKeyPressed(DN) ? 1 : 0;
	}


	@Override
	public void gameLoop(double deltaTime) {
		cam.setPosition(cam.getPosition().add(cameraMoveX * speed * deltaTime, cameraMoveY * speed * deltaTime));
		r[0].setVelocity(objectMoveX * speed, objectMoveY * speed);
		// c[0].setVelocity(objectMoveX * speed, objectMoveY * speed);
		physics.update(deltaTime);
	}
}
