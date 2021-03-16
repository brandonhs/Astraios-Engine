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
package com.therealjoe24.skygl.gui.elements;

import com.therealjoe24.skygl.Display;
import com.therealjoe24.skygl.gui.CanvasElement;
import com.therealjoe24.skygl.renderer.ShaderObject;
import java.io.InputStream;
import java.nio.ByteBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NanoVG;

/**
 * Canvas element for text rendering
 * 
 * @author Brandon Stevens
 * @author www.therealjoe24.com
 * @author owner@therealjoe24.com
 *
 */
public class TextElement extends CanvasElement {
	
	/**
	 * Name of the font
	 */
	public static final String FONT_NAME = "BOLD";
	/**
	 * internal text data
	 */
	private String _text;
	/**
	 * color buffer
	 */
	private NVGColor _col;
	/**
	 * size of font in pixels
	 */
	private float _fontSize;
	/**
	 * font id
	 */
	private int _font;

	/**
	 * Create Text Element
	 * 
	 * @param text text to display
	 * @param nx normalized x position
	 * @param ny normalized y position
	 * @param r red component
	 * @param g green component
	 * @param b blue component
	 * @param a transparency component
	 * @param fontSize font size in pixels
	 */
	public TextElement(String text, float nx, float ny, float r, float g, float b, float a, float fontSize) {
		super(nx, ny);
		
		_text = text;
		_fontSize = fontSize;
		
		/* create color buffer and store color data */
		_col = NVGColor.create();
		_col.r(r);
		_col.g(g);
		_col.b(b);
		_col.a(a);
	}
	
	public void SetText(String text) {
		_text = text;
	}

	@Override
	public void InitFromContext(long vg) {
		_font = CreateFont(vg);
	}

	@Override
	public void RenderToCanvas(int frameWidth, int frameHeight, long vg) {
		NanoVG.nvgFontFace(vg, "BOLD");
		NanoVG.nvgTextAlign(vg, 10);
		//float fontSize = _fontSize*(float)frameWidth/(float)Display.getMaxWidth();
		NanoVG.nvgFontSize(vg, _fontSize);
		NanoVG.nvgFillColor(vg, _col);
		NanoVG.nvgText(vg, (_transform.getPosition()).x * frameWidth,
				(_transform.getPosition()).y * frameHeight, _text);
	}

	/**
	 * Create a font
	 * 
	 * @param vg
	 * @return integer id of font
	 */
	static int CreateFont(long vg) {
		int font = -1;
		try {
			InputStream file = TextElement.class.getResourceAsStream("/res/OpenSans-Bold.ttf");
			if (file == null)
				// We are in IDE
				file = TextElement.class.getClassLoader().getResourceAsStream("OpenSans-Bold.ttf");
			byte[] data = file.readAllBytes();
			ByteBuffer buf = BufferUtils.createByteBuffer(data.length);
			buf.put(data);
			buf.flip();
			font = NanoVG.nvgCreateFontMem(vg, FONT_NAME, buf, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return font;
	}
}
