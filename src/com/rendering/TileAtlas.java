package com.rendering;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

/**
 * A class that splits an image into multiple {@link Tile Tiles}.
 */
public class TileAtlas extends Tile {

    private ArrayList<Tile> tiles;

    public int numTilesX, numTilesY;

    /**
     * Loads the image at the given file path.
     * Splits the image into many smaller sub images.
     * Starts from the top left of the image to the bottom right.
     * 
     * @param filepath   the path to the image file
     * @param tileWidth  the pixel width of the subImages
     * @param tileHeight the pixel height of the subImages
     * @param spacing    the pixel spacing between subImages
     * @throws IOException if an I/O error occurs
     */
    public TileAtlas(String filepath, int tileWidth, int tileHeight, int spacing) throws IOException {
        this(ImageIO.read(new File(filepath)), tileWidth, tileHeight, spacing);
    }

    /**
     * Splits an image into many smaller sub images.
     * Starts from the top left of the image to the bottom right.
     * 
     * @param image
     * @param tileWidth  the pixel width of the subImages
     * @param tileHeight the pixel height of the subImages
     * @param spacing    the pixel spacing between subImages
     */
    public TileAtlas(BufferedImage image, int tileWidth, int tileHeight, int spacing) {
        super(image);
        int rowIncrement = tileHeight + spacing;
        int columnIncrement = tileWidth + spacing;
        int maxRows = Math.floorDiv(height, rowIncrement);
        int maxCols = Math.floorDiv(width, columnIncrement);
        numTilesY = maxRows;
        numTilesX = maxCols;
        tiles = new ArrayList<>();

        sample(image, tileWidth, tileHeight, maxRows, maxCols, rowIncrement, columnIncrement);
    }

    /**
     * Loads the image at the given file path.
     * Splits the image into many smaller sub images.
     * Starts from the top left of the image to the bottom right.
     * 
     * @param filepath   the path to the image file
     * @param tileWidth  the pixel width of the subImages
     * @param tileHeight the pixel height of the subImages
     * @param spacing    the pixel spacing between subImages
     * @param columns    the number of subImages to make per row
     * @throws IOException if an I/O error occurs
     */
    public TileAtlas(String filepath, int tileWidth, int tileHeight, int spacing, int[] columns) throws IOException {
        this(ImageIO.read(new File(filepath)), tileWidth, tileHeight, spacing, columns);
    }

    /**
     * Splits the image into many smaller sub images.
     * Starts from the top left of the image to the bottom right.
     * 
     * @param image
     * @param tileWidth  the pixel width of the subImages
     * @param tileHeight the pixel height of the subImages
     * @param spacing    the pixel spacing between subImages
     * @param columns    the number of subImages to make per row
     */
    public TileAtlas(BufferedImage image, int tileWidth, int tileHeight, int spacing, int[] columns) {
        super(image);
        int rowIncrement = tileHeight + spacing;
        int columnIncrement = tileWidth + spacing;
        int maxRows = Math.floorDiv(height, rowIncrement);
        int maxCols = Math.floorDiv(width, columnIncrement);
        numTilesX = maxCols;
        numTilesY = maxRows;
        tiles = new ArrayList<>();

        for (int row = 0; row < maxRows; row++) {
            int numCols = Math.min(columns[row], maxCols);
            int imageY = row * rowIncrement;
            for (int column = 0; column < numCols; column++) {
                int imageX = column * columnIncrement;
                tiles.add(new Tile(image.getSubimage(imageX, imageY, tileWidth, tileHeight)));
            }
        }
    }

    /**
     * Samples a rectangular area of the given image.
     * 
     * @param image           the image to sample
     * @param tileWidth       the width of the sub-images
     * @param tileHeight      the height of the sub-images
     * @param maxRows         the height of the image in number of tiles to sample
     * @param maxCols         the width of the image in number of tiles to sample
     * @param rowIncrement
     * @param columnIncrement
     */
    private void sample(BufferedImage image, int tileWidth, int tileHeight, int maxRows, int maxCols, int rowIncrement,
            int columnIncrement) {
        for (int row = 0; row < maxRows; row++) {
            int imageY = row * rowIncrement;
            for (int column = 0; column < maxCols; column++) {
                int imageX = column * columnIncrement;
                tiles.add(new Tile(image.getSubimage(imageX, imageY, tileWidth, tileHeight)));
            }
        }
    }

    /**
     * @return All {@link Tile Tiles} in this TileAtlas
     */
    public Tile[] getTiles() {
        return tiles.toArray(new Tile[tiles.size()]);
    }

    /**
     * @param n
     * @return the nth {@link Tile} of the TileAtlas
     */
    public Tile getTile(int n) {
        return tiles.get(n);
    }

    /**
     * @return the number of tiles the TileAtlas was split into.
     */
    public int getNumTiles() {
        return tiles.size();
    }

    public Tile[] getTiles(int[] indices) {
        Tile[] tilesToReturn = new Tile[indices.length];
        for (int i = 0; i < tilesToReturn.length; i++) {
            tilesToReturn[i] = getTile(indices[i]);
        }

        return tilesToReturn;
    }

}