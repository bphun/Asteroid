import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;
import javax.swing.JFrame;

public class Asteroid {

	private static final int REFRESH_INTERVAL = 11;
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
		addGameObject(0, new Ship(new Location(DIMENSIONS.width / 2, DIMENSIONS.height / 2), 36, 50, this));
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
		ship().shoot();
		panel.repaint();
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
