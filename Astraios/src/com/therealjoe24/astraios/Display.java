/*******************************************************************************
 * This file is part of the Astraios Engine distribution <https://github.com/TheRealJoe24/Astraios-Engine>
 * Copyright (C) 2021 TheRealJoe24
 * 
 * Astraios Engine is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package com.therealjoe24.astraios;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.opengl.GL15;
import static org.lwjgl.opengl.GL45.*;

import static org.lwjgl.system.MemoryUtil.*;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;

/**
 * Static Display class
 * 
 * @author TheRealJoe24
 * 
 */
public class Display {

    /**
     * True if the display has already been initialized
     */
    private static boolean _initialized = false;
    /**
     * GLFW window ID
     */
    private static long _windowID = NULL;
    /**
     * window width
     */
    private static int _width;
    /**
     * window height
     */
    private static int _height;
    /**
     * window title
     */
    private static String _title;
    /**
     * window clear color
     */
    private static float[] _clearColor = { 0, 0, 0 };

    /**
     * Resize Callback
     * 
     */
    public static abstract interface SkyGLDisplayResizeFunc {
        public abstract void invoke();
    }

    private static List<SkyGLDisplayResizeFunc> _resizeFuncs = new ArrayList<SkyGLDisplayResizeFunc>();

    /**
     * Called when the window is resized
     * 
     * @param width
     * @param height
     */
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
     * Gets the aspect ratio
     * 
     * @return aspect ratio
     */
    public static float getAspect() {
        return (float) _width / (float) _height;
    }

    /**
     * Initializes Display
     * 
     * @param width  the width of the window
     * @param height the height of the window
     * @param title  the title of the window
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
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_VERSION_MAJOR, 4);
        glfwWindowHint(GLFW_VERSION_MAJOR, 5);
        _windowID = glfwCreateWindow(width, height, title, NULL, NULL);
        glfwMakeContextCurrent(_windowID);
        glfwSwapInterval(1);

        // glfwSetInputMode(_windowID, GLFW_CURSOR, GLFW_CURSOR_DISABLED);

        if (maximized) {
            glfwMaximizeWindow(_windowID);
        }

        GL.createCapabilities();

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        glfwSetWindowSizeCallback(_windowID, new GLFWWindowSizeCallback() {
            @Override
            public void invoke(long window, int width, int height) {
                _resizeCallback(width, height);

                for (SkyGLDisplayResizeFunc func : _resizeFuncs) {
                    func.invoke();
                }
            }
        });
    }

    /**
     * Add callback function to resize event
     * 
     * @param func function to call on resize
     */
    public static void AddResizeCallbackFunc(SkyGLDisplayResizeFunc func) {
        _resizeFuncs.add(func);
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

    /**
     * Get the monitor width
     * 
     * @return monitor width
     */
    public static int getMaxWidth() {
        GLFWVidMode mode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        return mode.width();
    }

    /**
     * Get the monitor height
     * 
     * @return monitor height
     */
    public static int getMaxHeight() {
        GLFWVidMode mode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        return mode.height();
    }

}
