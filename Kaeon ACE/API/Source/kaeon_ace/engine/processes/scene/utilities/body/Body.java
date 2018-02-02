package kaeon_ace.engine.processes.scene.utilities.body;

import java.util.ArrayList;
import java.util.Collections;

import kaeon_ace.engine.processes.loader.Loader;
import kaeon_ace.engine.processes.scene.utilities.Entity;
import kaeon_ace.engine.processes.scene.utilities.body.physics.Physics;
import kaeon_ace.engine.utilities.geometry.mesh.Mesh;
import kaeon_ace.engine.utilities.geometry.utilities.visual.effects.Effect;
import kaeon_ace.engine.utilities.math.transform.Transform;
import philosophers_stone.PhilosophersStoneUtilities;

public class Body {
	
	private Entity entity;
	private Entity parent;
	
	private ArrayList<Mesh> meshes;
	private ArrayList<Effect> effects;
	private ArrayList<Transform> transforms;
	
	private Physics physics;
	
	private boolean visible;
	
	public Loader loader;
	
	public Body(Entity entity) {
		
		this.entity = entity;
		parent = entity.getParent();
		
		meshes = new ArrayList<Mesh>();
		effects = new ArrayList<Effect>();
		transforms = new ArrayList<Transform>();
		
		visible = true;
	}
	
	public void initialize() {
		
		loader = (Loader) PhilosophersStoneUtilities.get(entity.getEngine(), "Loader").get(0);
		
		physics = new Physics(this);
		
		for(int i = 0; i < effects.size(); i++)
			loader.addNewEffect(effects.get(i));
		
		for(int i = 0; i < meshes.size(); i++)
			loader.addNewMesh(meshes.get(i));
	}
	
	public void addMesh(Mesh mesh) {
		
		if(mesh.getBody() != null)
			return;
		
		mesh.initialize(this);
		
		meshes.add(mesh);
		
		if(getEntity().getRunTime().hasStarted())
			loader.addNewMesh(mesh);
	}
	
	public void addEffect(Effect effect) {
		
		if(effect.getBody() != null)
			return;
		
		effect.setBody(this);
		
		effects.add(effect);
		
		if(getEntity().getRunTime().hasStarted())
			loader.addNewEffect(effect);
	}
	
	public void transform(Transform transform) {
		transforms.add(transform);
	}
	
	public Entity getEntity() {
		return entity;
	}
	
	public ArrayList<Mesh> getMeshes() {
		return meshes;
	}
	
	public ArrayList<Effect> getEffects() {
		return effects;
	}
	
	public ArrayList<Transform> getTransforms() {
		return transforms;
	}
	
	public Physics getPhysics() {
		return physics;
	}
	
	public void setVisible() {
		visible = true;
	}
	
	public void setInvisible() {
		visible = false;
	}
	
	public boolean isVisible() {
		
		if(visible) {
			
			if(parent != null)
				return parent.getBody().isVisible();
			
			return true;
		}
		
		return false;
	}
	
	public void refresh() {
		
		for(int i = 0; i < effects.size(); i++) {
			
			if(effects.get(i).isDestroyed()) {
				effects.remove(i);
				i--;
			}
		}
		
		for(int i = 0; i < meshes.size(); i++) {
			
			if(meshes.get(i).isDestroyed()) {
				meshes.remove(i);
				i--;
			}
			
			else
				meshes.get(i).refresh();
		}
		
		for(int i = 0; i < transforms.size(); i++) {
			
			if(transforms.get(i).isDestroyed()) {
				transforms.remove(i);
				i--;
			}
		}
		
		Collections.sort(transforms);
	}
}