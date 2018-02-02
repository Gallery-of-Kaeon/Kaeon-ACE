package kaeon_ace.engine.utilities.math.transform;

import kaeon_ace.engine.utilities.geometry.mesh.Mesh;
import kaeon_ace.engine.utilities.math.Vector3;

public class Transform implements Comparable<Transform> {
	
	private Vector3 transform;
	
	private String mode;
	private int priority;
	
	private boolean destroyed;
	
	public Transform() {
		this.transform = new Vector3();
	}
	
	public Transform(Vector3 transform) {
		this.transform = transform;
	}
	
	public Transform(double x, double y, double z) {
		transform = new Vector3(x, y, z);
	}
	
	public Transform(Vector3 transform, String mode) {
		this.transform = transform;
		setMode(mode);
	}
	
	public Transform(double x, double y, double z, String mode) {
		transform = new Vector3(x, y, z);
		setMode(mode);
	}
	
	public void setTransformAs(Vector3 transform) {
		this.transform = transform;
	}
	
	public void setTransform(Vector3 transform) {
		this.transform.copy(transform);
	}
	
	public void setTransform(double x, double y, double z) {
		this.transform.set(x, y, z);
	}
	
	public void setX(double x) {
		transform.setX(x);
	}
	
	public void setY(double y) {
		transform.setY(y);
	}
	
	public void setZ(double z) {
		transform.setZ(z);
	}
	
	public void increment(Vector3 increment) {
		transform.increment(increment);
	}
	
	public void increment(double x, double y, double z) {
		transform.increment(x, y, z);
	}
	
	public void incrementX(double x) {
		transform.incrementX(x);
	}
	
	public void incrementY(double y) {
		transform.incrementY(y);
	}
	
	public void incrementZ(double z) {
		transform.incrementZ(z);
	}
	
	public void decrement(Vector3 increment) {
		transform.decrement(increment);
	}
	
	public void decrement(double x, double y, double z) {
		transform.decrement(x, y, z);
	}
	
	public void decrementX(double x) {
		transform.decrementX(x);
	}
	
	public void decrementY(double y) {
		transform.decrementY(y);
	}
	
	public void decrementZ(double z) {
		transform.decrementZ(z);
	}
	
	public Vector3 getTransform() {
		return transform;
	}
	
	public double getX() {
		return transform.getX();
	}
	
	public double getY() {
		return transform.getY();
	}
	
	public double getZ() {
		return transform.getZ();
	}
	
	public void setMode(String mode) {
		
		if(mode != Mesh.RENDER_MODE_3D &&
				mode != Mesh.RENDER_MODE_2D &&
				mode != Mesh.RENDER_MODE_2D_ASPECT_OFF &&
				mode != Mesh.RENDER_MODE_2D_ABSOLUTE)
			
			return;
		
		this.mode = mode;
	}
	
	public String getMode() {
		return mode;
	}
	
	public void setPrioirty(int priority) {
		this.priority = priority;
	}
	
	public int getPriority() {
		return priority;
	}
	
	public void destroy() {
		destroyed = true;
	}
	
	public boolean isDestroyed() {
		return destroyed;
	}
	
	public int compareTo(Transform transform) {
		return priority - transform.getPriority();
	}
}