import java.awt.Graphics2D;

public class Particle extends GameObject {

	private static final double SPEED = 0.5;
	private static final int DIAMETER = 1;

	public Particle(Location location, double direction, Asteroid asteroid) {
		super(location, direction, 1000, asteroid);
	}

	@Override
	public void checkOffScreen() {
		if (!willMoveOffscreen()) { return; }
		markRemove();
	}

	@Override
	public void draw(Graphics2D g) {
		g.fillOval((int)location.x(), (int)location.y(), DIAMETER, DIAMETER);
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

		location.addVector(SPEED, direction);

	}

}