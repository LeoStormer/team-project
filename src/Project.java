import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import game.Game;
import game.InputReader;
import query.Circle;
import query.Physics;
import query.Rect;
import rendering.Animation;
import rendering.Animator;
import rendering.Camera;
import rendering.Renderer;
import rendering.TileAtlas;
import tilemap.TileParser;

public class Project extends Game {
	private static final long serialVersionUID = 1L;

	// Physics Test
	public Rect[] r;
	public Circle[] c;
	public double objectMoveX = 0;
	public double objectMoveY = 0;
	public int numTilesX = 200;
	public int numTilesY = 200;
	public static int tileSize = 32;
	public int worldWidth, worldHeight;

	// Asset Test
	public int currentAnimation = 0;
	public ArrayList<Animation> tileViewer;
	public Animator animator = new Animator();

	public Physics physics;
	public Renderer renderer;
	public Camera cam;
	public double cameraMoveX = 0;
	public double cameraMoveY = 0;
	public double speed = 300;

	public Project() {
		init();
		setBackground(Color.BLACK);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Draw Asset Test
		// animator.draw(g, 0,0,8);
		
		//Draw Physics Test
		// physics.draw(g, cam);

		renderer.draw(g, cam);
	}

	public void initializePhysicsTest() {
		worldWidth = numTilesX * tileSize;
		worldHeight = numTilesY * tileSize;
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
	
	public void updatePhysicsTest() {
		r[0].setVelocity(objectMoveX * speed, objectMoveY * speed);
		// c[0].setVelocity(objectMoveX * speed, objectMoveY * speed);
	}

	public void initializeAssetTest() {
		for (TileAtlas atlas : AssetPool.getObjectAtlases()){
			tileViewer.add(new Animation(atlas.getTiles(), 5));
		}

		for (TileAtlas atlas : AssetPool.getEntityAtlases()) {
			tileViewer.add(new Animation(atlas.getTiles(), 14));
		}

		for (TileAtlas atlas : AssetPool.getLandscapeAtlases()) {
			tileViewer.add(new Animation(atlas.getTiles(), 20));
		}

		animator.play(tileViewer.get(currentAnimation));
	}

	public void updateAssetTest(double deltaTime) {
		if (!animator.playing) {
			currentAnimation = (currentAnimation + 1) % tileViewer.size();
			animator.play(tileViewer.get(currentAnimation));
		}

		animator.update(deltaTime);
	}

	@Override
	public void initialize() {
		Dimension size = getSize();
		tileViewer = new ArrayList<>(32);
		cam = new Camera(0, 0, size.width, size.height);
		addComponentListener(cam);
		renderer = new Renderer(cam);
		TileParser tileParser = new TileParser(AssetPool.getLandscapeAtlases());
		TileMapLoader loader = new TileMapLoader("./res/txtmaps/map.map", tileParser, tileSize);
		physics = loader.parseCollisionLayer();
		loader.parseTileLayers(renderer);

		// Stress test physics
		// initializePhysicsTest();

		// Testing: View Assets
		// initializeAssetTest();
		
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

		// updatePhysicsTest();
		// updateAssetTest(deltaTime);

		// Update Physics
		physics.update(deltaTime);

		// Update Animations
		renderer.update(deltaTime);
	}
	
	
}
