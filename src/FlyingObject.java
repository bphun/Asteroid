import java.awt.Graphics2D;

public class FlyingObject extends GameObject {
	
	public FlyingObject(Location location, int radius, Asteroid asteroid) {
		super(location, radius, radius, asteroid);
	}

	@Override
	public void draw(Graphics2D g2) {
		
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