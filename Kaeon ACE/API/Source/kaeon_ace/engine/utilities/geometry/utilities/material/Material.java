package kaeon_ace.engine.utilities.geometry.utilities.material;

import java.awt.image.BufferedImage;

import kaeon_ace.engine.utilities.geometry.utilities.visual.Color;

public class Material {
	
	private String name;
	
	private String texture;
	public BufferedImage normal;
	
	private Color color;
	
	public Material() {
		color = new Color();
	}
	
	public Material(Material material) {
		copy(material);
	}
	
	public void copy(Material material) {
		
		name = material.getName();
		
		normal = material.normal;
		
		texture = material.getTexture();
		color = new Color(material.getColor());
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setTexture(String texture) {
		this.texture = texture;
	}
	
	public String getTexture() {
		return texture;
	}
	
	public void setColor(double red, double green, double blue) {
		
		if(color == null)
			color = new Color(red, green, blue);
		
		else
			color.setColor(red, green, blue);
	}
	
	public void setColor(double red, double green, double blue, double alpha) {
		
		if(color == null)
			color = new Color(red, green, blue, alpha);
		
		else
			color.setColor(red, green, blue, alpha);
	}
	
	public void setColor(Color color) {
		
		if(this.color == null)
			this.color = new Color(color);
		
		else
			this.color.copy(color);
	}
	
	public void setColorAs(Color color) {
		this.color = color;
	}
	
	public Color getColor() {
		return color;
	}
}