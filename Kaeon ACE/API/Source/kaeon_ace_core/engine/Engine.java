package kaeon_ace_core.engine;

import java.util.ArrayList;

import kaeon_ace_core.ace.ACE;
import kaeon_ace_core.engine.utilities.object.ACEObject;
import kaeon_ace_core.engine.utilities.process.RunningProcess;
import kaeon_ace_core.engine.utilities.thread.EngineThread;
import kaeon_ace_core.engine.utilities.thread.ProcessTimer;
import kaeon_ace_core.engine.utilities.use_directive.UseDirective;
import one.Element;
import philosophers_stone.PhilosophersStone;
import philosophers_stone.PhilosophersStoneUtilities;

public class Engine extends PhilosophersStone {
	
	public Element ace;
	public ArrayList<ACEObject> objects;

	public boolean running;
	public ArrayList<EngineThread> threads;
	
	public Engine() {
		
		tags.add("Engine");
		
		ace = new Element();
		objects = new ArrayList<ACEObject>();
		
		threads = new ArrayList<EngineThread>();
		
		ACE.connectACE(this, new UseDirective());
		
		loadDefaults();
	}
	
	public Object onCall(ArrayList<Object> packet) {
		
		if(packet.size() != 1)
			return null;
		
		if(!(packet.get(0) instanceof String))
			return null;
		
		if((((String) packet.get(0)).equalsIgnoreCase("Start"))) {
			
			running = true;
			
			ArrayList<PhilosophersStone> runningCoreProcesses =
					PhilosophersStoneUtilities.get(this, "Running Process");
			
			for(int i = 0; i < runningCoreProcesses.size(); i++) {
				
				RunningProcess process = (RunningProcess) runningCoreProcesses.get(i);
				process.running = true;
				
				ProcessTimer timer = new ProcessTimer();
				timer.process = process;
				
				boolean exists = false;
				
				for(int j = 0; j < threads.size(); j++) {
					
					if(threads.get(j).name.trim().equalsIgnoreCase(process.thread.trim())) {
						
						threads.get(j).processes.add(timer);
						exists = true;
						
						break;
					}
				}
				
				if(!exists) {
					
					EngineThread thread = new EngineThread();
					thread.name = process.thread;
					
					thread.processes.add(timer);
					
					threads.add(thread);
				}
			}
			
			for(int i = 0; i < threads.size(); i++)
				new Thread(threads.get(i)).start();
		}
		
		if((((String) packet.get(0)).equalsIgnoreCase("Stop"))) {
			
			running = false;
			
			for(int i = 0; i < threads.size(); i++)
				threads.get(i).running = false;
		}
		
		return null;
	}
	
	public void loadDefaults() {
		
	}
}