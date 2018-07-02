package kaeon_ace.engine.utilities.geometry.utilities.visual.effects;

import kaeon_ace.engine.processes.scene.utilities.body.Body;

public abstract class Effect {

	public static String EFFECT_LIGHTING = "EFFECT_LIGHTING";
	public static String EFFECT_FOG = "EFFECT_FOG";
	
	private Body body;
	
	private boolean destroyed;
	
	public void setBody(Body body) {
		
		if(this.body != null)
			return;
		
		this.body = body;
	}
	
	public Body getBody() {
		return body;
	}
	
	public boolean isActive() {
		return body.getEntity().getRunTime().isActive();
	}
	
	public void destroy() {
		destroyed = true;
	}
	
	public boolean isDestroyed() {
		
		if(getBody().getEntity().getRunTime().isDestroyed())
			return true;
		
		return destroyed;
	}
}