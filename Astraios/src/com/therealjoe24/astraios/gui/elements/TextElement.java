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
package com.therealjoe24.astraios.gui.elements;

import java.io.InputStream;
import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NanoVG;

import com.therealjoe24.astraios.Display;
import com.therealjoe24.astraios.gui.CanvasElement;
import com.therealjoe24.astraios.gui.CanvasElementEvent;
import com.therealjoe24.astraios.gui.fonts.Font;

/**
 * Canvas element for text rendering
 * 
 * @author TheRealJoe24
 *
 */
public class TextElement extends CanvasElement {

    /**
     * Name of the font
     */
    public static final String FONT_NAME = "BOLD";
    /**
     * internal text data
     */
    private String _text;
    /**
     * color buffer
     */
    private NVGColor _col;
    /**
     * size of font in pixels
     */
    private float _fontSize;
    /**
     * font
     */
    private Font _font;

    /**
     * Create Text Element
     * 
     * @param text     text to display
     * @param nx       normalized x position
     * @param ny       normalized y position
     * @param r        red component
     * @param g        green component
     * @param b        blue component
     * @param a        transparency component
     * @param fontSize font size in pixels
     */
    public TextElement(String text, float nx, float ny, float r, float g, float b, float a, float fontSize) {
        super(nx, ny, 1, 1);

        _text = text;
        _fontSize = fontSize;
        
        _font = new Font(FONT_NAME);

        /* create color buffer and store color data */
        _col = NVGColor.create();
        _col.r(r);
        _col.g(g);
        _col.b(b);
        _col.a(a);
    }

    /**
     * Sets the text if the element
     * 
     * @param text
     */
    public void SetText(String text) {
        _text = text;
    }

    @Override
    public void InitFromContext(long vg) {
        _font.Load("res/OpenSans-Bold.ttf", vg);
    }

    @Override
    public void RenderToCanvas(long vg) {
        NanoVG.nvgFontFace(vg, _font.getName());
        NanoVG.nvgTextAlign(vg, NanoVG.NVG_ALIGN_MIDDLE | NanoVG.NVG_ALIGN_CENTER);
        float maxSize = Display.getMaxWidth();
        float frameWidth = _transform.traverseViewportWidth();
        float frameHeight = _transform.traverseViewportHeight();
        float size = _transform.traverseViewportWidth();
        if (frameHeight > frameWidth) {
            size = frameHeight;
        }
        float fontSize = _fontSize*(size/maxSize);
        NanoVG.nvgFontSize(vg, fontSize);
        NanoVG.nvgFillColor(vg, _col);
        NanoVG.nvgText(vg, _transform.getPosition().x, _transform.getPosition().y,
                _text);
    }

    @Override
    protected void ReceiveEvent(CanvasElementEvent evt, double mouseX, double mouseY) { }
}
