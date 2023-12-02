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
	
	InputReader ir = new InputReader();;
	public Camera cam;
	public Circle c;
	public double cameraMoveX = 0;
	public double cameraMoveY = 0;
	public double speed = 100;
	
	Player boy = new Player(100, 100, 32, 32);

	Rect r1 = new Rect(500, 400, 100, 100);
	Rect r2 = new Rect(800, 400, 100, 100);
	
	
	public Project() {
		// TODO Auto-generated constructor stub
		init();
		setPreferredSize(new Dimension(1280, 800));
		setBackground(Color.WHITE);
		setDoubleBuffered(true);
		addKeyListener(ir);
	}
	
	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        r1.draw(g, cam);
        r2.draw(g, cam);
        boy.draw(g, cam);
       
    }

    @Override
    public void initialize() {
        cam = new Camera(100, 100, 5, 5);
    }

    @Override
    public void processInputState(InputReader inputReader) {

    	boy.physicsOFF();
    	
    	/*	
        if (ir.isKeyPressed(_A)) r1.goLT(5.0);
        if (ir.isKeyPressed(_D)) r1.goRT(5.0);
        if (ir.isKeyPressed(_W)) r1.goUP(5.0);
        if (ir.isKeyPressed(_S)) r1.goDN(5.0);
        */
    	
    	boy.handleInput(inputReader);

        boy.move();
    }

    @Override
    public void gameLoop(double deltaTime) {
        boy.update(deltaTime);
        handleCollisions();
    }


    private void handleCollisions() {
    
    	if(boy.intersects(r1)) {
    	   boy.pushedOutOf(r1);
    	}
    	
    	if(boy.intersects(r2)) {
     	   boy.pushedOutOf(r2);
     	}
    	
    }
}