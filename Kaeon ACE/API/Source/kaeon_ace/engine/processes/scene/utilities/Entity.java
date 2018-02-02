package kaeon_ace.engine.processes.scene.utilities;

import java.util.ArrayList;

import kaeon_ace.engine.Engine;
import kaeon_ace.engine.processes.scene.utilities.body.Body;
import kaeon_ace.engine.processes.scene.utilities.script.RunTime;

public class Entity {
	
	private Engine ace;
	
	private Entity parent;
	private ArrayList<Entity> children;
	
	private RunTime runTime;
	private Body body;
	
	public Entity() {
		
		children = new ArrayList<Entity>();
		
		runTime = new RunTime(this);
		body = new Body(this);
	}
	
	public void initialize(Engine ace) {
		
		this.ace = ace;
		
		onInitialize();
		body.initialize();
	}
	
	public void onInitialize() {
		
	}
	
	public void setParent(Entity entity) {
		this.parent = entity;
	}
	
	public void addChildren(ArrayList<Entity> children) {
		
		for(int i = 0; i < children.size(); i++)
			addChild(children.get(i));
	}
	
	public void addChild(Entity entity) {
		
		if(entity.getRunTime().hasStarted())
			return;
		
		entity.setParent(this);
		
		children.add(entity);
	}
	
	public Engine getEngine() {
		return ace;
	}
	
	public Entity getParent() {
		return parent;
	}
	
	public ArrayList<Entity> getChildren() {
		return children;
	}
	
	public RunTime getRunTime() {
		return runTime;
	}
	
	public Body getBody() {
		return body;
	}
}