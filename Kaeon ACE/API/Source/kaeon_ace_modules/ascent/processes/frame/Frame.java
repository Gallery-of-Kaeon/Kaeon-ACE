package kaeon_ace_modules.ascent.processes.frame;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFrame;

import kaeon_ace_core.engine.Engine;
import kaeon_ace_modules.ascent.processes.keyboard.Keyboard;
import kaeon_ace_modules.ascent.processes.mouse.Mouse;
import kaeon_ace_modules.ascent.processes.physics_engine.PhysicsEngine;
import kaeon_ace_modules.ascent.processes.renderer.Renderer;
import kaeon_ace_modules.ascent.processes.scene.Scene;
import kaeon_ace_modules.ascent.utilities.resource.visual.ImageLoader;
import philosophers_stone.PhilosophersStone;
import philosophers_stone.PhilosophersStoneUtilities;

public class Frame extends PhilosophersStone {
	
	private JFrame frame;
	
	private boolean inFocus;
	private boolean fullScreen;
	
	public Engine engine;
	public Keyboard keyboard;
	public Mouse mouse;
	public PhysicsEngine physicsEngine;
	public Renderer renderer;
	public Scene scene;
	
	public Frame() {
		
		PhilosophersStoneUtilities.tag(this, "Frame");
		
		frame = new JFrame();
		
		frame.setTitle("Kaeon ACE");
		frame.setSize(500, 500);
	}
	
	public Object onCall(ArrayList<Object> packet) {
		
		if(packet.size() == 0)
			return null;
		
		if(!(packet.get(0) instanceof String))
			return null;
		
		if(((String) packet.get(0)).equalsIgnoreCase("Start")) {
			
			engine = (Engine) PhilosophersStoneUtilities.get(this, "Engine").get(0);
			keyboard = (Keyboard) PhilosophersStoneUtilities.get(this, "Keyboard").get(0);
			mouse = (Mouse) PhilosophersStoneUtilities.get(this, "Mouse").get(0);
			physicsEngine = (PhysicsEngine) PhilosophersStoneUtilities.get(this, "Physics Engine").get(0);
			renderer = (Renderer) PhilosophersStoneUtilities.get(this, "Renderer").get(0);
			scene = (Scene) PhilosophersStoneUtilities.get(this, "Scene").get(0);
			
			start(renderer);
		}
		
		return null;
	}
	
