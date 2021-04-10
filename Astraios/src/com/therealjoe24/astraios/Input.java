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

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.glfw.*;

import com.therealjoe24.astraios.Display.SkyGLDisplayResizeFunc;

/**
 * Input Handler
 * 
 * @author TheRealJoe24
 *
 */
public class Input {

    /**
     * mouse data
     */
    private static double _mouseX, _mouseY, _deltaMouseX, _deltaMouseY;

    /**
     * Callback function for the cursor position change
     * 
     */
    private static GLFWCursorPosCallback _cursorPosCallback = new GLFWCursorPosCallback() {
        @Override
        public void invoke(long window, double xpos, double ypos) {
            _deltaMouseX = xpos - _mouseX;
            _deltaMouseY = ypos - _mouseY;
            _mouseX = xpos;
            _mouseY = ypos;
            
            for (AstraiosInputCursorFunc callback : _cursorFuncs) {
                callback.invoke(xpos, ypos, _deltaMouseX, _deltaMouseY);
            }
        }
    };
    
    private static GLFWMouseButtonCallback _mouseButtonCallback = new GLFWMouseButtonCallback() {
        @Override
        public void invoke(long window, int button, int action, int mods) {
            for (AstraiosInputMouseButtonFunc callback : _mouseButtonFuncs) {
                callback.invoke(_mouseX, _mouseY, button, action, mods);
            }
        }
    };
    
    /**
     * Cursor Callbacks
     * 
     */
    public static abstract interface AstraiosInputCursorFunc {
        public abstract void invoke(double xpos, double ypos, double deltaX, double deltaY);
    }

    private static List<AstraiosInputCursorFunc> _cursorFuncs = new ArrayList<>();
    
    /**
     * Cursor Callbacks
     * 
     */
    public static abstract interface AstraiosInputMouseButtonFunc {
        public abstract void invoke(double xpos, double ypos, int button, int action, int mods);
    }

    private static List<AstraiosInputMouseButtonFunc> _mouseButtonFuncs = new ArrayList<>();

    /**
     * Get the mouse delta x
     * 
     * @return
     */
    public static float getMouseDeltaX() {
        return (float) _deltaMouseX;
    }

    /**
     * Get the mouse delta y
     * 
     * @return
     */
    public static float getMouseDeltaY() {
        return (float) _deltaMouseY;
    }

    /**
     * Initialize Input Manager
     * 
     */
    public static void Init() {
        glfwSetCursorPosCallback(Display.getWindowID(), _cursorPosCallback);
        glfwSetMouseButtonCallback(Display.getWindowID(), _mouseButtonCallback);
    }
    
    public static void AddCursorCallback(AstraiosInputCursorFunc func) {
        _cursorFuncs.add(func);
    }

    public static void AddMouseButtonCallback(AstraiosInputMouseButtonFunc func) {
        _mouseButtonFuncs.add(func);
    }
    
    /**
     * Update the Input Manager
     * 
     */
    public static void Update() {
        glfwPollEvents();
        _deltaMouseX = 0;
        _deltaMouseY = 0;
    }

}
