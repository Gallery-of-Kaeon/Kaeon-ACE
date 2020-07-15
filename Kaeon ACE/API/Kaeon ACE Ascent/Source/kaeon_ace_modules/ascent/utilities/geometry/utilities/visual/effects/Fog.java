package kaeon_ace_modules.ascent.utilities.geometry.utilities.visual.effects;

import kaeon_ace_modules.ascent.utilities.geometry.utilities.visual.Color;

public class Fog extends Effect {
	
	private Color color;
	
	public Fog() {
		color = new Color();
	}
	
	public Fog(double red, double green, double blue) {
		color = new Color(red, green, blue);
	}
	
	public Fog(double red, double green, double blue, double alpha) {
		color = new Color(red, green, blue, alpha);
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