package kaeon_ace.engine.utilities.math.transform;

import kaeon_ace.engine.utilities.math.Vector3;

public class Translation extends Transform {
	
	public Translation() {
		super();
	}
	
	public Translation(Vector3 transform) {
		super(transform);
	}
	
	public Translation(double x, double y, double z) {
		super(x, y, z);
	}
	
	public Translation(Vector3 transform, String mode) {
		super(transform, mode);
	}
	
	public Translation(double x, double y, double z, String mode) {
		super(x, y, z, mode);
	}
}
