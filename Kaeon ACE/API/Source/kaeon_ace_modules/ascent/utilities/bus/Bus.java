package kaeon_ace_modules.ascent.utilities.bus;

import kaeon_ace_core.engine.Engine;
import kaeon_ace_modules.ascent.processes.camera.Camera;
import kaeon_ace_modules.ascent.processes.clock.Clock;
import kaeon_ace_modules.ascent.processes.core.Core;
import kaeon_ace_modules.ascent.processes.frame.Frame;
import kaeon_ace_modules.ascent.processes.keyboard.Keyboard;
import kaeon_ace_modules.ascent.processes.loader.Loader;
import kaeon_ace_modules.ascent.processes.mouse.Mouse;
import kaeon_ace_modules.ascent.processes.physics_engine.PhysicsEngine;
import kaeon_ace_modules.ascent.processes.renderer.Renderer;
import kaeon_ace_modules.ascent.processes.scene.Scene;
import philosophers_stone.PhilosophersStone;
import philosophers_stone.PhilosophersStoneUtilities;

public class Bus {
	
	public static Engine getEngine(PhilosophersStone stone) {
		return (Engine) PhilosophersStoneUtilities.get(stone, "Engine").get(0);
	}
	
	public static Camera getCamera(PhilosophersStone stone) {
		return (Camera) PhilosophersStoneUtilities.get(stone, "Camera").get(0);
	}
	
	public static Clock getClock(PhilosophersStone stone) {
		return (Clock) PhilosophersStoneUtilities.get(stone, "Clock").get(0);
	}
	
	public static Core getCore(PhilosophersStone stone) {
		return (Core) PhilosophersStoneUtilities.get(stone, "Core").get(0);
	}
	
	public static Frame getFrame(PhilosophersStone stone) {
		return (Frame) PhilosophersStoneUtilities.get(stone, "Frame").get(0);
	}
	
	public static Keyboard getKeyboard(PhilosophersStone stone) {
		return (Keyboard) PhilosophersStoneUtilities.get(stone, "Keyboard").get(0);
	}
	
	public static Loader getLoader(PhilosophersStone stone) {
		return (Loader) PhilosophersStoneUtilities.get(stone, "Loader").get(0);
	}
	
	public static Mouse getMouse(PhilosophersStone stone) {
		return (Mouse) PhilosophersStoneUtilities.get(stone, "Mouse").get(0);
	}
	
	public static PhysicsEngine getPhysicsEngine(PhilosophersStone stone) {
		return (PhysicsEngine) PhilosophersStoneUtilities.get(stone, "Physics Engine").get(0);
	}
	
	public static Renderer getRenderer(PhilosophersStone stone) {
		return (Renderer) PhilosophersStoneUtilities.get(stone, "Renderer").get(0);
	}
	
	public static Scene getScene(PhilosophersStone stone) {
		return (Scene) PhilosophersStoneUtilities.get(stone, "Scene").get(0);
	}
}