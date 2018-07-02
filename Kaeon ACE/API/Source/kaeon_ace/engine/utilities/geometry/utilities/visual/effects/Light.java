package kaeon_ace.engine.utilities.geometry.utilities.visual.effects;

import kaeon_ace.engine.utilities.math.Vector3;

public class Light extends Effect {
	
	private Vector3 position;
	
	public Light() {
		position = new Vector3(0, 1, 0);
	}
	
	public void setPositionAs(Vector3 position) {
		this.position = position;
	}
	
	public void setPosition(Vector3 position) {
		this.position.copy(position);
	}
	
	public void setPosition(double x, double y, double z) {
		position.set(x, y, z);
	}
	
	public Vector3 getPosition() {
		return position;
	}
}