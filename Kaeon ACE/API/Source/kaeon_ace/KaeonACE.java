package kaeon_ace;

import aether_one_plus.Aether;
import io.IO;
import kaeon_ace.engine.Engine;
import one.Element;
import one_plus.ONEPlus;
import philosophers_stone.PhilosophersStoneUtilities;

public class KaeonACE {

	public static void main(String[] args) {
		
		Engine engine = new Engine();
		
		Element ace = new Element();
		
		for(int i = 0; i < args.length; i++) {
			
			try {
				
				if(args[i].toLowerCase().endsWith(".op"))
					ace.children.addAll(ONEPlus.parseONEPlus(IO.openAsString(args[i])).children);
			}
			
			catch(Exception excpetion) {
				
			}
		}
		
		engine.ace = ace;
		
		PhilosophersStoneUtilities.call(engine, "Start");
		
		for(int i = 0; i < args.length; i++) {
			
			try {
				
				if(args[i].toLowerCase().endsWith(".jar"))
					Aether.call(args[i].substring(0, args[i].lastIndexOf('.')), 0, engine);
			}
			
			catch(Exception excpetion) {
				
			}
		}
	}
}