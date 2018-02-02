package kaeon_ace.engine.utilities.bus;

import kaeon_ace.engine.Engine;
import kaeon_ace.engine.processes.camera.Camera;
import kaeon_ace.engine.processes.clock.Clock;
import kaeon_ace.engine.processes.core.Core;
import kaeon_ace.engine.processes.frame.Frame;
import kaeon_ace.engine.processes.keyboard.Keyboard;
import kaeon_ace.engine.processes.loader.Loader;
import kaeon_ace.engine.processes.mouse.Mouse;
import kaeon_ace.engine.processes.physics_engine.PhysicsEngine;
import kaeon_ace.engine.processes.renderer.Renderer;
import kaeon_ace.engine.processes.scene.Scene;
import philosophers_stone.PhilosophersStone;
import philosophers_stone.PhilosophersStoneUtilities;

public class Bus {
	
	public static PhilosophersStone bus;
	
	public static Engine getEngine() {
		return (Engine) PhilosophersStoneUtilities.get(bus, "Engine").get(0);
	}
	
	public static Camera getCamera() {
		return (Camera) PhilosophersStoneUtilities.get(bus, "Camera").get(0);
	}
	
	public static Clock getClock() {
		return (Clock) PhilosophersStoneUtilities.get(bus, "Clock").get(0);
	}
	
	public static Core getCore() {
		return (Core) PhilosophersStoneUtilities.get(bus, "Core").get(0);
	}
	
	public static Frame getFrame() {
		return (Frame) PhilosophersStoneUtilities.get(bus, "Frame").get(0);
	}
	
	public static Keyboard getKeyboard() {
		return (Keyboard) PhilosophersStoneUtilities.get(bus, "Keyboard").get(0);
	}
	
	public static Loader getLoader() {
		return (Loader) PhilosophersStoneUtilities.get(bus, "Loader").get(0);
	}
	
	public static Mouse getMouse() {
		return (Mouse) PhilosophersStoneUtilities.get(bus, "Mouse").get(0);
	}
	
	public static PhysicsEngine getPhysicsEngine() {
		return (PhysicsEngine) PhilosophersStoneUtilities.get(bus, "Physics Engine").get(0);
	}
	
	public static Renderer getRenderer() {
		return (Renderer) PhilosophersStoneUtilities.get(bus, "Renderer").get(0);
	}
	
	public static Scene getScene() {
		return (Scene) PhilosophersStoneUtilities.get(bus, "Scene").get(0);
	}
}