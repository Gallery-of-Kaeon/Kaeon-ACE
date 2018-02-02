package kaeon_ace.engine.processes.renderer;

import java.util.ArrayList;
import java.util.Collections;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

import com.sun.opengl.util.Animator;

import kaeon_ace.engine.Engine;
import kaeon_ace.engine.processes.camera.Camera;
import kaeon_ace.engine.processes.clock.Clock;
import kaeon_ace.engine.processes.frame.Frame;
import kaeon_ace.engine.processes.loader.Loader;
import kaeon_ace.engine.processes.renderer.utilities.RenderedObject;
import kaeon_ace.engine.processes.renderer.utilities.RendererMesh;
import kaeon_ace.engine.processes.renderer.utilities.RendererOperations;
import kaeon_ace.engine.processes.renderer.utilities.RendererState;
import kaeon_ace.engine.processes.renderer.utilities.textures.TextureLoader;
import kaeon_ace.engine.processes.scene.Scene;
import kaeon_ace.engine.utilities.geometry.mesh.Mesh;
import kaeon_ace.engine.utilities.geometry.utilities.visual.effects.Effect;
import kaeon_ace.engine.utilities.user.super_user.CustomRender;
import philosophers_stone.PhilosophersStone;
import philosophers_stone.PhilosophersStoneUtilities;

public class Renderer extends PhilosophersStone {
	
	private GLCanvas renderer;
	
	private Animator animator;
	
	private TextureLoader textureLoader;
	private boolean loadingTextures;
	
	private ArrayList<Effect> effects;
	
	private ArrayList<RenderedObject> objects3D;
	private ArrayList<RenderedObject> objects2D;
	
	private RendererState rendererState;
	
	public Engine engine;
	public Camera camera;
	public Clock clock;
	public Frame frame;
	public Loader loader;
	public Scene scene;
	
	public Renderer() {
		
		PhilosophersStoneUtilities.tag(this, "Renderer");
		
		initialize();
	}
	
	private void initialize() {
		
		renderer = new GLCanvas(new GLCapabilities());
		
		renderer.addGLEventListener(
				
			new GLEventListener() {
	
				public void display(GLAutoDrawable glad) {
					GLDisplay(glad);
				}
	
				public void displayChanged(
						GLAutoDrawable glad,
						boolean modeChanged, boolean deviceChanged) {
					
					GLDisplayChanged(
							glad,
							modeChanged, deviceChanged);
				}
	
				public void init(GLAutoDrawable glad) {
					GLInit(glad);
				}
	
				public void reshape(
						GLAutoDrawable glad,
						int x, int y,
						int width, int height) {
					
					GLReshape(
							glad,
							x, y,
							width, height);
				}
			}
		);
		
		animator = new Animator(renderer);
		
		textureLoader = new TextureLoader();
		
		effects = new ArrayList<Effect>();
		
		objects3D = new ArrayList<RenderedObject>();
		objects2D = new ArrayList<RenderedObject>();
	}
	
	public Object onCall(ArrayList<Object> packet) {
		
		if(packet.size() == 0)
			return null;
		
		if(!(packet.get(0) instanceof String))
			return null;
		
		if(((String) packet.get(0)).equalsIgnoreCase("Start")) {
			
			engine = (Engine) PhilosophersStoneUtilities.get(this, "Engine").get(0);
			camera = (Camera) PhilosophersStoneUtilities.get(this, "Camera").get(0);
			clock = (Clock) PhilosophersStoneUtilities.get(this, "Clock").get(0);
			frame = (Frame) PhilosophersStoneUtilities.get(this, "Frame").get(0);
			loader = (Loader) PhilosophersStoneUtilities.get(this, "Loader").get(0);
			scene = (Scene) PhilosophersStoneUtilities.get(this, "Scene").get(0);
			
			start();
		}
		
		if(((String) packet.get(0)).equalsIgnoreCase("Stop"))
			stop();
		
		return null;
	}
	
