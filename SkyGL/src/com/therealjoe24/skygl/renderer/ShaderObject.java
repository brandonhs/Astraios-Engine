package com.therealjoe24.skygl.renderer;

import static org.lwjgl.opengl.GL45.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

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
	 * @throws RuntimeException
	 */
	public ShaderObject(int type, String source) {
		_shader = glCreateShader(type);
		glShaderSource(_shader, source);
		glCompileShader(_shader);
		
		if (glGetShaderi(_shader, GL_COMPILE_STATUS) == 0) {
			throw new RuntimeException(glGetShaderInfoLog(_shader));
		}
	}
	
	/**
	 * Get the source from a file
	 * 
	 * @return source of file
	 * @throws FileNotFoundException
	 */
	public static String LoadSource(String path) {
		String source = "";
		try {
			File file = new File(path);
			Scanner reader = new Scanner(file);
			while (reader.hasNextLine()) {
				source += reader.nextLine()+"\n";
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return source;
	}

	/**
	 * Get the shader id
	 * 
	 * @return
	 */
	public int getShader() {
		return _shader;
	}
	
	/**
	 * Check if there has been a compiler error
	 * 
	 * @return compileError
	 */
	public boolean getCompileError() {
		return _compileError;
	}
	
}
