import java.net.URL;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;

public class FlyingObject extends GameObject {

	private Image image;

	public FlyingObject(Location location, double vX, double vY, Asteroid asteroid) {
		super(location, asteroid);
	
		this.vX = vX;
		this.vY = vY;

		this.openImage((int)Math.random() * 3);

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

			image = image.getScaledInstance(image.getWidth(null) / 2, image.getHeight(null) / 2, Image.SCALE_DEFAULT);

		} catch (IOException e) {
			System.err.println("ERROR: Cold not open image");
			e.printStackTrace();
		}
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
					this.markRemove();
				}

				if (go instanceof FlyingObject) {
					this.vX -= (vX * 2);
					this.vY -= (vY * 2);
					go.vX -= 0.01;
					go.vY -= 0.01;
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
