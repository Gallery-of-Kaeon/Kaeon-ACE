package kaeon_ace_modules.ascent.utilities.extension.game.hud;

import kaeon_ace_modules.ascent.processes.mouse.Mouse;
import kaeon_ace_modules.ascent.utilities.extension.game.sprite.Sprite;
import kaeon_ace_modules.ascent.utilities.geometry.mesh.Mesh;
import philosophers_stone.PhilosophersStoneUtilities;

public class Button extends Sprite {
	
	private boolean held;
	
	public Mouse mouse;
	
	public Button() {
		
		super();
		
		setRenderMode(Mesh.RENDER_MODE_2D);
	}
	
	public Button(String image) {
		
		super(image);
		
		setRenderMode(Mesh.RENDER_MODE_2D);
	}
	
	public void setRenderMode(String renderMode) {
		
		if(renderMode.equals(Mesh.RENDER_MODE_3D))
			return;
		
		super.setRenderMode(renderMode);
	}
	
	public void onUpdate() {
		
		if(mouse == null)
			mouse = (Mouse) PhilosophersStoneUtilities.get(getEngine(), "Mouse").get(0);
		
		if(held && !mouse.leftButtonPressed())
			onClick();
		
		boolean hover = 
			imageOnPoint(
				mouse.getCursorPositionX(),
				mouse.getCursorPositionY());
		
		if(hover)
			onHover();
		
		else
			offHover();
		
		held = mouse.leftButtonPressed() && hover;
		
		if(held)
			onHold();
		
		else
			offHold();
	}
	
	public void onHover() {
		
	}
	
	public void offHover() {
		
	}
	
	public void onHold() {
		
	}
	
	public void offHold() {
		
	}
	
	public void onClick() {
		
	}
}
