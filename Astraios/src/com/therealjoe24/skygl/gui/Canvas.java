/*******************************************************************************
 * Copyright (C) 2021 TheRealJoe24
 * 
 * This program is free software: you can redistribute it and/or modify
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
package com.therealjoe24.skygl.gui;

import com.therealjoe24.skygl.Display;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.nanovg.NanoVGGL3;

/**
 * Stores individual elements
 * 
 * @author Brandon Stevens
 * @author www.therealjoe24.com
 * @author owner@therealjoe24.com
 *
 */
public class Canvas {
	
	private List<CanvasElement> _elements = new ArrayList<>();

	private int _width;
	private int _height;
	private long _vg;

	/**
	 * Create Canvas Object
	 * 
	 */
	public Canvas() {
		Display.AddResizeCallbackFunc(new Display.SkyGLDisplayResizeFunc() {
			public void invoke() {
				_width = Display.getWidth();
				_height = Display.getHeight();
			}
		});
		_vg = NanoVGGL3.nvgCreate(NanoVGGL3.NVG_ANTIALIAS | NanoVGGL3.NVG_STENCIL_STROKES);
	}

	/**
	 * Add new element to element list
	 * 
	 * @param el element to add
	 */
	public void AddElement(CanvasElement el) {
		_elements.add(el);
		el.InitFromContext(_vg);
	}

	/**
	 * Render the canvas
	 * 
	 */
	public void Render() {
		NanoVG.nvgBeginFrame(_vg, _width, _height, 1);
		for (CanvasElement el : _elements)
			el.RenderToCanvas(_width, _height, _vg);
		NanoVG.nvgEndFrame(_vg);
	}
	
}
