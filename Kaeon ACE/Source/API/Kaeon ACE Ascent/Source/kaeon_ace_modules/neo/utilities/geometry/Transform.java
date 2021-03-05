package kaeon_ace_modules.neo.utilities.geometry;

import kaeon_ace_modules.neo.utilities.math.Vector3;

public class Transform {
	
	public static final int TRANSLATION = 1;
	public static final int ROTATION = 2;
	public static final int SCALE = 3;
	
	public int type;
	public Vector3 transform;
	
	public Transform(int type, Vector3 transform) {
		this.type = type;
		this.transform = transform;
	}
	
	public Transform(int type, float x, float y, float z) {
		this.type = type;
		this.transform = new Vector3(x, y, z);
	}
}