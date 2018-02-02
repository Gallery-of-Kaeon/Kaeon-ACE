package kaeon_ace.engine.utilities.math;

public class Axis {
	
	public static Vector3 getXVector(Vector3 rotation) {
		 
		return new Vector3(
			-Math.sin(Math.toRadians(rotation.getY() - 90)) * Math.cos(Math.toRadians(rotation.getX())),
			Math.sin(Math.toRadians(rotation.getX())),
			Math.cos(Math.toRadians(rotation.getY() - 90)) * Math.cos(Math.toRadians(rotation.getX()))
		);
	}
	
	public static Vector3 getYVector(Vector3 rotation) {
		 
		return new Vector3(
			-Math.sin(Math.toRadians(rotation.getY())) * Math.cos(Math.toRadians(rotation.getX() - 90)),
			Math.sin(Math.toRadians(rotation.getX() - 90)),
			Math.cos(Math.toRadians(rotation.getY())) * Math.cos(Math.toRadians(rotation.getX() - 90))
		);
	}
	
	public static Vector3 getZVector(Vector3 rotation) {
		 
		return new Vector3(
			-Math.sin(Math.toRadians(rotation.getY())) * Math.cos(Math.toRadians(rotation.getX())),
			Math.sin(Math.toRadians(rotation.getX())),
			Math.cos(Math.toRadians(rotation.getY())) * Math.cos(Math.toRadians(rotation.getX()))
		);
	}
}