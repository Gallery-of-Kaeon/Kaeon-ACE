package kaeon_ace.engine.utilities.resource.visual;

import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;

import javax.swing.ImageIcon;

public class ImageProcessor {
	
	public static void shiftColor(BufferedImage image,
			int alpha, int red, int green, int blue) {
		
		for(int x = 0; x < image.getWidth(); x++) {
			for(int y = 0; y < image.getHeight(); y++) {
				
				int rgb = image.getRGB(x, y);
				
				int a = rgb & 0xff000000;
				a = a >>> 24;
				a = shiftValue(a, alpha);
				
				int r = rgb & 0x00ff0000;
				r = r >>> 16;
				r = shiftValue(r, red);
		
				int g = rgb & 0x0000ff00;
				g = g >>> 8;
				g = shiftValue(g, green);
			
				int b = rgb & 0x000000ff;
				b = shiftValue(b, blue);
				
				a = a << 24;
				r = r << 16;
				g = g << 8;
				
				int color = a | r | g | b;
				
				image.setRGB(x, y, color);
			}
		}
	}
	
	public static void shiftAlpha(BufferedImage image, int alpha) {
		
		for(int x = 0; x < image.getWidth(); x++) {
			for(int y = 0; y < image.getHeight(); y++) {
				
				int rgb = image.getRGB(x, y);
				
				int a = rgb & 0xff000000;
				a = a >>> 24;
				a = shiftValue(a, alpha);
				
				int r = rgb & 0x00ff0000;
				r = r >>> 16;
		
				int g = rgb & 0x0000ff00;
				g = g >>> 8;
			
				int b = rgb & 0x000000ff;
				
				a = a << 24;
				r = r << 16;
				g = g << 8;
				
				int color = a | r | g | b;
				
				image.setRGB(x, y, color);
			}
		}
	}
	
	public static void shiftRed(BufferedImage image, int red) {
		
		for(int x = 0; x < image.getWidth(); x++) {
			for(int y = 0; y < image.getHeight(); y++) {
				
				int rgb = image.getRGB(x, y);
				
				int a = rgb & 0xff000000;
				a = a >>> 24;
				
				int r = rgb & 0x00ff0000;
				r = r >>> 16;
				r = shiftValue(r, red);
		
				int g = rgb & 0x0000ff00;
				g = g >>> 8;
			
				int b = rgb & 0x000000ff;
				
				a = a << 24;
				r = r << 16;
				g = g << 8;
				
				int color = a | r | g | b;
				
				image.setRGB(x, y, color);
			}
		}
	}
	
	public static void shiftGreen(BufferedImage image, int green) {
		
		for(int x = 0; x < image.getWidth(); x++) {
			for(int y = 0; y < image.getHeight(); y++) {
				
				int rgb = image.getRGB(x, y);
				
				int a = rgb & 0xff000000;
				a = a >>> 24;
				
				int r = rgb & 0x00ff0000;
				r = r >>> 16;
		
				int g = rgb & 0x0000ff00;
				g = g >>> 8;
				g = shiftValue(g, green);
			
				int b = rgb & 0x000000ff;
				
				a = a << 24;
				r = r << 16;
				g = g << 8;
				
				int color = a | r | g | b;
				
				image.setRGB(x, y, color);
			}
		}
	}
	
	public static void shiftBlue(BufferedImage image, int blue) {
		
		for(int x = 0; x < image.getWidth(); x++) {
			for(int y = 0; y < image.getHeight(); y++) {
				
				int rgb = image.getRGB(x, y);
				
				int a = rgb & 0xff000000;
				a = a >>> 24;
				
				int r = rgb & 0x00ff0000;
				r = r >>> 16;
		
				int g = rgb & 0x0000ff00;
				g = g >>> 8;
			
				int b = rgb & 0x000000ff;
				b = shiftValue(b, blue);
				
				a = a << 24;
				r = r << 16;
				g = g << 8;
				
				int color = a | r | g | b;
				
				image.setRGB(x, y, color);
			}
		}
	}
	
	private static int shiftValue(int color, int value) {
		
		color += value;
		
		if(color > 0x000000ff)
			color = 0x000000ff;
		
		if(color < 0x00000000)
			color = 0x00000000;
		
		return color;
	}
	
	public static void setColor(BufferedImage image,
			int alpha, int red, int green, int blue,
			int x, int y) {
		
		int rgb = image.getRGB(x, y);
		
		int a = rgb & 0xff000000;
		a = a >>> 24;
		a = setValue(a, alpha);
		
		int r = rgb & 0x00ff0000;
		r = r >>> 16;
		r = setValue(r, red);

		int g = rgb & 0x0000ff00;
		g = g >>> 8;
		g = setValue(g, green);
	
		int b = rgb & 0x000000ff;
		b = setValue(b, blue);
		
		a = a << 24;
		r = r << 16;
		g = g << 8;
		
		int color = a | r | g | b;
		
		image.setRGB(x, y, color);
	}
	
