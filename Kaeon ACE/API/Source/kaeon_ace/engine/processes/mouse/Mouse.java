package kaeon_ace.engine.processes.mouse;

import java.awt.AWTException;
import java.awt.Robot;
import java.util.ArrayList;

import kaeon_ace.engine.processes.frame.Frame;
import kaeon_ace.engine.utilities.math.Vector3;
import philosophers_stone.PhilosophersStone;
import philosophers_stone.PhilosophersStoneUtilities;

public class Mouse extends PhilosophersStone {
	
	private boolean leftButtonPressed;
	private boolean middleButtonPressed;
	private boolean rightButtonPressed;
	
	private double scroll;
	
	private Vector3 cursorPosition;
	
	private Robot robot;
	
	public Frame frame;
	
	public Mouse() {
		
		PhilosophersStoneUtilities.tag(this, "Mouse");
		
		leftButtonPressed = false;
		middleButtonPressed = false;
		rightButtonPressed = false;
		
		scroll = 0;
		
		cursorPosition = new Vector3();
	}
	
	public Object onCall(ArrayList<Object> packet) {
		
		if(packet.size() == 0)
			return null;
		
		if(!(packet.get(0) instanceof String))
			return null;
		
		if(((String) packet.get(0)).equalsIgnoreCase("Start")) {
			frame = (Frame) PhilosophersStoneUtilities.get(this, "Frame").get(0);
		}
		
		return null;
	}
	
	public void pressLeftButton() {
		leftButtonPressed = true;
	}
	
	public void pressMiddleButton() {
		middleButtonPressed = true;
	}
	
	public void pressRightButton() {
		rightButtonPressed = true;
	}
	
	public void releaseLeftButton() {
		leftButtonPressed = false;
	}
	
	public void releaseMiddleButton() {
		middleButtonPressed = false;
	}
	
	public void releaseRightButton() {
		rightButtonPressed = false;
	}
	
	public boolean leftButtonPressed() {
		return leftButtonPressed;
	}
	
	public boolean middleButtonPressed() {
		return middleButtonPressed;
	}
	
	public boolean rightButtonPressed() {
		return rightButtonPressed;
	}
	
	public void scroll(double scroll) {
		this.scroll = scroll;
	}
	
	public double getScroll() {
		return scroll;
	}
	
	public void setCursorPosition(Vector3 cursorPosition) {
		this.cursorPosition.copy(cursorPosition);
	}
	
	public void setCursorPosition(double x, double y) {
		cursorPosition.set(x, y, 0);
	}
	
	public Vector3 getCursorPosition() {
		return new Vector3(cursorPosition);
	}
	
	public double getCursorPositionX() {
		return cursorPosition.getX();
	}
	
	public double getCursorPositionY() {
		return cursorPosition.getY();
	}
	
	public void moveCursorTo(double x, double y) {
		
		if(robot == null) {
			
			try {
				robot = new Robot();
			}
			
			catch (AWTException exception) {
				exception.printStackTrace();
			}
		}
		
		int framePositionX = frame.getFrame().getX();
		int framePositionY = frame.getFrame().getY();
		
		int frameWidth = frame.getWidth();
		int frameHeight = frame.getHeight();
		
		int xPos = (int) ((framePositionX + (frameWidth / 2)) + (x * (frameWidth / 2)));
		int yPos = (int) ((framePositionY + (frameHeight / 2)) + (y * (frameHeight / 2)));
		
		if(xPos < framePositionX)
			xPos = framePositionX;
		
		if(xPos > framePositionX + frameWidth)
			xPos = framePositionX + frameWidth;
		
		if(yPos < framePositionY)
			yPos = framePositionY;
		
		if(yPos > framePositionY + frameHeight)
			yPos = framePositionY + frameHeight;
		
		robot.mouseMove(xPos, yPos);
	}
			
	public void refresh() {
		releaseLeftButton();
		releaseMiddleButton();
		releaseRightButton();
	}
}