package com.therealjoe24.skygl.renderer.objects;

import java.io.File;
import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import com.therealjoe24.skygl.renderer.PrimitiveMesh;

/**
 * Creates a Model
 * 
 * @author Brandon Stevens
 * @author www.therealjoe24.com
 * @author owner@therealjoe24.com
 *
 */
public class Model {

	/* primitive mesh instance */
	private PrimitiveMesh _mesh;
	
	private Matrix4f _modelMatrix;
	
	/**
	 * Creates a model from an obj file
	 * 
	 * @param obj object file
	 * @return created model
	 */
	public static Model fromObj(File obj) {
		return null;
	}
	
	/**
	 * Initializes model from mesh
	 * 
	 * @param mesh mesh to store in model
	 */
	public Model(PrimitiveMesh mesh) {
		_modelMatrix = new Matrix4f();
		_modelMatrix.identity();
		_mesh = mesh;
	}
	
	/**
	 * Get the model matrix
	 * 
	 * @return model matrix
	 */
	public FloatBuffer getModelMatrix() {
		FloatBuffer fb = BufferUtils.createFloatBuffer(16);
		_modelMatrix.get(fb);
		return fb;
	}
	
	public void RotateY(float angle) {
		_modelMatrix.rotate(angle, 0, 1, 0);
	}
	
	public void RotateX(float angle) {
		_modelMatrix.rotate(angle, 1, 0, 0);
	}
	
	/**
	 * Get the primitive mesh data
	 * 
	 * @return mesh
	 */
	public PrimitiveMesh getMesh() {
		return _mesh;
	}
	
}
