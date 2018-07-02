package kaeon_ace.engine.utilities.extension.office;

import kaeon_ace.engine.Engine;
import kaeon_ace.engine.processes.frame.Frame;
import kaeon_ace.engine.processes.keyboard.Keyboard;
import kaeon_ace.engine.processes.physics_engine.PhysicsEngine;
import kaeon_ace.engine.processes.renderer.Renderer;
import kaeon_ace.engine.processes.scene.Scene;
import kaeon_ace.engine.processes.scene.utilities.script.Script;
import kaeon_ace.engine.utilities.extension.game.sprite.Sprite;
import kaeon_ace.engine.utilities.geometry.mesh.Mesh;
import kaeon_ace.engine.utilities.geometry.utilities.visual.effects.BackgroundColor;
import kaeon_ace.engine.utilities.user.GeneralEntity;
import philosophers_stone.PhilosophersStoneUtilities;

public class SlideShow extends Engine {
	
	private Sprite fade;
	
	private Slide currentSlide;
	private int slideIndex;
	
	public Frame frame;
	public Keyboard keyboard;
	public PhysicsEngine physicsEngine;
	public Renderer renderer;
	public Scene scene;
	
	public SlideShow() {
		
		super();
		
		frame = (Frame) PhilosophersStoneUtilities.get(this, "Frame").get(0);
		keyboard = (Keyboard) PhilosophersStoneUtilities.get(this, "Keyboard").get(0);
		physicsEngine = (PhysicsEngine) PhilosophersStoneUtilities.get(this, "Physics Engine").get(0);
		renderer = (Renderer) PhilosophersStoneUtilities.get(this, "Renderer").get(0);
		scene = (Scene) PhilosophersStoneUtilities.get(this, "Scene").get(0);
		
		loadResources();
		
		fade = new Sprite(null, Mesh.RENDER_MODE_2D_ASPECT_OFF);
		fade.setAlpha(0);
		fade.getMesh().setRenderPriority(Integer.MAX_VALUE);
		
		scene.addEntity(fade);
		
		setFrame();
		script();
		
		slideIndex = 1;
		loadSlide(slideIndex);
	}
	
	public void setFrame() {
		
		frame.setFullScreen();
		frame.setCursorToBlank();
		
		scene.addEntity(
				
			new GeneralEntity(
					
				new Script() {
					
					public void onUpdate() {
						
						if(keyboard.isKeyPressed(Keyboard.KEY_ESCAPE)) {
							physicsEngine.stop();
							renderer.stop();
							scene.stop();
						}
					}
				}
			)
		);
		
		scene.setTicksPerSecond(60);
		
		scene.addEntity(new GeneralEntity(new BackgroundColor(1, 1, 1)));
	}
	
	public void script() {
		
		scene.addEntity(
				
			new GeneralEntity(
					
				new Script() {
					
					public void onUpdate() {
						
						if(currentSlide != null) {
							
							if(currentSlide.isFinished()) {
								fade.colorAlpha(.025);
							}
							
							else {
								fade.colorAlpha(-.05);
							}
						}
						
						if(fade.getAlpha() == 1) {
							
							slideIndex++;
							loadSlide(slideIndex);
							
							fade.setAlpha(.999);
						}
					}
				}
			)
		);
	}
	
	public void loadSlide(int index) {
		
		if(currentSlide != null)
			currentSlide.destroy();
		
		currentSlide = generateSlide(index);
		
		if(currentSlide != null)
			scene.addEntity(currentSlide);
	}
	
	public Slide generateSlide(int index) {
		return null;
	}
	
	public void loadResources() {
		
	}
}
