package kaeon_ace_modules.ascent.processes.keyboard;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import philosophers_stone.PhilosophersStone;
import philosophers_stone.PhilosophersStoneUtilities;

public class Keyboard extends PhilosophersStone {
	
	public static int KEY_A = KeyEvent.VK_A;
	public static int KEY_B = KeyEvent.VK_B;
	public static int KEY_C = KeyEvent.VK_C;
	public static int KEY_D = KeyEvent.VK_D;
	public static int KEY_E = KeyEvent.VK_E;
	public static int KEY_F = KeyEvent.VK_F;
	public static int KEY_G = KeyEvent.VK_G;
	public static int KEY_H = KeyEvent.VK_H;
	public static int KEY_I = KeyEvent.VK_I;
	public static int KEY_J = KeyEvent.VK_J;
	public static int KEY_K = KeyEvent.VK_K;
	public static int KEY_L = KeyEvent.VK_L;
	public static int KEY_M = KeyEvent.VK_M;
	public static int KEY_N = KeyEvent.VK_N;
	public static int KEY_O = KeyEvent.VK_O;
	public static int KEY_P = KeyEvent.VK_P;
	public static int KEY_Q = KeyEvent.VK_Q;
	public static int KEY_R = KeyEvent.VK_R;
	public static int KEY_S = KeyEvent.VK_S;
	public static int KEY_T = KeyEvent.VK_T;
	public static int KEY_U = KeyEvent.VK_U;
	public static int KEY_V = KeyEvent.VK_V;
	public static int KEY_W = KeyEvent.VK_W;
	public static int KEY_X = KeyEvent.VK_X;
	public static int KEY_Y = KeyEvent.VK_Y;
	public static int KEY_Z = KeyEvent.VK_Z;

	public static int KEY_0 = KeyEvent.VK_0;
	public static int KEY_1 = KeyEvent.VK_1;
	public static int KEY_2 = KeyEvent.VK_2;
	public static int KEY_3 = KeyEvent.VK_3;
	public static int KEY_4 = KeyEvent.VK_4;
	public static int KEY_5 = KeyEvent.VK_5;
	public static int KEY_6 = KeyEvent.VK_6;
	public static int KEY_7 = KeyEvent.VK_7;
	public static int KEY_8 = KeyEvent.VK_8;
	public static int KEY_9 = KeyEvent.VK_9;

	public static int KEY_UP = KeyEvent.VK_UP;
	public static int KEY_DOWN = KeyEvent.VK_DOWN;
	public static int KEY_LEFT = KeyEvent.VK_LEFT;
	public static int KEY_RIGHT = KeyEvent.VK_RIGHT;

	public static int KEY_MINUS = KeyEvent.VK_MINUS;
	public static int KEY_EQUALS = KeyEvent.VK_EQUALS;
	public static int KEY_SEMICOLON = KeyEvent.VK_SEMICOLON;
	public static int KEY_QUOTE = KeyEvent.VK_QUOTE;
	public static int KEY_COMMA = KeyEvent.VK_COMMA;
	public static int KEY_PERIOD = KeyEvent.VK_PERIOD;
	public static int KEY_SLASH = KeyEvent.VK_SLASH;
	
	public static int KEY_ESCAPE = KeyEvent.VK_ESCAPE;
	public static int KEY_CAPS_LOCK = KeyEvent.VK_CAPS_LOCK;
	public static int KEY_SHIFT = KeyEvent.VK_SHIFT;
	public static int KEY_SPACE = KeyEvent.VK_SPACE;
	public static int KEY_CONTROL = KeyEvent.VK_CONTROL;
	public static int KEY_BACK_SPACE = KeyEvent.VK_BACK_SPACE;
	public static int KEY_ENTER = KeyEvent.VK_ENTER;
	
	private ArrayList<Integer> keysPressed;
	
	public Keyboard() {
		
		PhilosophersStoneUtilities.tag(this, "Keyboard");
		
		keysPressed = new ArrayList<Integer>();
	}
	
	public void pressKey(int key) {
		
		for(int i = 0; i < keysPressed.size(); i++) {
			
			if(keysPressed.get(i) == key)
				return;
		}
		
		keysPressed.add(key);
	}
	
	public void releaseKey(int key) {
		
		for(int i = 0; i < keysPressed.size(); i++) {
			
			if(keysPressed.get(i) == key) {
				keysPressed.remove(i);
				break;
			}
		}
	}
	
	public void refresh() {
		
		while(keysPressed.size() > 0)
			keysPressed.remove(0);
	}
	
	public ArrayList<Integer> getKeysPressed() {
		return keysPressed;
	}
	
	public double getNumberOfKeysPressed() {
		return keysPressed.size();
	}
	
	public boolean isKeyPressed(int key) {
		
		for(int i = 0; i < keysPressed.size(); i++) {
			
			if(keysPressed.get(i) == key)
				return true;
		}
		
		return false;
	}
}