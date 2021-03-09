package com.therealjoe24.skygl.renderer;

import static org.lwjgl.opengl.GL45.*;

/**
 * 
 * @author brand
 */
public class ShaderObject {

	/* has there been a compile error? */
	private boolean _compileError = false;
	/* opengl shader object */
	private int _shader;
	
	/**
	 * Initialize shader object
	 * 
	 * @param type
	 * @param source
	 */
	public ShaderObject(int type, String source) {
		_shader = glCreateShader(type);
		glShaderSource(_shader, source);
		glCompileShader(_shader);
	}

	public int getShader() {
		return _shader;
	}
	
	public boolean getCompileError() {
		return _compileError;
	}
	
}
