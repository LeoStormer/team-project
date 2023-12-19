package com;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
//NOTE: must change variables to this project's variables
public class UI {
	GamePanel gp;
	Font genericFont;
	public String displayString = "";
	int strLength = 0;
	public int option = 0;
	
	public UI(GamePanel gp) {
		this.gp = gp;
		genericFont = new Font("Arial", Font.PLAIN, 40);
	}
	
	public void pauseText() {
		displayString = "PAUSED";
	}
	
	public void healthBar(Graphics pen) {
		
		//Player health bar
		double scale = (double)gp.SCALED_TILE_SIZE * 4 / 100;
		double barAmount = gp.player.health * scale;
		
		pen.setColor(Color.BLACK);
		pen.fillRect(10, 10, gp.SCALED_TILE_SIZE * 4, 30);
		pen.setColor(Color.RED);
		pen.fillRect(10, 10, (int)barAmount, 30);
	}
	
	public void timer(Graphics pen) {//implementing for group project
		pen.setFont(pen.getFont().deriveFont(Font.BOLD, 50));
		pen.setColor(Color.BLACK);
		pen.drawString("Timer: ", 10, gp.SCREEN_HEIGHT - 10);
	}
	
	public void draw(Graphics pen) {
		gp.setBackground(Color.GRAY);
		pen.setFont(genericFont);
		pen.setColor(Color.BLACK);
	
		//Display title screen
		if (gp.gameState == gp.TITLE_SCREEN) {
			//Title screen
			displayString = "New Metal City";
			pen.setFont(pen.getFont().deriveFont(Font.BOLD, 100));
			strLength = (int)pen.getFontMetrics().getStringBounds(displayString, pen).getWidth();
			
			pen.drawString(displayString, (gp.SCREEN_WIDTH / 2 - strLength / 2) + 8, gp.SCALED_TILE_SIZE * 3 + 8);
			
			pen.setColor(Color.WHITE);
			pen.drawString(displayString, (gp.SCREEN_WIDTH / 2 - strLength / 2), gp.SCALED_TILE_SIZE * 3);
			
			//Title screen options
			//Start game
			pen.setFont(pen.getFont().deriveFont(Font.BOLD, 50));
			pen.setColor(Color.WHITE);
			displayString = "Start Game";
			strLength = (int)pen.getFontMetrics().getStringBounds(displayString, pen).getWidth();
			pen.drawString(displayString, (gp.SCREEN_WIDTH / 2 - strLength / 2), (gp.SCALED_TILE_SIZE * 3) * 3);
			
			if (option == 0) {
				pen.setColor(Color.YELLOW);
				pen.drawString(displayString, (gp.SCREEN_WIDTH / 2 - strLength / 2), (gp.SCALED_TILE_SIZE * 3) * 3);
			}
			
			//Quit game
			pen.setColor(Color.WHITE);
			displayString = "Quit";
			strLength = (int)pen.getFontMetrics().getStringBounds(displayString, pen).getWidth();
			pen.drawString(displayString, (gp.SCREEN_WIDTH / 2 - strLength / 2), (gp.SCALED_TILE_SIZE * 3) * 3 + (gp.SCALED_TILE_SIZE * 2));
			
			if (option == 1) {
				pen.setColor(Color.YELLOW);
				pen.drawString(displayString, (gp.SCREEN_WIDTH / 2 - strLength / 2), (gp.SCALED_TILE_SIZE * 3) * 3 + (gp.SCALED_TILE_SIZE * 2));
			}
		}
		
		//Set background for in game map
		if (gp.gameState == gp.PLAYING) {
			gp.setBackground(Color.WHITE);
		}
		
		//Display pause text
		if (gp.gameState == gp.PAUSED) {
			pen.setColor(Color.BLACK);
			pen.setFont(pen.getFont().deriveFont(Font.ITALIC, 100));
			strLength = (int)pen.getFontMetrics().getStringBounds(displayString, pen).getWidth();
			
			//center string
			pen.drawString(displayString, (gp.SCREEN_WIDTH / 2 - strLength / 2), gp.SCREEN_HEIGHT / 2);
		}
	}
}
