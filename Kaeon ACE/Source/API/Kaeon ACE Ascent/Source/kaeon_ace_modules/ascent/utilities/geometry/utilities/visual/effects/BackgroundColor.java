package kaeon_ace_modules.ascent.utilities.geometry.utilities.visual.effects;

import kaeon_ace_modules.ascent.utilities.geometry.utilities.visual.Color;

public class BackgroundColor extends Effect {
	
	private Color color;
	
	public BackgroundColor() {
		color = new Color();
	}
	
	public BackgroundColor(Color color) {
		
		this.color = new Color();
		
		setColor(color);
	}
	
	public BackgroundColor(double red, double green, double blue) {
		
		color = new Color();
		
		setColor(red, green, blue);
	}
	
	public BackgroundColor(double red, double green, double blue, double alpha) {
		
		color = new Color();
		
		color.setColor(red, green, blue, alpha);
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public void setColor(double red, double green, double blue) {
		color.setColor(red, green, blue);
	}
	
	public void setColor(double red, double green, double blue, double alpha) {
		color.setColor(red, green, blue, alpha);
	}
	
	public Color getColor() {
		return color;
	}
}