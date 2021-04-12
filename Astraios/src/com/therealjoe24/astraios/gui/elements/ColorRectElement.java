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

import org.joml.Vector2f;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NanoVG;

import com.therealjoe24.astraios.Display;
import com.therealjoe24.astraios.gui.CanvasElement;
import com.therealjoe24.astraios.gui.CanvasElementEvent;

/**
 * Colored rectangle element
 * 
 * @author TheRealJoe24
 *
 */
public class ColorRectElement extends CanvasElement {
    
    /**
     * color buffer
     */
    private NVGColor _col;

    /**
     * Creates a color rect element
     * 
     * @param nx
     * @param ny
     * @param nw
     * @param nh
     */
    public ColorRectElement(float nx, float ny, float nw, float nh) {
        super(nx, ny, nw, nh);
        
        /* create color buffer and store color data */
        _col = NVGColor.create();
        _col.r(1);
        _col.g(0);
        _col.b(0);
        _col.a(1);
    }
    
    /**
     * Sets the color of the rect
     * 
     * @param r
     * @param g
     * @param b
     * @param a
     */
    public void SetColor(float r, float g, float b, float a) {
        _col.r(r);
        _col.g(g);
        _col.b(b);
        _col.a(a);
    }

    @Override
    public void InitFromContext(long vg) {
    }

    @Override
    public void RenderToCanvas(long vg) {
        NanoVG.nvgFillColor(vg, _col);
        /* NOTE: the begin path here is VERY important */
        NanoVG.nvgBeginPath(vg);
        Vector2f pos = _transform.getPosition();
        NanoVG.nvgRect(vg, pos.x, pos.y, _transform.getWidth(), _transform.getHeight());
        NanoVG.nvgFill(vg);
    }

    @Override
    protected void ReceiveEvent(CanvasElementEvent evt, double mouseX, double mouseY) { }

}
