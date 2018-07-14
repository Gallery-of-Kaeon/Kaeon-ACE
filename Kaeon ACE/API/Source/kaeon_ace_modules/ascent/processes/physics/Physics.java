package kaeon_ace_modules.ascent.processes.physics;

import java.util.ArrayList;

import kaeon_ace_modules.ascent.processes.frame.Frame;
import kaeon_ace_modules.ascent.processes.loader.Loader;
import kaeon_ace_modules.ascent.processes.physics.utilities.EntityPhysics;
import kaeon_ace_modules.ascent.processes.renderer.Renderer;
import kaeon_ace_modules.ascent.processes.scene.Scene;
import kaeon_ace_modules.ascent.processes.scene.utilities.Entity;
import kaeon_ace_modules.ascent.utilities.geometry.utilities.physics.collider.Collider;
import kaeon_ace_modules.ascent.utilities.geometry.utilities.physics.collider.SphereCollider;
import kaeon_ace_modules.ascent.utilities.geometry.utilities.physics.forces.Force;
import philosophers_stone.PhilosophersStone;
import philosophers_stone.PhilosophersStoneUtilities;

public class Physics extends PhilosophersStone implements Runnable {
	
	private Thread thread;
	private boolean running;
	
	private ArrayList<EntityPhysics> entityPhysics;

	private ArrayList<Collider> colliders;
	private ArrayList<Force> forces;
	
	public Frame frame;
	public Loader loader;
	public Renderer renderer;
	public Scene scene;
	
	public Physics() {
		
		PhilosophersStoneUtilities.tag(this, "Physics Engine");
		
		thread = new Thread(this);
		
		entityPhysics = new ArrayList<EntityPhysics>();

		colliders = new ArrayList<Collider>();
		forces = new ArrayList<Force>();
	}
	
	public Object onCall(ArrayList<Object> packet) {
		
		if(packet.size() == 0)
			return null;
		
		if(!(packet.get(0) instanceof String))
			return null;
		
		if(((String) packet.get(0)).equalsIgnoreCase("Start")) {
			
			frame = (Frame) PhilosophersStoneUtilities.get(this, "Frame").get(0);
			loader = (Loader) PhilosophersStoneUtilities.get(this, "Loader").get(0);
			renderer = (Renderer) PhilosophersStoneUtilities.get(this, "Renderer").get(0);
			scene = (Scene) PhilosophersStoneUtilities.get(this, "Scene").get(0);
			
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
		running = false;
	}
	
	public void run() {
		
		while(running) {
			
			update();
			
			refresh();
			
			frame.refresh();
			renderer.refresh();
			
			int tickInterval = 1;
			
			if(scene != null)
				tickInterval = scene.getTickInterval();
			
			try {
				Thread.sleep(tickInterval);
			}
			
			catch (InterruptedException exception) {
				exception.printStackTrace();
			}
		}
	}
	
	public void update() {

		colliders.addAll(loader.getNewColliders());
		forces.addAll(loader.getNewForces());
		
		for(int i = 0; i < entityPhysics.size(); i++)
			entityPhysics.get(i).processForces(forces);
	}
	
	public void handleCollisions() {
		
		for(int i = 0; i < colliders.size(); i++) {
			
			for(int j = 0; j < colliders.size(); j++) {
				
				if(i != j)
					handleCollision(colliders.get(i), colliders.get(j));
			}
		}
	}
	
	public void handleCollision(Collider alpha, Collider beta) {
		
		if(alpha instanceof SphereCollider) {
			
			if(beta instanceof SphereCollider) {
				
			}
		}
	}
	
	public void refresh() {
		
		for(int i = 0; i < entityPhysics.size(); i++) {
			
			if(entityPhysics.get(i).getEntity().getRunTime().isDestroyed()) {
				entityPhysics.remove(i);
				i--;
			}
		}
		
		for(int i = 0; i < colliders.size(); i++) {
			
			if(colliders.get(i).isDestroyed()) {
				colliders.remove(i);
				i--;
			}
		}
		
		for(int i = 0; i < forces.size(); i++) {
			
			if(forces.get(i).isDestroyed()) {
				forces.remove(i);
				i--;
			}
		}
	}
	
	public void addEntity(Entity entity) {
		entityPhysics.add(new EntityPhysics(entity));
	}
}