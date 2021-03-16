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

import org.joml.Vector2f;

/**
 * Base class for canvas elements
 * 
 * @author TheRealJoe24
 *
 */
public abstract class CanvasElement {
	
	/**
	 * canvas element transform
	 */
	protected CanvasElementTransform _transform;

	/**
	 * Create base canvas element
	 * 
	 * @param nx
	 * @param ny
	 */
	public CanvasElement(float nx, float ny) {
		_transform = new CanvasElementTransform(new Vector2f(nx, ny));
	}

	/**
	 * Initialize the element from render context
	 * 
	 * @param vg
	 */
	public abstract void InitFromContext(long vg);

	/**
	 * Render the element to the render context
	 * 
	 * @param frameWidth
	 * @param frameHeight
	 * @param vg
	 */
	public abstract void RenderToCanvas(int frameWidth, int frameHeight, long vg);
	
}
