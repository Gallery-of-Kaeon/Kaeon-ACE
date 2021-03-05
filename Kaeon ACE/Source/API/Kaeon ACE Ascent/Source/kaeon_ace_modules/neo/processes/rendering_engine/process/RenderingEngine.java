package kaeon_ace_modules.neo.processes.rendering_engine.process;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import kaeon_ace_core.engine.utilities.process.ACEProcess;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class RenderingEngine extends ACEProcess {
	
	public long window;
	
	public void onStart() {
		
		new Thread() {
			
			public void run() {
				runRenderer();
			}
		}.start();
	}

	public void runRenderer() {

		init();
		loop();

		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);

		glfwTerminate();
		glfwSetErrorCallback(null).free();
		
		System.exit(0);
	}

	public void init() {
		
		GLFWErrorCallback.createPrint(System.err).set();

		if(!glfwInit())
			throw new IllegalStateException("Unable to initialize GLFW");

		glfwDefaultWindowHints();
		
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

		window = glfwCreateWindow(300, 300, "Kaeon ACE", NULL, NULL);
		
		if(window == NULL)
			throw new RuntimeException("Failed to create the GLFW window");

		glfwSetKeyCallback(
			window,
			(window, key, scancode, action, mods) -> {
				
				if(key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
					glfwSetWindowShouldClose(window, true);
			}
		);

		try(MemoryStack stack = stackPush()) {
			
			IntBuffer pWidth = stack.mallocInt(1);
			IntBuffer pHeight = stack.mallocInt(1);

			glfwGetWindowSize(window, pWidth, pHeight);

			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

			glfwSetWindowPos(
				window,
				(vidmode.width() - pWidth.get(0)) / 2,
				(vidmode.height() - pHeight.get(0)) / 2
			);
		}

		glfwMakeContextCurrent(window);
		glfwSwapInterval(1);

		glfwShowWindow(window);
	}

	public void loop() {
		
		GL.createCapabilities();
		
		glEnable(GL_DEPTH_TEST);
		glDepthFunc(GL_LEQUAL);
		
		glEnable(GL_BLEND);
		glEnable(GL_POLYGON_SMOOTH);
		
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glHint(GL_POLYGON_SMOOTH_HINT, GL_NICEST);
		
		glEnable(GL_LINE_SMOOTH);
		
		while (!glfwWindowShouldClose(window)) {
			
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			
			glMatrixMode (GL_PROJECTION);
			glLoadIdentity();

			int width = 1;
			int height = 1;
			
			try(MemoryStack stack = stackPush()) {
				
				IntBuffer pWidth = stack.mallocInt(1);
				IntBuffer pHeight = stack.mallocInt(1);
				
				glfwGetWindowSize(window, pWidth, pHeight);
				
				width = pWidth.get(0);
				height = pHeight.get(0);
			}
			
			glMatrixMode (GL_MODELVIEW);
			glLoadIdentity();
			
			perspectiveGL(45f, ((float) width) / ((float) height), 0f, 10000f);
			
			render();
			
			glfwSwapBuffers(window);

			glfwPollEvents();
		}
	}
	
	public static void perspectiveGL(float fov, float aspect, float zNear, float zFar) {
		
	    float fH = (float) Math.tan(fov / 360 * Math.PI) * zNear;
	    float fW = fH * aspect;
	    
	    glFrustum(-fW, fW, -fH, fH, zNear, zFar);
	}
	
	public void render() {
		
		glBegin(GL_TRIANGLES);
		
		glVertex3d(0, .5, 0);
		glVertex3d(.5, .5, 0);
		glVertex3d(.5, 0, 0);
		
		glEnd();
	}
}