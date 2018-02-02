package kaeon_ace.engine.utilities.geometry.mesh;

import java.util.ArrayList;

import kaeon_ace.engine.processes.camera.Camera;
import kaeon_ace.engine.utilities.geometry.utilities.visual.Color;
import kaeon_ace.engine.utilities.math.Vector3;
import kaeon_ace.engine.utilities.math.transform.Transform;
import kaeon_ace.engine.utilities.math.transform.Translation;

public class Vertex {

	private Mesh mesh;
	
	private Vector3 position;
	private Vector3 normal;
	
	private Vector3 transformation;
	
	private Color color;
	
	private boolean destroyed;
	
	public Vertex() {
		this(new Vector3());
	}
	
	public Vertex(double x, double y, double z) {
		this(new Vector3(x, y, z));
	}
	
	public Vertex(Vector3 position) {
		
		this.position = position;
		this.transformation = new Vector3();
		
		color = new Color();
	}
	
	public Vertex(Vertex vertex) {
		copy(vertex);
	}
	
	public void copy(Vertex vertex) {
		
		position = new Vector3(vertex.getPosition());
		normal = new Vector3(vertex.getNormal());
		
		transformation = vertex.getTransformation();
		
		color = new Color(vertex.getColor());
	}
	
	public void initialize(Mesh mesh) {
		
		if(this.mesh != null)
			return;
		
		this.mesh = mesh;
	}
	
	public void setPositionAs(Vector3 position) {
		this.position = position;
	}
	
	public void setPosition(Vector3 position) {
		this.position.copy(position);
	}
	
	public void setPosition(double x, double y, double z) {
		position.set(x, y, z);
	}
	
	public void setNormalAs(Vector3 normal) {
		this.normal = normal;
	}
	
	public void setNormal(Vector3 normal) {
		
		if(this.normal == null)
			this.normal = new Vector3();
		
		this.normal.copy(normal);
	}
	
	public void setNormal(double x, double y, double z) {
		
		if(normal == null)
			normal = new Vector3();
		
		normal.set(x, y, z);
	}
	
	public void setTransformationAs(Vector3 transformation) {
		this.transformation = transformation;
	}
	
	public void setTransformation(Vector3 transformation) {
		this.transformation.copy(transformation);
	}
	
	public void setTransformation(double x, double y, double z) {
		transformation.set(x, y, z);
	}
	
	public void transform(Vector3 transform) {
		transformation.increment(transform);
	}
	
	public void transform(double x, double y, double z) {
		transformation.increment(x, y, z);
	}
	
	public void moveTo(Vector3 transformation) {
		
		this.transformation.set(
				transformation.getX() - position.getX(),
				transformation.getY() - position.getY(),
				transformation.getZ() - position.getZ());
	}
	
	public void moveTo(double x, double y, double z) {
		
		transformation.set(
				x - position.getX(),
				y - position.getY(),
				z - position.getZ());
	}
	
	public void setColorAs(Color color) {
		this.color = color;
	}
	
	public void setColor(Color color) {
		
		if(this.color == null)
			this.color = new Color();
		
		this.color.copy(color);
	}
	
	public void setColor(double red, double green, double blue) {
		
		if(color == null)
			color = new Color();
		
		color.setColor(red, green, blue);
	}
	
	public void setColor(double red, double green, double blue, double alpha) {
		
		if(color == null)
			color = new Color();
		
		color.setColor(red, green, blue, alpha);
	}
	
	public Mesh getMesh() {
		return mesh;
	}
	
	public Vector3 getPosition() {
		return position;
	}
	
	public Vector3 getNormal() {
		return normal;
	}
	
	public Vector3 getTransformation() {
		return transformation;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void destroy() {
		destroyed = true;
	}
	
	public boolean isDestroyed() {
		return destroyed;
	}
	
	public double getCameraOffset(Camera camera, Mesh mesh) {
		
		Vector3 offset = new Vector3(position);
		
		ArrayList<Transform> transforms = mesh.getTransforms();
		
		for(int i = 0; i < transforms.size(); i++) {
			
			if(transforms.get(i) instanceof Translation)
				offset.increment(transforms.get(i).getTransform());
		}
		
		offset.decrement(camera.getPosition());
		
		return offset.getMagnitude();
	}
}