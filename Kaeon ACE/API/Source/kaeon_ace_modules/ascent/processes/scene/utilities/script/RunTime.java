package kaeon_ace_modules.ascent.processes.scene.utilities.script;

import java.util.ArrayList;

import kaeon_ace_modules.ascent.processes.scene.utilities.Entity;

public class RunTime {
	
	private boolean started;
	private boolean destroyed;
	
	private boolean active;
	
	private Entity entity;
	
	private Entity parent;
	private ArrayList<Entity> children;
	
	private ArrayList<Script> scripts;
	
	public RunTime(Entity entity) {
		
		started = false;
		destroyed = false;
		
		active = false;
		
		this.entity = entity;
		
		parent = entity.getParent();
		children = entity.getChildren();
		
		scripts = new ArrayList<Script>();
	}
	
	public boolean hasStarted() {
		return started;
	}
	
	public boolean isActive() {
		
		if(active) {
			
			if(parent != null)
				return parent.getRunTime().isActive();
			
			return true;
		}
		
		return false;
	}
	
	public boolean isDestroyed() {
		return destroyed;
	}
	
	public void addScript(Script script) {
		
		if(script.getEntity() != null)
			return;
		
		script.initialize(entity);
		
		scripts.add(script);
	}
	
	public ArrayList<Script> getScripts() {
		return scripts;
	}
	
	public void start() {
		
		if(started)
			return;
		
		for(int i = 0; i < scripts.size(); i++)
			scripts.get(i).onStart();
		
		started = true;
		active = true;
	}
	
	public void update() {
		
		if(!isActive())
			return;
		
		for(int i = 0; i < scripts.size(); i++)
			scripts.get(i).update();
		
		for(int i = 0; i < scripts.size(); i++) {
			
			if(scripts.get(i).isRemoved()) {
				scripts.remove(i);
				i--;
			}
		}
		
		entity.getBody().refresh();
		
		for(int i = 0; i < children.size(); i++) {
			
			if(!children.get(i).getRunTime().hasStarted()) {
				children.get(i).initialize(entity.getEngine());
				children.get(i).getRunTime().start();
			}
			
			children.get(i).getRunTime().update();
		}
		
		for(int i = 0; i < children.size(); i++) {
			
			if(children.get(i).getRunTime().isDestroyed()) {
				children.remove(i);
				i--;
			}
		}
	}
	
	public void activate() {
		
		if(active || !started || destroyed)
			return;
		
		active = true;
		
		start();
		
		for(int i = 0; i < scripts.size(); i++)
			scripts.get(i).onActivate();
		
		parentActivate();
	}
	
	public void parentActivate() {
		
		start();
		
		for(int i = 0; i < children.size(); i++)
			children.get(i).getRunTime().parentActivate();
	}
	
	public void deactivate() {
		
		if(!active || !started || destroyed)
			return;
		
		active = false;
		
		for(int j = 0; j < scripts.size(); j++)
			scripts.get(j).onDeactivate();
		
		parentDeactivate();
	}
	
	public void parentDeactivate() {
		
		for(int i = 0; i < children.size(); i++)
			children.get(i).getRunTime().parentDeactivate();
	}
	
	public void destroy() {
		
		if(destroyed)
			return;
		
		destroyed = true;
		
		for(int i = 0; i < scripts.size(); i++)
			scripts.get(i).onDestroy();
		
		for(int i = 0; i < children.size(); i++)
			children.get(i).getRunTime().destroy();
	}
	
	public void close() {
		
		for(int i = 0; i < scripts.size(); i++)
			scripts.get(i).onClose();
		
		for(int i = 0; i < children.size(); i++)
			children.get(i).getRunTime().close();
	}
}