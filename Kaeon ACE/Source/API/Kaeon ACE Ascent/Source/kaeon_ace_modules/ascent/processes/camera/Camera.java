package kaeon_ace_modules.ascent.processes.camera;

import java.util.ArrayList;

import kaeon_ace_modules.ascent.utilities.geometry.mesh.Mesh;
import kaeon_ace_modules.ascent.utilities.math.Vector3;
import kaeon_ace_modules.ascent.utilities.math.transform.Transform;
import philosophers_stone.PhilosophersStone;
import philosophers_stone.PhilosophersStoneUtilities;

public class Camera extends PhilosophersStone {
	
	private ArrayList<Transform> transforms3D;
	private ArrayList<Transform> transforms2D;
	
	private double fov = 60;
	private double nearest = .1;
	private double farthest = 10000;
	
	public Camera() {
		
		PhilosophersStoneUtilities.tag(this, "Camera");
		
		transforms3D = new ArrayList<Transform>();
		transforms2D = new ArrayList<Transform>();
		
		fov = 60;
		nearest = .1;
		farthest = 100000;
	}
	
	public void transform(Transform transform) {
		
		if(transform.getMode() == null) {
			
			transforms3D.add(transform);
			
			return;
		}
		
		if(transform.getMode().equals(Mesh.RENDER_MODE_3D))
			transforms3D.add(transform);
		
		else
			transforms2D.add(transform);
	}
	
	public ArrayList<Transform> getTransforms() {
		
		ArrayList<Transform> transforms = new ArrayList<Transform>();
		
		transforms.addAll(transforms3D);
		transforms.addAll(transforms2D);
		
		return transforms;
	}
	
	public ArrayList<Transform> getTransforms3D() {
		return transforms3D;
	}
	
	public ArrayList<Transform> getTransforms2D() {
		return transforms2D;
	}
	
	public Vector3 getPosition() {
		return new Vector3();
	}
	
	public void setFOV(double fov) {
		this.fov = fov;
	}
	
	public void setNearest(double nearest) {
		this.nearest = nearest;
	}
	
	public void setFarthest(double farthest) {
		this.farthest = farthest;
	}
	
	public double getFOV() {
		return fov;
	}
	
	public double getNearest() {
		return nearest;
	}
	
	public double getFarthest() {
		return farthest;
	}
	
	public void refresh() {
		
		for(int i = 0; i < transforms3D.size(); i++) {
			
			if(transforms3D.get(i).isDestroyed()) {
				transforms3D.remove(i);
				i--;
			}
		}
		
		for(int i = 0; i < transforms2D.size(); i++) {
			
			if(transforms2D.get(i).isDestroyed()) {
				transforms2D.remove(i);
				i--;
			}
		}
	}
	
	public void reset() {
		transforms3D = new ArrayList<Transform>();
		transforms2D = new ArrayList<Transform>();
	}
}