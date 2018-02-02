package kaeon_ace.ace;

import java.util.ArrayList;

import one.Element;
import one.ElementUtilities;

public class ACE {
	
	public static String getName(Element element) {
		return element.content;
	}
	
	public static String getType(Element element) {
		
		try {
			return ElementUtilities.getChild(element, "Type").children.get(0).content;
		}
		
		catch(Exception exception) {
			return "";
		}
	}
	
	public static ArrayList<Element> getChildren(Element element) {
		
		if(element.parent == null && element.content == null)
			return element.children;
		
		try {
			return ElementUtilities.getChild(element, "Children").children;
		}
		
		catch(Exception exception) {
			return new ArrayList<Element>();
		}
	}
	
	public static ArrayList<Element> getChildren(Element element, String name) {
		
		ArrayList<Element> requestedChildren = new ArrayList<Element>();
		ArrayList<Element> children = getChildren(element);
		
		for(int i = 0; i < children.size(); i++) {
			
			if(children.get(i).content.equalsIgnoreCase(name))
				requestedChildren.add(children.get(i));
		}
		
		return requestedChildren;
	}
	
	public static Element getChild(Element element, String name) {
		
		ArrayList<Element> children = getChildren(element);
		
		for(int i = 0; i < children.size(); i++) {
			
			if(children.get(i).content.equalsIgnoreCase(name))
				return children.get(i);
		}
		
		return null;
	}
	
	public static ArrayList<Element> getChildrenByType(Element element, String type) {
		
		ArrayList<Element> requestedChildren = new ArrayList<Element>();
		ArrayList<Element> children = getChildren(element);
		
		for(int i = 0; i < children.size(); i++) {
			
			if(getType(children.get(i)).equalsIgnoreCase(type))
				requestedChildren.add(children.get(i));
		}
		
		return requestedChildren;
	}
	
	public static Element getChildByType(Element element, String type) {
		
		ArrayList<Element> children = getChildren(element);
		
		for(int i = 0; i < children.size(); i++) {
			
			if(getType(children.get(i)).equalsIgnoreCase(type))
				return children.get(i);
		}
		
		return null;
	}
	
	public static ArrayList<Element> getFields(Element element) {
		
		try {
			return ElementUtilities.getChild(element, "Data").children;
		}
		
		catch(Exception exception) {
			return new ArrayList<Element>();
		}
	}
	
	public static ArrayList<Element> getFields(Element element, String name) {
		
		ArrayList<Element> requestedFields = new ArrayList<Element>();
		ArrayList<Element> fields = getFields(element);
		
		for(int i = 0; i < fields.size(); i++) {
			
			if(fields.get(i).content.equalsIgnoreCase(name))
				requestedFields.add(fields.get(i));
		}
		
		return requestedFields;
	}
	
	public static Element getField(Element element, String name) {
		
		ArrayList<Element> fields = getFields(element);
		
		for(int i = 0; i < fields.size(); i++) {
			
			if(fields.get(i).content.equalsIgnoreCase(name))
				return fields.get(i);
		}
		
		return null;
	}
	
	public static boolean hasField(Element element, String name) {
		
		ArrayList<Element> fields = getFields(element);
		
		for(int i = 0; i < fields.size(); i++) {
			
			if(fields.get(i).content.equalsIgnoreCase(name))
				return true;
		}
		
		return false;
	}
	
	public static Element getReference(Element element) {
		
		String alias = getReferenceAlias(element);
		
		try {
			
			Element current = element;
			
			while(current.parent != null) {
				
				for(int i = ElementUtilities.getIndex(current); i >= 0; i--) {
					
					if(current.parent.children.get(i).content.equalsIgnoreCase(alias))
						return current.parent.children.get(i);
				}
				
				current = current.parent;
			}
			
			current = element;
			
			while(current.parent != null) {
				
				for(int i = ElementUtilities.getIndex(current); i < element.parent.children.size(); i++) {
					
					if(current.parent.children.get(i).content.equalsIgnoreCase(alias))
						return current.parent.children.get(i);
				}
				
				current = current.parent;
			}
		}
		
		catch(Exception exception) {
			
		}
		
		return null;
	}
	
	public static String getReferenceAlias(Element element) {
		
		try {
			return ElementUtilities.getChild(element, "Reference").children.get(0).content;
		}
		
		catch(Exception exception) {
			return "";
		}
	}
}