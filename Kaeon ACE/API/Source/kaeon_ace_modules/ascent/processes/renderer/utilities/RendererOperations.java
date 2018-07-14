package kaeon_ace_modules.ascent.processes.renderer.utilities;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.media.opengl.GL;

import kaeon_ace_modules.ascent.processes.renderer.utilities.textures.Texture;
import kaeon_ace_modules.ascent.utilities.geometry.mesh.Mesh;
import kaeon_ace_modules.ascent.utilities.geometry.mesh.Polygon;
import kaeon_ace_modules.ascent.utilities.geometry.mesh.Vertex;
import kaeon_ace_modules.ascent.utilities.geometry.utilities.visual.Color;
import kaeon_ace_modules.ascent.utilities.geometry.utilities.visual.effects.BackgroundColor;
import kaeon_ace_modules.ascent.utilities.geometry.utilities.visual.effects.Effect;
import kaeon_ace_modules.ascent.utilities.geometry.utilities.visual.effects.Fog;
import kaeon_ace_modules.ascent.utilities.geometry.utilities.visual.effects.Light;
import kaeon_ace_modules.ascent.utilities.math.Vector3;
import kaeon_ace_modules.ascent.utilities.math.transform.Rotation;
import kaeon_ace_modules.ascent.utilities.math.transform.Scale;
import kaeon_ace_modules.ascent.utilities.math.transform.ScaleTo;
import kaeon_ace_modules.ascent.utilities.math.transform.Transform;
import kaeon_ace_modules.ascent.utilities.math.transform.Translation;
import kaeon_ace_modules.ascent.utilities.resource.visual.ImageProcessor;

public class RendererOperations {
	
	public static void enableEffect(RendererState rendererState, String effect) {
		
		GL gl = rendererState.getGL();
		
		if(effect.equals(Effect.EFFECT_FOG))
			gl.glEnable(GL.GL_FOG);
		
		if(effect.equals(Effect.EFFECT_LIGHTING))
			gl.glEnable(GL.GL_LIGHTING);
	}
	
	public static void disableEffect(RendererState rendererState, String effect) {
		
		GL gl = rendererState.getGL();
		
		if(effect.equals(Effect.EFFECT_FOG))
			gl.glDisable(GL.GL_FOG);
		
		if(effect.equals(Effect.EFFECT_LIGHTING))
			gl.glDisable(GL.GL_LIGHTING);
	}
	
	public static boolean isEffectEnabled(RendererState rendererState, String effect) {
		
		GL gl = rendererState.getGL();
		
		if(effect.equals(Effect.EFFECT_FOG))
			return gl.glIsEnabled(GL.GL_FOG);
		
		if(effect.equals(Effect.EFFECT_LIGHTING))
			return gl.glIsEnabled(GL.GL_LIGHTING);
		
		return false;
	}
	
	public static void renderEffect(RendererState rendererState, Effect effect) {
		
		if(!effect.isActive())
			return;
		
		if(effect instanceof BackgroundColor)
			renderBackgroundColor(rendererState, ((BackgroundColor) effect));
		
		if(effect instanceof Fog)
			renderFog(rendererState, ((Fog) effect));
		
		if(effect instanceof Light)
			renderLight(rendererState, ((Light) effect));
	}
	
	public static void renderBackgroundColor(RendererState rendererState, BackgroundColor backgroundColor) {
		
		GL gl = rendererState.getGL();
		
		Color color = backgroundColor.getColor();
		
		gl.glClearColor(
				(float) color.getRed(),
				(float) color.getGreen(),
				(float) color.getBlue(),
				(float) color.getAlpha());
	}
	
	public static void renderFog(RendererState rendererState, Fog fog) {
		
		GL gl = rendererState.getGL();
		
		gl.glEnable(GL.GL_FOG);
		
		gl.glFogi(GL.GL_FOG_MODE, GL.GL_EXP2);
		
		gl.glFogf(GL.GL_FOG_DENSITY,  (float) fog.getColor().getAlpha());
		
		gl.glFogfv(GL.GL_FOG_COLOR, new float[] {
				1,
				(float) fog.getColor().getRed(),
				(float) fog.getColor().getGreen(),
				(float) fog.getColor().getBlue()}, 1);
		
		gl.glHint(GL.GL_FOG_HINT, GL.GL_NICEST);
	}
	
	public static void renderLight(RendererState rendererState, Light light) {
		
		GL gl = rendererState.getGL();
		
		int index = GL.GL_LIGHT0;
		
		float[] position = {
				(float) light.getPosition().getX(),
				(float) light.getPosition().getY(),
				(float) light.getPosition().getZ()
		};
		
		gl.glEnable(index);

		gl.glLightfv(index, GL.GL_POSITION, position, 0);

		gl.glLightfv(index, GL.GL_DIFFUSE, new float[] {1.0f, 1.0f, 1.0f, 1.0f}, 0);
		gl.glLightfv(index, GL.GL_AMBIENT, new float[] {0.1f, 0.1f, 0.1f, 1.0f}, 0);
		gl.glLightfv(index, GL.GL_SPECULAR, new float[] {1.0f, 1.0f, 1.0f, 1.0f}, 0);
	}
	
