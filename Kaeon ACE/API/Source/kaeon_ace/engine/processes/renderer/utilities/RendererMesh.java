package kaeon_ace.engine.processes.renderer.utilities;

import java.util.ArrayList;

import javax.media.opengl.GL;

import kaeon_ace.engine.processes.scene.utilities.Entity;
import kaeon_ace.engine.utilities.geometry.mesh.Mesh;
import kaeon_ace.engine.utilities.geometry.mesh.Polygon;
import kaeon_ace.engine.utilities.geometry.utilities.visual.effects.Effect;
import kaeon_ace.engine.utilities.math.transform.Transform;

public class RendererMesh extends RenderedObject {
	
	private RendererState rendererState;
	
	private Mesh mesh;
	
	private int numPolygons;
	
	private ArrayList<PolygonList> opaquePoints;
	private ArrayList<PolygonList> opaqueLines;
	private ArrayList<PolygonList> opaquePolygons;

	private ArrayList<PolygonList> translucentPoints;
	private ArrayList<PolygonList> translucentLines;
	private ArrayList<PolygonList> translucentPolygons;

	private ArrayList<PolygonList> points2D;
	private ArrayList<PolygonList> lines2D;
	private ArrayList<PolygonList> polygons2D;
	
	public RendererMesh(RendererState rendererState, Mesh mesh) {
		
		setRendererState(rendererState);
		
		this.mesh = mesh;

		opaquePoints =
			intitializeLists(
				PolygonList.POLYGON_TYPE_POINT,
				PolygonList.POLYGON_MODE_OPAQUE);

		opaqueLines =
			intitializeLists(
				PolygonList.POLYGON_TYPE_LINE,
				PolygonList.POLYGON_MODE_OPAQUE);

		opaquePolygons =
			intitializeLists(
				PolygonList.POLYGON_TYPE_TRIANGLE_FAN,
				PolygonList.POLYGON_MODE_OPAQUE);

		translucentPoints =
			intitializeLists(
				PolygonList.POLYGON_TYPE_POINT,
				PolygonList.POLYGON_MODE_TRANSLUCENT);

		translucentLines =
			intitializeLists(
				PolygonList.POLYGON_TYPE_LINE,
				PolygonList.POLYGON_MODE_TRANSLUCENT);

		translucentPolygons =
			intitializeLists(
				PolygonList.POLYGON_TYPE_TRIANGLE_FAN,
				PolygonList.POLYGON_MODE_TRANSLUCENT);

		points2D =
			intitializeLists(
				PolygonList.POLYGON_TYPE_POINT,
				PolygonList.POLYGON_MODE_2D);

		lines2D =
			intitializeLists(
				PolygonList.POLYGON_TYPE_LINE,
				PolygonList.POLYGON_MODE_2D);

		polygons2D =
			intitializeLists(
				PolygonList.POLYGON_TYPE_TRIANGLE_FAN,
				PolygonList.POLYGON_MODE_2D);
	}
	
	private ArrayList<PolygonList> intitializeLists(
			String type,
			String mode) {

		ArrayList<PolygonList> polygonLists =
				new ArrayList<PolygonList>();
		
		polygonLists.add(
				new PolygonList(
						rendererState,
						mesh,
						type,
						mode));
		
		return polygonLists;
	}
	
	public void setRendererState(RendererState rendererState) {
		this.rendererState = rendererState;
	}
	
	public boolean isVisible() {
		
		if(!mesh.isVisible())
			return false;
		
		Entity entity = mesh.getBody().getEntity();
		
		while(entity != null) {
			
			if(!entity.getBody().isVisible())
				return false;
			
			entity = entity.getParent();
		}
		
		return true;
	}
	
	public String getRenderMode() {
		return mesh.getRenderMode();
	}
	
	public int getRenderPriority() {
		return mesh.getRenderPriority();
	}
	
	public void renderObject3DOpaque() {
		
		if(!isVisible())
			return;

		boolean fogEnabled = RendererOperations.isEffectEnabled(rendererState, Effect.EFFECT_FOG);
		boolean lightingEnabled = RendererOperations.isEffectEnabled(rendererState, Effect.EFFECT_LIGHTING);
		
		if(mesh.isDisabled(Effect.EFFECT_FOG))
			RendererOperations.disableEffect(rendererState, Effect.EFFECT_FOG);
		
		if(mesh.isDisabled(Effect.EFFECT_LIGHTING))
			RendererOperations.disableEffect(rendererState, Effect.EFFECT_LIGHTING);
		
		ArrayList<Transform> transforms = getTransforms();
		
		RendererOperations.transform(rendererState, mesh.getRenderMode(), transforms, true, true);
		
		for(int i = 0; i < opaquePoints.size(); i++)
			RendererOperations.renderPolygonList(rendererState, opaquePoints.get(i));
		
		for(int i = 0; i < opaqueLines.size(); i++)
			RendererOperations.renderPolygonList(rendererState, opaqueLines.get(i));
		
		for(int i = 0; i < opaquePolygons.size(); i++)
			RendererOperations.renderPolygonList(rendererState, opaquePolygons.get(i));
		
		RendererOperations.transform(rendererState, mesh.getRenderMode(), transforms, false, false);
		
		if(fogEnabled)
			RendererOperations.enableEffect(rendererState, Effect.EFFECT_FOG);
		
		if(lightingEnabled)
			RendererOperations.enableEffect(rendererState, Effect.EFFECT_LIGHTING);
	}
	
