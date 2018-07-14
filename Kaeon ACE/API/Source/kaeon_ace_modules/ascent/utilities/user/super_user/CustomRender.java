package kaeon_ace_modules.ascent.utilities.user.super_user;

import java.util.ArrayList;

import kaeon_ace_core.engine.Engine;
import kaeon_ace_modules.ascent.processes.renderer.utilities.RenderedObject;
import kaeon_ace_modules.ascent.processes.renderer.utilities.RendererOperations;
import kaeon_ace_modules.ascent.processes.renderer.utilities.RendererState;
import kaeon_ace_modules.ascent.processes.scene.utilities.Entity;
import kaeon_ace_modules.ascent.utilities.geometry.mesh.Mesh;
import kaeon_ace_modules.ascent.utilities.math.transform.Transform;

public class CustomRender extends RenderedObject {
	
	private Entity entity;
	
	private RendererState rendererState;
	
	private String renderMode;
	private int renderPriority;
	
	private ArrayList<Transform> transforms;
	
	private boolean destroyed;
	
	public CustomRender() {
		
		renderMode = Mesh.RENDER_MODE_3D;
		
		transforms = new ArrayList<Transform>();
	}
	
	public CustomRender(Entity entity) {
		
		this();
		
		this.entity = entity;
	}
	
	public CustomRender(Entity entity, String renderMode) {
		
		this();
		
		this.entity = entity;
		
		this.renderMode = renderMode;
	}
	
	public void setEntity(Entity entity) {
		this.entity = entity;
	}
	
	public Entity getEntity() {
		return entity;
	}
	
	public Engine getACE() {
		return entity.getEngine();
	}
	
	public void setRendererState(RendererState rendererState) {
		this.rendererState = rendererState;
	}
	
	public RendererState getRendererState() {
		return rendererState;
	}
	
	public void onInitialize() {
		
	}
	
	public void setRenderMode(String renderMode) {
		this.renderMode = renderMode;
	}
	
	public void setRenderPriority(int renderPriority) {
		this.renderPriority = renderPriority;
	}
	
	public void transform(Transform transform) {
		transforms.add(transform);
	}
	
	public String getRenderMode() {
		return renderMode;
	}
	
	public int getRenderPriority() {
		return renderPriority;
	}
	
	public ArrayList<Transform> getTransforms() {
		return transforms;
	}
	
	public void destroy() {
		destroyed = true;
	}
	
	public boolean isDestroyed() {
		
		if(destroyed)
			return true;
		
		Entity entity = this.entity;
		
		while(entity != null) {
			
			if(entity.getRunTime().isDestroyed())
				return true;
			
			entity = entity.getParent();
		}
		
		return false;
	}
	
	public void renderObject3DOpaque() {
		
		renderTransforms(true);
		
		render3DOpaque();
		
		renderTransforms(false);
	}
	
	public void renderObject3DTranslucent() {
		
		renderTransforms(true);
		
		render3DTranslucent();
		
		renderTransforms(false);
	}
	
	public void renderObject2D() {
		
		renderTransforms(true);
		
		render2D();
		
		renderTransforms(false);
	}
	
	public void renderTransforms(boolean transforming) {
		
		ArrayList<Transform> transformsToRender = new ArrayList<Transform>();
		
		Entity entity = this.entity;
		
		while(entity != null) {
			
			transformsToRender.addAll(0, entity.getBody().getTransforms());
			
			entity = entity.getParent();
		}
		
		transformsToRender.addAll(transforms);
		
		RendererOperations.transform(
				rendererState,
				renderMode,
				transformsToRender,
				transforming,
				transforming);
	}
	
	public void render3DOpaque() {
		
	}
	
	public void render3DTranslucent() {
		
	}
	
	public void render2D() {
		
	}

	public void update() {
		
	}
}