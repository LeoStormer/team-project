import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import game.Game;
import game.InputReader;
import query.Circle;
import query.Physics;
import query.Rect;
import rendering.Animation;
import rendering.Animator;
import rendering.Camera;
import rendering.TileAtlas;
import rendering.TileMap;

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
	public ArrayList<Animation> tileViewer;
	public int currentAnimation = 0;
	public Animator animator = new Animator();
	
	public TileAtlas tileAtlas;
    public TileMap tilemap;
    public int scale = 3; // Scale for tilemap

	public Project() {
		init();
		setBackground(Color.BLACK);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// animator.draw(g, cam);
		// animator.draw(g, 0,0,8);
		physics.draw(g, cam);
		tilemap.draw(g, cam);

	}

	
	@Override
	public void initialize() {
		Dimension size = getSize();
		tileViewer = new ArrayList<>(32);
		cam = new Camera(size.width * -0.5, size.height * -0.5, size.width, size.height);
		int worldWidth = numTilesX * tileSize;
		int worldHeight = numTilesY * tileSize;
		physics = new Physics(1 / 60.0d, 0, 0, worldWidth, worldHeight, 128);
		
		// Stress test physics
		// int halfWidth = worldWidth / 2;
		// int halfHeight = worldHeight / 2;
		
		// c = new Circle[5000];
		// for (int i = 0; i < c.length; i++) {
		// 	double flipSign = Math.random() < 0.5 ? -1 : 1;
		// 	double randomNumber = Math.random() * halfWidth;
		// 	double x = randomNumber * flipSign;
		// 	flipSign = Math.random() < 0.5 ? -1 : 1;
		// 	randomNumber = Math.random() * halfHeight;
		// 	double y = randomNumber * flipSign;

		// 	c[i] = new Circle(x, y, 50);
		// }

		//r = new Rect[5000];
		// for (int i = 0; i < r.length; i++) {
		// 	double flipSign = Math.random() < 0.5 ? -1 : 1;
		// 	double randomNumber = Math.random() * halfWidth;
		// 	double x = randomNumber * flipSign;
		// 	flipSign = Math.random() < 0.5 ? -1 : 1;
		// 	randomNumber = Math.random() * halfHeight;
		// 	double y = randomNumber * flipSign;
			
		// 	r[i] = new Rect(x, y, 128, 128);
		// }

		// c[0].setPosition(0, 0);
		//r[0].setPosition(0, 0);
		
		// c[1].setAnchored(true);;
		// c[1].setPosition(100,0);
		// r[1].setAnchored(true);;
		// r[1].setPosition(128,0);
		
		// physics.addColliders(r);
		// physics.addColliders(c);

		/* Testing: View Assets
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
		*/
		
		 try {
	            BufferedImage grounds = ImageIO.read(new File("res/sprites/landscape/FG_Grounds.png"));
	            
	            //First sample area
	            Rect grass = new Rect(96, 192, 90, 48);

	            // Second sample area
	            Rect dirt = new Rect(96, 576, 90, 48);

	            // Create TileAtlas with both sample areas
	            int[][] tileDimensions = {
	                    {16, 16, 0}, // Specify the tile dimensions and spacing for sampleArea1
	                    {16, 16, 0}  // Specify the tile dimensions and spacing for sampleArea2
	            };
	            
	            tileAtlas = new TileAtlas(grounds, new Rect[]{grass, dirt}, tileDimensions);
		 }	catch(IOException e) {e.printStackTrace();}
		
	            System.out.println(tileAtlas.getNumTiles());

	            // You need to adjust code based on how many tiles. The num of tiles is printed to the console.
	            tilemap = new TileMap("map.map", tileAtlas, 16 * scale, "abcdefghijklmnopqrstuvwxyzABCD", cam);
	}
	


	@Override
	public void processInputState(InputReader inputReader) {
		objectMoveX = inputReader.isKeyPressed(_A) ? -1 : 0;
		objectMoveX += inputReader.isKeyPressed(_D) ? 1 : 0;
		objectMoveY = inputReader.isKeyPressed(_W) ? -1 : 0;
		objectMoveY += inputReader.isKeyPressed(_S) ? 1 : 0;
		cameraMoveX = inputReader.isKeyPressed(LT) ? -3 : 0;
		cameraMoveX += inputReader.isKeyPressed(RT) ? 3 : 0;
		cameraMoveY = inputReader.isKeyPressed(UP) ? -3 : 0;
		cameraMoveY += inputReader.isKeyPressed(DN) ? 3 : 0;
	}


	@Override
	public void gameLoop(double deltaTime) {
		cam.setPosition(cam.getPosition().add(cameraMoveX * speed * deltaTime, cameraMoveY * speed * deltaTime));
		System.out.println("cameraMoveX: " + cameraMoveX + ", cameraMoveY: " + cameraMoveY);

		//r[0].setVelocity(objectMoveX * speed, objectMoveY * speed);
		// c[0].setVelocity(objectMoveX * speed, objectMoveY * speed);
		/*
		if (!animator.playing) {
			currentAnimation = (currentAnimation + 1) % tileViewer.size();
			animator.play(tileViewer.get(currentAnimation));
		}

		animator.update(deltaTime);
		*/
		
		//physics.update(deltaTime);
	}
	
	
}
