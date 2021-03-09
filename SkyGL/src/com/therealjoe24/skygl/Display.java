package com.therealjoe24.skygl;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.opengl.GL15;
import static org.lwjgl.opengl.GL45.*;

import static org.lwjgl.system.MemoryUtil.*;

import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;

/**
 * Static Display class
 * 
 * @author Brandon Stevens
 * @author owner@therealjoe24.com
 * @author www.therealjoe24.com
 */
public class Display {
	
	/* has the display already been initialized? */
	private static boolean _initialized = false;
	
	/* glfw window id */
	private static long _windowID = NULL;
	/* window width */
	private static int _width;
	/* window height */
	private static int _height;
	/* the window title */
	private static String _title;
	/* window color */
	private static float[] _clearColor = { 0, 0, 0 };
	
	private static void _resizeCallback(int width, int height) {
		_width = width;
		_height = height;
		glViewport(0, 0, width, height);
	}
	
	/**
	 * Gets the window id
	 * 
	 * @return glfw window id
	 */
	public static long getWindowID() {
		return _windowID;
	}
	
	/**
	 * Gets the window width
	 * 
	 * @return window width
	 */
	public static int getWidth() {
		return _width;
	}

	/**
	 * Gets the window height
	 * 
	 * @return window height
	 */
	public static int getHeight() {
		return _height;
	}
	
	/**
	 * Gets the window title
	 * 
	 * @return window title
	 */
	public static String getTitle() {
		return _title;
	}

	/** 
	 * Initializes Display
	 * 
	 * @param width the width of the window
	 * @param height the height of the window
	 * @param title the title of the window
	 * @throws RuntimeException
	 */
	public static void Create(int width, int height, String title, boolean maximized) {
		_width = width;
		_height = height;
		_title = title;
		
		if (_initialized) {
			glfwTerminate();
			if (_windowID != NULL) {
				glfwDestroyWindow(height);
				_windowID = NULL;
			}
		}
		
		/* display has been initialized */
		_initialized = true;
		
		/* initialize GLFW */
		if (!glfwInit()) {
			throw new RuntimeException("Failed to Initialize GLFW");
		}
		
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_VERSION_MAJOR, 4);
		glfwWindowHint(GLFW_VERSION_MAJOR, 5);
		_windowID = glfwCreateWindow(width, height, title, NULL, NULL);
		glfwMakeContextCurrent(_windowID);
		
		if (maximized) {
			glfwMaximizeWindow(_windowID);
		}
		
		GL.createCapabilities();
		
		glfwSetWindowSizeCallback(_windowID, new GLFWWindowSizeCallback() {
			@Override
			public void invoke(long window, int width, int height) {
				_resizeCallback(width, height);
			}
		});
	}
	
	/**
	 * Terminate GL
	 * 
	 */
	public static void Terminate() {
		_initialized = false;
		
		glfwDestroyWindow(_windowID);
		GL.destroy();
		glfwTerminate();
	}
	
	/** 
	 * Makes the window visible
	 * 
	 */
	public static void ShowWindow() {
		glfwShowWindow(_windowID);
	}
	
	/**
	 * Clear the current window
	 * 
	 */
	public static void ClearScreen() {
		GL15.glClearColor(_clearColor[0], _clearColor[1], _clearColor[2], 1);
		GL15.glClear(GL15.GL_COLOR_BUFFER_BIT | GL15.GL_DEPTH_BUFFER_BIT);
	}
	
	/**
	 * Polls glfw events
	 * 
	 */
	public static void PollEvents() {
		glfwPollEvents();
	}
	
	/**
	 * Swaps the window buffers
	 * 
	 */
	public static void SwapBuffers() {
		glfwSwapBuffers(_windowID);
	}
	
	/**
	 * Sets the display clear color
	 * 
	 * @param r red
	 * @param g green
	 * @param b blue
	 */
	public static void setClearColor(float r, float g, float b) {
		_clearColor[0] = r;
		_clearColor[1] = g;
		_clearColor[2] = b;
	}

	/**
	 * Checks if the user has closed the window
	 * 
	 * @return true if user closes the window
	 */
	public static boolean windowShouldClose() {
		return glfwWindowShouldClose(_windowID);
	}
	
}