	public void start() {
		
		rendererState = new RendererState(engine);
		rendererState.setTextureLoader(textureLoader);
		
		animator.start();
	}
	
	public void stop() {
		
		textureLoader.dispose();
		
		animator.stop();
	}
	
	private void GLDisplay(GLAutoDrawable glad) {
		
		GL gl = glad.getGL();
		
		if(scene.isUpdating()) {
			
			loadingTextures = true;
			
			ArrayList<String> newTextures = (loader).getNewTextures();
			
			for(int i = 0; i < newTextures.size(); i++)
				textureLoader.getTexture(newTextures.get(i));
			
			textureLoader.loadTextures(gl);
			
			loadingTextures = false;
			
			return;
		}
		
		GLU glu = new GLU();
		
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		gl.glShadeModel(GL.GL_SMOOTH);
		
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glLoadIdentity();
		
		glu.gluPerspective(
				camera.getFOV(),
				((float) renderer.getWidth() / (float) renderer.getHeight()),
				camera.getNearest(),
				camera.getFarthest());
		
		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();
		
		glu.gluLookAt(
				0, 0, 0,
				0, 0, 1,
				0, 1, 0);
		
		try {
			render(gl);
		}
		
		catch(Exception exception) {
			exception.printStackTrace();
		}
		
		frame.updateCursor();
		
		clock.incrementFrameCount();
	}
	
	private void GLDisplayChanged(
			GLAutoDrawable glad,
			boolean modeChanged, boolean deviceChanged) {
		
	}

	private void GLInit(GLAutoDrawable glad) {
		
		GL gl = glad.getGL();
		
		gl.glEnable(GL.GL_DEPTH_TEST);
		gl.glDepthFunc(GL.GL_LEQUAL);
		
		gl.glEnable(GL.GL_LIGHTING);
		gl.glEnable(GL.GL_EMISSION);
		gl.glEnable(GL.GL_COLOR_MATERIAL);
		
		gl.glColorMaterial(GL.GL_FRONT_AND_BACK, GL.GL_AMBIENT_AND_DIFFUSE);
		gl.glLightModeli(GL.GL_LIGHT_MODEL_TWO_SIDE, GL.GL_TRUE);
		
		gl.glEnable(GL.GL_BLEND);
		gl.glEnable(GL.GL_POLYGON_SMOOTH);
		
		gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
		gl.glHint(GL.GL_POLYGON_SMOOTH_HINT, GL.GL_NICEST);
		
		gl.glEnable(GL.GL_LINE_SMOOTH);
		gl.glEnable(GL.GL_MULTISAMPLE);
		
		rendererState.setGL(gl);
	}

	private void GLReshape(
			GLAutoDrawable glad,
			int x, int y,
			int width, int height) {
		
	}
	
