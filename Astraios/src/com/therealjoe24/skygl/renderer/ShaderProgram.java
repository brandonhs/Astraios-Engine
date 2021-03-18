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
package com.therealjoe24.skygl.renderer;

import static org.lwjgl.opengl.GL45.*;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.BufferUtils;

/**
 * Creates a program
 * 
 * @author TheRealJoe24
 *
 */
public class ShaderProgram {

    /* list of shader objects to be linked */
    private List<ShaderObject> _shaders = new ArrayList<ShaderObject>();
    /* program int */
    private int _program;

    /**
     * Initialize shader program
     * 
     * @param shaders
     */
    public ShaderProgram(List<ShaderObject> shaders) {
        _program = glCreateProgram();
        for (ShaderObject shader : shaders) {
            if (!shader.getCompileError()) {
                glAttachShader(_program, shader.getShader());
                _shaders.add(shader);
            }
        }
        glLinkProgram(_program);
    }

    /**
     * Finds the location of a uniform name
     * 
     * @param name
     * @return uniform location
     */
    public int getUniformLocation(String name) {
        return glGetUniformLocation(_program, name);
    }

    /**
     * Gets the type of the uniform EX. GL_FLOAT, GLOAT_FLOAT_VEC3
     * 
     * @param location location of the uniform
     * @return
     */
    public int getUniformType(int location) {
        IntBuffer size = BufferUtils.createIntBuffer(32);
        IntBuffer type = BufferUtils.createIntBuffer(32);
        glGetActiveUniform(_program, location, size, type);
        return type.get();
    }

    /**
     * Gets a list of all active uniforms in the shader
     * 
     * @return List of uniform names
     */
    public List<String> getUniformNames() {
        int count = glGetProgrami(_program, GL_ACTIVE_UNIFORMS);
        List<String> uniformNames = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            IntBuffer s = BufferUtils.createIntBuffer(32);
            String name = glGetActiveUniform(_program, i, s, s);
            uniformNames.add(name);
        }
        return uniformNames;
    }

    /**
     * 
     */
    public void Use() {
        glUseProgram(_program);
    }

}
