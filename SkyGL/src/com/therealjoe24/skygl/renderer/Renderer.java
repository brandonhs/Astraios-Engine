package com.therealjoe24.skygl.renderer;

import static org.lwjgl.opengl.GL45.*;

public class Renderer {
	
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
	}
	
}