	public void renderObject3DTranslucent() {
		
		if(!isVisible())
			return;

		boolean fogEnabled = RendererOperations.isEffectEnabled(rendererState, Effect.EFFECT_FOG);
		boolean lightingEnabled = RendererOperations.isEffectEnabled(rendererState, Effect.EFFECT_LIGHTING);
		
		if(mesh.isDisabled(Effect.EFFECT_FOG))
			RendererOperations.disableEffect(rendererState, Effect.EFFECT_FOG);
		
		if(mesh.isDisabled(Effect.EFFECT_LIGHTING))
			RendererOperations.disableEffect(rendererState, Effect.EFFECT_LIGHTING);
		
		ArrayList<Transform> transforms = getTransforms();
		
		RendererOperations.transform(rendererState, mesh.getRenderMode(), transforms, true, true);
		
		for(int i = 0; i < translucentPoints.size(); i++)
			RendererOperations.renderPolygonList(rendererState, translucentPoints.get(i));
		
		for(int i = 0; i < translucentLines.size(); i++)
			RendererOperations.renderPolygonList(rendererState, translucentLines.get(i));
		
		for(int i = 0; i < translucentPolygons.size(); i++)
			RendererOperations.renderPolygonList(rendererState, translucentPolygons.get(i));
		
		RendererOperations.transform(rendererState, mesh.getRenderMode(), transforms, false, false);
		
		if(fogEnabled)
			RendererOperations.enableEffect(rendererState, Effect.EFFECT_FOG);
		
		if(lightingEnabled)
			RendererOperations.enableEffect(rendererState, Effect.EFFECT_LIGHTING);
	}
	
	public void renderObject2D() {
		
		if(!isVisible())
			return;

		boolean fogEnabled = RendererOperations.isEffectEnabled(rendererState, Effect.EFFECT_FOG);
		boolean lightingEnabled = RendererOperations.isEffectEnabled(rendererState, Effect.EFFECT_LIGHTING);
		
		if(mesh.isDisabled(Effect.EFFECT_FOG))
			RendererOperations.disableEffect(rendererState, Effect.EFFECT_FOG);
		
		if(mesh.isDisabled(Effect.EFFECT_LIGHTING))
			RendererOperations.disableEffect(rendererState, Effect.EFFECT_LIGHTING);
		
		GL gl = rendererState.getGL();
		
		gl.glTranslated(
				rendererState.frame.getWidth() / 2,
				rendererState.frame.getHeight() / 2,
				0);
		
		ArrayList<Transform> transforms = getTransforms();
		
		RendererOperations.transform(rendererState, mesh.getRenderMode(), transforms, true, true);
		
		for(int i = 0; i < points2D.size(); i++)
			RendererOperations.renderPolygonList(rendererState, points2D.get(i));
		
		for(int i = 0; i < lines2D.size(); i++)
			RendererOperations.renderPolygonList(rendererState, lines2D.get(i));
		
		for(int i = 0; i < polygons2D.size(); i++)
			RendererOperations.renderPolygonList(rendererState, polygons2D.get(i));
		
		RendererOperations.transform(rendererState, mesh.getRenderMode(), transforms, false, false);
		
		gl.glTranslated(
				-rendererState.frame.getWidth() / 2,
				-rendererState.frame.getHeight() / 2,
				0);
		
		if(fogEnabled)
			RendererOperations.enableEffect(rendererState, Effect.EFFECT_FOG);
		
		if(lightingEnabled)
			RendererOperations.enableEffect(rendererState, Effect.EFFECT_LIGHTING);
	}
	
	public ArrayList<Transform> getTransforms() {
		
		ArrayList<Transform> requestedTransforms = new ArrayList<Transform>();
		
		Entity entity = mesh.getBody().getEntity();
		
		while(entity != null) {
			requestedTransforms.addAll(0, entity.getBody().getTransforms());
			entity = entity.getParent();
		}
		
		requestedTransforms.addAll(mesh.getTransforms());
		
		return requestedTransforms;
	}
	
