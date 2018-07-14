package kaeon_ace_modules.ascent.processes.scene;

import java.util.ArrayList;

import kaeon_ace_core.engine.Engine;
import kaeon_ace_modules.ascent.processes.camera.Camera;
import kaeon_ace_modules.ascent.processes.clock.Clock;
import kaeon_ace_modules.ascent.processes.scene.utilities.Entity;
import philosophers_stone.PhilosophersStone;
import philosophers_stone.PhilosophersStoneUtilities;

public class Scene extends PhilosophersStone implements Runnable {
	
	private Thread thread;
	private boolean running;

	private int tickInterval;
	private boolean updating;
	
	private ArrayList<Entity> entities;
	
	public Engine engine;
	public Camera camera;
	public Clock clock;
	
	public Scene() {
		
		PhilosophersStoneUtilities.tag(this, "Scene");
		
		thread = new Thread(this);
		tickInterval = 1;
		
		entities = new ArrayList<Entity>();
	}
	
	public Object onCall(ArrayList<Object> packet) {
		
		if(packet.size() == 0)
			return null;
		
		if(!(packet.get(0) instanceof String))
			return null;
		
		if(((String) packet.get(0)).equalsIgnoreCase("Start")) {
			
			engine = (Engine) PhilosophersStoneUtilities.get(this, "Engine").get(0);
			camera = (Camera) PhilosophersStoneUtilities.get(this, "Camera").get(0);
			clock = (Clock) PhilosophersStoneUtilities.get(this, "Clock").get(0);
			
			start();
		}
		
		if(((String) packet.get(0)).equalsIgnoreCase("Stop"))
			stop();
		
		return null;
	}
	
	public void start() {
		
		running = true;
		
		thread.start();
	}
	
	public void stop() {
		
		for(int i = 0; i < entities.size(); i++)
			entities.get(i).getRunTime().close();
		
		running = false;
	}
	
	public void run() {
		
		while(running) {
			
			try {
				
				updating = true;
				
				update();
				camera.refresh();
				
				updating = false;
				
				clock.incrementTickCount();
				
				Thread.sleep(tickInterval);
			}
			
			catch (Exception exception) {
				exception.printStackTrace();
			}
		}
	}
	
	public void update() {
		
		for(int i = 0; i < entities.size(); i++) {
			
			if(!entities.get(i).getRunTime().hasStarted()) {
				entities.get(i).initialize(engine);
				entities.get(i).getRunTime().start();
			}
			
			entities.get(i).getRunTime().update();
		}
		
		for(int i = 0; i < entities.size(); i++) {
			
			if(entities.get(i).getRunTime().isDestroyed()) {
				entities.remove(i);
				i--;
			}
		}
	}
	
	public boolean isUpdating() {
		return updating;
	}
	
	public void setTickInterval(int tickInterval) {
		this.tickInterval = tickInterval;
	}
	
	public void setTicksPerSecond(int ticksPerSecond) {
		
		if(ticksPerSecond <= 1) {
			tickInterval = 1000;
			return;
		}
		
		if(ticksPerSecond >= 1000) {
			tickInterval = 1;
			return;
		}
		
		tickInterval = 1000 / ticksPerSecond;
	}
	
	public int getTickInterval() {
		return tickInterval;
	}
	
	public int getTicksPerSecond() {
		return 1000 / tickInterval;
	}
	
	public void addEntity(Entity entity) {
		
		if(entity.getRunTime().hasStarted())
			return;
		
		entities.add(entity);
	}
	
	public void addEntities(ArrayList<Entity> entities) {
		
		for(int i = 0; i < entities.size(); i++)
			addEntity(entities.get(i));
	}
	
	public ArrayList<Entity> getEntities() {
		return entities;
	}
}