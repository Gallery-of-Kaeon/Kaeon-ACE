package kaeon_ace.engine.utilities.geometry.utilities.physics.collider;

public class SphereCollider extends Collider {
	
	private double radius;
	
	public SphereCollider() {
		radius = 0;
	}
	
	public SphereCollider(double radius) {
		this.radius = radius;
	}
	
	public double getRadius() {
		return radius;
	}
}