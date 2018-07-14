package kaeon_ace_modules.ascent.processes.scene.utilities.body.physics;

import java.util.ArrayList;

import kaeon_ace_modules.ascent.processes.loader.Loader;
import kaeon_ace_modules.ascent.processes.scene.utilities.Entity;
import kaeon_ace_modules.ascent.processes.scene.utilities.body.Body;
import kaeon_ace_modules.ascent.utilities.geometry.utilities.physics.collider.Collider;
import kaeon_ace_modules.ascent.utilities.geometry.utilities.physics.forces.Force;
import kaeon_ace_modules.ascent.utilities.math.Vector3;
import kaeon_ace_modules.ascent.utilities.math.transform.Translation;
import philosophers_stone.PhilosophersStoneUtilities;

public class Physics {
	
	private Entity entity;
	
	private ArrayList<Collider> colliders;
	private ArrayList<Force> forces;
	
	private Translation position;
	private Vector3 velocity;
	
	public Loader loader;
	
	public Physics(Body body) {
		
		this.entity = body.getEntity();
		
		loader = (Loader) PhilosophersStoneUtilities.get(entity.getEngine(), "Loader").get(0);
		
		colliders = new ArrayList<Collider>();
		forces = new ArrayList<Force>();
		
		position = new Translation();
		velocity = new Vector3();
		
		body.transform(position);
	}
	
	public void addForce(Force force) {
		
		if(force.getEntity() != null)
			return;
		
		force.setEntity(entity);
		
		forces.add(force);
		loader.addNewForce(force);
	}
	
	public void addCollider(Collider collider) {
		
		if(collider.getEntity() != null)
			return;
		
		collider.setEntity(entity);
		
		colliders.add(collider);
		loader.addNewCollider(collider);
	}
	
	public ArrayList<Collider> getColliders() {
		return colliders;
	}
	
	public ArrayList<Force> getForces() {
		return forces;
	}
	
	public Translation getPosition() {
		return position;
	}
	
	public Vector3 getVelocity() {
		return velocity;
	}
}