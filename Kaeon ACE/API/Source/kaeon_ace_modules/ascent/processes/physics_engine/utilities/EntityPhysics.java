package kaeon_ace_modules.ascent.processes.physics_engine.utilities;

import java.util.ArrayList;

import kaeon_ace_modules.ascent.processes.scene.utilities.Entity;
import kaeon_ace_modules.ascent.utilities.geometry.utilities.physics.forces.Force;
import kaeon_ace_modules.ascent.utilities.geometry.utilities.physics.forces.Gravity;
import kaeon_ace_modules.ascent.utilities.geometry.utilities.physics.forces.Wind;
import kaeon_ace_modules.ascent.utilities.math.Vector3;
import kaeon_ace_modules.ascent.utilities.math.transform.Translation;

public class EntityPhysics {
	
	private Entity entity;
	
	public EntityPhysics(Entity entity) {
		this.entity = entity;
	}
	
	public Entity getEntity() {
		return entity;
	}
	
	public void processForces(ArrayList<Force> forces) {
		
		Translation position = entity.getBody().getPhysics().getPosition();
		Vector3 velocity = entity.getBody().getPhysics().getVelocity();
		
		position.increment(velocity);
		
		for(int i = 0; i < forces.size(); i++) {
			
			if(forces.get(i) instanceof Gravity) {
				velocity.increment(((Gravity) forces.get(i)).getGravity());
			}
			
			if(forces.get(i) instanceof Wind) {
				
				Vector3 offset = new Vector3(position.getTransform());
				offset.decrement(((Wind) forces.get(i)).getPosition());
				
				double accelerationMultiplier =
						offset.getMagnitude() / ((Wind) forces.get(i)).getWind().getMagnitude();
				
				if(accelerationMultiplier > 2)
					accelerationMultiplier = 2;
				
				Vector3 acceleration = new Vector3(((Wind) forces.get(i)).getWind());
				acceleration.setMagnitude(acceleration.getMagnitude() * accelerationMultiplier);
				
				velocity.increment(acceleration);
			}
		}
	}
}