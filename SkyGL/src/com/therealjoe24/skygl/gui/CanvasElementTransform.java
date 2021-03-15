package com.therealjoe24.skygl.gui;

import org.joml.Vector2i;

/**
 * Stores the transform of a canvas element
 * 
 * @author Brandon Stevens
 * @author www.therealjoe24.com
 * @author owner@therealjoe24.com
 *
 */
public class CanvasElementTransform {

	private Vector2i _position;
	
	public CanvasElementTransform(Vector2i position) {
		_position = position;
	}

	/**
	 * Gets the position
	 * 
	 * @return position
	 */
	public Vector2i getPosition() {
		return new Vector2i(_position);
	}
	
}
