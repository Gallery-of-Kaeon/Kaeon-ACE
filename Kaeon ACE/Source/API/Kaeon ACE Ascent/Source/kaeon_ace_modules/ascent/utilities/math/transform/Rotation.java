package kaeon_ace_modules.ascent.utilities.math.transform;

import kaeon_ace_modules.ascent.utilities.math.Vector3;

public class Rotation extends Transform {
	
	public Rotation() {
		super();
	}
	
	public Rotation(Vector3 transform) {
		super(transform);
	}
	
	public Rotation(double x, double y, double z) {
		super(x, y, z);
	}
	
	public Rotation(Vector3 transform, String mode) {
		super(transform, mode);
	}
	
	public Rotation(double x, double y, double z, String mode) {
		super(x, y, z, mode);
	}
	
	public double getX() {
		return super.getX() % 360;
	}
	
	public double getY() {
		return super.getY() % 360;
	}
	
	public double getZ() {
		return super.getZ() % 360;
	}
}