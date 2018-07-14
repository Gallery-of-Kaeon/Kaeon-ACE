package kaeon_ace_modules.ascent.utilities.geometry.utilities.animation.scripting;

import java.util.ArrayList;

public class Animation {
	
	private ArrayList<KeyFrame> keyFrames;
	
	private int frame;
	private double interpolation;
	
	public Animation() {
		keyFrames = new ArrayList<KeyFrame>();
	}
	
	public void addKeyFrame(KeyFrame keyFrame) {
		keyFrames.add(keyFrame);
	}
	
	public void setFrame(int frame) {
		this.frame = frame;
	}
	
	public void setInterpolation(double interpolation) {
		this.interpolation = interpolation;
	}
	
	public ArrayList<KeyFrame> getKeyFrames() {
		return keyFrames;
	}
	
	public int getFrame() {
		return frame;
	}
	
	public double getInterpolation() {
		return interpolation;
	}
}