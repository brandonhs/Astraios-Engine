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

/**
 * Stores the vao and mesh data of a mesh
 * 
 * @author TheRealJoe24
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
