package kaeon_ace_core.engine.utilities.process;

import java.util.ArrayList;

public class RunningProcess extends ACEProcess {

	public boolean started;
	public boolean running;
	
	public double ticksPerSecond;
	public String thread;
	
	public RunningProcess() {
		
		tags.add("Running Process");
		
		ticksPerSecond = 60;
		thread = "Default";
	}
	
	public Object onCall(ArrayList<Object> packet) {
		
		if(packet.size() == 0)
			return null;
		
		if(!(packet.get(0) instanceof String))
			return null;
		
		super.onCall(packet);
		
		if(((String) packet.get(0)).equalsIgnoreCase("Start"))
			started = true;
		
		return null;
	}
	
	public void onUpdate() {
		
	}
}