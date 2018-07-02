package kaeon_ace.engine.utilities.resource.visual.models.parsing;

import java.util.ArrayList;

import io.IO;
import kaeon_ace.engine.utilities.geometry.mesh.Mesh;
import kaeon_ace.engine.utilities.geometry.mesh.Polygon;
import kaeon_ace.engine.utilities.geometry.mesh.Vertex;
import kaeon_ace.engine.utilities.resource.visual.models.Model;

public class OFFParser {
	
	public static void parseAsOFF(Model model, String filePath) {
		
		Mesh mesh = new Mesh();
		
		ArrayList<String> off = IO.openAsList(filePath);
		
		do {
			off.remove(0);
		}
		
		while(off.get(0).charAt(0) == '#');
		
		String line = off.remove(0).trim();
		int numVertices = Integer.parseInt(line.substring(0, line.indexOf(' ')));
		
		for(int i = 0; i < numVertices; i++) {
			
			line = off.remove(0);
			
			double x = Double.parseDouble(line.substring(0, line.indexOf(' ')));
			
			line = line.substring(line.indexOf(' ') + 1).trim();
			double y = Double.parseDouble(line.substring(0, line.indexOf(' ')));
			
			line = line.substring(line.indexOf(' ') + 1).trim();
			double z = Double.parseDouble(line);
			
			mesh.addVertex(new Vertex(x, y, z));
		}
		
		while(off.size() > 0) {
			
			line = off.remove(0);
			line = line.substring(line.indexOf("\t") + 1).trim() + "\t";
			
			Polygon polygon = new Polygon();
			
			while(line.indexOf('\t') > -1) {
				
				polygon.addVertex(Integer.parseInt(line.substring(0, line.indexOf('\t'))));
				
				line = line.substring(line.indexOf('\t') + 1);
			}
			
			mesh.addPolygon(polygon);
		}
		
		model.addMesh(mesh);
	}
}