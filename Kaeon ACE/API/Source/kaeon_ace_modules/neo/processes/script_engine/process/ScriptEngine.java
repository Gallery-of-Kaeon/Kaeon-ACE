package kaeon_ace_modules.neo.processes.script_engine.process;

import kaeon_ace_core.engine.utilities.process.RunningProcess;
import philosophers_stone.PhilosophersStoneUtilities;

public class ScriptEngine extends RunningProcess {
	
	public ScriptEngine() {
		
		PhilosophersStoneUtilities.tag(this, "Script Processor");
		
		thread = "Script";
	}
	
	public void update() {
		
	}
}