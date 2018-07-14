package kaeon_ace.versions;

import kaeon_ace_core.ace.ACE;
import kaeon_ace_core.engine.Engine;
import kaeon_ace_modules.ascent.processes.camera.Camera;
import kaeon_ace_modules.ascent.processes.clock.Clock;
import kaeon_ace_modules.ascent.processes.core.Core;
import kaeon_ace_modules.ascent.processes.frame.Frame;
import kaeon_ace_modules.ascent.processes.keyboard.Keyboard;
import kaeon_ace_modules.ascent.processes.loader.Loader;
import kaeon_ace_modules.ascent.processes.mouse.Mouse;
import kaeon_ace_modules.ascent.processes.physics.Physics;
import kaeon_ace_modules.ascent.processes.renderer.Renderer;
import kaeon_ace_modules.ascent.processes.scene.Scene;

public class Ascent {

	public static void loadAscent(Engine engine) {
		ACE.connectACE(engine, new Camera());
		ACE.connectACE(engine, new Clock());
		ACE.connectACE(engine, new Core());
		ACE.connectACE(engine, new Frame());
		ACE.connectACE(engine, new Keyboard());
		ACE.connectACE(engine, new Loader());
		ACE.connectACE(engine, new Mouse());
		ACE.connectACE(engine, new Physics());
		ACE.connectACE(engine, new Renderer());
		ACE.connectACE(engine, new Scene());
	}
}