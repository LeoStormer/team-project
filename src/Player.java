


import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.Game;
import game.InputReader;
import query.Rect;
import rendering.Camera;

public class Player extends Rect {

	private BufferedImage[] walkAnimations;

	public String direction = "";

	public int spriteCounter = 0;

	public int spriteNum = 1; // Represents current frame

	boolean keyPressed = false;

	public Player(double x, double y, double width, double height) {
		super(x, y, width, height);
		loadWalkAnimations();
		direction = "down"; // Set the initial direction to "down"
		spriteNum = 1; // Set the initial sprite frame for the "down" direction
	}

	private void loadWalkAnimations() {
		try {
			walkAnimations = new BufferedImage[16];
			walkAnimations[0] = ImageIO.read(getClass().getResource("down_1.png"));
			walkAnimations[1] = ImageIO.read(getClass().getResource("down_2.png"));
			walkAnimations[2] = ImageIO.read(getClass().getResource("down_3.png"));
			walkAnimations[3] = ImageIO.read(getClass().getResource("down_4.png"));
			walkAnimations[4] = ImageIO.read(getClass().getResource("left_1.png"));
			walkAnimations[5] = ImageIO.read(getClass().getResource("left_2.png"));
			walkAnimations[6] = ImageIO.read(getClass().getResource("left_3.png"));
			walkAnimations[7] = ImageIO.read(getClass().getResource("left_4.png"));
			walkAnimations[8] = ImageIO.read(getClass().getResource("right_1.png"));
			walkAnimations[9] = ImageIO.read(getClass().getResource("right_2.png"));
			walkAnimations[10] = ImageIO.read(getClass().getResource("right_3.png"));
			walkAnimations[11] = ImageIO.read(getClass().getResource("right_4.png"));
			walkAnimations[12] = ImageIO.read(getClass().getResource("up_1.png"));
			walkAnimations[13] = ImageIO.read(getClass().getResource("up_2.png"));
			walkAnimations[14] = ImageIO.read(getClass().getResource("up_3.png"));
			walkAnimations[15] = ImageIO.read(getClass().getResource("up_4.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void update(double deltaTime) {
	    if (keyPressed) {
	        spriteCounter++;
	        // Change 6 for animation speed
	        if (spriteCounter > 6) {
	            // Toggle between sprite frames 1, 2, 3, and 4
	            spriteNum = (spriteNum % 4) + 1;

	            // Reset the animation counter
	            spriteCounter = 0;
	        }
	    }
	}



	 public void draw(Graphics g, Camera cam) {
	        super.draw(g, cam);

	        int x = (int) (getPosition().getX() - cam.position.getX());
	        int y = (int) (getPosition().getY() - cam.position.getY());

	        BufferedImage image = null;

	        // Using -1 to account for image[] starting at 0
	        switch (direction) {
	            case "down":
	                image = walkAnimations[spriteNum - 1];
	                break;
	            case "left":
	                image = walkAnimations[4 + spriteNum - 1];
	                break;
	            case "right":
	                image = walkAnimations[8 + spriteNum - 1];
	                break;
	            case "up":
	                image = walkAnimations[12 + spriteNum - 1];
	                break;
	        }

	        g.drawImage(image, x, y, (int) getSize().getX(), (int) getSize().getY(), null);
	    }

	public void handleInput(InputReader inputReader) {
		
		keyPressed = false;
		
		if (inputReader.isKeyPressed(Game._A)) {
			goLT(4);
			direction = "left";
			keyPressed = true;
		} 
		else if (inputReader.isKeyPressed(Game._D)) {
			goRT(4);
			direction = "right";
			keyPressed = true;
		} 
		else if (inputReader.isKeyPressed(Game._W)) {
			goUP(4);
			direction = "up";
			keyPressed = true;
		} 
		else if (inputReader.isKeyPressed(Game._S)) {
			goDN(4);
			direction = "down";
			keyPressed = true;
		}
	}

}
