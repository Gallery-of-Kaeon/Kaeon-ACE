package kaeon_ace.versions;

import kaeon_ace_core.ace.ACE;
import kaeon_ace_core.engine.Engine;
import kaeon_ace_modules.neo.processes.physics_engine.process.PhysicsEngine;
import kaeon_ace_modules.neo.processes.rendering_engine.process.RenderingEngine;
import kaeon_ace_modules.neo.processes.script_engine.process.ScriptEngine;

public class Neo {
	
	public static void loadNEO(Engine engine) {
		ACE.connectACE(engine, new PhysicsEngine());
		ACE.connectACE(engine, new RenderingEngine());
		ACE.connectACE(engine, new ScriptEngine());
	}
}