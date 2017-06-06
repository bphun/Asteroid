import java.awt.Font;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.util.ArrayList;
import java.awt.Graphics2D;
import javax.swing.KeyStroke;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;

public class AsteroidPanel extends JPanel {

	private Ship ship;
	private int points;
	private int currPoints;
	private Asteroid asteroid;
	private boolean clickToRestart;

	public AsteroidPanel(Dimension dimension, Asteroid asteroid) {
		this.asteroid = asteroid;
		this.setPreferredSize(dimension);
		this.setBackground(Color.BLACK);

		this.setUpKeyMappings();
		this.setUpClickListener();
		this.ship = asteroid.ship();
	}
	
	private void setUpKeyMappings() {
		this.requestFocusInWindow();

		this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, false),"thrust");
		this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, true),"stopThrust");

		this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, false),"right");
		this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, true),"stopRight");	

		this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, false),"left");
		this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, true),"stopLeft");

		this.getActionMap().put("thrust",new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e) {
				ship.shouldAccelerate = true;
			}
		});	

		this.getActionMap().put("right",new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e) {
				ship.rotateRight = true;
			}
		});	

		this.getActionMap().put("left",new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e) {
				ship.rotateLeft = true;
			}
		});	

		this.getActionMap().put("stopThrust",new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e) {
				ship.shouldAccelerate = false;
			}
		});	

		this.getActionMap().put("stopRight",new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e) {
				ship.rotateRight = false;
			}
		});	

		this.getActionMap().put("stopLeft",new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e) {
				ship.rotateLeft = false;
			}
		});		
	}
	
	private void setUpClickListener() {
		this.requestFocusInWindow();

		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {

			}
			@Override
			public void mouseExited(MouseEvent arg0) {

			}

			@Override
			public void mousePressed(MouseEvent click) {
				ship.shouldShoot(true);
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				if (clickToRestart) {
					clickToRestart = false;
					points = 0;
					currPoints = 0;
					asteroid.restart();
					ship = asteroid.ship();
				}
				ship.shouldShoot(false);
			}
		});
	}
	
	public void updatePoints(int points) {
		this.points += points;
	}

	public void clickToRestart(boolean clickToRestart) {
		this.clickToRestart = clickToRestart;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);		
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2.setFont(new Font("Avenir Next", Font.PLAIN, 20));
		if (clickToRestart) {
			g2.setColor(Color.WHITE);
			g2.drawString("You died... click to restart.", asteroid.width() / 2, asteroid.height() / 2);
			return;
		}

		ArrayList<GameObject> gameObjects = asteroid.gameObjects();
		for (int i = 0; i < gameObjects.size(); i++) {
			gameObjects.get(i).draw(g2);
		}
		gameObjects = null;

		g2.setColor(Color.WHITE);
		if (currPoints < points) {
			g2.drawString("Points: " + currPoints, 20, 30);
			currPoints++;
		} else {
			g2.drawString("Points: " + points, 20, 30);
		}
		g2.setColor(Color.BLACK);
	}
}