	public void start(Renderer renderer) {
		
		frame.add(renderer.getRenderer());
		
		addListeners();
		
		frame.setVisible(true);
		frame.setFocusable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void addListeners() {
		
		inFocus = true;
		
		frame.addWindowListener(
				
			new WindowAdapter() {
				
				public void windowClosing(WindowEvent windowEvent) {
					physicsEngine.stop();
					renderer.stop();
					scene.stop();
			    }
			}
		);
		
		frame.addWindowFocusListener(
				
			new WindowFocusListener() {
				
				public void windowGainedFocus(WindowEvent windowEvent) {
					
					inFocus = true;
					
					keyboard.refresh();
					mouse.refresh();
				}
				
				public void windowLostFocus(WindowEvent windowEvent) {
					
					inFocus = false;
					
					keyboard.refresh();
					mouse.refresh();
				}
			}
		);
		
		frame.addKeyListener(
				
			new KeyListener() {
				
				public void keyPressed(KeyEvent keyEvent) {
					keyboard.pressKey(keyEvent.getKeyCode());
				}
				
				public void keyReleased(KeyEvent keyEvent) {
					keyboard.releaseKey(keyEvent.getKeyCode());
				}
				
				public void keyTyped(KeyEvent keyEvent) {
					
				}
			}
		);
		
		renderer.getRenderer().addMouseListener(
				
			new MouseAdapter() {
				
				public void mousePressed(MouseEvent mouseEvent) {
					
					if(mouseEvent.getButton() == MouseEvent.BUTTON1)
						mouse.pressLeftButton();
					
					if(mouseEvent.getButton() == MouseEvent.BUTTON2)
						mouse.pressMiddleButton();
					
					if(mouseEvent.getButton() == MouseEvent.BUTTON3)
						mouse.pressRightButton();
				}
				
				public void mouseReleased(MouseEvent mouseEvent) {
					
					if(mouseEvent.getButton() == MouseEvent.BUTTON1)
						mouse.releaseLeftButton();
					
					if(mouseEvent.getButton() == MouseEvent.BUTTON2)
						mouse.releaseMiddleButton();
					
					if(mouseEvent.getButton() == MouseEvent.BUTTON3)
						mouse.releaseRightButton();
				}
			}
		);
		
		frame.requestFocus();
	}
	
	public void stop() {
		frame.setVisible(false);
		frame.dispose();
	}
	
	public void setTitle(String title) {
		frame.setTitle(title);
	}
	
	public void setIcon(String image) {
		frame.setIconImage(ImageLoader.getImage(image));
	}
	
	public void setPosition(int x, int y) {
		frame.setLocation(x, y);
	}
	
	public void setXPosition(int x) {
		frame.setLocation(x, frame.getY());
	}
	
	public void setYPosition(int y) {
		frame.setLocation(frame.getX(), y);
	}
	
	public void setSize(int width, int height) {
		
		if(fullScreen)
			frame.setUndecorated(false);
		
		frame.setSize(width, height);
		
		fullScreen = false;
	}
	
	public void setWidth(int width) {
		frame.setSize(width, frame.getHeight());
	}
	
	public void setHeight(int height) {
		frame.setSize(frame.getWidth(), height);
	}
	
	public void setFullScreen() {
		
		if(fullScreen)
			return;
		
		String operatingSystem = System.getProperty("os.name");
		
		GraphicsDevice gd =
				GraphicsEnvironment.
				getLocalGraphicsEnvironment().getDefaultScreenDevice();
		
		if(gd.isFullScreenSupported()) {

			frame.setUndecorated(true);
			
			if(operatingSystem.indexOf("Mac") != -1)
				gd.setFullScreenWindow(frame);
			
			else {
				
				frame.setSize(
					(int) (Toolkit.getDefaultToolkit().getScreenSize().
					getWidth()),
					(int) (Toolkit.getDefaultToolkit().getScreenSize().
					getHeight())
					);
			}
			
			fullScreen = true;
		}
		
		frame.revalidate();
	}
	
	public void enableResizing() {
		frame.setResizable(true);
	}
	
	public void disableResizing() {
		frame.setResizable(false);
	}
	
	public void setCursor(String image) {
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension size = toolkit.getBestCursorSize(getWidth(), getHeight());
		Point cursorHotSpot = new Point(size.width / 2, size.height / 2);
		
		Cursor customCursor = toolkit.createCustomCursor(
				ImageLoader.getImage(image),
				cursorHotSpot,
				"Cursor");
		
		frame.setCursor(customCursor);
	}
	
	public void setCursorToBlank() {
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension dim = toolkit.getBestCursorSize(getWidth(), getHeight());
		Point cursorHotSpot = new Point(dim.width / 2, dim.height / 2);
		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		
		Cursor blankCursor =
				Toolkit.getDefaultToolkit().createCustomCursor(
						cursorImg, cursorHotSpot, "blank cursor");
		
		frame.setCursor(blankCursor);
	}
	
	public boolean hasFocus() {
		return frame.hasFocus();
	}
	
	public JFrame getFrame() {
		return frame;
	}
	
	public int getXPosition() {
		return frame.getX();
	}
	
	public int getYPosition() {
		return frame.getY();
	}
	
	public int getWidth() {
		return frame.getWidth();
	}
	
	public int getHeight() {
		return frame.getHeight();
	}
	
	public boolean isInFocus() {
		return inFocus;
	}
	
	public boolean isFullScreen() {
		return fullScreen;
	}
	
	public void refresh() {
		frame.revalidate();
		frame.requestFocus();
	}
	
	public void updateCursor() {
		
		PointerInfo pointer = MouseInfo.getPointerInfo();
		Point point = pointer.getLocation();
		
		double x = (point.getX() - frame.getX() - (double) frame.getWidth() / 2) *
				(1 / ((double) frame.getWidth() / 2));
		
		double y = (point.getY() - frame.getY() - (double) frame.getHeight() / 2) *
				(1 / ((double) frame.getHeight() / 2));
		
		mouse.setCursorPosition(x / 2, -y / 2);
	}
}