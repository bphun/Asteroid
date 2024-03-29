public class Weapon {

	private Ship owner;
	private boolean isFiring;
	private double direction;
	private double maxCooldown;
	private double currentCooldown;
	
	public Weapon(double direction, Ship owner) {
		this.direction = direction;
		this.owner = owner;
		this.maxCooldown = 20;
		this.currentCooldown = maxCooldown;
		this.isFiring = false;
	}
	
	/**
	 * Shoots a bullet from the given location in the given direction
	 * with the given speed.
	 * @param location to spawn the bullet
	 * @param speed that the bullet will travel at
	 * @return the bullet that was created
	 */
	public Bullet shoot(Location location, double speed) {
		return new Bullet(location, this.direction, speed, 5, 5, owner, owner.asteroid);
	}

	public void aim(double direction) {
		this.direction = direction;
	}
	
	public void tickCooldown() {
		if (currentCooldown > 0) {
			currentCooldown--;
		}
	}
	
	public double getCooldown() {
		return currentCooldown;
	}
	
	public void resetCooldown() {
		currentCooldown = maxCooldown;
	}
	
	public boolean readyToFire() {
		return currentCooldown == 0 && isFiring;
	}
	
	public boolean getFiringStatus() {
		return isFiring;
	}
	
	public void setFiringStatus(boolean shouldFire) {
		isFiring = shouldFire;
	}
}
