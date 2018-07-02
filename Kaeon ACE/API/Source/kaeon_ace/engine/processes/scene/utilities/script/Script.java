package kaeon_ace.engine.processes.scene.utilities.script;

import java.util.ArrayList;

import kaeon_ace.engine.Engine;
import kaeon_ace.engine.processes.scene.utilities.Entity;

public class Script {
	
	private Entity entity;
	private ArrayList<Script> subscripts;
	
	private boolean removed;
	
	public Script() {
		subscripts = new ArrayList<Script>();
	}
	
	public void initialize(Entity entity) {
		
		if(this.entity != null)
			return;
		
		this.entity = entity;
	}
	
	public Entity getEntity() {
		return entity;
	}
	
	public Engine getACE() {
		return entity.getEngine();
	}
	
	public void addSubscript(Script subscript) {
		subscript.initialize(entity);
	}
	
	public void remove() {
		removed = true;
	}
	
	public boolean isRemoved() {
		return removed;
	}
	
	public void start() {
		
		onStart();
		
		for(int i = 0; i < subscripts.size(); i++)
			subscripts.get(i).start();
	}
	
	public void onStart() {
		
	}
	
	public void update() {
		
		onUpdate();
		
		for(int i = 0; i < subscripts.size(); i++)
			subscripts.get(i).update();
	}
	
	public void onUpdate() {
		
	}
	
	public void activate() {
		
		onActivate();
		
		for(int i = 0; i < subscripts.size(); i++)
			subscripts.get(i).activate();
	}
	
	public void onActivate() {
		
	}
	
	public void deactivate() {
		
		onDeactivate();
		
		for(int i = 0; i < subscripts.size(); i++)
			subscripts.get(i).deactivate();
	}
	
	public void onDeactivate() {
		
	}
	
	public void parentActivate() {
		
		onParentActivate();
		
		for(int i = 0; i < subscripts.size(); i++)
			subscripts.get(i).parentActivate();
	}
	
	public void onParentActivate() {
		
	}
	
	public void parentDeactivate() {
		
		onParentDeactivate();
		
		for(int i = 0; i < subscripts.size(); i++)
			subscripts.get(i).parentDeactivate();
	}
	
	public void onParentDeactivate() {
		
	}
	
	public void destroy() {
		
		onDestroy();
		
		for(int i = 0; i < subscripts.size(); i++)
			subscripts.get(i).destroy();
	}
	
	public void onDestroy() {
		
	}
	
	public void close() {
		
		onClose();
		
		for(int i = 0; i < subscripts.size(); i++)
			subscripts.get(i).close();
	}
	
	public void onClose() {
		
	}
}