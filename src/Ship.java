import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class Ship extends GameObject {
	
	private static Image shipImage;

	protected Weapon weapon;
	protected boolean rotateLeft;
	protected boolean rotateRight;
	protected boolean shouldAccelerate;

	public Ship(Location location, int width, int height, Asteroid asteroid) {
		super(location, width, height, asteroid);

		this.weapon = new Weapon(direction, this);

		this.openImage();
		this.direction = Math.PI / 2;
	}

	private void openImage() {
		if (shipImage != null) { return; }
		try {		
			URL shipImageURL = getClass().getResource("shipImage.png");
			if (shipImageURL != null) {
				shipImage = ImageIO.read(shipImageURL);
				shipImage = shipImage.getScaledInstance(shipImage.getWidth(null) * 2, shipImage.getHeight(null) * 2, Image.SCALE_DEFAULT);
			}
		} catch (IOException e) {
			System.err.println("Could not open image ( shipImage.png )");
			e.printStackTrace();
		}

	}

	public void aimWeapon() {
		weapon.aim(-this.direction);
	}

	public void shoot() {
		if (weapon.readyToFire()) {
			aimWeapon();
			asteroid.addGameObject(asteroid.gameObjects().size(), weapon.shoot(new Location((this.location.x() + (width / 2)) - 3, this.location.y()), 5.0));
			weapon.resetCooldown();
		}
	}

	public void shouldShoot(boolean shouldShoot) {
		weapon.setFiringStatus(shouldShoot);
	}

	@Override
	public void draw(Graphics2D g2) {
		// AffineTransform a = g2.getTransform();
		// g2.translate((int)(location.x() + (width / 2)), (int)(location.y() + (height / 2)));
		// g2.rotate(direction);
		g2.drawImage(shipImage, (int)location.x(),  (int)location.y(), null);
		// g2.setTransform(a);
	}

	@Override
	public void checkCollision() {
		for (GameObject go : asteroid.gameObjects()) {
			if (go.equals(this)) { continue; }
			if (go.boundingRect().intersects(this.boundingRect())) {
				if (go instanceof FlyingObject) {

				}

				if (go instanceof Bullet) {
					this.health -= 5;
					if (this.health <= 0) {
						this.markRemove();
					}
				}
			}
		}
	}

	@Override
	public void checkOffScreen() {
		if (!willMoveOffscreen()) { return; }

		switch (getEdge()) {	
			case 0:
				location.setX(0 - width);
				break;
			case 1:
				location.setX(0 - height);
				break;
			case 2:
				location.setX(asteroid.width() + width);
				break;
			case 3:
				location.setX(asteroid.height() + height);
				break;
		}
	}


	@Override
	public void move() {
		Thread moveThread = new Thread(new Runnable() {
			public void run() {
				if (shouldAccelerate) {
					if (vX < 10) {
						location.addX(vX += 0.09);
					}
					if (vY < 10) {
						location.addY(vY += 0.09);
					}
				} else {
					if (vX > 0) {
						location.addX(vX -= 0.09);
					}
					if (vY > 0) {
						location.addY(vY -= 0.09);
					}
				}
			}
		});
		moveThread.start();

		Thread rotateThread = new Thread(new Runnable() {
			public void run() {
				if (rotateLeft) {
					direction += 0.02;
				} 

				if (rotateRight) {
					direction -= 0.02;
				}

			}
		});
		rotateThread.start();
		weapon.tickCooldown();
	}

}