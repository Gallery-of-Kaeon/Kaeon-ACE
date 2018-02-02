package kaeon_ace.engine.utilities.geometry.utilities.visual;

public class Color {
	
	private double red;
	private double green;
	private double blue;
	private double alpha;
	
	public Color() {
		setColor(1, 1, 1, 1);
	}
	
	public Color(Color color) {
		copy(color);
	}
	
	public Color(double red, double green, double blue) {
		setColor(red, green, blue, 1);
	}
	
	public Color(double red, double green, double blue, double alpha) {
		setColor(red, green, blue, alpha);
	}
	
	public void copy(Color color) {
		
		setColor(
				color.getRed(),
				color.getGreen(),
				color.getBlue(),
				color.getAlpha());
	}
	
	public void setColor(double red, double green, double blue) {
		setColor(red, green, blue, alpha);
	}
	
	public void setColor(double red, double green, double blue, double alpha) {
		setRed(red);
		setGreen(green);
		setBlue(blue);
		setAlpha(alpha);
	}
	
	public void setRed(double red) {
		this.red = checkColorValue(red);
	}
	
	public void setGreen(double green) {
		this.green = checkColorValue(green);
	}
	
	public void setBlue(double blue) {
		this.blue = checkColorValue(blue);
	}
	
	public void setAlpha(double alpha) {
		this.alpha = checkColorValue(alpha);
	}
	
	public void color(double red, double green, double blue) {
		color(red, green, blue, 0);
	}
	
	public void color(double red, double green, double blue, double alpha) {
		colorRed(red);
		colorGreen(green);
		colorBlue(blue);
		colorAlpha(alpha);
	}
	
	public void colorRed(double red) {
		this.red = checkColorValue(this.red + red);
	}
	
	public void colorGreen(double green) {
		this.green = checkColorValue(this.green + green);
	}
	
	public void colorBlue(double blue) {
		this.blue = checkColorValue(this.blue + blue);
	}
	
	public void colorAlpha(double alpha) {
		this.alpha = checkColorValue(this.alpha + alpha);
	}
	
	public double getRed() {
		return red;
	}
	
	public double getGreen() {
		return green;
	}
	
	public double getBlue() {
		return blue;
	}
	
	public double getAlpha() {
		return alpha;
	}
	
	public double checkColorValue(double color) {
		
		if(color > 1)
			color = 1;
		
		if(color < 0)
			color = 0;
		
		return color;
	}
	
	public String toString() {
		return red + " " + green + " " + blue + " " + alpha;
	}
}