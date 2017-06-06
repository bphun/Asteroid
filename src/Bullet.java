import java.awt.Color;
import java.util.Random;
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
					switch (((FlyingObject)go).division) {
						case 0:
							asteroid.updatePoints(50);
							break;
						case 1:
							asteroid.updatePoints(30);
							break;
						case 2:
							asteroid.updatePoints(10);
							break;
					}

					Location loc = new Location(this.location.x() + width, this.location.y() + height);
					Random random = new Random();
					for (int n = 0; n < random.nextInt(5 + 1 - 3) + 3; n++) {
						double vX = Math.random() * 0.3;
						double vY = Math.random() * 0.3;

						switch ((int)Math.random() * 2) {
							case 0:
								vX = -vX;
								break;
							case 1:
								vY = -vY;
								break;
						}
						asteroid.addGameObject(asteroid.gameObjectSize(), new Particle(new Location(loc), vX, vY, 100, asteroid)); 
					}
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
