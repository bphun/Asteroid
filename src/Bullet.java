import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.awt.Graphics2D;

public class Bullet extends GameObject {

	private double speed;
	private Ship owner;

	protected double direction;

	public Bullet(Location location, double direction, double speed, int width, int height, Ship owner, Asteroid asteroid) {
		super(location, width, height, asteroid);
		super.health = 5;
		this.owner = owner;
		this.speed = speed;
		this.direction = direction;
	}

	private void checkCollision(Ship p) {
		if (isOwner(p)) {
			return;
		}
		if (this.boundingRect().intersects(p.boundingRect())) {
			markRemove();
		}
	}
	
	public boolean isOwner(Ship ship) {
		return owner.equals(ship);
	}

	public Ship getOwner() {
		return owner;
	}

	@Override
	public void checkOffScreen() {
		markRemove();
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
			if (go.equals(this)) {
				continue;
			}
			if (go instanceof Ship) {
				checkCollision((Ship) go);
				continue;
			}  

			if (this.boundingRect().intersects(go.boundingRect())) {
				markRemove();
			}
		}
	}

	@Override
	public void move() {
		this.location.addVector(speed, direction);
	}
}
