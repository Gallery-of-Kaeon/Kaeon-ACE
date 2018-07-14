package kaeon_ace_modules.ascent.utilities.geometry.utilities.animation.skeleton;

import kaeon_ace_modules.ascent.utilities.geometry.mesh.Vertex;

public class Connection {
	
	private Vertex vertex;
	private double weight;
	
	public Connection(Vertex vertex, double weight) {
		this.vertex = vertex;
		this.weight = weight;
	}
	
	public Vertex getVertex() {
		return vertex;
	}
	
	public double getWeight() {
		return weight;
	}
}