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

import org.lwjgl.nanovg.NanoVG;

import com.therealjoe24.astraios.gui.CanvasElement;
import com.therealjoe24.astraios.gui.CanvasElementEvent;

public class ColorRectElement extends CanvasElement {

    public ColorRectElement(float nx, float ny, float nw, float nh) {
        super(nx, ny, nw, nh);
    }

    @Override
    public void SendEvent(CanvasElementEvent evt) {
    }

    @Override
    public void InitFromContext(long vg) {
    }

    @Override
    public void RenderToCanvas(int frameWidth, int frameHeight, long vg) {
        NanoVG.nvgRect(vg, _transform.getPosition().x * frameWidth, _transform.getPosition().y * frameHeight, _width*frameWidth, _height*frameHeight);
        NanoVG.nvgFill(vg);
    }

}
