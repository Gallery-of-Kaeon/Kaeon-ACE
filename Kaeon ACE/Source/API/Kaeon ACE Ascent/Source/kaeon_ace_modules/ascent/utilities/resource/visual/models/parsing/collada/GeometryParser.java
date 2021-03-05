package kaeon_ace_modules.ascent.utilities.resource.visual.models.parsing.collada;

import java.util.ArrayList;
import java.util.Collections;

import kaeon_ace_modules.ascent.utilities.geometry.mesh.Mesh;
import kaeon_ace_modules.ascent.utilities.geometry.mesh.Polygon;
import kaeon_ace_modules.ascent.utilities.geometry.mesh.Vertex;
import kaeon_ace_modules.ascent.utilities.geometry.utilities.material.Material;
import kaeon_ace_modules.ascent.utilities.math.Vector3;
import xml.XMLElement;

public class GeometryParser {
	
	public static ArrayList<Mesh> getGeometries(
			XMLElement libraryGeometries,
			ArrayList<Material> materials) {
		
		ArrayList<Mesh> meshes = new ArrayList<Mesh>();
		
		for(int i = 0; i < libraryGeometries.getElements().size(); i++) {
			
			Mesh mesh = getMesh(libraryGeometries.getElements().get(i).getElement("mesh"), materials);
			
			if(libraryGeometries.getElements().get(i).getAttribute("id") != null)
				mesh.setID(libraryGeometries.getElements().get(i).getAttribute("id").getContent());
			
			meshes.add(mesh);
		}
		
		return meshes;
	}
	
	private static Mesh getMesh(XMLElement geometry, ArrayList<Material> materials) {
		
		Mesh mesh = new Mesh();
		
		ArrayList<Source> sources = new ArrayList<Source>();
		
		ArrayList<XMLElement> sourceData = geometry.getElements("source");
		
		XMLElement vertices = geometry.getElement("vertices");
		
		String positionsID = vertices.getElement("input").getAttribute("source").getContent().substring(1);
		String verticesID = vertices.getAttribute("id").getContent();
		
		for(int i = 0; i < sourceData.size(); i++) {
			
			Source source = new Source(sourceData.get(i));
			
			if(source.getID().equals(positionsID))
				source.setID(verticesID);
			
			sources.add(source);
		}
		
		processVertices(mesh, verticesID, sources);
		
		ArrayList<XMLElement> triangles = geometry.getElements("triangles");
		
		for(int i = 0; i < triangles.size(); i++)
			processTriangles(mesh, triangles.get(i), materials, sources);
		
		return mesh;
	}
	
	private static void processVertices(
			Mesh mesh,
			String verticesID,
			ArrayList<Source> sources) {
		
		Source vertexSource = null;
		
		for(int i = 0; i < sources.size(); i++) {
			
			if(sources.get(i).getID().equals(verticesID)) {
				vertexSource = sources.get(i);
				break;
			}
		}
		
		for(int i = 0; i < vertexSource.getNumUnits(); i++) {
			
			ArrayList<Float> vertexData = vertexSource.getUnit(i);
			
			mesh.addVertex(new Vertex(vertexData.get(0), vertexData.get(1), vertexData.get(2)));
		}
	}
	
	private static void processTriangles(
			Mesh mesh,
			XMLElement triangles,
			ArrayList<Material> materials,
			ArrayList<Source> sources) {

		Material material = getMaterial(triangles, materials);
		ArrayList<Input> inputs = getInputs(triangles, sources);
		
		ArrayList<Integer> indexArray = StringProcessor.getIntArray(triangles.getElement("p").getAllContent());
		int vertexOffset = 0;
		
		for(int i = 0; i < indexArray.size(); i += 3 * inputs.size()) {
			
			Polygon polygon = new Polygon();
			polygon.setMaterial(material);
			
			for(int j = 0; j < inputs.size(); j++) {
				
				if(inputs.get(j).getSemantic().equals("VERTEX")) {
					
					vertexOffset = j;
					
					polygon.addVertex(indexArray.get(i + j));
					polygon.addVertex(indexArray.get(i + inputs.size() + j));
					polygon.addVertex(indexArray.get(i + (2 * inputs.size()) + j));
				}
				
				if(inputs.get(j).getSemantic().equals("NORMAL")) {
					
					Source normalSource = inputs.get(j).getSource();

					ArrayList<Float> normalData = normalSource.getUnit(indexArray.get(i + j));
					
					mesh.getVertex(
							indexArray.get(i + vertexOffset)).setNormal(
									new Vector3(normalData.get(0), normalData.get(1), normalData.get(2)));

					normalData = normalSource.getUnit(indexArray.get(i + inputs.size() + j));
					
					mesh.getVertex(
							indexArray.get(i + inputs.size() + vertexOffset)).setNormal(
									new Vector3(normalData.get(0), normalData.get(1), normalData.get(2)));

					normalData = normalSource.getUnit(indexArray.get(i + (2 * inputs.size()) + j));
					
					mesh.getVertex(
							indexArray.get(i + (2 * inputs.size()) + vertexOffset)).setNormal(
									new Vector3(normalData.get(0), normalData.get(1), normalData.get(2)));
				}
				
				if(inputs.get(j).getSemantic().equals("TEXCOORD")) {
					
					Source textureSource = inputs.get(j).getSource();

					ArrayList<Float> textureData = textureSource.getUnit(indexArray.get(i + j));
					
					polygon.setTexCoord(
							indexArray.get(i + vertexOffset),
							new Vector3(
									textureData.get(0),
									1 - textureData.get(1),
									textureData.size() > 2 ? textureData.get(2) : 0));

					textureData = textureSource.getUnit(indexArray.get(i + inputs.size() + j));
					
					polygon.setTexCoord(
							indexArray.get(i + inputs.size() + vertexOffset),
							new Vector3(
									textureData.get(0),
									1 - textureData.get(1),
									textureData.size() > 2 ? textureData.get(2) : 0));

					textureData = textureSource.getUnit(indexArray.get(i + (2 * inputs.size()) + j));
					
					polygon.setTexCoord(
							indexArray.get(i + (2 * inputs.size()) + vertexOffset),
							new Vector3(
									textureData.get(0),
									1 - textureData.get(1),
									textureData.size() > 2 ? textureData.get(2) : 0));
				}
			}
			
			mesh.addPolygon(polygon);
		}
	}
	
	private static Material getMaterial(XMLElement polygons, ArrayList<Material> materials) {
		
		String materialName = polygons.getAttribute("material").getContent();
		
		for(int i = 0; i < materials.size(); i++) {
			
			if(materials.get(i).getName().equals(materialName))
				return materials.get(i);
		}
		
		return null;
	}
	
	private static ArrayList<Input> getInputs(XMLElement polygons, ArrayList<Source> sources) {
		
		ArrayList<Input> inputs = new ArrayList<Input>();
		
		ArrayList<XMLElement> inputData = polygons.getElements("input");
		
		for(int i = 0; i < inputData.size(); i++)
			inputs.add(new Input(inputData.get(i), sources));
		
		Collections.sort(inputs);
		
		return inputs;
	}
}