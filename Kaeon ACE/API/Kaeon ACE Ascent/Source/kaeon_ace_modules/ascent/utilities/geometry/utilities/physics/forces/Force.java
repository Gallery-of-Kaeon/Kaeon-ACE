package kaeon_ace_modules.ascent.utilities.geometry.utilities.physics.forces;

import kaeon_ace_modules.ascent.processes.scene.utilities.Entity;

public abstract class Force {
	
	private Entity entity;
	
	private boolean destroyed;
	
	public Force() {
		
	}
	
	public void setEntity(Entity entity) {
		
		if(entity != null)
			return;
		
		this.entity = entity;
	}
	
	public Entity getEntity() {
		return entity;
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