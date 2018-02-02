package kaeon_ace.engine.utilities.extension.game.geometry;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

import kaeon_ace.engine.processes.renderer.utilities.RendererState;
import kaeon_ace.engine.processes.renderer.utilities.textures.Texture;
import kaeon_ace.engine.utilities.geometry.utilities.material.Material;
import kaeon_ace.engine.utilities.user.super_user.CustomRender;

public class Sphere extends CustomRender {
	
	private Material material;
	
	private int slices;
	private int stacks;
	
	public Sphere() {
		
		super();
		
		material = new Material();
		
		slices = 20;
		stacks = 20;
	}
	
	public Sphere(String texture) {
		
		this();
		
		material.setTexture(texture);
	}
	
	public void render3DOpaque() {
		
		if(!isTranslucent(getRendererState()))
			render(getRendererState());
	}
	
	public void render3DTranslucent() {
		
		if(isTranslucent(getRendererState()))
			render(getRendererState());
	}
	
	private boolean isTranslucent(RendererState rendererState) {
		
		if(material.getTexture() == null)
			return false;
		
		Texture texture =
				rendererState.
				getTextureLoader().
				getTexture(
						material.getTexture());
		
		return texture.hasAlpha() || material.getColor().getAlpha() != 1;
	}
	
	public void render(RendererState rendererState) {

		GL gl = rendererState.getGL();
		GLU glu = new GLU();
		
		gl.glRotated(90, 1, 0, 0);
		
		gl.glColor4d(
				material.getColor().getRed(),
				material.getColor().getGreen(),
				material.getColor().getBlue(),
				material.getColor().getAlpha());
		
		Texture texture = null;
		
		if(material.getTexture() != null) {
			
			texture = 
					rendererState.
					getTextureLoader().
					getTexture(
							material.getTexture());
		}
	
		if(texture != null)
			texture.enable();
		
		GLUquadric sphere = glu.gluNewQuadric();
		
		glu.gluQuadricTexture(sphere, true);
		
		glu.gluQuadricDrawStyle(sphere, GLU.GLU_FILL);
		glu.gluQuadricNormals(sphere, GLU.GLU_FLAT);
		glu.gluQuadricOrientation(sphere, GLU.GLU_OUTSIDE);
		
		glu.gluSphere(
				sphere,
				rendererState.getScale().getMagnitude(),
				slices,
				stacks);
		
		glu.gluDeleteQuadric(sphere);
		
		if(texture != null)
			texture.disable();
		
		gl.glColor4d(1, 1, 1, 1);
		
		gl.glRotated(90, -1, 0, 0);
	}
	
	public void setDimensions(int slices, int stacks) {
		this.slices = slices;
		this.stacks = stacks;
	}
	
	public Material getMaterial() {
		return material;
	}
}