	public static void renderPolygonList(
			RendererState rendererState,
			PolygonList polygonList) {
		
		GL gl = rendererState.getGL();
		
		Color meshColor = polygonList.getMesh().getMaterial().getColor();
		
		if(meshColor.getAlpha() == 0)
			return;
		
		Texture texture = null;
		
		if(polygonList.getTexture() != null)
			texture = rendererState.getTextureLoader().getTexture(polygonList.getTexture());
		
		if(texture != null)
			texture.enable();

		String renderMode = polygonList.getMesh().getRenderMode();
		boolean wireframe = polygonList.getMesh().isWireframe();
		
		if(polygonList.getPolygonType().equals(PolygonList.POLYGON_TYPE_POINT))
			gl.glBegin(GL.GL_POINTS);
		
		else if(polygonList.getPolygonType().equals(PolygonList.POLYGON_TYPE_LINE))
			gl.glBegin(GL.GL_LINES);
		
		else if(polygonList.getPolygonType().equals(PolygonList.POLYGON_TYPE_TRIANGLE_FAN)) {
			
			if(!wireframe)
				gl.glBegin(GL.GL_TRIANGLES);
			
			else
				gl.glBegin(GL.GL_LINES);
		}
		
		ArrayList<Polygon> polygons = polygonList.getPolygons();
		
		for(int i = 0; i < polygons.size(); i++) {
			
			renderVertices(
					rendererState,
					polygons.get(i),
					renderMode,
					wireframe,
					meshColor);
		}
		
		gl.glEnd();
		
		if(texture != null)
			texture.disable();
	}
	
	public static void renderVertices(
			RendererState rendererState,
			Polygon polygon,
			String renderMode,
			boolean wireframe,
			Color meshColor) {
		
		Color polygonColor = polygon.getMaterial().getColor();
		
		if(polygonColor.getAlpha() == 0)
			return;
		
		int numVertices = polygon.getVertices().size();
		
		if(numVertices <= 2) {
			
			for(int i = 0; i < numVertices; i++) {
				
				renderVertex(
						rendererState,
						renderMode,
						wireframe,
						polygon.getMesh().getMaterial().getColor(),
						polygonColor,
						polygon.getMaterial().normal,
						polygon.getMesh().getVertex(polygon.getVertices().get(i)),
						polygon.getTexCoords().get(i));
			}
		}
		
		else {
			
			for(int i = 2; i < numVertices; i++) {
				
				int[] vertices = null;
				
				if(!wireframe)
					vertices = new int[] {0, i - 1, i};
				
				else
					vertices = new int[] {0, i - 1, i - 1, i, i, 0};
				
				for(int j = 0; j < vertices.length; j++) {
					
					renderVertex(
							rendererState,
							renderMode,
							wireframe,
							polygon.getMesh().getMaterial().getColor(),
							polygon.getMaterial().getColor(),
							polygon.getMaterial().normal,
							polygon.getMesh().getVertex(polygon.getVertices().get(vertices[j])),
							polygon.getTexCoords().get(vertices[j]));
				}
			}
		}
	}
	
	public static void renderVertex(
			RendererState rendererState,
			String renderMode,
			boolean wireframe,
			Color meshColor,
			Color polygonColor,
			BufferedImage normalMap,
			Vertex vertex,
			Vector3 texCoord) {
		
		GL gl = rendererState.getGL();
		
		Vector3 scale = rendererState.getScale();
		Color vertexColor = vertex.getColor();
		
		gl.glColor4d(
				meshColor.getRed() * polygonColor.getRed() * vertexColor.getRed(),
				meshColor.getGreen() * polygonColor.getGreen() * vertexColor.getGreen(),
				meshColor.getBlue() * polygonColor.getBlue() * vertexColor.getBlue(),
				meshColor.getAlpha() * polygonColor.getAlpha() * vertexColor.getAlpha());
		
		if(texCoord != null) {
			
			gl.glTexCoord3d(
					texCoord.getX(),
					texCoord.getY(),
					texCoord.getZ());
			
			if(normalMap != null) {
				
				double red =
						((double) ImageProcessor.getRed(
								normalMap,
								(int) (texCoord.getX() * normalMap.getWidth()),
								(int) (texCoord.getY() * normalMap.getHeight()))) / 255;
				
				double green =
						((double) ImageProcessor.getGreen(
								normalMap,
								(int) (texCoord.getX() * normalMap.getWidth()),
								(int) (texCoord.getY() * normalMap.getHeight()))) / 255;
				
				double blue =
						((double) ImageProcessor.getBlue(
								normalMap,
								(int) (texCoord.getX() * normalMap.getWidth()),
								(int) (texCoord.getY() * normalMap.getHeight()))) / 255;
				
				gl.glNormal3d(red, green, blue);
			}
		}
		
		Vector3 normal = vertex.getNormal();
		
		if(normal != null && normalMap == null) {
			
			gl.glNormal3d(
					normal.getX(),
					normal.getY(),
					normal.getZ());
		}
		
		int frameWidth = rendererState.frame.getWidth();
		int frameHeight = rendererState.frame.getHeight();
		
		Vector3 position = new Vector3(vertex.getPosition());
		position.increment(vertex.getTransformation());
		
		position = getPosition(
				position, renderMode,
				frameWidth, frameHeight);
		
		gl.glVertex3d(
				position.getX() * scale.getX(),
				position.getY() * scale.getY(),
				position.getZ() * scale.getZ());
	}
	
