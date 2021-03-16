package com.therealjoe24.skygl.gui;

import org.joml.Vector2f;

/**
 * The Transform of a canvas element
 * 
 * @author Brandon Stevens
 * @author www.therealjoe24.com
 * @author owner@therealjoe24.com
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