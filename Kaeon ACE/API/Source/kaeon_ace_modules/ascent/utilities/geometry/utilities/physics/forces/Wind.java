package kaeon_ace_modules.ascent.utilities.geometry.utilities.physics.forces;

import kaeon_ace_modules.ascent.utilities.math.Vector3;

public class Wind extends Force {
	
	private Vector3 position;
	private Vector3 wind;
	
	public Wind() {
		position = new Vector3();
		wind = new Vector3();
	}
	
	public Wind(Vector3 position, Vector3 wind) {
		this.position = position;
		this.wind = wind;
	}
	
	public Wind(
			double xPosition, double yPosition, double zPosition,
			double xWind, double yWind, double zWind) {
		
		position = new Vector3(xPosition, yPosition, zPosition);
		wind = new Vector3(xWind, yWind, zWind);
	}
	
	public void setPositionAs(Vector3 position) {
		this.position = position;
	}
	
	public void setPosition(Vector3 position) {
		this.position.copy(position);
	}
	
	public void setPosition(double xPosition, double yPosition, double zPosition) {
		position.set(xPosition, yPosition, zPosition);
	}
	
	public void setWindAs(Vector3 wind) {
		this.wind = wind;
	}
	
	public void setWind(Vector3 wind) {
		this.wind.copy(wind);
	}
	
	public void setWind(double xPosition, double yPosition, double zPosition) {
		wind.set(xPosition, yPosition, zPosition);
	}
	
	public Vector3 getPosition() {
		return position;
	}
	
	public Vector3 getWind() {
		return wind;
	}
}