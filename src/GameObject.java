import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public abstract class GameObject {

	protected double vX, vY;
	protected double direction;
	protected Location location;
	protected Asteroid asteroid;
	protected int height, width, health;
	protected boolean hitPlatform, shouldFall, shouldRemove, canJump;

	protected static final Color STANDARD_COLOR = new Color(117,117,117);

	public GameObject(Location location, int width, int height, Asteroid asteroid) {
		this.location = location;
		this.width = width;
		this.height =  height;
		this.asteroid = asteroid;
		this.shouldFall = true;
		this.health = 100;
	}

	public abstract void draw(Graphics2D g2);

	public abstract void checkCollision();

	public abstract void checkOffScreen();

	public abstract void move();

	public Rectangle boundingRect() {
		return new Rectangle((int) (location.x()), (int) (location.y()), (int) width, (int) height);
	}

	protected boolean willMoveOffscreen() {
		Location loc = new Location(location);
		loc.addVector(Math.atan2(vY, vX), direction);
		return !loc.inMap(new Rectangle(asteroid.dimensions()));
	}

	/**
	 * @return the edge of the panel that the GameObject is on
	 * 0 = left, 1 = top, 2 = right, 3 = bottom
	 */
	protected int getEdge() {
		if (location.y() <= height) {
			return 1;
		} else if (location.y() >= asteroid.height() - height) {
			return 3;
		} else if (location.x() <= width) {
			return 0;
		} else if (location.x() >= asteroid.width() - width) {
			return 2;
		}
		return -1;
	}

	public void markRemove() {
		this.shouldRemove = true;
	}

	public boolean shouldRemove() {
		return this.shouldRemove;
	}
}