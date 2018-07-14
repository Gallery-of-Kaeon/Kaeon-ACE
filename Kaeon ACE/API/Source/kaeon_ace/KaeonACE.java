package kaeon_ace;

import developer.Source;
import io.IO;
import kaeon_ace_core.ace.ACE;
import kaeon_ace_core.engine.Engine;
import kaeon_ace_modules.ascent.processes.camera.Camera;
import kaeon_ace_modules.ascent.processes.clock.Clock;
import kaeon_ace_modules.ascent.processes.core.Core;
import kaeon_ace_modules.ascent.processes.frame.Frame;
import kaeon_ace_modules.ascent.processes.keyboard.Keyboard;
import kaeon_ace_modules.ascent.processes.loader.Loader;
import kaeon_ace_modules.ascent.processes.mouse.Mouse;
import kaeon_ace_modules.ascent.processes.physics_engine.PhysicsEngine;
import kaeon_ace_modules.ascent.processes.renderer.Renderer;
import kaeon_ace_modules.ascent.processes.scene.Scene;
import one.Element;
import one_plus.ONEPlus;
import philosophers_stone.PhilosophersStoneUtilities;

public class KaeonACE {

	public static void main(String[] args) {
		
		Element ace = new Element();
		
		for(int i = 0; i < args.length; i++) {
			
			try {
				ace.children.addAll(ONEPlus.parseONEPlus(IO.openAsString(args[i])).children);
			}
			
			catch(Exception excpetion) {
				
			}
		}
		
		Engine engine = new Engine() {
			
			public void loadDefaults() {
				ACE.connectACE(this, new Camera());
				ACE.connectACE(this, new Clock());
				ACE.connectACE(this, new Core());
				ACE.connectACE(this, new Frame());
				ACE.connectACE(this, new Keyboard());
				ACE.connectACE(this, new Loader());
				ACE.connectACE(this, new Mouse());
				ACE.connectACE(this, new PhysicsEngine());
				ACE.connectACE(this, new Renderer());
				ACE.connectACE(this, new Scene());
			}
		};
		
		engine.ace = ace;
		
		Source.onInitialize(engine, args);
		
		PhilosophersStoneUtilities.call(engine, "Start");
		
		Source.onStart(engine, args);
	}
}