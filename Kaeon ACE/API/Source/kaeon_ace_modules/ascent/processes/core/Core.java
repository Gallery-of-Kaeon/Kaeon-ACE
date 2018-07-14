package kaeon_ace_modules.ascent.processes.core;

import java.util.ArrayList;

import kaeon_ace_core.engine.Engine;
import kaeon_ace_modules.ascent.processes.core.utilities.CoreEntity;
import kaeon_ace_modules.ascent.processes.core.utilities.loader.DirectiveLoader;
import kaeon_ace_modules.ascent.processes.scene.Scene;
import one.Element;
import philosophers_stone.PhilosophersStone;
import philosophers_stone.PhilosophersStoneUtilities;

public class Core extends PhilosophersStone {
	
	public ArrayList<CoreEntity> entities;
	
	public Core() {
		
		PhilosophersStoneUtilities.tag(this, "Core");
		
		entities = new ArrayList<CoreEntity>();
	}
	
	public Object onCall(ArrayList<Object> packet) {
		
		if(packet.size() == 0)
			return null;
		
		if(!(packet.get(0) instanceof String))
			return null;
		
		if(((String) packet.get(0)).equalsIgnoreCase("Start"))
			start();
		
		return null;
	}
	
	public void start() {
		
		Engine engine = (Engine) PhilosophersStoneUtilities.get(this, "Engine").get(0);
		Element ace = engine.ace;
		
		Scene scene = (Scene) PhilosophersStoneUtilities.get(this, "Scene").get(0);
		
		scene.setTicksPerSecond(60);
		
		DirectiveLoader.loadDirectives(engine, ace);
	}
}