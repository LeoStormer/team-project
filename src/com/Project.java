package com;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import com.game.Game;
import com.game.InputReader;

import com.query.Physics;

import com.rendering.Camera;
import com.rendering.Renderer;

import com.tilemap.TileParser;

public class Project extends Game {
	private static final long serialVersionUID = 1L;
	public static final int TILE_SIZE = 32;

	private Physics physics;
	private Renderer renderer;
	private Camera cam;
	private double cameraMoveX = 0;
	private double cameraMoveY = 0;
	private double speed = 300;

	public Project() {
		init();
		setBackground(Color.BLACK);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		renderer.draw(g, cam);
	}

	@Override
	public void initialize() {
		Dimension size = getSize();
		cam = new Camera(0, 0, size.width, size.height);
		addComponentListener(cam);
		renderer = new Renderer(cam);
		TileParser tileParser = new TileParser(AssetPool.getLandscapeAtlases());
		TileMapLoader loader = new TileMapLoader("./res/txtmaps/map.map", tileParser, TILE_SIZE);
		physics = loader.parseCollisionLayer();
		loader.parseTileLayers(renderer);
	}

	@Override
	public void processInputState(InputReader inputReader) {
		cameraMoveX = inputReader.isKeyPressed(LT) ? -1 : 0;
		cameraMoveX += inputReader.isKeyPressed(RT) ? 1 : 0;
		cameraMoveY = inputReader.isKeyPressed(UP) ? -1 : 0;
		cameraMoveY += inputReader.isKeyPressed(DN) ? 1 : 0;
	}

	@Override
	public void gameLoop(double deltaTime) {
		cam.setPosition(cam.getPosition().add(cameraMoveX * speed * deltaTime, cameraMoveY * speed * deltaTime));

		// Update Physics
		physics.update(deltaTime);

		// Update Animations
		renderer.update(deltaTime);
	}

}
