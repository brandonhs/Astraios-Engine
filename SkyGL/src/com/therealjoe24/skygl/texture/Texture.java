package com.therealjoe24.skygl.texture;

import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_REPEAT;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;
import static org.lwjgl.opengl.GL45.glCreateTextures;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;

import de.matthiasmann.twl.utils.PNGDecoder;

/**
 * Texture utils class
 * 
 * @author Brandon Stevens
 * @author owner@therealjoe24.com
 * @author TheRealJoe24
 *
 */
public class Texture {
	
	private int texID;
	
	public int width, height;
	public ByteBuffer buf;
	
	/**
	 * Initialize texture object
	 * 
	 * @param w the image width
	 * @param h the image height
	 * @param buf the byte buffer to be stored
	 */
	public Texture(int w, int h, ByteBuffer buf) {
		width = w;
		height = h;
		this.buf = buf;
		
		texID = glCreateTextures(GL_TEXTURE_2D);
		glBindTexture(GL_TEXTURE_2D, texID);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);	
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, this.buf);
		glGenerateMipmap(GL_TEXTURE_2D);
		glBindTexture(GL_TEXTURE_2D, 0);
	}

	/**
	 * Load the texture into a texture object
	 * 
	 * @param path path to png
	 * @return Texture object with loaded data
	 */
	public static Texture LoadTexture(String path) {
		ByteBuffer buf = null;
		int w = 0;
		int h = 0;
		try {
			InputStream in = new FileInputStream(path);
			
			PNGDecoder decoder = new PNGDecoder(in);
			w = decoder.getWidth();
			h = decoder.getHeight();
			buf = ByteBuffer.allocateDirect(4*w*h);
			decoder.decode(buf, w*4, PNGDecoder.Format.RGBA);
			buf.flip();
			
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Texture(w, h, buf);
	}
	
	/**
	 * Bind the texture
	 * 
	 */
	public void bind() {
		glBindTexture(GL_TEXTURE_2D, texID);
	}
	
	/**
	 * Unbind the texture
	 * 
	 */
	public void unbind() {
		glBindTexture(GL_TEXTURE_2D, 0);
	}
	
}
