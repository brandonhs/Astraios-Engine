package com.therealjoe24.skygl.renderer;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_DYNAMIC_DRAW;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL45.glCreateBuffers;
import static org.lwjgl.opengl.GL45.glCreateVertexArrays;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;

/**
 * Allows loading buffers into a vao
 * 
 * @author Brandon Stevens
 * @author www.therealjoe24.com
 * @author owner@therealjoe24.com
 *
 */
public class BufferLoader {

	/* list of vaos */
	private List<Integer> _vaos = new ArrayList<Integer>();
	/* list of vbos */
	private List<Integer> _vbos = new ArrayList<Integer>();
	
	/**
	 * Destroy all buffers
	 * 
	 */
	public void Terminate() {
		for (int vao : _vaos) {
			glDeleteVertexArrays(vao);
		}
		for (int vbo : _vbos) {
			glDeleteVertexArrays(vbo);
		}
	}
	
	/**
	 * TODO: Remove redundant code
	 */
	public int CreateTextVAO() {
		int vao = glCreateVertexArrays();
		_vaos.add(vao);
		glBindVertexArray(vao);
		
		int vbo = glCreateBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, 6*4*Float.BYTES, GL_DYNAMIC_DRAW);
		glEnableVertexAttribArray(0);
		glVertexAttribPointer(0, 4, GL_FLOAT, false, 4*Float.BYTES, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindVertexArray(0);
		
		return vao;
	}
	
	public PrimitiveMesh LoadToVAO(MeshData data) {
		float[] positions = data.getVertices();
		float[] texturePositions = data.getUvs();
		int  [] indices = data.getIndices();
		
		/* position data must be set */
		if (positions == null) return null;
		
		PrimitiveMesh mesh = null;
		
		if (indices != null) {
			/* TODO: allow texture positions to be set separately from indices */
			if (texturePositions != null) {
				mesh = LoadToVAO(indices, positions, texturePositions);
			} else {
				mesh = LoadToVAO(indices, positions);
			}
		} else {
			mesh = LoadToVAO(positions);
		}
		
		return mesh;
	}
	
	/**
	 * Stores data in primitive mesh
	 * 
	 * @param positions
	 * @return primitive mesh
	 */
	public PrimitiveMesh LoadToVAO(float[] positions) {
		int vao = glCreateVertexArrays();
		_vaos.add(vao);
		glBindVertexArray(vao);
		
		storeInAttribute(0, positions, 3);
		
		glBindVertexArray(0);
		return new PrimitiveMesh(positions.length/3, vao);
	}
	
	/**
	 * Stores data in primitive mesh
	 * 
	 * @param positions
	 * @param indices
	 * @return primitive mesh
	 */
	public PrimitiveMesh LoadToVAO(int[] indices, float[] positions) {
		int vao = glCreateVertexArrays();
		_vaos.add(vao);
		glBindVertexArray(vao);
		
		bindIndicesBuffer(indices);
		storeInAttribute(0, positions, 3);
		
		glBindVertexArray(0);
		return new PrimitiveMesh(indices.length, vao);
	}
	
	/**
	 * Stores data in primitive mesh
	 * 
	 * @param positions
	 * @param indices
	 * @return primitive mesh
	 */
	public PrimitiveMesh LoadToVAO(int[] indices, float[] positions, float[] texturePositions) {
		int vao = glCreateVertexArrays();
		_vaos.add(vao);
		glBindVertexArray(vao);
		
		bindIndicesBuffer(indices);
		storeInAttribute(0, positions, 3);
		storeInAttribute(1, texturePositions, 2);
		
		glBindVertexArray(0);
		return new PrimitiveMesh(indices.length, vao);
	}
	
	/**
	 * Stores indices in GL_ELEMENT_ARRAY_BUFFER
	 * 
	 * @param data
	 */
	public void bindIndicesBuffer(int[] data) {
		int vbo = glCreateBuffers();
		_vbos.add(vbo);
		
		IntBuffer buf = BufferUtils.createIntBuffer(data.length);
		buf.put(data);
		buf.flip();
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vbo);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, buf, GL_STATIC_DRAW);
	}

	/**
	 * Stores float data in vbo
	 * 
	 * @param index
	 * @param data
	 * @param numComponents
	 */
	public void storeInAttribute(int index, float[] data, int numComponents) {
		int vbo = glCreateBuffers();
		_vbos.add(vbo);
		
		FloatBuffer buf = BufferUtils.createFloatBuffer(data.length);
		buf.put(data);
		buf.flip();
		
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, buf, GL_STATIC_DRAW);
		glVertexAttribPointer(index, numComponents, GL_FLOAT, false, 0, 0);
		glEnableVertexAttribArray(index);
	}
	
	/**
	 * Stores float data in vbo
	 * 
	 * @param index
	 * @param data
	 * @param numComponents
	 */
	public void storeInAttribute(int index, int[] data, int numComponents) {
		int vbo = glCreateBuffers();
		_vbos.add(vbo);
		
		IntBuffer buf = BufferUtils.createIntBuffer(data.length);
		buf.put(data);
		buf.flip();
		
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, buf, GL_STATIC_DRAW);
		glVertexAttribPointer(index, numComponents, GL_UNSIGNED_INT, false, 0, 0);
		glEnableVertexAttribArray(index);
	}
	
}
