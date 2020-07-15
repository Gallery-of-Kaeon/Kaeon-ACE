package kaeon_ace_modules.ascent.processes.renderer.utilities;

import java.util.ArrayList;

import javax.media.opengl.GL;

import kaeon_ace_core.engine.Engine;
import kaeon_ace_modules.ascent.processes.frame.Frame;
import kaeon_ace_modules.ascent.processes.renderer.utilities.textures.TextureLoader;
import kaeon_ace_modules.ascent.utilities.math.Vector3;
import philosophers_stone.PhilosophersStoneUtilities;

public class RendererState {
	
	private Engine engine;
	
	private GL gl;
	
	private TextureLoader textureLoader;
	
	private ArrayList<Vector3> scaleStack;
	
	private boolean isLightingEnabled;
	private boolean isFogEnabled;
	
	public Frame frame;
	
	public RendererState(Engine engine) {
		
		this.engine = engine;
		
		frame = (Frame) PhilosophersStoneUtilities.get(engine, "Frame").get(0);
		
		scaleStack = new ArrayList<Vector3>();
		
		isLightingEnabled = true;
		isFogEnabled = true;
	}
	
	public void setGL(GL gl) {
		this.gl = gl;
	}
	
	public void setTextureLoader(TextureLoader textureLoader) {
		this.textureLoader = textureLoader;
	}
	
	
	public void pushScale(Vector3 scale) {
		scaleStack.add(scale);
	}
	
	public void popScale() {
		scaleStack.remove(scaleStack.size() - 1);
	}
	
	public void enableLighting() {
		isLightingEnabled = true;
	}
	
	public void disableLighting() {
		isLightingEnabled = false;
	}
	
	public void enableFog() {
		isFogEnabled = true;
	}
	
	public void disableFog() {
		isFogEnabled = false;
	}
	
	public Engine getACE() {
		return engine;
	}
	
	public GL getGL() {
		return gl;
	}
	
	public TextureLoader getTextureLoader() {
		return textureLoader;
	}
	
	public Vector3 getScale() {
		
		if(scaleStack.size() == 0)
			return new Vector3(1, 1, 1);
		
		return scaleStack.get(scaleStack.size() - 1);
	}
	
	public boolean isLightingEnabled() {
		return isLightingEnabled;
	}
	
	public boolean isFogEnabled() {
		return isFogEnabled;
	}
}