package kaeon_ace_modules.ascent.utilities.extension.office;

import java.util.ArrayList;

import kaeon_ace_modules.ascent.processes.mouse.Mouse;
import kaeon_ace_modules.ascent.processes.scene.utilities.Entity;
import kaeon_ace_modules.ascent.utilities.user.GeneralEntity;
import philosophers_stone.PhilosophersStoneUtilities;

public class Slide extends GeneralEntity {
	
	private ArrayList<Entity> addedElements;
	
	private boolean held;
	private boolean finished;
	
	public Mouse mouse;
	
	public Slide() {
		addedElements = new ArrayList<Entity>();
	}
	
	public void addElement(Entity element) {
		addedElements.add(element);
	}
	
	public void onUpdate() {
		
		if(mouse == null)
			mouse = (Mouse) PhilosophersStoneUtilities.get(getEngine(), "Mouse").get(0);
		
		if(!mouse.leftButtonPressed() && held) {
			
			if(addedElements.size() > 0)
				addChild(addedElements.remove(0));
			
			else
				finished = true;
		}
		
		if(mouse.leftButtonPressed())
			held = true;
		
		else
			held = false;
	}
	
	public boolean isFinished() {
		return finished;
	}
}
