package com.therealjoe24.skygl.gui;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.joml.Vector2i;

/**
 * Base class for canvas elements
 * 
 * @author Brandon Stevens
 * @author www.therealjoe24.com
 * @author owner@therealjoe24.com
 *
 */
public abstract class CanvasElement {

	protected CanvasElementTransform _transform;
	
	/**
	 * Create base canvas element
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public CanvasElement(int x, int y) {
		_transform = new CanvasElementTransform(new Vector2i(x, y));
	}
	
	/**
	 * Render Element to canvas graphic
	 * 
	 * @param g
	 */
	public abstract void RenderToCanvas(Graphics2D g);
	
	public abstract void OnRender();
	
}
