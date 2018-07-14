package kaeon_ace_modules.ascent.utilities.math;

public class Vector3 {
	
	private double x;
	private double y;
	private double z;
	
	public Vector3() {
		setX(0);
		setY(0);
		setZ(0);
	}
	
	public Vector3(
			double x,
			double y,
			double z) {
		
		set(x, y, z);
	}
	
	public Vector3(Vector3 vector3) {
		copy(vector3);
	}
	
	public void copy(Vector3 vector3) {
		setX(vector3.getX());
		setY(vector3.getY());
		setZ(vector3.getZ());
	}
	
	public void set(
			double x,
			double y,
			double z) {
		
		setX(x);
		setY(y);
		setZ(z);
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public void setZ(double z) {
		this.z = z;
	}
	
	public void increment(Vector3 vector3) {
		setX(x + vector3.getX());
		setY(y + vector3.getY());
		setZ(z + vector3.getZ());
	}
	
	public void increment(
			double x,
			double y,
			double z) {
		
		setX(this.x + x);
		setY(this.y + y);
		setZ(this.z + z);
	}
	
	public void incrementX(double x) {
		this.x += x;
	}
	
	public void incrementY(double y) {
		this.y += y;
	}
	
	public void incrementZ(double z) {
		this.z += z;
	}
	
	public void decrement(Vector3 vector3) {
		setX(x - vector3.getX());
		setY(y - vector3.getY());
		setZ(z - vector3.getZ());
	}
	
	public void decrement(
			double x,
			double y,
			double z) {
		
		setX(this.x - x);
		setY(this.y - y);
		setZ(this.z - z);
	}
	
	public void decrementX(double x) {
		this.x -= x;
	}
	
	public void decrementY(double y) {
		this.y -= y;
	}
	
	public void decrementZ(double z) {
		this.z -= z;
	}
	
	public void scale(Vector3 vector3) {
		setX(x * vector3.getX());
		setY(y * vector3.getY());
		setZ(z * vector3.getZ());
	}
	
	public void scale(
			double x,
			double y,
			double z) {
		
		setX(this.x * x);
		setY(this.y * y);
		setZ(this.z * z);
	}
	
	public void scaleX(double x) {
		this.x *= x;
	}
	
	public void scaleY(double y) {
		this.y *= y;
	}
	
	public void scaleZ(double z) {
		this.z *= z;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public double getZ() {
		return z;
	}
	
	public void setMagnitude(double magnitude) {
		
		if(getMagnitude() == 0) {
			setX(1);
			setY(1);
			setZ(1);
		}
		
		Vector3 unitVector = getUnitVector();
		
		setX(unitVector.getX() * magnitude);
		setY(unitVector.getY() * magnitude);
		setZ(unitVector.getZ() * magnitude);
	}
	
	public double getMagnitude() {
		
		return Math.sqrt(
				(x * x) +
				(y * y) +
				(z * z));
	}
	
	public Vector3 getUnitVector() {
		
		if(getMagnitude() > 0)
			
			return new Vector3(
					x / getMagnitude(),
					y / getMagnitude(),
					z / getMagnitude()
					);
		
		else
			
			return new Vector3(0, 0, 0);
	}
	
	public String toString() {
		return x + " " + y + " " + z;
	}
}