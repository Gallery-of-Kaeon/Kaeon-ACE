package kaeon_ace;

import io.IO;
import kaeon_ace_core.engine.Engine;
import kaeon_ace_developer.Source;
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
		
		Engine engine = new Engine();
		engine.ace = ace;
		
		Source.onInitialize(engine, args);
		
		PhilosophersStoneUtilities.call(engine, "Start");
		
		Source.onStart(engine, args);
	}
}