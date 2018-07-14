package kaeon_ace_modules.ascent.utilities.resource.visual.models.parsing;

import java.util.ArrayList;

import kaeon_ace_modules.ascent.utilities.geometry.mesh.Mesh;
import kaeon_ace_modules.ascent.utilities.resource.visual.models.Model;
import kaeon_ace_modules.ascent.utilities.resource.visual.models.parsing.collada.GeometryParser;
import kaeon_ace_modules.ascent.utilities.resource.visual.models.parsing.collada.MaterialParser;
import xml.XML;
import xml.XMLElement;

public class COLLADAParser {
	
	public static void parseAsCOLLADA(Model model, String filePath) {
		
		XMLElement collada = new XML(filePath).getElement("COLLADA");
		
		ArrayList<Mesh> meshes = GeometryParser.getGeometries(
				collada.getElement("library_geometries"),
				MaterialParser.getMaterials(collada, filePath));
		
		for(int i = 0; i < meshes.size(); i++)
			model.getBody().addMesh(meshes.get(i));
	}
}