package kaeon_ace.engine.processes.loader;

import java.util.ArrayList;

import kaeon_ace.engine.utilities.geometry.mesh.Mesh;
import kaeon_ace.engine.utilities.geometry.utilities.physics.collider.Collider;
import kaeon_ace.engine.utilities.geometry.utilities.physics.forces.Force;
import kaeon_ace.engine.utilities.geometry.utilities.visual.effects.Effect;
import kaeon_ace.engine.utilities.user.super_user.CustomRender;
import philosophers_stone.PhilosophersStone;
import philosophers_stone.PhilosophersStoneUtilities;

public class Loader extends PhilosophersStone {
	
	private ArrayList<String> newTextures;

	private ArrayList<Effect> newEffects;
	private ArrayList<Mesh> newMeshes;
	
	private ArrayList<CustomRender> newCustomRenders;
	
	private ArrayList<Collider> newColliders;
	private ArrayList<Force> newForces;
	
	public Loader() {
		
		PhilosophersStoneUtilities.tag(this, "Loader");
		
		newTextures = new ArrayList<String>();
		
		newEffects = new ArrayList<Effect>();
		newMeshes = new ArrayList<Mesh>();
		
		newCustomRenders = new ArrayList<CustomRender>();
		
		newColliders = new ArrayList<Collider>();
		newForces = new ArrayList<Force>();
	}
	
	public void addNewTexture(String texture) {
		newTextures.add(texture);
	}
	
	public ArrayList<String> getNewTextures() {
		
		ArrayList<String> requestedTextures = new ArrayList<String>();
		requestedTextures.addAll(newTextures);
		
		newTextures = new ArrayList<String>();
		
		return requestedTextures;
	}
	
	public void addNewEffect(Effect effect) {
		newEffects.add(effect);
	}
	
	public ArrayList<Effect> getNewEffects() {
		
		ArrayList<Effect> requestedEffects = new ArrayList<Effect>();
		requestedEffects.addAll(newEffects);
		
		newEffects = new ArrayList<Effect>();
		
		return requestedEffects;
	}
	
	public void addNewMesh(Mesh mesh) {
		newMeshes.add(mesh);
	}
	
	public ArrayList<Mesh> getNewMeshes() {
		
		ArrayList<Mesh> requestedMeshes = new ArrayList<Mesh>();
		requestedMeshes.addAll(newMeshes);
		
		newMeshes = new ArrayList<Mesh>();
		
		return requestedMeshes;
	}
	
	public void addNewCustomRender(CustomRender customRender) {
		newCustomRenders.add(customRender);
	}
	
	public ArrayList<CustomRender> getNewCustomRenders() {
		
		ArrayList<CustomRender> requestedCustomRenders = new ArrayList<CustomRender>();
		requestedCustomRenders.addAll(newCustomRenders);
		
		newCustomRenders = new ArrayList<CustomRender>();
		
		return requestedCustomRenders;
	}
	
	public void addNewCollider(Collider collider) {
		newColliders.add(collider);
	}
	
	public ArrayList<Collider> getNewColliders() {
		
		ArrayList<Collider> requestedColliders = new ArrayList<Collider>();
		requestedColliders.addAll(newColliders);
		
		newColliders = new ArrayList<Collider>();
		
		return requestedColliders;
	}
	
	public void addNewForce(Force force) {
		newForces.add(force);
	}
	
	public ArrayList<Force> getNewForces() {
		
		ArrayList<Force> requestedForces = new ArrayList<Force>();
		requestedForces.addAll(newForces);
		
		newForces = new ArrayList<Force>();
		
		return requestedForces;
	}
}