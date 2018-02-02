package kaeon_ace.engine.processes.renderer.utilities;

import kaeon_ace.engine.utilities.geometry.mesh.Mesh;

public abstract class RenderedObject implements Comparable<RenderedObject> {
	
	public void setRendererState(RendererState rendererState) {
		
	}
	
	public String getRenderMode() {
		return Mesh.RENDER_MODE_3D;
	}
	
	public int getRenderPriority() {
		return 0;
	}
	
	public boolean isDestroyed() {
		return false;
	}
	
	public void renderObject3DOpaque() {
		
	}
	
	public void renderObject3DTranslucent() {
		
	}
	
	public void renderObject2D() {
		
	}
	
	public void update() {
		
	}

	public int compareTo(RenderedObject object) {
		
		if(getRenderMode().equals(Mesh.RENDER_MODE_3D)) {
			return 0;
		}
		
		else {
			return getRenderPriority() - ((RenderedObject) object).getRenderPriority();
		}
	}
}