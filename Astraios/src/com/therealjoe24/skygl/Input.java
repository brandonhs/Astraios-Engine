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
package com.therealjoe24.skygl;

import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.glfw.*;

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
        }
    };

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
