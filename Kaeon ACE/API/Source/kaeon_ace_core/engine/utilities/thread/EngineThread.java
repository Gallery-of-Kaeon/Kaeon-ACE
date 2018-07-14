package kaeon_ace_core.engine.utilities.thread;

import java.util.ArrayList;

public class EngineThread implements Runnable {
	
	public String name = "";
	
	public boolean running;
	public ArrayList<ProcessTimer> processes = new ArrayList<ProcessTimer>();
	
	public void run() {
		
		running = true;
		
		while(running) {
			
			long currentTime = System.currentTimeMillis();
			
			for(int i = 0; i < processes.size(); i++) {
				
				if(!processes.get(i).process.running) {
					
					processes.remove(i);
					i--;
					
					continue;
				}
				
				boolean tick =
						processes.get(i).process.ticksPerSecond <= 0 ?
								true :
								currentTime - processes.get(i).recentTime >=
										1000.0 / processes.get(i).process.ticksPerSecond;
				
				if(tick) {
					
					processes.get(i).process.update();
					
					processes.get(i).recentTime = currentTime;
				}
			}
		}
	}
}