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
package com.therealjoe24.astraios.gui.fonts;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.nanovg.NanoVG;

/**
 * Wrapper for loading and managing fonts
 * 
 * @author TheRealJoe24
 *
 */
public class Font {

    private int _font;
    
    private String _name;
    
    /**
     * Create font without loading
     */
    public Font() {
        this("BOLD");
    }
    
    /**
     * Create font with name
     * 
     * @param name
     */
    public Font(String name) {
        _name = name;
    }
    
    /**
     * Create and load font from path
     * 
     * @param name
     * @param path
     * @param vg
     */
    public Font(String name, String path, long vg) {
        this(name);
        Load(path, vg);
    }
    
    /**
     * Load the font from a path
     * 
     * @param path
     * @param vg
     */
    public void Load(String path, long vg) {
        _font = LoadFont(vg, _name, path);
    }
    
    /*
     * Get the name of the font
     */
    public String getName() {
        return _name;
    }
    
    /**
     * Utility for loading a font from a path
     * 
     * @param vg
     * @param name
     * @param path
     * @return
     */
    public static int LoadFont(long vg, String name, String path) {
        int font = -1;
        try {
            InputStream in = new FileInputStream(path);
            byte[] data = in.readAllBytes();
            ByteBuffer buf = BufferUtils.createByteBuffer(data.length);
            buf.put(data);
            buf.flip();
            font = NanoVG.nvgCreateFontMem(vg, name, buf, 0);
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return font;
    }
    
}
