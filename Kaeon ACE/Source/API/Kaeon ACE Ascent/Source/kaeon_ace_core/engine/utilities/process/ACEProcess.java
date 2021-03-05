package kaeon_ace_core.engine.utilities.process;

import java.util.ArrayList;

import kaeon_ace_core.engine.Engine;
import philosophers_stone.PhilosophersStone;
import philosophers_stone.PhilosophersStoneUtilities;

public class ACEProcess extends PhilosophersStone {

	public Engine engine;
	
	public ACEProcess() {
		tags.add("Process");
	}
	
	public Object onCall(ArrayList<Object> packet) {
		
		if(packet.size() == 0)
			return null;
		
		if(!(packet.get(0) instanceof String))
			return null;
		
		if((((String) packet.get(0)).equalsIgnoreCase("Start")))
			engine = (Engine) PhilosophersStoneUtilities.get(this, "Engine").get(0);
		
		if(((String) packet.get(0)).equalsIgnoreCase("Start"))
			onStart();
		
		if(((String) packet.get(0)).equalsIgnoreCase("Stop"))
			onStop();
		
		return null;
	}
	
	public void onStart() {
		
	}
	
	public void onStop() {
		
	}
}