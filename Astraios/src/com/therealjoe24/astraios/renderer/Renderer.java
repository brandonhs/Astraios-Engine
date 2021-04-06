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

import static org.lwjgl.opengl.GL45.*;

import com.therealjoe24.astraios.gui.Canvas;
import com.therealjoe24.astraios.gui.elements.TextElement;
import com.therealjoe24.astraios.renderer.camera.PerspectiveCamera;
import com.therealjoe24.astraios.renderer.objects.Model;

/**
 * Renderer
 * 
 * @author TheRealJoe24
 *
 */
public class Renderer {

    /**
     * Render mesh
     * 
     * @param instance instance of shader
     * @param mesh     mesh object to render
     */
    public void RenderMesh(ShaderInstance instance, PrimitiveMesh mesh) {
        glBindVertexArray(mesh.getVAO());

        instance.getShaderProgram().Use();
        instance.SetUniforms();

        if (glGetInteger(GL_ELEMENT_ARRAY_BUFFER_BINDING) > 0) {
            glDrawElements(GL_TRIANGLES, mesh.getVertexCount(), GL_UNSIGNED_INT, 0);
        } else {
            glDrawArrays(GL_TRIANGLES, 0, mesh.getVertexCount());
        }
        glBindVertexArray(0);

        instance.postRender();
    }

    /**
     * Render model
     * 
     * @param model model object to render
     * 
     * @apiNote Will throw RuntimeException if the model shader instance is a null reference
     */
    public void RenderModel(Model model) {
        ShaderInstance instance = model.getShaderInstance();
        if (instance == null) {
            throw new RuntimeException("Renderer: Shader instance of model has not been set!");
        } else {
            model.PrepareInstance();
            RenderMesh(instance, model.getMesh());
        }
    }

}
