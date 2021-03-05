package kaeon_ace_core.engine.utilities.utilities;

import java.util.ArrayList;

import kaeon_ace_core.engine.Engine;
import one.Element;
import philosophers_stone.PhilosophersStoneUtilities;

public class EngineUtilities {
	
	public void removeACEElement(Engine engine, ArrayList<Integer> indices) {
		
		try {
			
			Element current = engine.ace;
			
			for(int i = 0; i < indices.size() - 1; i++)
				current = current.children.get(indices.get(i));
			
			PhilosophersStoneUtilities.call(
					engine,
					"ACE Remove",
					current.children.remove((int) indices.get(indices.size() - 1)));
		}
		
		catch(Exception exception) {
			
		}
	}
	
	public static void addACEElement(Engine engine, ArrayList<Integer> indices, Element element) {
		
		try {
			// STUB
		}
		
		catch(Exception exception) {
			
		}
	}
	
	public static void setACEElement(Engine engine, ArrayList<Integer> indices, Element element) {
		
		try {
			// STUB
		}
		
		catch(Exception exception) {
			
		}
	}
	
	public static void setACEElementContent(Engine engine, ArrayList<Integer> indices, String content) {
		
		try {
			// STUB
		}
		
		catch(Exception exception) {
			
		}
	}
}