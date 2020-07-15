package kaeon_ace_modules.ascent.utilities.geometry.utilities.animation.skeleton;

import kaeon_ace_modules.ascent.utilities.math.Vector3;
import kaeon_ace_modules.ascent.utilities.resource.visual.models.Model;

public class Skeleton {

	private Model model;
	
	private Origin origin;
	private Bone root;
	
	public Skeleton(Model model) {
		
		this.model = model;
		
		origin = new Origin();
	}
	
	public void setOriginPosition(Vector3 originPosition) {
		origin.setPosition(originPosition);
	}
	
	public void setRoot(Bone root) {
		
		root.setAsRoot();
		
		this.root = root;
	}
	
	public Model getModel() {
		return model;
	}
	
	public Bone getRoot() {
		return root;
	}
	
	public Origin getOrigin() {
		return origin;
	}
	
	public void animate() {
		
		if(root != null) {
			
			if(root.isMoved())
				root.animate(new Origin(origin));
		}
	}
}