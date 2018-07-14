package kaeon_ace_modules.ascent.utilities.resource.visual.models.parsing;

import java.util.ArrayList;

import io.IO;
import kaeon_ace_modules.ascent.utilities.geometry.mesh.Mesh;
import kaeon_ace_modules.ascent.utilities.geometry.mesh.Polygon;
import kaeon_ace_modules.ascent.utilities.geometry.mesh.Vertex;
import kaeon_ace_modules.ascent.utilities.geometry.utilities.material.Material;
import kaeon_ace_modules.ascent.utilities.math.Vector3;
import kaeon_ace_modules.ascent.utilities.resource.visual.ImageLoader;
import kaeon_ace_modules.ascent.utilities.resource.visual.models.Model;

public class OBJParser {
	
	public static void parseAsOBJ(Model model, String filePath, String textureProfile) {
		
		Mesh mesh = new Mesh();
		
		ArrayList<String> obj = IO.openAsList(filePath);
		
		ArrayList<Material> materials = null;
		Material material = null;
		
		ArrayList<Vector3> texCoords = new ArrayList<Vector3>();
		ArrayList<Vector3> normals = new ArrayList<Vector3>();
		
		for(int i = 0; i < obj.size(); i++) {
			
			String line = obj.get(i).trim();
			
			if(line.indexOf('#') == 0)
				continue;
			
			if(line.indexOf("mtllib") == 0) {
				
				materials = getMaterials(
						filePath.substring(0, filePath.lastIndexOf('/') + 1),
						line.substring(line.indexOf("mtllib") + 7));
				
				continue;
			}
			
			if(line.indexOf("v ") == 0) {
				
				mesh.addVertex(new Vertex(getVector3(line)));
				
				continue;
			}
			
			if(line.indexOf("vt ") == 0) {
				
				Vector3 texCoord = getVector3(line);
				
				if(textureProfile.equals(Model.TEXTURE_PROFILE_DIRECTX))
					texCoord.setY(1 - texCoord.getY());
				
				texCoords.add(texCoord);
				
				continue;
			}
			
			if(line.indexOf("vn ") == 0) {
				
				normals.add(getVector3(line));
				
				continue;
			}
			
			if(line.indexOf("usemtl ") == 0) {
				
				line = line.substring(line.indexOf(' ') + 1).trim();
				
				for(int j = 0; j < materials.size(); j++) {
					
					if(materials.get(j).getName().equals(line)) {
						material = materials.get(j);
						break;
					}
				}
				
				continue;
			}
			
			if(line.indexOf("f ") == 0) {
				
				mesh.addPolygon(getPolygon(mesh, line, material, texCoords, normals));
				
				continue;
			}
		}
		
		model.addMesh(mesh);
	}
	
	private static ArrayList<Material> getMaterials(String directory, String mtlPath) {
		
		ArrayList<String> mtl = null;
		
		try {
			mtl = IO.openAsList(directory + mtlPath);
		}
		
		catch(Exception exception) {
			return new ArrayList<Material>();
		}
		
		ArrayList<Material> materials = new ArrayList<Material>();
		Material material = null;
		
		for(int i = 0; i < mtl.size(); i++) {
			
			String line = mtl.get(i).trim();
			
			if(line.indexOf('#') == 0)
				continue;
			
			if(line.indexOf("newmtl ") == 0) {
				
				if(material != null)
					materials.add(material);
				
				material = new Material();
				
				material.setName(line.substring(line.indexOf(' ')).trim());
				
				continue;
			}
			
			if(line.indexOf("map_Kd ") == 0) {
				
				material.setTexture(directory + line.substring(line.indexOf(' ')).trim());
				
				continue;
			}
			
			if(line.indexOf("map_Ka ") == 0) {
				
				material.normal = ImageLoader.getBufferedImage(directory + line.substring(line.indexOf(' ')).trim());
				
				continue;
			}
			
			if(line.indexOf("Kd ") == 0) {
				
				line = line.substring(line.indexOf(' ') + 1).trim();
				
				double red = Double.parseDouble(line.substring(0, line.indexOf(' ')));
				line = line.substring(line.indexOf(' ') + 1).trim();
				
				double green = Double.parseDouble(line.substring(0, line.indexOf(' ')));
				line = line.substring(line.indexOf(' ') + 1).trim();
				
				double blue = Double.parseDouble(line);
				
				material.setColor(red, green, blue);
				
				continue;
			}
			
			if(line.indexOf("d ") == 0) {
				
				line = line.substring(line.indexOf(' ') + 1).trim();
				
				material.getColor().setAlpha(Double.parseDouble(line));
			}
			
			if(line.indexOf("Tr ") == 0) {
				
				line = line.substring(line.indexOf(' ') + 1).trim();
				
				material.getColor().setAlpha(1 - Double.parseDouble(line));
			}
		}
		
		materials.add(material);
		
		return materials;
	}
	
