package kaeon_ace_modules.ascent.utilities.math.transform;

import kaeon_ace_modules.ascent.utilities.math.Vector3;

public class Scale extends Transform {
	
	public Scale() {
		super();
	}
	
	public Scale(double x, double y, double z) {
		super(x, y, z);
	}
	
	public Scale(Vector3 transform, String mode) {
		super(transform, mode);
	}
	
	public Scale(double x, double y, double z, String mode) {
		super(x, y, z, mode);
	}
}