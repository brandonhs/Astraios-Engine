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
package com.therealjoe24.skygl.renderer;

import com.therealjoe24.skygl.renderer.texture.Texture;

/**
 * Mesh Data class
 * 
 * @author TheRealJoe24
 *
 */
public class MeshData {

    private int[] _indices = null;
    private float[] _vertices = null, _uvs = null;

    public MeshData() {
    }

    /**
     * Create Quad Mesh Data from texture
     * 
     * @param texture texture to create quad mesh data from
     * @param scale   the scale of the texture
     */
    public MeshData(Texture texture, float scale) {
        /* aspect ratio of the texture */
        float aspect = (float) texture.width / (float) texture.height;

        float width = (1f) * scale;
        float height = (1f / aspect) * scale;

        _vertices = new float[] { -width, height, 0, -width, -height, 0, width, -height, 0, width, height, 0, };
        _uvs = new float[] { 0, 1, 0, 0, 1, 0, 1, 1 };
        _indices = new int[] { 0, 1, 2, 2, 3, 0, };
    }

    public void SetVertices(float[] vertices) {
        _vertices = vertices;
    }

    public void SetIndices(int[] indices) {
        _indices = indices;
    }

    public void SetUvs(float[] uvs) {
        _uvs = uvs;
    }

    public float[] getVertices() {
        return _vertices;
    }

    public int[] getIndices() {
        return _indices;
    }

    public float[] getUvs() {
        return _uvs;
    }

}
