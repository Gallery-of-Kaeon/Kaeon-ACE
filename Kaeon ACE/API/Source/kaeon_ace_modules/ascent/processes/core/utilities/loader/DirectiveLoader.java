package kaeon_ace_modules.ascent.processes.core.utilities.loader;

import java.util.ArrayList;

import kaeon_ace_core.ace.ACE;
import kaeon_ace_core.engine.Engine;
import kaeon_ace_modules.ascent.processes.frame.Frame;
import kaeon_ace_modules.ascent.processes.keyboard.Keyboard;
import kaeon_ace_modules.ascent.processes.scene.Scene;
import kaeon_ace_modules.ascent.processes.scene.utilities.script.Script;
import kaeon_ace_modules.ascent.utilities.user.GeneralEntity;
import one.Element;
import philosophers_stone.PhilosophersStoneUtilities;

public class DirectiveLoader {
	
	public static void loadDirectives(Engine engine, Element ace) {
		
		ArrayList<Element> directives = ACE.getChildrenByType(ace, "Directive");
		
		for(Element directive : directives) {
			cursor(engine, directive);
			fullscreen(engine, directive);
		}
	}
	
	public static void cursor(Engine engine, Element directive) {
		
		Frame frame = (Frame) PhilosophersStoneUtilities.get(engine, "Frame").get(0);

		if(ACE.hasField(directive, "Cursor")) {
			
			if(ACE.getField(directive, "Cursor").children.size() > 0) {
				
				if(ACE.getField(directive, "Cursor").children.get(0).content.equalsIgnoreCase("None"))
					frame.setCursorToBlank();
			}
		}
	}
	
	public static void fullscreen(Engine engine, Element directive) {
		
		Frame frame = (Frame) PhilosophersStoneUtilities.get(engine, "Frame").get(0);
		Scene scene = (Scene) PhilosophersStoneUtilities.get(engine, "Scene").get(0);
		
		if(ACE.hasField(directive, "Fullscreen")) {
			
			frame.setFullScreen();
			
			scene.addEntity(
					
				new GeneralEntity(
						
					new Script() {
		
						public void onUpdate() {

							Keyboard keyboard =
									(Keyboard) PhilosophersStoneUtilities.get(
											getEntity().getEngine(), "Keyboard").get(0);
							
							if(keyboard.isKeyPressed(Keyboard.KEY_ESCAPE))
								System.exit(0);
						}
					}
				)
			);
		}
	}
}