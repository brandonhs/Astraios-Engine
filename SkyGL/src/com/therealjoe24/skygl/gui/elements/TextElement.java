package com.therealjoe24.skygl.gui.elements;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;

import com.therealjoe24.skygl.gui.CanvasElement;
import com.therealjoe24.skygl.renderer.BufferLoader;
import com.therealjoe24.skygl.renderer.texture.Texture;

/**
 * 
 * @author brand
 *
 */
public class TextElement extends CanvasElement {
	
	private String _text;
	
	/**
	 * Create text element
	 * 
	 * @param loader
	 * @param text
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public TextElement(String text, int x, int y) {
		super(x, y);
		_text = text;
	}
	
	@Override
	public void RenderToCanvas(Graphics2D g) {
		g.setFont(new Font("asdf", 0, 24));
		g.setColor(Color.white);
		g.drawString(_text, _transform.getPosition().x, _transform.getPosition().y);
	}

	@Override
	public void OnRender() { }
	
}
