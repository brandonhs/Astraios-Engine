package com.therealjoe24.skygl.renderer.text;

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

import com.therealjoe24.skygl.renderer.BufferLoader;
import com.therealjoe24.skygl.renderer.texture.Texture;

/**
 * 
 * @author brand
 *
 */
public class TextElement {

	private Texture _texture;
	
	public TextElement(BufferLoader loader, String text, int size) {
		BufferedImage b = new BufferedImage(size, size, BufferedImage.TYPE_4BYTE_ABGR);
		
		Graphics2D g = b.createGraphics();
		g.setFont(new Font("asdf", 0, 24));
		g.setColor(Color.red);
		g.fillRect(0, 0, size, size);
		g.setColor(Color.white);
		g.drawString(text, 50, 50);
		
		AffineTransform tx = AffineTransform.getScaleInstance(1, -1);
	    tx.translate(0, -b.getHeight(null));
	    AffineTransformOp op = new AffineTransformOp(tx,
	        AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
	    b = op.filter(b, null);
		
		ByteBuffer buf = ByteBuffer.allocateDirect(size*size*4);
		DataBuffer db = b.getRaster().getDataBuffer();
		byte[] pix = ((DataBufferByte)(db)).getData();
		buf.put(pix);
		buf.flip();
		
		_texture = new Texture(size, size, buf);
	}
	
	public Texture getTexture() {
		return _texture;
	}
	
}
