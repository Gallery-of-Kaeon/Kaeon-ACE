package kaeon_ace_modules.ascent.utilities.resource.visual.models.parsing.collada;

import java.util.ArrayList;

import xml.XMLElement;

public class Source {
	
	private String id;
	
	private ArrayList<Float> floatArray;
	private int stride;
	
	public Source(XMLElement source) {
		
		id = source.getAttribute("id").getContent();
		
		stride =
				Integer.parseInt(
						source.
						getElement("technique_common").
						getElement("accessor").
						getAttribute("stride").
						getContent());
		
		floatArray =
				StringProcessor.getFloatArray(
						source.
						getElement("float_array").
						getAllContent());
	}
	
	public void setID(String id) {
		this.id = id;
	}
	
	public String getID() {
		return id;
	}
	
	public ArrayList<Float> getUnit(int index) {
		
		ArrayList<Float> unit = new ArrayList<Float>();
		
		for(int i = index * stride; i < (index + 1) * stride; i++)
			unit.add(floatArray.get(i));
		
		return unit;
	}
	
	public int getNumUnits() {
		return floatArray.size() / stride;
	}
}