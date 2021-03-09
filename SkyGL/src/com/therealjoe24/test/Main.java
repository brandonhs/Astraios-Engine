package com.therealjoe24.test;

import com.therealjoe24.skygl.Display;
import com.therealjoe24.skygl.renderer.BufferLoader;
import com.therealjoe24.skygl.renderer.PrimitiveMesh;
import com.therealjoe24.skygl.renderer.Renderer;
import com.therealjoe24.skygl.renderer.ShaderInstance;
import com.therealjoe24.skygl.renderer.ShaderObject;
import com.therealjoe24.skygl.renderer.ShaderProgram;

import static org.lwjgl.opengl.GL45.*;

import java.util.Arrays;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		Display.Create(640, 480, "Window");
		
		float[] positions = {
			-0.5f, 0.5f, 0, 
			-0.5f, -0.5f, 0, 
			0.5f, -0.5f, 0, 
			0.5f, 0.5f
		};
		int[] indices = {
				0, 1, 2,
				2,3,0
			};
 		
		BufferLoader loader = new BufferLoader();
		PrimitiveMesh mesh = loader.LoadToVAO(indices, positions);
		
		String vsSource =  
				"#version 110\n"+
		        "attribute vec3 vPos;\n"+
		        "void main()\n"+
		        "{\n"+
		        "    gl_Position = vec4(vPos, 1.0);\n"+
		        "}\n";
		String fsSource = 
				"#version 110\n"+
				"uniform vec3 color;\n"+
		        "void main()\n"+
		        "{\n"+
		        "    gl_FragColor = vec4(color, 1.0);\n"+
		        "}\n";
		
		ShaderObject vs = new ShaderObject(GL_VERTEX_SHADER, vsSource);
		ShaderObject fs = new ShaderObject(GL_FRAGMENT_SHADER, fsSource);
		ShaderObject[] shaders = {
				vs, fs
		};
		ShaderProgram program = new ShaderProgram(Arrays.asList(shaders));
		
		ShaderInstance instance = new ShaderInstance(program);
		
		Renderer renderer = new Renderer();
		
		Display.ShowWindow();
		Display.setClearColor(1, 0, 0);
		
		instance.SetAuxUniform("color", new float[] { 1,0,1 });
		
		while (!Display.windowShouldClose()) {
			Display.PollEvents();
			
			Display.ClearScreen();
			
			renderer.RenderMesh(instance, mesh);
			
			Display.SwapBuffers();
		}
		
		loader.Terminate();
		Display.Terminate();
	}

}