	public static void transform(
			RendererState rendererState,
			String renderMode,
			ArrayList<Transform> transforms,
			boolean transforming,
			boolean scaling) {
		
		for(
				int i = transforming ? 0 : transforms.size() - 1;
				transforming ? i < transforms.size() : i >= 0;
				i += transforming ? 1 : -1) {
			
			if(transforms.get(i) instanceof Translation)
				translate(rendererState, renderMode, transforms.get(i), transforming);
			
			if(transforms.get(i) instanceof Rotation)
				rotate(rendererState, transforms.get(i), transforming);
			
			if(transforms.get(i) instanceof Scale)
				scale(rendererState, transforms.get(i), scaling);
			
			if(transforms.get(i) instanceof ScaleTo)
				scaleTo(rendererState, transforms.get(i), scaling);
		}
	}
	
	public static void translate(
			RendererState rendererState,
			String renderMode,
			Transform translation,
			boolean transforming) {
		
		GL gl = rendererState.getGL();

		Vector3 scale = rendererState.getScale();
		
		int multiplier = transforming ? 1 : -1;
		
		int frameWidth = rendererState.frame.getWidth();
		int frameHeight = rendererState.frame.getHeight();
		
		Vector3 translate = getPosition(
				translation.getTransform(),
				translation.getMode() != null ? translation.getMode() : renderMode,
				frameWidth,
				frameHeight);
		
		gl.glTranslated(
				translate.getX() * scale.getX() * multiplier,
				translate.getY() * scale.getY() * multiplier,
				translate.getZ() * scale.getZ() * multiplier);
	}
	
	public static void rotate(
			RendererState rendererState,
			Transform rotate,
			boolean transforming) {
		
		GL gl = rendererState.getGL();
		
		if(transforming) {
			gl.glRotated(rotate.getX(), 1, 0, 0);
			gl.glRotated(rotate.getY(), 0, 1, 0);
			gl.glRotated(rotate.getZ(), 0, 0, 1);
		}
		
		else {
			gl.glRotated(-rotate.getZ(), 0, 0, 1);
			gl.glRotated(-rotate.getY(), 0, 1, 0);
			gl.glRotated(-rotate.getX(), 1, 0, 0);
		}
	}
	
	public static void scale(
			RendererState rendererState,
			Transform scale,
			boolean scaling) {
		
		if(scaling) {
			
			Vector3 newScale = new Vector3(rendererState.getScale());
			newScale.increment(scale.getTransform());
			
			rendererState.pushScale(newScale);
		}
		
		else
			rendererState.popScale();
	}
	
	public static void scaleTo(
			RendererState rendererState,
			Transform scaleTo,
			boolean scaling) {
		
		if(scaling)
			rendererState.pushScale(scaleTo.getTransform());
		
		else
			rendererState.popScale();
	}
	
	private static Vector3 getPosition(
			Vector3 coordinate, String mode,
			int frameWidth, int frameHeight) {
		
		Vector3 position = new Vector3(coordinate);
		
		if(!mode.equals(Mesh.RENDER_MODE_3D)) {
			
			if(mode.equals(Mesh.RENDER_MODE_2D)) {
				
				double frameAspect = (frameWidth + frameHeight) / 2;
				
				position.scale(
						frameAspect,
						frameAspect,
						0);
			}
			
			else if(mode.equals(Mesh.RENDER_MODE_2D_ASPECT_OFF)) {
				
				position.scale(
						frameWidth,
						frameHeight,
						0);
			}
			
			position.scaleY(-1);
		}
		
		else
			position.scaleX(-1);
		
		return position;
	}
	
	public static boolean isPolygonTranslucent(RendererState rendererState, Polygon polygon) {
		
		if(polygon.getMaterial().getColor().getAlpha() < 1)
			return true;
		
		for(int i = 0; i < polygon.getVertices().size(); i++) {
			
			if(polygon.getMesh().getVertex(polygon.getVertices().get(i)).getColor().getAlpha() < 1)
				return true;
		}
		
		if(polygon.getMaterial().getTexture() != null) {
			
			Texture texture =
					rendererState.getTextureLoader().getTexture(
							polygon.getMaterial().getTexture());
			
			if(texture != null)
				return texture.hasAlpha();
		}
		
		return false;
	}
}