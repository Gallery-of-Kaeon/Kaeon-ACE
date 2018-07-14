package kaeon_ace_modules.ascent.utilities.math.transform;

import kaeon_ace_modules.ascent.utilities.math.Vector3;

public class ScaleTo extends Transform {
	
	public ScaleTo() {
		super();
	}
	
	public ScaleTo(Vector3 transform) {
		super(transform);
	}
	
	public ScaleTo(double x, double y, double z) {
		super(x, y, z);
	}
	
	public ScaleTo(Vector3 transform, String mode) {
		super(transform, mode);
	}
	
	public ScaleTo(double x, double y, double z, String mode) {
		super(x, y, z, mode);
	}
}