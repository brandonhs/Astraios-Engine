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
package com.therealjoe24.astraios.renderer;

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
 * Utility for loading data into a mesh
 * 
 * @author TheRealJoe24
 *
 */
public class BufferLoader {

    /* list of vaos */
    private List<Integer> _vaos = new ArrayList<Integer>();
    /* list of vbos */
    private List<Integer> _vbos = new ArrayList<Integer>();

    /**
     * Deallocate loaded buffers
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
     * Create primitive mesh from data object
     * 
     * @param data
     * @return
     */
    public PrimitiveMesh LoadToVAO(MeshData data) {
        float[] positions = data.getVertices();
        float[] texturePositions = data.getUvs();
        int[] indices = data.getIndices();

        /* position data must be set */
        if (positions == null)
            return null;

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
     * @param positions position data to store
     * @return primitive mesh
     */
    public PrimitiveMesh LoadToVAO(float[] positions) {
        int vao = glCreateVertexArrays();
        _vaos.add(vao);
        glBindVertexArray(vao);

        storeInAttribute(0, positions, 3);

        glBindVertexArray(0);
        return new PrimitiveMesh(positions.length / 3, vao);
    }

    /**
     * Stores data in primitive mesh
     * 
     * @param positions position data to store
     * @param indices index data to store
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
     * @param positions position data to store
     * @param indices index data to store
     * @param texturePositions uv coordinates to store
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
     * @param data index data to store
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
     * @param index index of attribute
     * @param data float data to store
     * @param numComponents number of componenets per vertex
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
     * Stores integer data in vbo
     * 
     * @param index index of attribute
     * @param data int data to store
     * @param numComponents number of componenets per vertex
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
