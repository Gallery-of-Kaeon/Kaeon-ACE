package kaeon_ace.engine.utilities.geometry.utilities.animation.skeleton;

import kaeon_ace.engine.utilities.math.Vector3;

public class Origin {
	
	private Vector3 position;
	private Vector3 rotation;
	private Vector3 scale;
	
	public Origin() {
		position = new Vector3();
		rotation = new Vector3();
		scale = new Vector3();
	}
	
	public Origin(Origin origin) {
		position = new Vector3(origin.getPosition());
		rotation = new Vector3(origin.getRotation());
		scale = new Vector3(origin.getScale());
	}
	
	public void setPosition(Vector3 position) {
		this.position = position;
	}
	
	public void setRotation(Vector3 rotation) {
		this.rotation = rotation;
	}
	
	public void setScale(Vector3 scale) {
		this.scale = scale;
	}
	
	public void move(Vector3 move) {
		position.increment(move);
	}
	
	public void rotate(Vector3 rotate) {
		rotation.increment(rotate);
	}
	
	public void scale (Vector3 scale) {
		this.scale.increment(scale);
	}
	
	public Vector3 getPosition() {
		return position;
	}
	
	public Vector3 getRotation() {
		return rotation;
	}
	
	public Vector3 getScale() {
		return scale;
	}
}