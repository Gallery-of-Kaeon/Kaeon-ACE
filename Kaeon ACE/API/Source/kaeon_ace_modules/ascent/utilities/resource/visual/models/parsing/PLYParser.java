package kaeon_ace_modules.ascent.utilities.resource.visual.models.parsing;

import java.util.ArrayList;

import io.IO;
import kaeon_ace_modules.ascent.utilities.geometry.mesh.Mesh;
import kaeon_ace_modules.ascent.utilities.geometry.mesh.Polygon;
import kaeon_ace_modules.ascent.utilities.geometry.mesh.Vertex;
import kaeon_ace_modules.ascent.utilities.geometry.utilities.visual.Color;
import kaeon_ace_modules.ascent.utilities.math.Vector3;
import kaeon_ace_modules.ascent.utilities.resource.visual.models.Model;

public class PLYParser {
	
	public static void parseAsPLY(Model model, String filePath) {
		
		Mesh mesh = new Mesh();
		
		ArrayList<String> ply = IO.openAsList(filePath);
		
		String line = "";
		
		int numVertices = 0;
		boolean particleCloud = false;
		
		while(true) {
			
			line = ply.remove(0).trim();
			
			if(line.indexOf("element vertex ") == 0)
				numVertices = Integer.parseInt(line.substring(15));
			
			if(line.indexOf("element face ") == 0) {
				
				if(Integer.parseInt(line.substring(13)) == 0)
					particleCloud = true;
			}
			
			else if(line.equals("end_header"))
				break;
		}
		
		ArrayList<Vector3> vertices = new ArrayList<Vector3>();
		ArrayList<Color> colors = new ArrayList<Color>();
		
		for(int i = 0; i < numVertices; i++) {
			
			line = ply.remove(0);
			
			double x = Double.parseDouble(line.substring(0, line.indexOf(' ')));
			
			line = line.substring(line.indexOf(' ') + 1).trim();
			double y = Double.parseDouble(line.substring(0, line.indexOf(' ')));
			
			line = line.substring(line.indexOf(' ') + 1).trim();
			
			double z = 0;
			
			if(!particleCloud)
				z = Double.parseDouble(line);
			
			if(particleCloud) {
				
				z = Double.parseDouble(line.substring(0, line.indexOf(' ')));
				
				line = line.substring(line.indexOf(' ') + 1).trim();
				double red = Double.parseDouble(line.substring(0, line.indexOf(' '))) / 255;
				
				line = line.substring(line.indexOf(' ') + 1).trim();
				double green = Double.parseDouble(line.substring(0, line.indexOf(' '))) / 255;
				
				line = line.substring(line.indexOf(' ') + 1).trim();
				double blue = Double.parseDouble(line) / 255;
				
				colors.add(new Color(red, green, blue));
			}
			
			mesh.addVertex(new Vertex(x, y, z));
		}
		
		if(!particleCloud) {
		
			while(ply.size() > 0) {
				
				line = ply.remove(0);
				line = line.substring(line.indexOf(" ") + 1).trim() + " ";
				
				Polygon polygon = new Polygon();
				
				while(line.indexOf(' ') > -1) {
					
					polygon.addVertex(Integer.parseInt(line.substring(0, line.indexOf(' '))));
					
					line = line.substring(line.indexOf(' ') + 1);
				}
				
				mesh.addPolygon(polygon);
			}
		}
		
		else {
			
			for(int i = 0; i < vertices.size(); i++) {
				
				mesh.getVertex(i).setColor(colors.get(i));
				
				Polygon particle = new Polygon();
				particle.addVertex(i);
				
				mesh.addPolygon(particle);
			}
		}
		
		model.addMesh(mesh);
	}
}