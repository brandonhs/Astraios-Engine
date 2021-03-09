package com.therealjoe24.skygl.renderer;

/**
 * Stores the vao and mesh data of a mesh
 * 
 * @author Brandon Stevens
 * @author www.therealjoe24.com
 * @author owner@therealjoe24.com
 *
 */
public class PrimitiveMesh {

	/* number of vertices */
	private int _vertexCount;
	/* vao */
	private int _vao;
	
	public int getVertexCount() {
		return _vertexCount;
	}

	public int getVAO() {
		return _vao;
	}
	
	/**
	 * Instance of Primitive Mesh
	 * 
	 * @param vertexCount
	 * @param vao
	 */
	public PrimitiveMesh(int vertexCount, int vao) {
		_vertexCount = vertexCount;
		_vao = vao;
	}
	
}
