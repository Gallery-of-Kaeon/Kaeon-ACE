package kaeon_ace_modules.ascent.processes.renderer.utilities.textures;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.media.opengl.GL;

import com.sun.opengl.util.texture.TextureData;
import com.sun.opengl.util.texture.TextureIO;

import kaeon_ace_modules.ascent.utilities.resource.visual.ImageLoader;

public class Texture {
	
	private com.sun.opengl.util.texture.Texture texture;
	private String path;
	
	private boolean loaded;
	
	private boolean hasAlpha;
	
	public Texture(String path) {
		this.path = path;
		loaded = false;
	}
	
	public void loadTexture(GL gl) {
		
		try {
			
			texture = createTexture("/" + path);
			
			checkForAlpha();
			
			enable();
			
			gl.glGenerateMipmapEXT(GL.GL_TEXTURE_2D);
			
			gl.glTexParameteri(
					GL.GL_TEXTURE_2D,
					GL.GL_TEXTURE_MIN_FILTER,
					GL.GL_LINEAR_MIPMAP_LINEAR);
			
			gl.glTexParameterf(
					GL.GL_TEXTURE_2D,
					GL.GL_TEXTURE_MAX_ANISOTROPY_EXT,
					16);
			
			disable();
		}
		
		catch(Exception exception) {
			hasAlpha = false;
		}
		
		loaded = true;
	}
	
	private void checkForAlpha() {
		
		String fileType = path.substring(path.lastIndexOf('.') + 1);
		
		if( ! (fileType.equalsIgnoreCase("jpg") || fileType.equalsIgnoreCase("jpeg"))) {
			
			BufferedImage image = ImageLoader.getBufferedImage(path);
	
			for(int x = 0; x < image.getWidth() && !hasAlpha; x++) {
				for(int y = 0; y < image.getHeight() && !hasAlpha; y++) {
					
					int alpha = (image.getRGB(x, y) & 0xff000000) >>> 24;
					
					if(alpha < 255)
						hasAlpha = true;
				}
			}
		}
	}
	
	public com.sun.opengl.util.texture.Texture getTexture() {
		return texture;
	}
	
	public String getPath() {
		return path;
	}
	
	public boolean isLoaded() {
		return loaded;
	}
	
	public boolean hasAlpha() {
		return hasAlpha;
	}
	
	public void enable() {
		
		if(texture != null) {
			texture.enable();
			texture.bind();
		}
	}
	
	public void disable() {
		
		if(texture != null)
			texture.disable();
	}
	
	private com.sun.opengl.util.texture.Texture createTexture(String texture) {
		
		TextureData data = null;
		
		try {
			
			InputStream stream = getClass().getResourceAsStream(texture);
			
			data = TextureIO.newTextureData(stream, false,
					texture.substring(texture.lastIndexOf(".") + 1));
		}
		
		catch (IOException exc) {
			data = null;
		}
		
		if(data != null)
			return TextureIO.newTexture(data);
		
		else
			return null;
	}
}