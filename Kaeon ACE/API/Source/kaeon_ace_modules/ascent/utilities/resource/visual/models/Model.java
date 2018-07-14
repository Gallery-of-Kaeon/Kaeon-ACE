package kaeon_ace_modules.ascent.utilities.resource.visual.models;

import java.util.ArrayList;

import kaeon_ace_modules.ascent.processes.scene.utilities.script.Script;
import kaeon_ace_modules.ascent.utilities.geometry.utilities.animation.scripting.Animation;
import kaeon_ace_modules.ascent.utilities.geometry.utilities.animation.skeleton.Skeleton;
import kaeon_ace_modules.ascent.utilities.math.Vector3;
import kaeon_ace_modules.ascent.utilities.resource.visual.models.parsing.OBJParser;
import kaeon_ace_modules.ascent.utilities.resource.visual.models.parsing.OFFParser;
import kaeon_ace_modules.ascent.utilities.resource.visual.models.parsing.PLYParser;
import kaeon_ace_modules.ascent.utilities.resource.visual.models.parsing.STLParser;
import kaeon_ace_modules.ascent.utilities.resource.visual.models.parsing.collada.COLLADAParser;
import kaeon_ace_modules.ascent.utilities.user.GeneralEntity;

public class Model extends GeneralEntity {
	
	public static String TEXTURE_PROFILE_DIRECTX = "TEXTURE_PROFILE_DIRECTX";
	public static String TEXTURE_PROFILE_OPENGL = "TEXTURE_PROFILE_OPENGL";
	
	private String filePath;
	private String textureProfile;
	
	private Skeleton skeleton;
	private ArrayList<Animation> animations;
	
	public Model(String filePath) {
		this(filePath, TEXTURE_PROFILE_DIRECTX);
	}
	
	public Model(String filePath, String textureProfile) {
		
		this.filePath = filePath;
		
		if(textureProfile.equals(TEXTURE_PROFILE_DIRECTX) ||
				textureProfile.equals(TEXTURE_PROFILE_OPENGL)) {
			
			this.textureProfile = textureProfile;
		}
		
		else
			this.textureProfile = TEXTURE_PROFILE_DIRECTX;

		initializeAnimations();
		
		parse();
		scale();
	}
	
	private void initializeAnimations() {
		
		skeleton = new Skeleton(this);
		animations = new ArrayList<Animation>();
		
		addScript(
				
			new Script() {
				
				public void onUpdate() {
					skeleton.animate();
				}
			}
		);
	}
	
	private void parse() {
		
		String extension = filePath.substring(filePath.lastIndexOf('.') + 1);
		
		if(extension.equalsIgnoreCase("dae"))
			COLLADAParser.parseAsCOLLADA(this, filePath);
		
		if(extension.equalsIgnoreCase("obj"))
			OBJParser.parseAsOBJ(this, filePath, textureProfile);
		
		if(extension.equalsIgnoreCase("off"))
			OFFParser.parseAsOFF(this, filePath);
		
		if(extension.equalsIgnoreCase("ply"))
			PLYParser.parseAsPLY(this, filePath);
		
		if(extension.equalsIgnoreCase("stl"))
			STLParser.parseAsSTL(this, filePath);
	}
	
	private void scale() {
		
		ArrayList<Vector3> positions = getPositions();

		if(positions.size() == 0)
			return;
		
		Vector3 referencePosition = positions.get(0);
		
		Vector3 minBounds = new Vector3(referencePosition);
		Vector3 maxBounds = new Vector3(referencePosition);
		
		for(int i = 0; i < positions.size(); i++) {
			
			Vector3 position = positions.get(i);
			
			minBounds.setX(
					position.getX() < minBounds.getX() ?
							position.getX() : minBounds.getX());
			
			minBounds.setY(
					position.getY() < minBounds.getY() ?
							position.getY() : minBounds.getY());
			
			minBounds.setZ(
					position.getZ() < minBounds.getZ() ?
							position.getZ() : minBounds.getZ());
			
			maxBounds.setX(
					position.getX() > maxBounds.getX() ?
							position.getX() : maxBounds.getX());
			
			maxBounds.setY(
					position.getY() > maxBounds.getY() ?
							position.getY() : maxBounds.getY());
			
			maxBounds.setZ(
					position.getZ() > maxBounds.getZ() ?
							position.getZ() : maxBounds.getZ());
		}
		
		Vector3 bounds =
				new Vector3(
						Math.abs(maxBounds.getX() - minBounds.getX()),
						Math.abs(maxBounds.getY() - minBounds.getY()),
						Math.abs(maxBounds.getZ() - minBounds.getZ()));
		
		Vector3 scale = new Vector3(bounds);
		
		scale.setMagnitude(1);
		
		scale.set(
				scale.getX() / bounds.getX(),
				scale.getY() / bounds.getY(),
				scale.getZ() / bounds.getZ());
		
		Vector3 offset =
				new Vector3(
						(scale.getX() * minBounds.getX()) + ((scale.getX() * maxBounds.getX())),
						(scale.getY() * minBounds.getY()) + ((scale.getY() * maxBounds.getY())),
						(scale.getZ() * minBounds.getZ()) + ((scale.getZ() * maxBounds.getZ())));
		
		for(int i = 0; i < positions.size(); i++) {
		
			Vector3 position = positions.get(i);
			
			position.setX((position.getX() * scale.getX()) - (offset.getX() / 2));
			position.setY((position.getY() * scale.getY()) - (offset.getY() / 2));
			position.setZ((position.getZ() * scale.getZ()) - (offset.getZ() / 2));
		}
	}
	
	private ArrayList<Vector3> getPositions() {
		
		ArrayList<Vector3> positions = new ArrayList<Vector3>();
		
		for(int i = 0; i < getBody().getMeshes().size(); i++) {
			
			for(int j = 0; j < getBody().getMeshes().get(i).getVertices().size(); j++)
				positions.add(getBody().getMeshes().get(i).getVertex(j).getPosition());
		}
		
		return positions;
	}
	
	public Skeleton getSkeleton() {
		return skeleton;
	}
	
	public ArrayList<Animation> getAnimations() {
		return animations;
	}
}