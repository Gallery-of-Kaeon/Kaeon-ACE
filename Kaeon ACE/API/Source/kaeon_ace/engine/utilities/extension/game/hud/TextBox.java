package kaeon_ace.engine.utilities.extension.game.hud;

import kaeon_ace.engine.utilities.extension.game.sprite.Sprite;
import kaeon_ace.engine.utilities.geometry.utilities.visual.Color;
import kaeon_ace.engine.utilities.math.transform.Translation;
import kaeon_ace.engine.utilities.user.GeneralEntity;

public class TextBox extends GeneralEntity {
	
	private String text;
	
	private String fontSheet;
	private String mode;
	
	private int scrollPeriod;
	private double scrollFade;
	
	private int time;
	
	private double xSize;
	private double ySize;
	
	private double xGap;
	private double yGap;
	
	public TextBox() {
		
		scrollPeriod = 0;
		scrollFade = 1;

		xSize = 1;
		ySize = 1;
		
		xGap = 1;
		yGap = 1;
	}
	
	public void setText(String text) {
		
		for(int i = 0; i < getChildren().size(); i++)
			((Sprite) getChildren().get(i)).destroy();
		
		time = 0;
		
		this.text = text;
		
		int row = 0;
		int column = 0;
		
		for(int i = 0; i < text.length(); i++) {
			
			if(text.charAt(i) != '\n') {
				
				Sprite character = new Sprite(fontSheet);
				
				double ascii = (int) text.charAt(i) - 32;
				
				character.setTexCoords(
						(ascii % 16) / 16,
						((int) (ascii / 16)) * (1.0 / 14),
						1.0 / 16,
						1.0 / 14);

				character.getMesh().setRenderMode(mode);
				character.getMesh().transform(new Translation(column * xGap, -row * yGap, 0));
				
				character.setColor(new Color(1, 1, 1, 0));
				
				character.setSize(xSize, ySize, 0);
				
				addChild(character);
				
				column++;
			}
			
			else {
				row++;
				column = 0;
			}
		}
	}
	
	public void setFontSheet(String fontSheet) {
		this.fontSheet = fontSheet;
	}
	
	public void setMode(String mode) {
		
		for(int i = 0; i < getChildren().size(); i++)
			((Sprite) getChildren().get(i)).setRenderMode(mode);
		
		this.mode = mode;
	}
	
	public void setSize(double x, double y) {
		xSize = x;
		ySize = y;
	}
	
	public void setScroll(int scrollPeriod, double scrollFade) {
		this.scrollPeriod = scrollPeriod;
		this.scrollFade = scrollFade;
	}
	
	public void setGap(double xGap, double yGap) {
		this.xGap = xGap;
		this.yGap = yGap;
	}
	
	public void onUpdate() {
		
		if(time < text.length() * scrollPeriod)
			time++;
		
		for(int i = 0; i < getChildren().size(); i++) {
			
			if(i * scrollPeriod <= time)
				((Sprite) getChildren().get(i)).getColor().colorAlpha(scrollFade);
		}
	}
}
