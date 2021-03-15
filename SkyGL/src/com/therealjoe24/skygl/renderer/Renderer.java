package com.therealjoe24.skygl.renderer;

import static org.lwjgl.opengl.GL45.*;

import com.therealjoe24.skygl.gui.Canvas;
import com.therealjoe24.skygl.gui.elements.TextElement;
import com.therealjoe24.skygl.renderer.camera.PerspectiveCamera;
import com.therealjoe24.skygl.renderer.objects.Model;

/**
 * Renderer
 * 
 * @author Brandon Stevens
 * @author www.therealjoe24.com
 * @author owner@therealjoe24.com
 *
 */
public class Renderer {
	
	/**
	 * Render mesh
	 * 
	 * @param instance instance of shader
	 * @param mesh mesh object to render
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
	 * @param instance instance of shader
	 * @param model model object to render
	 */
	public void RenderModel(ShaderInstance instance, Model model) {
		instance.SetAuxUniform("uModelMatrix", model.getModelMatrix());
		
		RenderMesh(instance, model.getMesh());
	}
	
	public void RenderCanvas(Canvas canvas) {
		RenderMesh(canvas.getShaderInstance(), canvas.getQuad());
	}
	
}
