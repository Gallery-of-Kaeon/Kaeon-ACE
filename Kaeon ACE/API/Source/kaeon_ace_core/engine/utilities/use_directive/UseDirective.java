package kaeon_ace_core.engine.utilities.use_directive;

import java.util.ArrayList;

import aether_one_plus.Aether;
import kaeon_ace_core.ace.ACE;
import kaeon_ace_core.engine.utilities.process.BootstrapProcess;
import one.Element;

public class UseDirective extends BootstrapProcess {
	
	public void onStart() {
		
		ArrayList<String> modules = getModules(engine.ace);
		
		for(int i = 0; i < modules.size(); i++)
			Aether.call(modules.get(i), 0, engine);
	}
	
	public static ArrayList<String> getModules(Element ace) {
		
		ArrayList<String> modules = new ArrayList<String>();
		
		ArrayList<Element> entities = ACE.getChildrenByType(ace, "Entity");
		ArrayList<Element> components = ACE.getChildrenByType(ace, "Component");
		
		for(int i = 0; i < entities.size(); i++)
			modules.addAll(getModules(entities.get(i)));
		
		for(int i = 0; i < components.size(); i++) {
			
			ArrayList<String> module = getModule(components.get(i));
			
			if(module != null)
				modules.addAll(module);
		}
		
		return modules;
	}
	
	public static ArrayList<String> getModule(Element ace) {
		
		try {
			
			if(!ACE.getType(ACE.getChildren(ace).get(0)).equalsIgnoreCase("Directive"))
				return null;
			
			if(!ACE.getField(ACE.getChildren(ace).get(0), "Type").children.get(0).content.equalsIgnoreCase("Use"))
				return null;
			
			ArrayList<Element> modules = ACE.getField(ACE.getChildren(ace).get(0), "Directive").children;
			
			ArrayList<String> moduleNames = new ArrayList<String>();
			
			for(int i = 0; i < modules.size(); i++)
				moduleNames.add(modules.get(i).content);
			
			return moduleNames;
		}
		
		catch(Exception excpetion) {
			
		}
		
		return null;
	}
	
	public void onUpdate() {
		
		if(engine.ace.children.size() == 0 && engine.publicConnections.size() <= 1) {
			
			if(engine.publicConnections.size() == 1) {
				
				if(engine.publicConnections.get(0) == this)
					System.exit(0);
			}
			
			else
				System.exit(0);
		}
	}
}