	public void update() {
		
		ArrayList<Polygon> newPolygons = new ArrayList<Polygon>();
		
		if(mesh.getPolygons().size() > numPolygons) {
			
			for(int i = numPolygons; i < mesh.getPolygons().size(); i++)
				newPolygons.add(mesh.getPolygons().get(i));
		}
		
		for(int i = 0; i < opaquePoints.size(); i++)
			newPolygons.addAll(opaquePoints.get(i).updateAndSwap());
		
		for(int i = 0; i < opaqueLines.size(); i++)
			newPolygons.addAll(opaqueLines.get(i).updateAndSwap());
		
		for(int i = 0; i < opaquePolygons.size(); i++)
			newPolygons.addAll(opaquePolygons.get(i).updateAndSwap());
		
		for(int i = 0; i < translucentPoints.size(); i++)
			newPolygons.addAll(translucentPoints.get(i).updateAndSwap());
		
		for(int i = 0; i < translucentLines.size(); i++)
			newPolygons.addAll(translucentLines.get(i).updateAndSwap());
		
		for(int i = 0; i < translucentPolygons.size(); i++)
			newPolygons.addAll(translucentPolygons.get(i).updateAndSwap());
		
		for(int i = 0; i < points2D.size(); i++)
			newPolygons.addAll(points2D.get(i).updateAndSwap());
		
		for(int i = 0; i < lines2D.size(); i++)
			newPolygons.addAll(lines2D.get(i).updateAndSwap());
		
		for(int i = 0; i < polygons2D.size(); i++)
			newPolygons.addAll(polygons2D.get(i).updateAndSwap());
		
		if(mesh.getRenderMode().equals(Mesh.RENDER_MODE_3D)) {
			
			while(newPolygons.size() > 0) {
				
				Polygon newPolygon = newPolygons.remove(newPolygons.size() - 1);
				
				if(!RendererOperations.isPolygonTranslucent(rendererState, newPolygon)) {
					
					if(newPolygon.getVertices().size() == 1)
						addPolygonToPolygonLists(newPolygon, opaquePoints);
					
					else if(newPolygon.getVertices().size() == 2)
						addPolygonToPolygonLists(newPolygon, opaqueLines);
					
					else
						addPolygonToPolygonLists(newPolygon, opaquePolygons);
				}
				
				else {
					
					if(newPolygon.getVertices().size() == 1)
						addPolygonToPolygonLists(newPolygon, translucentPoints);
					
					else if(newPolygon.getVertices().size() == 2)
						addPolygonToPolygonLists(newPolygon, translucentLines);
					
					else
						addPolygonToPolygonLists(newPolygon, translucentPolygons);
				}
			}
		}
		
		else {
			
			while(newPolygons.size() > 0) {
				
				Polygon newPolygon = newPolygons.remove(newPolygons.size() - 1);
				
				if(newPolygon.getVertices().size() == 1)
					addPolygonToPolygonLists(newPolygon, points2D);
				
				else if(newPolygon.getVertices().size() == 2)
					addPolygonToPolygonLists(newPolygon, lines2D);
				
				else
					addPolygonToPolygonLists(newPolygon, polygons2D);
			}
		}
		
		numPolygons = 0;
		
		for(int i = 0; i < opaquePoints.size(); i++)
			numPolygons += opaquePoints.get(i).getNumPolygons();
		
		for(int i = 0; i < opaqueLines.size(); i++)
			numPolygons += opaqueLines.get(i).getNumPolygons();
		
		for(int i = 0; i < opaquePolygons.size(); i++)
			numPolygons += opaquePolygons.get(i).getNumPolygons();
		
		for(int i = 0; i < translucentPoints.size(); i++)
			numPolygons += translucentPoints.get(i).getNumPolygons();
		
		for(int i = 0; i < translucentLines.size(); i++)
			numPolygons += translucentLines.get(i).getNumPolygons();
		
		for(int i = 0; i < translucentPolygons.size(); i++)
			numPolygons += translucentPolygons.get(i).getNumPolygons();
		
		for(int i = 0; i < points2D.size(); i++)
			numPolygons += points2D.get(i).getNumPolygons();
		
		for(int i = 0; i < lines2D.size(); i++)
			numPolygons += lines2D.get(i).getNumPolygons();
		
		for(int i = 0; i < polygons2D.size(); i++)
			numPolygons += polygons2D.get(i).getNumPolygons();
	}
	
	private void addPolygonToPolygonLists(Polygon polygon, ArrayList<PolygonList> polygonLists) {
		
		String texture = polygon.getMaterial().getTexture();
		
		if(texture == null)
			polygonLists.get(0).addPolygon(polygon);
		
		else {
			
			boolean newTexture = true;
			
			for(int i = 1; i < polygonLists.size(); i++) {
				
				if(polygonLists.get(i).getTexture().equals(
						polygon.getMaterial().getTexture())) {
					
					polygonLists.get(i).addPolygon(polygon);
					
					newTexture = false;
					break;
				}
			}
			
			if(newTexture) {
				
				PolygonList newPolygonList =
						new PolygonList(
								rendererState,
								mesh,
								polygonLists.get(0).getPolygonType(),
								polygonLists.get(0).getPolygonMode(),
								polygon.getMaterial().getTexture());
				
				newPolygonList.addPolygon(polygon);
				
				polygonLists.add(newPolygonList);
			}
		}
	}
	
	public boolean isDestroyed() {
		
		if(mesh.isDestroyed())
			return true;
		
		Entity entity = mesh.getBody().getEntity();
		
		while(entity != null) {
			
			if(entity.getRunTime().isDestroyed())
				return true;
			
			entity = entity.getParent();
		}
		
		return false;
	}
}