	public static void setAlpha(BufferedImage image, int alpha, int x, int y) {
		
		int rgb = image.getRGB(x, y);
		
		int a = rgb & 0xff000000;
		a = a >>> 24;
		a = setValue(a, alpha);
		
		int r = rgb & 0x00ff0000;
		r = r >>> 16;

		int g = rgb & 0x0000ff00;
		g = g >>> 8;
	
		int b = rgb & 0x000000ff;
		
		a = a << 24;
		r = r << 16;
		g = g << 8;
		
		int color = a | r | g | b;
		
		image.setRGB(x, y, color);
	}
	
	public static void setRed(BufferedImage image, int red, int x, int y) {
		
		int rgb = image.getRGB(x, y);
		
		int a = rgb & 0xff000000;
		a = a >>> 24;
		
		int r = rgb & 0x00ff0000;
		r = r >>> 16;
		r = setValue(r, red);

		int g = rgb & 0x0000ff00;
		g = g >>> 8;
	
		int b = rgb & 0x000000ff;
		
		a = a << 24;
		r = r << 16;
		g = g << 8;
		
		int color = a | r | g | b;
		
		image.setRGB(x, y, color);
	}
	
	public static void setGreen(BufferedImage image, int green, int x, int y) {
		
		int rgb = image.getRGB(x, y);
		
		int a = rgb & 0xff000000;
		a = a >>> 24;
		
		int r = rgb & 0x00ff0000;
		r = r >>> 16;

		int g = rgb & 0x0000ff00;
		g = g >>> 8;
		g = setValue(g, green);
	
		int b = rgb & 0x000000ff;
		
		a = a << 24;
		r = r << 16;
		g = g << 8;
		
		int color = a | r | g | b;
		
		image.setRGB(x, y, color);
	}
	
	public static void setBlue(BufferedImage image, int blue, int x, int y) {
		
		int rgb = image.getRGB(x, y);
		
		int a = rgb & 0xff000000;
		a = a >>> 24;
		
		int r = rgb & 0x00ff0000;
		r = r >>> 16;

		int g = rgb & 0x0000ff00;
		g = g >>> 8;
	
		int b = rgb & 0x000000ff;
		b = setValue(b, blue);
		
		a = a << 24;
		r = r << 16;
		g = g << 8;
		
		int color = a | r | g | b;
		
		image.setRGB(x, y, color);
	}
	
	private static int setValue(int color, int value) {
		
		color = value;
		
		if(color > 0x000000ff)
			color = 0x000000ff;
		
		if(color < 0x00000000)
			color = 0x00000000;
		
		return color;
	}
	
	public static int getAlpha(BufferedImage image, int x, int y) {
		
		int rgb = image.getRGB(x, y);
		
		int alpha = rgb & 0xff000000;
		alpha = alpha >>> 24;
		
		return alpha;
	}
	
	public static int getRed(BufferedImage image, int x, int y) {
		
		int rgb = image.getRGB(x, y);
		
		int red = rgb & 0x00ff0000;
		red = red >>> 16;
		
		return red;
	}
	
	public static int getGreen(BufferedImage image, int x, int y) {
		
		int rgb = image.getRGB(x, y);

		int green = rgb & 0x0000ff00;
		green = green >>> 8;
		
		return green;
	}
	
	public static int getBlue(BufferedImage image, int x, int y) {
		
		int rgb = image.getRGB(x, y);
	
		int blue = rgb & 0x000000ff;
		
		return blue;
	}

	public static BufferedImage toBufferedImage(Image image) {

		if (image instanceof BufferedImage)
			return (BufferedImage)image;
		
		image = new ImageIcon(image).getImage();
		
		boolean hasAlpha = hasAlpha(image);
		
		BufferedImage bimage = null;

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

		try {
			
			int transparency = Transparency.OPAQUE;

			if (hasAlpha == true)
				transparency = Transparency.BITMASK;
			
			GraphicsDevice gs = ge.getDefaultScreenDevice();
			GraphicsConfiguration gc = gs.getDefaultConfiguration();

			bimage = gc.createCompatibleImage(image.getWidth(null), image.getHeight(null), transparency);
		}
		
		catch (HeadlessException e)
		{
		}
		
		if (bimage == null) {
			
			int type = BufferedImage.TYPE_INT_RGB;

			if (hasAlpha == true)
				type = BufferedImage.TYPE_INT_ARGB;

			bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
		}
		
		Graphics g = bimage.createGraphics();
		
		g.drawImage(image, 0, 0, null);
		g.dispose();
		
		System.gc();

		return bimage;
	}
	
	public static boolean hasAlpha(Image image) {
		
		if (image instanceof BufferedImage)
			return ((BufferedImage)image).getColorModel().hasAlpha();
		
		PixelGrabber pg = new PixelGrabber(image, 0, 0, 1, 1, false);
		
		try {
			pg.grabPixels();
		}
		
		catch (InterruptedException e)
		{
		}
		
		return pg.getColorModel().hasAlpha();
	}
}