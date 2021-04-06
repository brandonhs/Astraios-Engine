/*******************************************************************************
 * This file is part of the Astraios Engine distribution <https://github.com/TheRealJoe24/Astraios-Engine>
 * Copyright (C) 2021 TheRealJoe24
 * 
 * Astraios Engine is free software: you can redistribute it and/or modify
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
package com.therealjoe24.astraios.renderer.objects;

import java.io.File;
import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import com.therealjoe24.astraios.renderer.PrimitiveMesh;
import com.therealjoe24.astraios.renderer.ShaderInstance;

/**
 * Creates a Model
 * 
 * @author TheRealJoe24
 *
 */
public class Model {

    /* primitive mesh instance */
    private PrimitiveMesh _mesh;

    private Matrix4f _modelMatrix;
    
    private ShaderInstance _instance;

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
    
    /**
     * Gets the shader instance from the model for rendering
     * 
     */
    public ShaderInstance getShaderInstance() {
        return _instance;
    }

    /**
     * Set the shader instance of the model
     * 
     * @param instance
     */
    public void SetInstance(ShaderInstance instance) {
       _instance = instance;
    }

    /**
     * Prepares the model for rendering
     * 
     */
    public void PrepareInstance() {
        if (_instance == null) return;
        _instance.SetAuxUniform("uModelMatrix", getModelMatrix());
    }

}
