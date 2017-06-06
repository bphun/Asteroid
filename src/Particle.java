import java.awt.Color;
import java.awt.Graphics2D;

public class Particle extends GameObject {

	private static final int DIAMETER = 2;
	private static final double SPEED = 0.2;

	public Particle(Location location, double vX, double vY, int health, Asteroid asteroid) {
		super(location, vX, vY, health, asteroid);
	}

	@Override
	public void checkOffScreen() {
		if (!willMoveOffscreen()) { return; }
		markRemove();
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.fillOval((int)location.x(), (int)location.y(), DIAMETER, DIAMETER);
		g.setColor(Color.BLACK);
	}

	@Override
	public void checkCollision() {
	}

	@Override
	public void move() {
		if (--health <= 0) { 
			markRemove();
			return;
		}

		location.addX(vX);
		location.addY(vY);

	}

}