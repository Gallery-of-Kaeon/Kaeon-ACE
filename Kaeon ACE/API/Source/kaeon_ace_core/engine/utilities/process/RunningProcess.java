package kaeon_ace_core.engine.utilities.process;

public class RunningProcess extends ACEProcess {
	
	public boolean running;
	
	public double ticksPerSecond;
	public String thread;
	
	public RunningProcess() {
		
		tags.add("Running Process");
		
		ticksPerSecond = 60;
		thread = "Default";
	}
	
	public void update() {
		
	}
}