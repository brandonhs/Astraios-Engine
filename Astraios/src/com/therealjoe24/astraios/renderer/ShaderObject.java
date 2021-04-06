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
package com.therealjoe24.astraios.renderer;

import static org.lwjgl.opengl.GL45.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

/**
 * Shader Object Class
 * 
 * @author TheRealJoe24
 *
 */
public class ShaderObject {

    /* has there been a compile error? */
    private boolean _compileError = false;
    /* opengl shader object */
    private int _shader;

    /**
     * Initialize shader object
     * 
     * @param type
     * @param source
     * @throws RuntimeException
     */
    public ShaderObject(int type, String source) {
        _shader = glCreateShader(type);
        glShaderSource(_shader, source);
        glCompileShader(_shader);

        if (glGetShaderi(_shader, GL_COMPILE_STATUS) == 0) {
            throw new RuntimeException(glGetShaderInfoLog(_shader));
        }
    }

    /**
     * Get the source from a file
     * 
     * @return source of file
     * @throws FileNotFoundException
     */
    public static String LoadSource(String path) {
        String source = "";
        try {
            InputStream file = ShaderObject.class.getResourceAsStream("/res/" + path);
            if (file == null) {
                // this is how we load file within editor (eg eclipse)
                file = ShaderObject.class.getClassLoader().getResourceAsStream(path);
            }
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                source += reader.nextLine() + "\n";
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return source;
    }

    /**
     * Get the shader id
     * 
     * @return shader id
     */
    public int getShader() {
        return _shader;
    }

    /**
     * Check if there has been a compiler error
     * 
     * @return compileError
     */
    public boolean getCompileError() {
        return _compileError;
    }

}
