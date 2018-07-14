package kaeon_ace_modules.neo.utilities.geometry_utilities.visual;

public class Color {
	
	public float alpha;
	public float red;
	public float green;
	public float blue;
	
	public Color() {
		this(1, 1, 1, 1);
	}
	
	public Color(float alpha, float red, float green, float blue) {
		this.alpha = alpha;
		this.red = red;
		this.green = green;
		this.blue = blue;
	}
	
	public Color(float red, float green, float blue) {
		this(1, red, green, blue);
	}
	
	public Color(Color color) {
		this(color.alpha, color.red, color.green, color.blue);
	}
}