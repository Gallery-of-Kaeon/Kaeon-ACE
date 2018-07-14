package kaeon_ace_modules.neo.processes.script_processor.process;

import kaeon_ace_core.engine.utilities.process.RunningProcess;
import philosophers_stone.PhilosophersStoneUtilities;

public class ScriptProcessor extends RunningProcess {
	
	public ScriptProcessor() {
		
		PhilosophersStoneUtilities.tag(this, "Script Processor");
		
		thread = "Script";
	}
	
	public void update() {
		
	}
}