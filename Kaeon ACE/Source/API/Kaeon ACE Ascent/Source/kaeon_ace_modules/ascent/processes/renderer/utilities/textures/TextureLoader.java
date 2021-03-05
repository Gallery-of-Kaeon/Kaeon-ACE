package kaeon_ace_modules.ascent.processes.renderer.utilities.textures;

import java.util.ArrayList;

import javax.media.opengl.GL;

public class TextureLoader {

	private ArrayList<Texture> textures = new ArrayList<Texture>();
	
	public TextureLoader() {
		textures = new ArrayList<Texture>();
	}
	
	public Texture getTexture(String texture) {
		
		for(int i = 0; i < textures.size(); i++)
			
			if(textures.get(i).getPath().equals(texture))
				return textures.get(i);
		
		textures.add(new Texture(texture));
		
		return textures.get(textures.size() - 1);
	}
	
	public void loadTextures(GL gl) {
		
		for(int i = 0; i < textures.size(); i++) {
			
			if(!textures.get(i).isLoaded())
				textures.get(i).loadTexture(gl);
		}
	}
	
	public void dispose() {
		textures = new ArrayList<Texture>();
	}
}