	private void render(GL gl) {
		
		for(int i = 0; i < effects.size(); i++)
			RendererOperations.renderEffect(rendererState, effects.get(i));
		
		for(int i = 0; i < objects3D.size(); i++) {
			
			gl.glPushMatrix();
			
			RendererOperations.transform(rendererState, Mesh.RENDER_MODE_3D, camera.getTransforms3D(), false, true);
			
			objects3D.get(i).renderObject3DOpaque();
			
			RendererOperations.transform(rendererState, Mesh.RENDER_MODE_3D, camera.getTransforms3D(), true, false);
			
			gl.glPopMatrix();
		}
		
		for(int i = 0; i < objects3D.size(); i++) {
			
			gl.glPushMatrix();
			
			RendererOperations.transform(rendererState, Mesh.RENDER_MODE_3D, camera.getTransforms3D(), false, true);
			
			objects3D.get(i).renderObject3DTranslucent();

			RendererOperations.transform(rendererState, Mesh.RENDER_MODE_3D, camera.getTransforms3D(), true, false);
			
			gl.glPopMatrix();
		}
		
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glLoadIdentity();
		
		gl.glOrtho(
				0,
				frame.getWidth(),
				frame.getHeight(),
				0, -1, 1);
		
		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();

		boolean lightingEnabled = gl.glIsEnabled(GL.GL_LIGHTING);
		boolean fogEnabled = gl.glIsEnabled(GL.GL_FOG);
		
		gl.glDisable(GL.GL_LIGHTING);
		gl.glDisable(GL.GL_FOG);
		
		for(int i = 0; i < objects2D.size(); i++) {
			
			gl.glPushMatrix();
			
			RendererOperations.transform(rendererState, Mesh.RENDER_MODE_2D, camera.getTransforms2D(), false, true);
			
			objects2D.get(i).renderObject2D();

			RendererOperations.transform(rendererState, Mesh.RENDER_MODE_2D, camera.getTransforms2D(), true, false);
			
			gl.glPopMatrix();
		}
		
		if(lightingEnabled)
			gl.glEnable(GL.GL_LIGHTING);
		
		if(fogEnabled)
			gl.glEnable(GL.GL_FOG);
	}
	
	public void loadTexture(String texture) {
		
		loadingTextures = true;
		
		textureLoader.getTexture(texture);
		
		loadingTextures = false;
	}
	
	public boolean isLoadingTextures() {
		return loadingTextures;
	}
	
	public void refresh() {
		
		ArrayList<Effect> newEffects = loader.getNewEffects();
		ArrayList<Effect> updatedEffects = new ArrayList<Effect>(effects);
		
		for(int i = 0; i < newEffects.size(); i++)
			updatedEffects.add(newEffects.get(i));
		
		for(int i = 0; i < updatedEffects.size(); i++) {
			
			if(updatedEffects.get(i).isDestroyed()) {
				updatedEffects.remove(i);
				i--;
			}
		}
		
		effects = updatedEffects;
		
		ArrayList<Mesh> newMeshes = loader.getNewMeshes();
		
		ArrayList<RenderedObject> updatedObjects3D = new ArrayList<RenderedObject>(objects3D);
		ArrayList<RenderedObject> updatedObjects2D = new ArrayList<RenderedObject>(objects2D);
		
		for(int i = 0; i < newMeshes.size(); i++) {
			
			if(newMeshes.get(i).getRenderMode().equals(Mesh.RENDER_MODE_3D))
				updatedObjects3D.add(new RendererMesh(rendererState, newMeshes.get(i)));
			
			else
				updatedObjects2D.add(new RendererMesh(rendererState, newMeshes.get(i)));
		}
		
		ArrayList<CustomRender> newCustomRenders = loader.getNewCustomRenders();
		
		for(int i = 0; i < newCustomRenders.size(); i++) {
			
			newCustomRenders.get(i).setRendererState(rendererState);
			
			if(newCustomRenders.get(i).getRenderMode().equals(Mesh.RENDER_MODE_3D))
				updatedObjects3D.add(newCustomRenders.get(i));
			
			else
				updatedObjects2D.add(newCustomRenders.get(i));
		}
		
		for(int i = 0; i < updatedObjects3D.size(); i++) {
			
			if(!updatedObjects3D.get(i).isDestroyed())
				updatedObjects3D.get(i).update();
			
			else {
				updatedObjects3D.remove(i);
				i--;
			}
		}
		
		for(int i = 0; i < updatedObjects2D.size(); i++) {
			
			if(!updatedObjects2D.get(i).isDestroyed())
				updatedObjects2D.get(i).update();
			
			else {
				updatedObjects2D.remove(i);
				i--;
			}
		}
		
		Collections.sort(updatedObjects3D);
		Collections.sort(updatedObjects2D);
		
		objects3D = updatedObjects3D;
		objects2D = updatedObjects2D;
	}
	
	public GLCanvas getRenderer() {
		return renderer;
	}
}