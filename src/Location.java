import java.awt.Point;
import java.awt.Rectangle;

public class Location {
	
	public double x,y;
	
	public Location(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	public Location(Location other) {
		x = other.x;
		y = other.y;
	}
	
	public double x() {
		return x;
	}
	
	public double y() {
		return y;
	}
	
	public void addX(double x) {
		this.x += x;
	}

	public void setX(double x) {
		this.x = x;
	}
	
	public void addY(double y) {
		this.y += y;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	/**
	 * Adds a vector defined by magnitude and direction to itself.
	 * @param magnitude
	 * @param direction
	 */
	public void addVector(double magnitude, double direction) {
		addX(magnitude * Math.cos(direction));
		addY(magnitude * Math.sin(direction));
	}
	
	public double distanceTo(Location other) {
		double otherX = other.x;
		double otherY = other.y;
		return Math.sqrt(Math.pow(otherX - x, 2) + Math.pow(otherY - y, 2));
	}

	public boolean inMap(Rectangle rect) {
		if (rect.contains(new Point((int) x, (int) y))) {
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Location other = (Location) obj;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "("+x+","+y+")";
	}
}
