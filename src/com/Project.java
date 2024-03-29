package com;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import com.game.Game;
import com.game.InputListener;

import com.query.Circle;
import com.query.Collider;
import com.query.Physics;
import com.query.Rect;
import com.query.Vector2;

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

	private Collider testCollider;

	public Project() {
		init();
		setBackground(Color.BLACK);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		renderer.draw(g, cam);
		physics.draw(g, cam);
	}

	@Override
	public void initialize() {
		Dimension size = getSize();
		cam = new Camera(0, 0, size.width, size.height);
		addComponentListener(cam);
		testCollider = new Rect(128, 128, TILE_SIZE, TILE_SIZE);
		// testCollider = new Circle(128,128, TILE_SIZE / 2);
		renderer = new Renderer(cam);
		TileParser tileParser = new TileParser(AssetPool.getLandscapeAtlases());
		TileMapLoader loader = new TileMapLoader("./res/txtmaps/map.map", tileParser, TILE_SIZE);
		physics = loader.parseCollisionLayer();
		loader.parseTileLayers(renderer);
		physics.addCollider(testCollider);
	}

	@Override
	public void processInputState(InputListener inputListener) {
		cameraMoveX = inputListener.isKeyPressed(LT) ? -1 : 0;
		cameraMoveX += inputListener.isKeyPressed(RT) ? 1 : 0;
		cameraMoveY = inputListener.isKeyPressed(UP) ? -1 : 0;
		cameraMoveY += inputListener.isKeyPressed(DN) ? 1 : 0;
	}

	@Override
	public void gameLoop(double deltaTime) {
		testCollider.setVelocity(cameraMoveX * speed, cameraMoveY * speed);

		// Update Physics
		physics.update(deltaTime);

		Vector2 newCenter = testCollider.getCenter().subtract(cam.getSize().scale(0.5));
		cam.setPosition(cam.getPosition().lerp(newCenter, 0.9));

		// Update Animations
		renderer.update(deltaTime);
	}

}
