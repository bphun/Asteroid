import java.net.URL;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;

public class FlyingObject extends GameObject {

	private Image image;
	
	protected int division;

	public FlyingObject(Location location, double vX, double vY, int division, Asteroid asteroid) {
		super(location, vX, vY, asteroid);
	
		this.division = division;

		this.openImage((int)Math.random() * 3);

		this.width = image.getWidth(null);
		this.height = image.getHeight(null);
	}

	public FlyingObject(Location location, double vX, double vY, int division, Image image, Asteroid asteroid) {
		super(location, asteroid);
	
		this.vX = vX;
		this.vY = vY;

		this.division = division;

		this.image = image;

		this.width = image.getWidth(null);
		this.height = image.getHeight(null);

	}

	private void openImage(int type) {
		try {
			URL imgURL = null;
			switch (type) {
				case 0:
					imgURL = getClass().getResource("asteroid0.png");
					break;
				case 1:
					imgURL = getClass().getResource("asteroid1.png");
					break;
				case 2:
					imgURL = getClass().getResource("asteroid2.png");
					break;
			}

			if (imgURL != null) {
				image = ImageIO.read(imgURL);
			}

		} catch (IOException e) {
			System.err.println("ERROR: Cold not open image");
			e.printStackTrace();
		}
	}

	private Image resize(Image img) {
		switch (division) {
			case 1:
				img = img.getScaledInstance((int)img.getWidth(null) / 2, (int)img.getHeight(null) / 2, Image.SCALE_DEFAULT);
				break;
			case 2:
				img = img.getScaledInstance((int)img.getWidth(null) - 5, (int)img.getHeight(null) - 5, Image.SCALE_DEFAULT);
				break;
		}
		return img;
	}

	private void didCollide(FlyingObject flyingObject) {
		
	}

	public void updateDivisions() {
		division++;

		image = resize(image);

		width = image.getWidth(null);
		height = image.getHeight(null);
	}

	@Override
	public void draw(Graphics2D g2) {
		AffineTransform a = g2.getTransform();
		g2.translate(location.x() + (width / 2), location.y() + (height / 2));
		g2.rotate(-direction);
		g2.drawImage(image, -width / 2,  -height / 2, null);
		g2.setTransform(a);

		direction += 0.01;
	}

	@Override
	public void checkCollision() {
		ArrayList<GameObject> gameObjects = asteroid.gameObjects();
		for (int i = 0; i < gameObjects.size(); i++) {
			GameObject go = gameObjects.get(i);
			
			if (go.equals(this)) { continue; }
		
			if (this.boundingRect().intersects(go.boundingRect())) {
				if (go instanceof Bullet) {
					if (division == 2) {
						markRemove();
						return;
					}

					
					this.updateDivisions();
					asteroid.addGameObject(asteroid.gameObjects().size(), new FlyingObject(new Location(this.location.x() + this.width, this.location.y() + this.height), this.vX - (this.vX * 2), this.vY, this.division, this.image, asteroid));
				}

				if (go instanceof FlyingObject) {
					this.vX -= (vX * 2);
					this.vY -= (vY * 2);
					go.vX -= 0.01;
					go.vY -= 0.01;
					// didCollide((FlyingObject)go);
				}
			}
		}
		checkOffScreen();
	}

	@Override
	public void checkOffScreen() {
		if (!willMoveOffscreen()) { return; }
		this.markRemove();
	}

	@Override
	public void move() {
		location.addX(vX);
		location.addY(vY);
	}
}
