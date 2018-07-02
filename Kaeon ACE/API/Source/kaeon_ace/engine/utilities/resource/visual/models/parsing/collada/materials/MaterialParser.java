package kaeon_ace.engine.utilities.resource.visual.models.parsing.collada.materials;

import java.util.ArrayList;

import kaeon_ace.engine.utilities.geometry.utilities.material.Material;
import xml.XMLElement;

public class MaterialParser {
	
	public static ArrayList<Material> getMaterials(XMLElement collada, String filePath) {
		
		ArrayList<Material> materials = new ArrayList<Material>();
		
		XMLElement libraryMaterials = collada.getElement("library_materials");
		
		for(int i = 0; i < libraryMaterials.getElements().size(); i++) {
			
			Material material = new Material();
			material.setName(libraryMaterials.getElements().get(i).getAttribute("id").getContent());
			
			material.setTexture(
				filePath.substring(0, filePath.lastIndexOf('/') + 1) +
				getImagePathFromEffect(collada,
					libraryMaterials.
					getElements().get(i).
					getElement("instance_effect").
					getAttribute("url").getContent().
					substring(1)));
			
			materials.add(material);
		}
		
		return materials;
	}
	
	private static String getImagePathFromEffect(XMLElement collada, String effectURL) {

		XMLElement libraryEffects = collada.getElement("library_effects");
		
		for(int i = 0; i < libraryEffects.getElements().size(); i++) {
			
			if(libraryEffects.getElements().get(i).getAttribute("id").getContent().equals(effectURL)) {
				
				ArrayList<XMLElement> newParams =
					libraryEffects.getElements().get(i).getElement("profile_COMMON").getElements("newparam");
				
				for(int j = 0; j < newParams.size(); j++) {
					
					if(newParams.get(j).hasElement("surface")) {
						return getImagePathFromImage(
							collada,
							newParams.get(j).
							getElement("surface").
							getElement("init_from").
							getAllContent());
					}
				}
			}
		}
		
		return "";
	}
	
	private static String getImagePathFromImage(XMLElement collada, String imageURL) {
		
		XMLElement libraryImages = collada.getElement("library_images");
		
		for(int i = 0; i < libraryImages.getElements().size(); i++) {
			
			if(libraryImages.getElements().get(i).getAttribute("id").getContent().equals(imageURL))
				return libraryImages.getElements().get(i).getElement("init_from").getAllContent();
		}
		
		return "";
	}
}