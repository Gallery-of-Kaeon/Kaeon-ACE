package kaeon_ace_modules.neo.processes.physics_engine.process;

import kaeon_ace_core.engine.utilities.process.RunningProcess;
import philosophers_stone.PhilosophersStoneUtilities;

public class PhysicsEngine extends RunningProcess {
	
	public PhysicsEngine() {
		
		PhilosophersStoneUtilities.tag(this, "Physics Engine");
		
		thread = "Physics";
	}
	
	public void update() {
		
	}
}