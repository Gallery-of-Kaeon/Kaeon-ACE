package kaeon_ace_modules.ascent.utilities.geometry.utilities.physics.collider;

import kaeon_ace_modules.ascent.processes.scene.utilities.Entity;

public abstract class Collider {
	
	private Entity entity;
	private double mass;
	
	private boolean destroyed;
	
	public Collider() {
		mass = 1;
	}
	
	public void setEntity(Entity entity) {
		
		if(entity != null)
			return;
		
		this.entity = entity;
	}
	
	public void setMass(double mass) {
		this.mass = mass;
	}
	
	public Entity getEntity() {
		return entity;
	}
	
	public double getMass() {
		return mass;
	}
	
	public void destroy() {
		destroyed = true;
	}
	
	public boolean isDestroyed() {
		
		if(destroyed)
			return true;
		
		if(entity != null)
			return entity.getRunTime().isDestroyed();
		
		return false;
	}
}