	private static Vector3 getVector3(String line) {
		
		line = line.substring(line.indexOf(' ') + 1).trim();
		
		double x = Double.parseDouble(line.substring(0, line.indexOf(' ')));
		
		line = line.substring(line.indexOf(' ') + 1).trim();
		
		double y = 0;
		double z = 0;
		
		if(line.indexOf(' ') > -1) {
			
			y = Double.parseDouble(line.substring(0, line.indexOf(' ')));
		
			line = line.substring(line.indexOf(' ') + 1).trim();
			z = Double.parseDouble(line);
		}
		
		else
			y = Double.parseDouble(line);
		
		return new Vector3(x, y, z);
	}
	
	private static Polygon getPolygon(
			Mesh mesh,
			String line,
			Material material,
			ArrayList<Vector3> texCoords,
			ArrayList<Vector3> normals) {
		
		Polygon polygon = new Polygon();
		
		polygon.setMaterial(material);
		
		line = line.substring(line.indexOf(' ')).trim();
		
		while(true) {
			
			if(line.indexOf(' ') > -1) {
		
				setVertex(
					mesh,
					polygon,
					line.substring(0, line.indexOf(' ')),
					texCoords,
					normals);
				
				line = line.substring(line.indexOf(' ')).trim();
			}
			
			else {
		
				setVertex(
					mesh,
					polygon,
					line,
					texCoords,
					normals);
				
				break;
			}
		}
		
		return polygon;
	}
	
	private static void setVertex(
			Mesh mesh,
			Polygon polygon,
			String line,
			ArrayList<Vector3> texCoords,
			ArrayList<Vector3> normals) {
		
		Integer position = 0;
		Integer texCoord = null;
		Integer normal = null;
		
		int slashes = 0;
		
		for(int i = 0; i < line.length(); i++) {
			
			if(line.charAt(i) == '/')
				slashes++;
		}
		
		if(slashes == 0)
			position = Integer.parseInt(line) - 1;
		
		if(slashes == 1) {
			
			position = Integer.parseInt(line.substring(0, line.indexOf('/'))) - 1;
			
			line = line.substring(line.indexOf('/') + 1);
			
			texCoord = Integer.parseInt(line) - 1;
		}
		
		if(slashes == 2) {
			
			position = Integer.parseInt(line.substring(0, line.indexOf('/'))) - 1;
			
			line = line.substring(line.indexOf('/') + 1);
			
			if(line.indexOf('/') == 0) {
				
				line = line.substring(1);
				
				normal = Integer.parseInt(line) - 1;
			}
			
			else {
				
				texCoord = Integer.parseInt(line.substring(0, line.indexOf('/'))) - 1;
				
				line = line.substring(line.indexOf('/') + 1);
				
				normal = Integer.parseInt(line) - 1;
			}
		}
		
		polygon.addVertex(position);
		
		if(texCoord != null)
			polygon.setTexCoord(position, texCoords.get(texCoord));
		
		if(normal != null)
			mesh.getVertex(position).setNormal(normals.get(normal));
	}
}