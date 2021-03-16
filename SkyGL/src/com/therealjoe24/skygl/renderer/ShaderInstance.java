package com.therealjoe24.skygl.renderer;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_INT;
import static org.lwjgl.opengl.GL20.GL_FLOAT_MAT4;
import static org.lwjgl.opengl.GL20.GL_FLOAT_VEC2;
import static org.lwjgl.opengl.GL20.GL_FLOAT_VEC3;
import static org.lwjgl.opengl.GL20.GL_FLOAT_VEC4;
import static org.lwjgl.opengl.GL20.GL_INT_VEC2;
import static org.lwjgl.opengl.GL20.GL_INT_VEC3;
import static org.lwjgl.opengl.GL20.GL_INT_VEC4;
import static org.lwjgl.opengl.GL20.glUniform1f;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL20.glUniform2fv;
import static org.lwjgl.opengl.GL20.glUniform2iv;
import static org.lwjgl.opengl.GL20.glUniform3fv;
import static org.lwjgl.opengl.GL20.glUniform3iv;
import static org.lwjgl.opengl.GL20.glUniform4fv;
import static org.lwjgl.opengl.GL20.glUniform4iv;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.BufferUtils;

import com.therealjoe24.skygl.renderer.camera.PerspectiveCamera;
import com.therealjoe24.skygl.renderer.texture.Texture;

/**
 * Shader Instance class
 * 
 * @author Brandon Stevens
 * @author www.therealjoe24.com
 * @author owner@therealjoe24.com
 *
 */
public class ShaderInstance {

	private ShaderProgram _program;
	private Map<String, Map.Entry<Integer, Integer>> _auxLocations;
	private Map<String, Object> _auxValues;
	
	private Map<String, Texture> _textures;
	
	private PerspectiveCamera _camera;
	
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
	 * Update the camera
	 * 
	 */
	private void UpdateCamera() {
		if (_camera == null) return;
		FloatBuffer fb1 = BufferUtils.createFloatBuffer(16);
		FloatBuffer fb2 = BufferUtils.createFloatBuffer(16);
		try {
			/* set the projection matrix uniform */
			_camera.getProjection(fb1);
			SetAuxUniform("uProjectionMatrix", fb1);
			/* set the view matrix uniform */
			_camera.getView(fb2);
			SetAuxUniform("uViewMatrix", fb2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Sets the camera
	 * 
	 * @param camera
	 */
	public void SetCamera(PerspectiveCamera camera) {
		_camera = camera;
		UpdateCamera();
	}
	
	/**
	 * Sets the program
	 * 
	 * @param program
	 */
	public void SetProgram(ShaderProgram program) {
		_program = program;
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
		UpdateCamera();
		for (String name : _auxLocations.keySet()) {
			if (_auxLocations.containsKey(name)) {
				Map.Entry<Integer, Integer> entry = _auxLocations.get(name);
				int type = entry.getValue();
				int location = entry.getKey();
				Object value = _auxValues.get(name);
				if (value == null) continue;
				try {
					/**
					 * Switches the GL type to set the uniform
					 * TODO: allow for more types (EX. FloatBuffer) for use with compatible GL types
					 * 
					 */
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
						case GL_FLOAT_MAT4:
							glUniformMatrix4fv(location, false, (FloatBuffer)value);
							break;
						default:
							break;
					}
				} catch (ClassCastException e) {
					/* the value given was not compatible with the opengl type */
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
	 * Called after the frame has completed rendering
	 * 
	 */
	public void postRender() {
		for (String name : _textures.keySet()) {
			_textures.get(name).unbind();
		}
	}
	
	/**
	 * Gets the shader program id
	 * 
	 * @return program ID
	 */
	public ShaderProgram getShaderProgram() {
		return _program;
	}
	
}
