package kaeon_ace_core.engine.utilities.process;

import java.util.ArrayList;

import javax.lang.model.element.Element;

public class BootstrapProcess extends RunningProcess {
	
	public ACEProcess process;
	
	public ArrayList<Element> removeQueue;
	
	public BootstrapProcess() {
		
		tags.add("Bootstrap Process");
		
		thread = "Bootstrap";
		
		removeQueue = new ArrayList<Element>();
	}
	
	public Object onCall(ArrayList<Object> packet) {
		
		if(packet.size() == 0)
			return null;
		
		if(!(packet.get(0) instanceof String))
			return null;
		
		super.onCall(packet);
		
		if(((String) packet.get(0)).equalsIgnoreCase("ACE Remove"))
			removeQueue.add((Element) packet.get(0));
		
		return null;
	}
	
	public void onUpdate() {
		
		if(process == null)
			return;
	}
	
	public void remove() {
		
	}
}