package kaeon_ace_modules.ascent.utilities.geometry.utilities.physics.forces;

import kaeon_ace_modules.ascent.utilities.math.Vector3;

public class Gravity extends Force {
	
	private Vector3 gravity;
	
	public Gravity() {
		gravity = new Vector3();
	}
	
	public Gravity(Vector3 gravity) {
		this.gravity = gravity;
	}
	
	public Gravity(double x, double y, double z) {
		gravity = new Vector3(x, y, z);;
	}
	
	public void setGravityAs(Vector3 gravity) {
		this.gravity = gravity;
	}
	
	public void setGravity(Vector3 gravity) {
		this.gravity.copy(gravity);
	}
	
	public void setGravity(double x, double y, double z) {
		gravity.set(x, y, z);;
	}
	
	public Vector3 getGravity() {
		return gravity;
	}
}