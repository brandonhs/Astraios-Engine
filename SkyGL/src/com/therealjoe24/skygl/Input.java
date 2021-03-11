package com.therealjoe24.skygl;

import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.glfw.*;

/**
 * Input Handler
 * 
 * @author brand
 *
 */
public class Input {
	
	private static double _mouseX, _mouseY, _deltaMouseX, _deltaMouseY;
	
	private static GLFWCursorPosCallback _cursorPosCallback = new GLFWCursorPosCallback() {
		@Override
		public void invoke(long window, double xpos, double ypos) {
			_deltaMouseX = xpos-_mouseX;
			_deltaMouseY = ypos-_mouseY;
			_mouseX = xpos;
			_mouseY = ypos;
		}
	};
	
	/**
	 * Get the mouse delta x
	 * 
	 * @return
	 */
	public static float getMouseDeltaX() {
		return (float)_deltaMouseX;
	}
	
	/**
	 * Get the mouse delta y
	 * 
	 * @return
	 */
	public static float getMouseDeltaY() {
		return (float)_deltaMouseY;
	}
	
	/**
	 * Initialize Input Manager
	 * 
	 */
	public static void Init() {
		glfwSetCursorPosCallback(Display.getWindowID(), _cursorPosCallback);
	}
	
	public static void Update() {
		glfwPollEvents();
		_deltaMouseX = 0;
		_deltaMouseY = 0;
	}
	
}
