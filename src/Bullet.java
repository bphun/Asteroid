import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class Bullet extends GameObject {

	private Ship owner;
	private double speed;

	protected double direction;

	public Bullet(Location location, double direction, double speed, int width, int height, Ship owner, Asteroid asteroid) {
		super(location, width, height, asteroid);
		
		this.owner = owner;
		this.speed = speed;
		this.direction = direction;
	}

	public boolean isOwner(Ship ship) {
		return owner.equals(ship);
	}

	public Ship getOwner() {
		return owner;
	}

	@Override
	public void checkOffScreen() {
		if (!willMoveOffscreen()) { return; }
		this.markRemove();
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.fillOval((int)location.x(), (int)location.y(), width, height);
	}

	@Override
	public void checkCollision() {
		ArrayList<GameObject> gameObjects = asteroid.gameObjects();
		for (int i = 0; i < gameObjects.size(); i++) {
			GameObject go = gameObjects.get(i);
			if (go.equals(this)) { continue; }

			if (go.boundingRect().intersects(this.boundingRect())) {

				if (go instanceof FlyingObject) {
					this.markRemove();
				}
				
			}
		}
		checkOffScreen();
	}

	@Override
	public void move() {
		this.location.addVector(speed, direction);
	}
}
