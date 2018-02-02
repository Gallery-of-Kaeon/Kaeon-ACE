package kaeon_ace.engine.utilities.geometry.utilities.animation.skeleton;

import java.util.ArrayList;

import kaeon_ace.engine.utilities.geometry.mesh.Vertex;
import kaeon_ace.engine.utilities.math.Vector3;
import kaeon_ace.engine.utilities.math.transform.Rotation;
import kaeon_ace.engine.utilities.math.transform.Scale;
import kaeon_ace.engine.utilities.math.transform.ScaleTo;
import kaeon_ace.engine.utilities.math.transform.Transform;
import kaeon_ace.engine.utilities.math.transform.Translation;

public class Bone {
	
	private boolean isRoot;
	
	private ArrayList<Connection> connections;
	private ArrayList<Bone> children;
	
	private Vector3 dimensions;
	private ArrayList<Transform> transforms;
	
	private boolean moved;
	
	public Bone(Vector3 dimensions) {
		
		connections = new ArrayList<Connection>();
		children = new ArrayList<Bone>();
		
		this.dimensions = dimensions;
		transforms = new ArrayList<Transform>();
	}
	
	public void setAsRoot() {
		isRoot = true;
	}
	
	public void connect(Vertex vertex, double weight) {
		connections.add(new Connection(vertex, weight));
	}
	
	public void attach(Bone bone) {
		children.add(bone);
	}
	
	public void transform(Transform transform) {
		transforms.add(transform);
	}
	
	public void moveTo(int transform, Vector3 moveTo) {
		
		transforms.get(transform).setTransform(moveTo);
		
		moved = true;
	}
	
	public void move(int transform, Vector3 move) {
		
		transforms.get(transform).increment(move);
		
		moved = true;
	}
	
	public void animate(Origin origin) {
		
		if(!isMoved())
			return;
		
		for(int i = 0; i < transforms.size(); i++) {
			
			transformOrigin(transforms.get(i), origin);
			
			for(int j = 0; j < connections.size(); j++)
				transformConnection(transforms.get(i), origin, connections.get(j));
		}
		
		translateOrigin(dimensions, origin);
		
		moved = false;
		
		for(int i = 0; i < children.size(); i++)
			children.get(i).animate(new Origin(origin));
	}
	
	public boolean isMoved() {
		
		if(moved)
			return true;
		
		for(int i = 0; i < children.size(); i++) {
			
			if(children.get(i).isMoved())
				return true;
		}
		
		return false;
	}
	
	public ArrayList<Bone> getChildren() {
		return children;
	}
	
	private void transformOrigin(Transform transform, Origin origin) {
		
		if(transform instanceof Translation)
			translateOrigin(transform.getTransform(), origin);
		
		if(transform instanceof Rotation)
			rotateOrigin(transform.getTransform(), origin);
		
		if(transform instanceof Scale)
			scaleOrigin(transform.getTransform(), origin);
		
		if(transform instanceof ScaleTo)
			scaleOriginTo(transform.getTransform(), origin);
	}
	
	private void translateOrigin(Vector3 translate, Origin origin) {
		origin.move(translate);
	}
	
	private void rotateOrigin(Vector3 rotate, Origin origin) {
		origin.rotate(rotate);
	}
	
	private void scaleOrigin(Vector3 scale, Origin origin) {
		origin.scale(scale);
	}
	
	private void scaleOriginTo(Vector3 scale, Origin origin) {
		origin.setScale(scale);
	}
	
	private void transformConnection(Transform transform, Origin origin, Connection connection) {
		
		if(transform instanceof Translation)
			translateConnection(transform.getTransform(), origin, connection);
		
		if(transform instanceof Rotation)
			rotateConnection(transform.getTransform(), origin, connection);
		
		if(transform instanceof Scale)
			scaleConnection(transform.getTransform(), origin, connection);
		
		if(transform instanceof ScaleTo)
			scaleConnectionTo(transform.getTransform(), origin, connection);
	}
	
	private void translateConnection(Vector3 translate, Origin origin, Connection connection) {
		
		if(isRoot) {
			
			connection.getVertex().getTransformation().set(
				translate.getX() * connection.getWeight(),
				translate.getY() * connection.getWeight(),
				translate.getZ() * connection.getWeight());
		}
		
		else {
			
			connection.getVertex().getTransformation().increment(
				translate.getX() * connection.getWeight(),
				translate.getY() * connection.getWeight(),
				translate.getZ() * connection.getWeight());
		}
	}
	
	private void rotateConnection(Vector3 rotate, Origin origin, Connection connection) {
		
	}
	
	private void scaleConnection(Vector3 rotate, Origin origin, Connection connection) {
		
	}
	
	private void scaleConnectionTo(Vector3 rotate, Origin origin, Connection connection) {
		
	}
}