import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;
import javax.swing.JFrame;

public class Asteroid {

	private static final int REFRESH_INTERVAL = 8;
	private static final int NUM_FLYING_OBJECTS =  15;
	private static final Dimension DIMENSIONS = Toolkit.getDefaultToolkit().getScreenSize();

	
	private Timer t;
	private JFrame frame;
	private AsteroidPanel panel;
	private ArrayList<GameObject> gameObjects;

	public static void main(String[] args) {
		new Asteroid().start();
	}
	
	private void start() {
		frame = new JFrame("Asteroid");
		gameObjects = new ArrayList<>();

		initShip();
		
		panel = new AsteroidPanel(DIMENSIONS, this);
		
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		startTimer();
	}
	
	private void initShip() {
		addGameObject(0, new Ship(new Location(DIMENSIONS.width / 2, DIMENSIONS.height / 2), 18, 25, this));
	}

	private void startTimer() {
		t = new Timer(REFRESH_INTERVAL, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				refresh();
			}
		});
		t.start();
	}
	
	public void refresh() {
		move();
		checkCollision();
		drawFlyingObjects();
		ship().shoot();
		panel.repaint();
	}

	private void checkCollision() {
		for (int i = 0; i < gameObjects.size(); i++) {
			gameObjects.get(i).checkCollision();
		}
	}

	private void drawFlyingObjects() {
		if ((int)Math.random() * 10 != (int)Math.random() * 10 ) { return; }

		int currFlyingObjects = 0;
		for (GameObject go : gameObjects) {
			if (go instanceof FlyingObject) {
				currFlyingObjects++;
			}
		}

		if (currFlyingObjects < NUM_FLYING_OBJECTS) {
			for (int i = 0; i < currFlyingObjects; i++) {
				switch ((int)Math.random() * 4) {
					case 0:
						addGameObject(0, new FlyingObject( new Location(0, (int)Math.random() * height()), this ));
						break;
					case 1:
						addGameObject(0, new FlyingObject( new Location((int)Math.random() * width(), 0), this ));
						break;
					case 2:
						addGameObject(0, new FlyingObject( new Location(width(), (int)Math.random() * height()), this ));
						break;
					case 3:
						addGameObject(0, new FlyingObject( new Location((int)Math.random() * width(), height()), this ));
						break;
				}
			}
		}
	}

	private void move() {
		for (int i = 0; i < gameObjects.size(); i++) {
			gameObjects.get(i).move();
		}
	}

	public Ship ship() {
		return (Ship)gameObjects.get(0);
	}

	public void addGameObject(int index, GameObject go) {
		this.gameObjects.add(index, go);
	}

	public int height() {
		return this.DIMENSIONS.height;
	}

	public int width() {
		return this.DIMENSIONS.width;
	}	

	public ArrayList<GameObject> gameObjects() {
		return this.gameObjects;
	}
	
	public Dimension dimensions() {
		return this.DIMENSIONS;
	}

}
