package kaeon_ace.engine.processes.renderer.utilities;

import java.util.ArrayList;

import kaeon_ace.engine.utilities.geometry.mesh.Mesh;
import kaeon_ace.engine.utilities.geometry.mesh.Polygon;

public class PolygonList {
	
	public static String POLYGON_TYPE_POINT = "POLYGON_TYPE_POINT";
	public static String POLYGON_TYPE_LINE = "POLYGON_TYPE_LINE";
	public static String POLYGON_TYPE_TRIANGLE_FAN = "POLYGON_TYPE_TRIANGLE_FAN";
	
	public static String POLYGON_MODE_OPAQUE = "POLYGON_MODE_OPAQUE";
	public static String POLYGON_MODE_TRANSLUCENT = "POLYGON_MODE_TRANSLUCENT";
	public static String POLYGON_MODE_2D = "POLYGON_MODE_2D";
	
	private RendererState rendererState;
	
	private Mesh mesh;
	
	private String polygonType;
	private String polygonMode;
	
	private String renderMode;
	
	private String texture;
	
	private ArrayList<Polygon> polygons;
	
	public PolygonList(RendererState rendererState, Mesh mesh, String polygonType, String polygonMode) {
		
		this.rendererState = rendererState;
		this.mesh = mesh;
		
		setPolygonType(polygonType);
		setPolygonMode(polygonMode);
		
		polygons = new ArrayList<Polygon>();
	}
	
	public PolygonList(RendererState rendererState, Mesh mesh, String polygonType, String polygonMode, String texture) {
		
		this(rendererState, mesh, polygonType, polygonMode);
		
		this.texture = texture;
	}
	
	public void setPolygonType(String polygonType) {
		
		if(polygonType.equals(POLYGON_TYPE_POINT) ||
				polygonType.equals(POLYGON_TYPE_LINE) ||
				polygonType.equals(POLYGON_TYPE_TRIANGLE_FAN))
		{
			this.polygonType = polygonType;
		}
		
		else
			this.polygonType = POLYGON_TYPE_TRIANGLE_FAN;
	}
	
	public void setPolygonMode(String polygonMode) {
		
		if(polygonMode.equals(POLYGON_MODE_OPAQUE) ||
				polygonMode.equals(POLYGON_MODE_TRANSLUCENT) ||
				polygonMode.equals(POLYGON_MODE_2D))
		{
			this.polygonMode = polygonMode;
		}
		
		else
			this.polygonMode = POLYGON_MODE_OPAQUE;
	}
	
	public void setRenderMode(String renderMode) {
		this.renderMode = renderMode;
	}
	
	public void addPolygon(Polygon polygon) {
		polygons.add(polygon);
	}
	
	public Mesh getMesh(){
		return mesh;
	}
	
	public String getPolygonType() {
		return polygonType;
	}
	
	public String getPolygonMode() {
		return polygonMode;
	}
	
	public String getRenderMode() {
		return renderMode;
	}
	
	public String getTexture() {
		return texture;
	}
	
	public ArrayList<Polygon> getPolygons() {
		return polygons;
	}
	
	public int getNumPolygons() {
		return polygons.size();
	}
	
	public ArrayList<Polygon> updateAndSwap() {
		
		boolean polygonsUpdated = false;
		
		ArrayList<Polygon> updatedPolygons = new ArrayList<Polygon>(polygons);
		ArrayList<Polygon> swappedPolygons = new ArrayList<Polygon>();
		
		for(int i = 0; i < updatedPolygons.size(); i++) {
			
			if(updatedPolygons.get(i).isDestroyed()) {
				updatedPolygons.remove(i);
				continue;
			}
			
			if(texture != null) {
			
				if(updatedPolygons.get(i).getMaterial().getTexture() == null) {
					
					swappedPolygons.add(updatedPolygons.remove(i));
					i--;
					
					polygonsUpdated = true;
					
					continue;
				}
				
				else if(!updatedPolygons.get(i).getMaterial().getTexture().equals(texture)) {
					
					swappedPolygons.add(updatedPolygons.remove(i));
					i--;
					
					polygonsUpdated = true;
					
					continue;
				}
				
				else {
					
					boolean translucent =
							RendererOperations.isPolygonTranslucent(
									rendererState,
									updatedPolygons.get(i));
					
					if((translucent && polygonMode.equals(POLYGON_MODE_OPAQUE)) ||
							(!translucent && polygonMode.equals(POLYGON_MODE_TRANSLUCENT))) {
						
						swappedPolygons.add(updatedPolygons.remove(i));
						i--;
						
						polygonsUpdated = true;
						
						continue;
					}
				}
			}
			
			if(polygonType.equals(POLYGON_TYPE_POINT)) {
					
				if(updatedPolygons.get(i).getVertices().size() != 1) {
					
					swappedPolygons.add(updatedPolygons.remove(i));
					i--;
					
					polygonsUpdated = true;
					
					continue;
				}
			}
			
			else if(polygonType.equals(POLYGON_TYPE_LINE)) {
					
				if(updatedPolygons.get(i).getVertices().size() != 2) {
					
					swappedPolygons.add(updatedPolygons.remove(i));
					i--;
					
					polygonsUpdated = true;
					
					continue;
				}
			}
			
			else if(updatedPolygons.get(i).getVertices().size() < 3) {
				
				swappedPolygons.add(updatedPolygons.remove(i));
				i--;
				
				polygonsUpdated = true;
				
				continue;
			}
		}
		
		if(polygonsUpdated)
			polygons = updatedPolygons;
		
		return swappedPolygons;
	}
	
	public String getPolygonTexture(Polygon polygon) {
		return polygon.getMaterial().getTexture();
	}
}