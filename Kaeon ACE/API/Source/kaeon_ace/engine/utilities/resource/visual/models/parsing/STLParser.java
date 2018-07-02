package kaeon_ace.engine.utilities.resource.visual.models.parsing;

import java.util.ArrayList;

import io.IO;
import kaeon_ace.engine.utilities.geometry.mesh.Mesh;
import kaeon_ace.engine.utilities.geometry.mesh.Polygon;
import kaeon_ace.engine.utilities.geometry.mesh.Vertex;
import kaeon_ace.engine.utilities.math.Vector3;
import kaeon_ace.engine.utilities.resource.visual.models.Model;

public class STLParser {
	
	public static void parseAsSTL(Model model, String filePath) {
		
		Mesh mesh = new Mesh();
		
		ArrayList<String> stl = IO.openAsList(filePath);
		
		while(stl.size() > 0) {
			
			String line = stl.remove(0).trim();
			
			if(line.indexOf("facet") == 0) {
				
				Vector3 normal = getVector3(line.substring(line.indexOf(' ') + 1).trim());
				
				stl.remove(0);
				
				for(int i = 0; i < 3; i++) {
					
					Vector3 position = getVector3(stl.remove(0).trim());
					
					Vertex vertex = new Vertex();
					
					vertex.setPosition(position);
					vertex.setNormal(normal);
					
					mesh.addVertex(vertex);
				}
				
				for(int i = 0; i < 2; i++)
					stl.remove(0);
				
				Polygon polygon = new Polygon();
				
				polygon.addVertex(mesh.getVertices().size() - 3);
				polygon.addVertex(mesh.getVertices().size() - 2);
				polygon.addVertex(mesh.getVertices().size() - 1);
				
				mesh.addPolygon(polygon);
			}
		}
		
		model.addMesh(mesh);
	}
	
	private static Vector3 getVector3(String line) {
		
		line = line.substring(line.indexOf(' ') + 1).trim();
		double x = Double.parseDouble(line.substring(0, line.indexOf(' ')));
		
		line = line.substring(line.indexOf(' ') + 1).trim();
		double y = Double.parseDouble(line.substring(0, line.indexOf(' ')));
		
		line = line.substring(line.indexOf(' ') + 1).trim();
		double z = Double.parseDouble(line);
		
		return new Vector3(x, y, z);
	}
}