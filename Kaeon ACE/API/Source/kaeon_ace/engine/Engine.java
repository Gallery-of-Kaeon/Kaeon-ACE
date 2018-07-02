package kaeon_ace.engine;

import java.util.ArrayList;

import aether_kaeon_ace.Aether;
import kaeon_ace.ace.ACE;
import kaeon_ace.engine.processes.camera.Camera;
import kaeon_ace.engine.processes.clock.Clock;
import kaeon_ace.engine.processes.core.Core;
import kaeon_ace.engine.processes.frame.Frame;
import kaeon_ace.engine.processes.keyboard.Keyboard;
import kaeon_ace.engine.processes.loader.Loader;
import kaeon_ace.engine.processes.mouse.Mouse;
import kaeon_ace.engine.processes.physics_engine.PhysicsEngine;
import kaeon_ace.engine.processes.renderer.Renderer;
import kaeon_ace.engine.processes.scene.Scene;
import one.Element;
import one.ElementUtilities;
import philosophers_stone.PhilosophersStone;
import philosophers_stone.PhilosophersStoneUtilities;

public class Engine extends PhilosophersStone {
	
	public Element ace;

	public boolean running;
	
	public Engine() {
		
		ace = new Element();
		
		tags.add("Engine");
		
		loadDefaultProcesses();
	}
	
	public Object onCall(ArrayList<Object> packet) {
		
		if(packet.size() != 1)
			return null;
		
		if(!(packet.get(0) instanceof String))
			return null;
		
		if((((String) packet.get(0)).equalsIgnoreCase("Start"))) {
			
			if(!running) {
				
				running = true;
				
				new Thread(
						
					new Runnable() {
						
						public void run() {
							
							while(running) {
								update();
							}
						}
					}
				).start();
			}
		}
		
		if((((String) packet.get(0)).equalsIgnoreCase("Stop")))
			running = false;
		
		if(!(((String) packet.get(0)).equalsIgnoreCase("Get ACE")))
			return null;
		
		return ace;
	}
	
	public void update() {
		
		ArrayList<String> modules = getModules();
		
		for(String module : modules)
			Aether.call(module, 0, this);
	}
	
	public ArrayList<String> getModules() {
		
		ArrayList<String> modules = new ArrayList<String>();
		
		ArrayList<Element> directives =
				ACE.getChildrenByType(ace, "Directive");
		
		for(int i = 0; i < directives.size(); i++) {
			
			Element use = ACE.getField(directives.get(i), "Use");
			
			if(use != null) {
				
				for(int j = 0; j < use.children.size(); j++)
					modules.add(use.children.get(j).content);
				
				ElementUtilities.removeChild(
						ElementUtilities.getChild(
								directives.get(i), "Data"), "Use");
			}
		}
		
		return modules;
	}
	
	public void loadDefaultProcesses() {
		PhilosophersStoneUtilities.publiclyConnectMutually(this, new Camera());
		PhilosophersStoneUtilities.publiclyConnectMutually(this, new Clock());
		PhilosophersStoneUtilities.publiclyConnectMutually(this, new Core());
		PhilosophersStoneUtilities.publiclyConnectMutually(this, new Frame());
		PhilosophersStoneUtilities.publiclyConnectMutually(this, new Keyboard());
		PhilosophersStoneUtilities.publiclyConnectMutually(this, new Loader());
		PhilosophersStoneUtilities.publiclyConnectMutually(this, new Mouse());
		PhilosophersStoneUtilities.publiclyConnectMutually(this, new PhysicsEngine());
		PhilosophersStoneUtilities.publiclyConnectMutually(this, new Renderer());
		PhilosophersStoneUtilities.publiclyConnectMutually(this, new Scene());
	}
}