package com.therealjoe24.skygl.renderer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.management.RuntimeErrorException;

import com.therealjoe24.skygl.texture.Texture;

import static org.lwjgl.opengl.GL45.*;

public class ShaderInstance {

	private ShaderProgram _program;
	private Map<String, Map.Entry<Integer, Integer>> _auxLocations;
	private Map<String, Object> _auxValues;
	
	private Map<String, Texture> _textures;
	
	public final static String[] defaultUniforms = {
			"uModelMatrix", "uProjectionMatrix", "uViewMatrix"
	};
	
	/**
	 * Initialize Shader Instance with Program
	 * 
	 * @param program
	 */
	public ShaderInstance(ShaderProgram program) {
		_program = program;
		_auxLocations = new HashMap<String, Map.Entry<Integer, Integer>>();
		_auxValues = new HashMap<String, Object>();
		_textures = new HashMap<String, Texture>();
		
		for (String name : program.getUniformNames()) {
			int location = program.getUniformLocation(name);
			int type = program.getUniformType(location);
			_auxLocations.put(name, Map.entry(location, type));
		}
	}
	
	/**
	 * Sets an auxiliary uniform
	 * 
	 * If the value is a texture, it will be added to a list of textures to be bound.
	 * 
	 * @param name
	 * @param value
	 * @throws RuntimeException
	 */
	public void SetAuxUniform(String name, Object value) {
		if (!_auxLocations.containsKey(name)) {
			throw new RuntimeException("Invalid aux uniform name");
		}
		if (value.getClass() == Texture.class) {
			System.out.println("Hey there");
			_textures.put(name, (Texture)value);
			_auxValues.put(name, 0);
		} else {
			_auxValues.put(name, value);
		}
	}
	
	/**
	 * Sets uniforms
	 * 
	 * @throws RuntimeException
	 */
	public void SetUniforms() {
		for (String name : _auxLocations.keySet()) {
			if (_auxLocations.containsKey(name)) {
				Map.Entry<Integer, Integer> entry = _auxLocations.get(name);
				int type = entry.getValue();
				int location = entry.getKey();
				Object value = _auxValues.get(name);
				try {
					switch (type) {
						case GL_FLOAT:
							glUniform1f(location, (float)value);
							break;
						case GL_FLOAT_VEC2:
							glUniform2fv(location, (float[])value);
							break;
						case GL_FLOAT_VEC3:
							glUniform3fv(location, (float[])value);
							break;
						case GL_FLOAT_VEC4:
							glUniform4fv(location, (float[])value);
							break;
						case GL_INT:
							glUniform1i(location, (int)value);
							break;
						case GL_INT_VEC2:
							glUniform2iv(location, (int[])value);
							break;
						case GL_INT_VEC3:
							glUniform3iv(location, (int[])value);
							break;
						case GL_INT_VEC4:
							glUniform4iv(location, (int[])value);
							break;
						default:
							break;
					}
				} catch (ClassCastException e) {
					e.printStackTrace();
				}
			}
			if (_textures.containsKey(name)) {
				Texture texture = _textures.get(name);
				texture.bind();
			}
		}
	}
	
	/**
	 * 
	 */
	public void postRender() {
		for (String name : _textures.keySet()) {
			_textures.get(name).unbind();
		}
	}
	
	public ShaderProgram getShaderProgram() {
		return _program;
	}
	
}
