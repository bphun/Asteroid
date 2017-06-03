import java.net.URL;
import java.awt.Image;
import java.io.IOException;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;

public class Ship extends GameObject {

	protected Weapon weapon;
	protected boolean rotateLeft;
	protected boolean rotateRight;
	protected boolean shouldAccelerate;

	private static Image shipImage;

	private static final double ACCEL = 0.02;
	private static final int MAX_VELOCITY = 10;
	private static final double FRICTION = ACCEL;

	public Ship(Location location, int width, int height, Asteroid asteroid) {
		super(location, width, height, asteroid);

		this.weapon = new Weapon(direction, this);
		this.openImage();
	}

	private void openImage() {
		if (shipImage != null) { return; }

		try {		
			URL shipImageURL = getClass().getResource("shipImage.png");
			if (shipImageURL != null) {
				shipImage = ImageIO.read(shipImageURL);
			}
		} catch (IOException e) {
			System.err.println("Could not open image ( shipImage.png )");
			e.printStackTrace();
		}
	}

	public void aimWeapon() {
		weapon.aim(-this.direction - (Math.PI / 2));
	}

	public void shoot() {
		if (weapon.readyToFire()) {
			aimWeapon();
			asteroid.addGameObject(asteroid.gameObjects().size(), weapon.shoot(new Location(this.location.x() + (width / 2), this.location.y() + (height / 2)), 5));
			weapon.resetCooldown();
		}
	}

	public void shouldShoot(boolean shouldShoot) {
		weapon.setFiringStatus(shouldShoot);
	}

	@Override
	public void draw(Graphics2D g2) {
		AffineTransform a = g2.getTransform();
		g2.translate(location.x() + (width / 2), location.y() + (height / 2));
		g2.rotate(-direction);
		g2.drawImage(shipImage, -width / 2,  -height / 2, null);
		g2.setTransform(a);
	}

	@Override
	public void checkCollision() {
		for (GameObject go : asteroid.gameObjects()) {
			if (go.equals(this)) { continue; }
			if (go.boundingRect().intersects(this.boundingRect())) {
				if (go instanceof FlyingObject) {
					this.markRemove();
					asteroid.clickToRestart();					
				}
			}
		}
		checkOffScreen();
	}

	@Override
	public void checkOffScreen() {
		if (!willMoveOffscreen()) { return; }
		switch (getEdge()) {	
			case 0:
				location.setX(asteroid.width());
				break;
			case 1:
				location.setY(asteroid.height());
				break;
			case 2:
				location.setX(width);
				break;
			case 3:
				location.setY(height);
				break;
		}
	}

	@Override
	public void move() {

		if (shouldAccelerate) {
			if (vX < MAX_VELOCITY && vX != MAX_VELOCITY * Math.sin(direction)) {
				if (vX > MAX_VELOCITY * Math.sin(direction)) {
					location.addX(vX += ACCEL);
				} else {
					location.addX(vX -= FRICTION);
				}
			} else {
				location.addX(vX -= FRICTION);
			}

			if (vY < MAX_VELOCITY && vY != -MAX_VELOCITY * Math.cos(direction)) {
				if (vY > -MAX_VELOCITY * Math.cos(direction)) {
					location.addY(vY -= ACCEL);
				} else {
					location.addY(vY += FRICTION);
				}
			} else {
				location.addY(vY -= FRICTION);
			}
		} else {
			if (vX != 0) {
				if (vX > 0) {
					location.addX(vX -= FRICTION);
				} else {
					location.addX(vX += FRICTION);
				}
			}
			if (vY != 0) {
				if (vY > 0) {
					location.addY(vY -= FRICTION);
				} else {
					location.addY(vY += FRICTION);
				}
			}
		}

		if (rotateLeft) {
			direction += 0.03;
		}

		if (rotateRight) {
			direction -= 0.03;
		}

		weapon.tickCooldown();
	}
}
