package tiles;

import java.awt.*;
import java.io.*;

import rendering.Camera;

public class TileMap {
    public String[] map;
    Image[] tile;
    String[] tile_name;

    int S;
    
    public TileMap(String[] map, Image[] tile, int scale) {
        this.map = map;
        this.tile = tile;
        S = scale;
    }

    public TileMap(String filename, int scale) {
        loadMap(filename);
        loadAssets();
        S = scale;
    }

    public void loadMap(String filename) {
        File file = new File(filename);

        try {
            BufferedReader input = new BufferedReader(new FileReader(file));

            int n = Integer.parseInt(input.readLine());  // How many rows in the map?

            map = new String[n];

            for (int row = 0; row < n; row++) {
                map[row] = input.readLine();
            }

            n = Integer.parseInt(input.readLine());     // How many tiles?

            tile_name = new String[n];

            for (int i = 0; i < n; i++) {
                tile_name[i] = input.readLine();
            }

            input.close();

            //Testing
            System.out.println("Number of Rows: " + map.length);
            System.out.println("Number of Columns: " + (map.length > 0 ? map[0].length() : 0));

        } catch (IOException x) {
        }
    }

    public void loadAssets() {
        tile = new Image[tile_name.length];

        for (int i = 0; i < tile.length; i++) {
            tile[i] = getImage("res/" + tile_name[i]);
            
            //Testing
            System.out.println(tile_name[i]);
        }
    }

    /* Methods for Tile Map Editor */
    
    public void saveMap(String filename) {
        File file = new File(filename);
        try {
            BufferedWriter output = new BufferedWriter(new FileWriter(file));

            output.write(map.length + "\n");

            for (int row = 0; row < map.length; row++)
                output.write(map[row] + "\n");

            output.write(tile_name.length + "\n");

            for (int i = 0; i < tile_name.length; i++)
                output.write(tile_name[i] + "\n");

            output.close();
        } catch (IOException x) {
        }
    }

    public char valueAt(int y, int x) {
        int row = y / S;
        int col = x / S;

        return map[row].charAt(col);
    }

    public void change(int x, int y, char c) {
        int row = y / S;
        int col = x / S;

        if (row >= 0 && row < map.length && col >= 0 && col < map[0].length()) {
            map[row] = map[row].substring(0, col) + c + map[row].substring(col + 1);
        } else {
            System.out.println("Invalid row or column: " + row + ", " + col);
        }
    }
    
    // Draw
    public void draw(Graphics g, Camera cam) {
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[row].length(); col++) {
                char c = map[row].charAt(col);
                
                	if (c != '.' && tile[c - 'A'] != null) {
                		g.drawImage(tile[c - 'A'], (int) (S * col - cam.getPosition().getX()), (int) (S * row - cam.getPosition().getY()), S, S, null);
                		//g.drawRect((int) (S * col - cam.getPosition().getX()), (int) (S * row - cam.getPosition().getY()), S, S);
                	}
            }
        }
    }

    public Image getImage(String filename) {
        return Toolkit.getDefaultToolkit().getImage(filename);
    }
}

