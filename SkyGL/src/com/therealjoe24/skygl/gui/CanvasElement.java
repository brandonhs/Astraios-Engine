package com.therealjoe24.skygl.gui;

import org.joml.Vector2f;

/**
 * Base class for canvas elements
 * 
 * @author Brandon Stevens
 * @author www.therealjoe24.com
 * @author owner@therealjoe24.com
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
