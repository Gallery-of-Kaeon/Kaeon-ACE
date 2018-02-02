package kaeon_ace.engine.utilities.geometry.mesh;

import java.util.ArrayList;
import java.util.Collections;

import kaeon_ace.engine.processes.scene.utilities.body.Body;
import kaeon_ace.engine.utilities.geometry.utilities.material.Material;
import kaeon_ace.engine.utilities.geometry.utilities.visual.effects.Effect;
import kaeon_ace.engine.utilities.math.transform.Transform;

public class Mesh {
	
	public static String RENDER_MODE_3D =
			"RENDER_MODE_3D";
	
	public static String RENDER_MODE_2D =
			"RENDER_MODE_2D";
	
	public static String RENDER_MODE_2D_ASPECT_OFF =
			"RENDER_MODE_2D_ASPECT_OFF";
	
	public static String RENDER_MODE_2D_ABSOLUTE =
			"RENDER_MODE_2D_ABSOLUTE";
	
	private String id;
	
	private Body body;
	
	private String renderMode;
	private boolean wireframe;
	
	private ArrayList<String> disabledEffects;

	private ArrayList<Transform> transforms;
	private int renderPriority;
	
	private Material material;
	
	private ArrayList<Vertex> vertices;
	private ArrayList<Polygon> polygons;
	
	private boolean visible;
	private boolean destroyed;
	
	public Mesh() {
		
		id = "";
		
		renderMode = RENDER_MODE_3D;
		wireframe = false;
		
		disabledEffects = new ArrayList<String>();
		
		transforms = new ArrayList<Transform>();
		
		material = new Material();

		vertices = new ArrayList<Vertex>();
		polygons = new ArrayList<Polygon>();
		
		visible = false;
		destroyed = false;
	}
	
	public Mesh(Mesh mesh) {
		
		disabledEffects = new ArrayList<String>();
		transforms = new ArrayList<Transform>();
		
		copy(mesh);
	}
	
	public void copy(Mesh mesh) {
		
		id = mesh.getID();
		
		renderMode = mesh.getRenderMode();
		
		material = new Material(mesh.getMaterial());
		
		vertices = new ArrayList<Vertex>();
		
		for(int i = 0; i < mesh.getVertices().size(); i++)
			vertices.add(new Vertex(mesh.getVertices().get(i)));
		
		polygons = new ArrayList<Polygon>();
		
		for(int i = 0; i < mesh.getPolygons().size(); i++)
			polygons.add(new Polygon(mesh.getPolygons().get(i)));
	}
	
	public void initialize(Body body) {
		
		if(this.body != null)
			return;
		
		this.body = body;
		
		visible = true;
		
		onInitialize();
	}
	
	public void onInitialize() {
		
	}
	
	public void setID(String id) {
		this.id = id;
	}
	
	public void setRenderMode(String renderMode) {
		
		if(renderMode != RENDER_MODE_3D &&
				renderMode != RENDER_MODE_2D &&
				renderMode != RENDER_MODE_2D_ASPECT_OFF &&
				renderMode != RENDER_MODE_2D_ABSOLUTE) {
			
			return;
		}
		
		this.renderMode = renderMode;
	}
	
	public void setWireframe(boolean wireframe) {
		this.wireframe = wireframe;
	}
	
	public void enableEffect(String effect) {
		
		if(effect != Effect.EFFECT_LIGHTING &&
				effect != Effect.EFFECT_FOG) {
			
			return;
		}
		
		for(int i = 0; i < disabledEffects.size(); i++) {
			
			if(disabledEffects.get(i).equals(effect)) {
				
				disabledEffects.remove(i);
				
				return;
			}
		}
	}
	
	public void disableEffect(String effect) {
		
		if(effect != Effect.EFFECT_LIGHTING &&
				effect != Effect.EFFECT_FOG) {
			
			return;
		}
		
		boolean disabled = false;
		
		for(int i = 0; i < disabledEffects.size(); i++) {
			
			if(disabledEffects.get(i).equals(effect))
				disabled = true;
		}
		
		if(!disabled)
			disabledEffects.add(effect);
	}
	
	public void transform(Transform transform) {
		
		transform.setMode(renderMode);
		
		transforms.add(transform);
	}
	
	public void setRenderPriority(int renderPriority) {
		this.renderPriority = renderPriority;
	}
	
	public void setMaterial(Material material) {
		this.material = material;
	}
	
	public void addVertex(Vertex vertex) {
		
		if(vertex.getMesh() != null)
			return;
		
		vertex.initialize(this);
		
		vertices.add(vertex);
	}
	
	public void addPolygon(Polygon polygon) {
		
		if(polygon.getMesh() != null)
			return;
		
		polygon.initialize(this);
		
		polygons.add(polygon);
	}
	
	public String getID() {
		return id;
	}
	
	public Body getBody() {
		return body;
	}
	
	public String getRenderMode() {
		return renderMode;
	}
	
	public boolean isWireframe() {
		return wireframe;
	}
	
	public boolean isEnabled(String effect) {
		return !isDisabled(effect);
	}
	
	public boolean isDisabled(String effect) {
		
		for(int i = 0; i < disabledEffects.size(); i++) {
			
			if(effect.equals(disabledEffects.get(i)))
				return true;
		}
		
		return false;
	}
	
	public ArrayList<Transform> getTransforms() {
		return transforms;
	}
	
	public int getRenderPriority() {
		return renderPriority;
	}
	
	public Material getMaterial() {
		return material;
	}
	
	public ArrayList<Vertex> getVertices() {
		return vertices;
	}
	
	public Vertex getVertex(int i) {
		return vertices.get(i);
	}
	
	public ArrayList<Polygon> getPolygons() {
		return polygons;
	}
	
	public void refresh() {
		
		for(int i = 0; i < vertices.size(); i++) {
			
			if(vertices.get(i).isDestroyed()) {
				vertices.remove(i);
				i--;
			}
		}
		
		for(int i = 0; i < polygons.size(); i++) {
			
			if(polygons.get(i).isDestroyed()) {
				polygons.remove(i);
				i--;
			}
			
			else
				polygons.get(i).refresh();
		}
		
		for(int i = 0; i < transforms.size(); i++) {
			
			if(transforms.get(i).isDestroyed()) {
				transforms.remove(i);
				i--;
			}
		}
		
		Collections.sort(transforms);
	}
	
	public boolean isVisible() {
		return visible;
	}
	
	public boolean isDestroyed() {
		return destroyed;
	}
}