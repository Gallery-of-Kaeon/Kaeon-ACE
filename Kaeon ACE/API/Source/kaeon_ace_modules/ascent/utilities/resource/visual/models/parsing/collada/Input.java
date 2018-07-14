package kaeon_ace_modules.ascent.utilities.resource.visual.models.parsing.collada;

import java.util.ArrayList;

import xml.XMLElement;

public class Input implements Comparable<Input> {
	
	private Source source;
	private String semantic;
	
	private int offset;
	
	public Input(XMLElement input, ArrayList<Source> sources) {
		
		String sourceID = input.getAttribute("source").getContent().substring(1);
		
		for(int i = 0; i < sources.size(); i++) {
			
			if(sources.get(i).getID().equals(sourceID)) {
				source = sources.get(i);
				break;
			}
		}
		
		semantic = input.getAttribute("semantic").getContent();
		
		offset = Integer.parseInt(input.getAttribute("offset").getContent());
	}
	
	public Source getSource() {
		return source;
	}
	
	public String getSemantic() {
		return semantic;
	}
	
	public int getOffset() {
		return offset;
	}
	
	public int compareTo(Input input) {
		return 0;
	}
}