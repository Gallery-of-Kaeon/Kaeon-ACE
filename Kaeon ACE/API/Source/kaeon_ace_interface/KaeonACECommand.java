package kaeon_ace_interface;

import java.util.ArrayList;

import fusion.FUSIONUnit;
import one.Element;

public class KaeonACECommand extends FUSIONUnit {
	
	public boolean verify(Element element) {
		return element.content.equalsIgnoreCase("Kaeon ACE");
	}
	
	public Object process(Element element, ArrayList<Object> processed) {
		
		String command = "";
		
		for(int i = 0; i < processed.size(); i++) {
			
			command += "" + processed.get(i);
			
			if(i < processed.size() - 1)
				command += " ";
		}
		
		try {
			Runtime.getRuntime().exec("java -jar \"Kaeon ACE.jar\" " + command);
		}
		
		catch(Exception exception) {
			
		}
		
		return null;
	}
}