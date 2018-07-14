package kaeon_ace_modules.ascent.utilities.geometry.mesh;

import java.util.ArrayList;

import kaeon_ace_modules.ascent.utilities.geometry.utilities.material.Material;
import kaeon_ace_modules.ascent.utilities.math.Vector3;

@SuppressWarnings("rawtypes")
public class Polygon implements Comparable {

	private Mesh mesh;

	private ArrayList<Integer> vertices;
	private ArrayList<Vector3> texCoords;
	
	private Material material;
	
	private boolean destroyed;

	public Polygon() {
		
		vertices = new ArrayList<Integer>();
		texCoords = new ArrayList<Vector3>();
		
		material = new Material();
	}
	
	public Polygon(Polygon polygon) {
		copy(polygon);
	}
	
	public void copy(Polygon polygon) {
		
		vertices = new ArrayList<Integer>(polygon.getVertices());
		
		for(int i = 0; i < polygon.getTexCoords().size(); i++)
			texCoords.add(new Vector3(polygon.getTexCoords().get(i)));
		
		material = new Material(polygon.getMaterial());
	}
	
	public void initialize(Mesh mesh) {
		
		if(this.mesh != null)
			return;
		
		this.mesh = mesh;
	}
	
	public Mesh getMesh() {
		return mesh;
	}

	public void addVertex(Integer vertex) {
		vertices.add(vertex);
		texCoords.add(null);
	}

	public void setTexCoord(Integer vertex, Vector3 texCoord) {
		
		for(int i = 0; i < vertices.size(); i++) {
			
			if(vertices.get(i) == vertex) {
				texCoords.set(i, texCoord);
				break;
			}
		}
	}

	public ArrayList<Integer> getVertices() {
		return vertices;
	}

	public ArrayList<Vector3> getTexCoords() {
		return texCoords;
	}

	public void setMaterial(Material material) {
		
		if(material == null)
			this.material = new Material();
		
		else
			this.material = material;
	}

	public Material getMaterial() {
		return material;
	}
	
	public String getRenderMode() {
		return mesh.getRenderMode();
	}
	
	public boolean isWireframe() {
		return mesh.isWireframe();
	}
	
	public void refresh() {
		
		for(int i = 0; i < vertices.size(); i++) {
			
			if(mesh.getVertex(i).isDestroyed()) {
				
				vertices.remove(i);
				texCoords.remove(i);
				
				i--;
			}
		}
	}
	
	public void destroy() {
		destroyed = true;
	}
	
	public boolean isDestroyed() {
		
		if(mesh.isDestroyed())
			return true;
		
		if(mesh.getBody().getEntity().getRunTime().isDestroyed())
			return true;
		
		return destroyed;
	}
	
	public int compareTo(Object object) {
		
		if(mesh.getRenderMode().equals(Mesh.RENDER_MODE_3D)) {
			return 0;
		}
		
		else {
			
			return
					getRenderPriority() -
					((Polygon) object).getRenderPriority();
		}
	}
	
	public int getRenderPriority() {
		return mesh.getRenderPriority();
	}
}