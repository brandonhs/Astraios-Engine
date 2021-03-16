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
 * The Transform of a canvas element
 * 
 * @author TheRealJoe24
 *
 */
public class CanvasElementTransform {
	
	private Vector2f _position;

	/**
	 * Create an element transform
	 * 
	 * @param position
	 */
	public CanvasElementTransform(Vector2f position) {
		_position = position;
	}

	/**
	 * get the position
	 * 
	 * @return position
	 */
	public Vector2f getPosition() {
		return new Vector2f(_position);
	}
	
}
