import java.awt.Graphics2D;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.net.URL;
import java.io.IOException;

public class FlyingObject extends GameObject {

	Image image;

	public FlyingObject(Location location, Asteroid asteroid) {
		super(location, asteroid);
	
		openImage((int)Math.random() * 3);

		width = image.getWidth(null);
		height = image.getHeight(null);

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

	@Override
	public void draw(Graphics2D g2) {
		g2.drawImage(image, (int)location.x(), (int)location.y(), null);		
	}

	@Override
	public void checkCollision() {

	}

	@Override
	public void checkOffScreen() {

	}

	@Override
	public void move() {
		
	}

}