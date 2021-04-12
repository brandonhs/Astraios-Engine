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
package com.therealjoe24.test;

import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;

import java.util.Arrays;
import org.joml.Math;
import org.joml.Vector3f;

import com.therealjoe24.astraios.Display;
import com.therealjoe24.astraios.Input;
import com.therealjoe24.astraios.gui.Canvas;
import com.therealjoe24.astraios.gui.elements.ButtonElement;
import com.therealjoe24.astraios.gui.elements.ColorRectElement;
import com.therealjoe24.astraios.gui.elements.TextElement;
import com.therealjoe24.astraios.gui.elements.ButtonElement.ButtonState;
import com.therealjoe24.astraios.renderer.BufferLoader;
import com.therealjoe24.astraios.renderer.MeshData;
import com.therealjoe24.astraios.renderer.PrimitiveMesh;
import com.therealjoe24.astraios.renderer.Renderer;
import com.therealjoe24.astraios.renderer.ShaderInstance;
import com.therealjoe24.astraios.renderer.ShaderObject;
import com.therealjoe24.astraios.renderer.ShaderProgram;
import com.therealjoe24.astraios.renderer.camera.PerspectiveCamera;
import com.therealjoe24.astraios.renderer.objects.Model;
import com.therealjoe24.astraios.renderer.texture.Texture;

public class Main {

    public static void main(String[] args) {
        /* Initialize the display and input manager */
        Display.Create(512, 512, "Astraios 3D Demo", true);
        Input.Init();

        /* Load the shaders */
        String vsSource = ShaderObject.LoadSource("shader.vert");
        String fsSource = ShaderObject.LoadSource("shader.frag");
        ShaderObject vs = new ShaderObject(GL_VERTEX_SHADER, vsSource);
        ShaderObject fs = new ShaderObject(GL_FRAGMENT_SHADER, fsSource);
        ShaderObject[] shaders = { vs, fs };
        ShaderProgram program = new ShaderProgram(Arrays.asList(shaders));

        BufferLoader loader = new BufferLoader();
        Renderer renderer = new Renderer();

        PerspectiveCamera camera = new PerspectiveCamera((float) Math.toRadians(45f), 0.0001f, 100000f,
                new Vector3f(0, 0, 5), new Vector3f(0, 0, 0), new Vector3f(0, 1, 0));

        Texture texture = Texture.LoadTexture("res/wall.png");

        Canvas canvas = new Canvas();
        canvas.AddElement(new TextElement("Astraios 3D Demo", 0.5f, 0.05f, 0, 1, 0, 1, 48));
        canvas.AddElement(new TextElement("Made by TheRealJoe24", 0.8f, 0.05f, 0.1f, 0.1f, 0.8f, 1, 48));
        TextElement el = new TextElement("fps: 0", 0.2f, 0.05f, 0.8f, 0.1f, 0.3f, 1, 48);
        canvas.AddElement(el);
        
        ButtonElement button = new ButtonElement(0.2f, 0.2f, 0.1f, 0.1f, "Click Me!");
        button.AddButtonEventHandler(new ButtonElement.ButtonEventHandler() {
            @Override
            public void invoke(ButtonState state) {
                if (state == ButtonState.BUTTON_PRESSED) {
                    System.out.println("Pressed!");
                }
            }
        });
        canvas.AddElement(button);

        ShaderInstance instance = new ShaderInstance(program);
        PrimitiveMesh mesh = new PrimitiveMesh(loader, new MeshData(texture, 1));
        Model model = new Model(mesh);
        model.SetInstance(instance);

        instance.SetAuxUniform("uTexture", texture);
        instance.SetCamera(camera);

        double time, newTime, dt;

        time = System.currentTimeMillis();

        Display.setClearColor(0, 0, 0);
        Display.ShowWindow();

        while (!Display.windowShouldClose()) {
            Display.ClearScreen();

            model.RotateY(0.003f);
            model.RotateX(0.02f);
            renderer.RenderModel(model);

            newTime = System.currentTimeMillis();
            dt = newTime - time;
            int fps = (int) Math.round(1 / (dt / 1000));
            time = newTime;
            el.SetText(String.format("fps: %d", fps));

            canvas.Render();

            Display.SwapBuffers();

            Input.Update();
        }
        texture.Dispose();
        loader.Terminate();
        Display.Terminate();
    }

}
