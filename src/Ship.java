import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Color;


public class Ship extends GameObject {
	
	private Thread moveThread;
	private Thread rotateThread;

	protected Weapon weapon;
	protected boolean rotateLeft;
	protected boolean rotateRight;
	protected boolean shouldAccelerate;

	private static Image shipImage;

	private static final double ACCEL = 0.02;
	private static final double DECEL = ACCEL;
	private static final int MAX_VELOCITY = 10;

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
		weapon.aim(this.direction);
	}

	public void shoot() {
		if (weapon.readyToFire()) {
			aimWeapon();
			asteroid.addGameObject(asteroid.gameObjects().size(), weapon.shoot(new Location((this.location.x() + (width / 2)) - 12, this.location.y()), 10.0));
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

		// g2.setColor(Color.GREEN);
		// g2.drawRect(-(int)location.x() / 2,  -(int)location.y(), width, height);
		// g2.setColor(Color.BLACK);
	}

	@Override
	public void checkCollision() {
		for (GameObject go : asteroid.gameObjects()) {
			if (go.equals(this)) { continue; }
			if (go.boundingRect().intersects(this.boundingRect())) {
				if (go instanceof FlyingObject) {
					this.health = 0;
					this.markRemove();
				}

				if (go instanceof Bullet) {
					this.health -= 5;
					if (this.health <= 0) {
						this.markRemove();
					}
				}
			}
		}
		checkOffScreen();
	}

	@Override
	public void checkOffScreen() {
		if (!willMoveOffscreen()) { return; }
		System.out.println(getEdge());
		switch (getEdge()) {	
			case 0:
				location.setX(asteroid.width() - width);
				break;
			case 1:
				location.setY(asteroid.height() - height);
				break;
			case 2:
				location.setX(0);
				break;
			case 3:
				location.setY(0);
				break;
		}
	}


	@Override
	public void move() {
		if (shouldAccelerate) {
			if (vX != MAX_VELOCITY * Math.sin(direction) && vX < MAX_VELOCITY) {
				if (vX > MAX_VELOCITY * Math.sin(direction)) {
					location.addX(vX += ACCEL);
				} else {
					location.addX(vX -= DECEL);
				}
			}

			if (vY != -MAX_VELOCITY * Math.cos(direction) && vY < MAX_VELOCITY) {
				if (vY > -MAX_VELOCITY * Math.cos(direction)) {
					location.addY(vY -= ACCEL);
				} else {
					location.addY(vY += DECEL);
				}
			}
		} else {
			if (vX != 0) {
				if (vX > 0) {
					location.addX(vX -= DECEL);
				} else {
					location.addX(vX += DECEL);
				}
			}
			if (vY != 0) {
				if (vY > 0) {
					location.addY(vY -= DECEL);
				} else {
					location.addY(vY += DECEL);
				}
			}
			
		}

		// if (shouldAccelerate) {
		// 	if (vX < MAX_VELOCITY) {
		// 		location.addX(vX += 0.02);
		// 	}
		// 	if (vY < MAX_VELOCITY) {
		// 		location.addY(vY += 0.02);
		// 	}
		// } else {
		// 	if (vX > 0) {
		// 		location.addX(vX -= 0.02);
		// 	}
		// 	if (vY > 0) {
		// 		location.addY(vY -= 0.02);
		// 	}
		// }

		if (rotateLeft) {
			direction += 0.02;
		} 

		if (rotateRight) {
			direction -= 0.02;
		}

		weapon.tickCooldown();
	}

}