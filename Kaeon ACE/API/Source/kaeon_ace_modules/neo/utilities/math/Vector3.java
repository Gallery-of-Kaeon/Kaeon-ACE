package kaeon_ace_modules.neo.utilities.math;

public class Vector3 {

	public float x;
	public float y;
	public float z;
	
	public Vector3() {
		
	}
	
	public Vector3(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3(Vector3 vector3) {
		this(vector3.x, vector3.y, vector3.z);
	}
	
	public float getMagnitude() {
		return (float) Math.sqrt((x * x) + (y * y) + (z * z));
	}
	
	public void scale(float magnitude) {
		
		float currentMagnitude = getMagnitude();
		
		if(currentMagnitude == 0) {
			this.x = 1;
			this.y = 1;
			this.z = 1;
		}
		
		this.x = x / currentMagnitude * magnitude;
		this.y = y / currentMagnitude * magnitude;
		this.z = z / currentMagnitude * magnitude;
	}
	
	public void add(Vector3 vector3) {
		x += vector3.x;
		y += vector3.y;
		z += vector3.z;
	}
	
	public void subtract(Vector3 vector3) {
		x -= vector3.x;
		y -= vector3.y;
		z -= vector3.z;
	}
	
	public void multiply(Vector3 vector3) {
		x *= vector3.x;
		y *= vector3.y;
		z *= vector3.z;
	}
	
	public void divide(Vector3 vector3) {
		x /= vector3.x;
		y /= vector3.y;
		z /= vector3.z;
	}
}