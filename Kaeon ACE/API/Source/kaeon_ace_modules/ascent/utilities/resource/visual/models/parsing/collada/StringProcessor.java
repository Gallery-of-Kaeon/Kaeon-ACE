package kaeon_ace_modules.ascent.utilities.resource.visual.models.parsing.collada;

import java.util.ArrayList;

public class StringProcessor {

	
	public static ArrayList<Integer> getIntArray(String array) {
		
		ArrayList<Integer> intArray = new ArrayList<Integer>();
		
		int start = 0;
		int end = array.indexOf(' ', start);
		
		while(end != -1) {
			
			intArray.add(Integer.parseInt(array.substring(start, end)));
			
			start = end + 1;
			end = array.indexOf(' ', start);
		}
		
		intArray.add(Integer.parseInt(array.substring(start)));
		
		return intArray;
	}
	
	public static ArrayList<Float> getFloatArray(String array) {
		
		ArrayList<Float> floatArray = new ArrayList<Float>();
		
		int start = 0;
		int end = array.indexOf(' ', start);
		
		while(end != -1) {
			
			floatArray.add(Float.parseFloat(array.substring(start, end)));
			
			start = end + 1;
			end = array.indexOf(' ', start);
		}
		
		floatArray.add(Float.parseFloat(array.substring(start)));
		
		return floatArray;
	}
}
