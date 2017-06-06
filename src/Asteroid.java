import java.awt.Toolkit;
import javax.swing.Timer;
import java.awt.Dimension;
import javax.swing.JFrame;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Asteroid {

	private Timer t;
	private JFrame frame;
	private AsteroidPanel panel;
	private ArrayList<GameObject> gameObjects;
	
	private static final int REFRESH_INTERVAL = 8;
	private static final int NUM_FLYING_OBJECTS = 100;
	private static final Dimension DIMENSIONS = Toolkit.getDefaultToolkit().getScreenSize();

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
		
		addFlyingObjects();

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
	
	private void refresh() {
		move();
		checkCollision();
		addFlyingObjects();
		ship().shoot();	
		remove();	
		panel.repaint();
	}

	private void move() {
		for (int i = 0; i < gameObjects.size(); i++) {
			gameObjects.get(i).move();
		}
	}

	private void checkCollision() {
		for (int i = 0; i < gameObjects.size(); i++) {
			gameObjects.get(i).checkCollision();
		}
	}

	private void remove() {
		for (int i = gameObjects.size() - 1; i >= 0; i--) {
			GameObject go = gameObjects.get(i);
			if (go.shouldRemove()) {
				gameObjects.remove(go);
			}
		}
	}

	private void addFlyingObjects() {
		if ((int)(Math.random() * 20) != (int)(Math.random() * 40) ) { return; }

		int currFlyingObjects = 0;
		for (int i = 0; i < gameObjects.size(); i++) {
			if (gameObjects.get(i) instanceof FlyingObject) {
				currFlyingObjects++;
			}
		}

		if (NUM_FLYING_OBJECTS - currFlyingObjects <= 0) { return; }

		switch ((int)(Math.random() * 4)) {
			case 0:
				addGameObject(gameObjects.size() , new FlyingObject( new Location(0, (int)(Math.random() * height())), Math.random() * 0.2, Math.random() * 0.2, 0,this));
				break;
			case 1:
				addGameObject(gameObjects.size() , new FlyingObject( new Location((int)(Math.random() * width()), 0), Math.random() * 0.2, Math.random() * 0.2, 0, this));
				break;
			case 2:
				addGameObject(gameObjects.size() , new FlyingObject( new Location(width(), (int)(Math.random() * height())), -(Math.random() * 0.2), Math.random() * 0.2, 0, this));
				break;
			case 3:
				addGameObject(gameObjects.size() , new FlyingObject( new Location((int)(Math.random() * width()), height()), Math.random() * 0.2, -(Math.random() * 0.2), 0, this));
				break;
		}
	}

	public int gameObjectSize() {
		return gameObjects.size();
	}

	public void updatePoints(int points) {
		panel.updatePoints(points);
	}

	public void restart() {
		t.stop();
		gameObjects.clear();
		initShip();
		// addFlyingObjects();
		t.start();
	}

	public void clickToRestart() {
 		panel.clickToRestart(true);
	}

	public Ship ship() {
		return (Ship)gameObjects.get(0);
	}

	public void addGameObject(int index, GameObject go) {
		this.gameObjects.add(index, go);
	}

	public int height() {
		return Asteroid.DIMENSIONS.height;
	}

	public int width() {
		return Asteroid.DIMENSIONS.width;
	}	

	public ArrayList<GameObject> gameObjects() {
		return this.gameObjects;
	}
	
	public Dimension dimensions() {
		return Asteroid.DIMENSIONS;
	}
}
