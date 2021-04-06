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
package com.therealjoe24.astraios.renderer.camera;

import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import com.therealjoe24.astraios.Display;

/**
 * Perspective Camera main class
 * 
 * TODO: Refactor Code TODO: Update aspect ratio on display resize
 * 
 * @author TheRealJoe24
 */
public class PerspectiveCamera {

    private Matrix4f _viewMatrix, _projectionMatrix;

    private Vector3f _eye, _center, _up;
    private Vector3f _forward;

    private float _zNear, _zFar, _fovy;

    /**
     * Creates a perspective camera
     * 
     * @param fovy   y fov in radians
     * @param zNear  zNear plane
     * @param zFar   zFar plane
     * @param eye    eye position
     * @param center eye center
     * @param up     eye up
     */
    public PerspectiveCamera(float fovy, float zNear, float zFar, Vector3f eye, Vector3f center, Vector3f up) {
        _projectionMatrix = new Matrix4f();
        _viewMatrix = new Matrix4f();
        _projectionMatrix.perspective(fovy, Display.getAspect(), zNear, zFar);
        _viewMatrix.lookAt(eye, center, up);

        _eye = new Vector3f(eye);
        _center = new Vector3f(center);
        _up = new Vector3f(up);

        _zNear = zNear;
        _zFar = zFar;
        _fovy = fovy;

        _forward = new Vector3f(center);
        _forward.sub(eye);
        _forward.normalize();
    }

    /**
     * Translate the view
     * 
     * @param d
     */
    public void Translate(Vector3f d) {
        _eye.add(d);
    }

    /**
     * Translate the view
     * 
     * @param x
     * @param y
     * @param z
     */
    public void Translate(float x, float y, float z) {
        Translate(new Vector3f(x, y, z));
    }

    /**
     * Translate the view
     * 
     * @param x
     * @param y
     * @param z
     */
    public void TranslateX(float x) {
        Translate(new Vector3f(x, 0, 0));
    }

    /**
     * Translate the view
     * 
     * @param x
     * @param y
     * @param z
     */
    public void TranslateY(float y) {
        Translate(new Vector3f(0, y, 0));
    }

    /**
     * Translate the view
     * 
     * @param x
     * @param y
     * @param z
     */
    public void TranslateZ(float z) {
        Translate(new Vector3f(0, 0, z));
    }

    /**
     * Sets the forward vector
     * 
     */
    public void setForward(Vector3f forward) {
        _forward = new Vector3f(forward);
    }

    /**
     * Gets the projection matrix
     * 
     * TODO: Change aspect ratio only when needed
     * 
     * @param fb buffer to load the matrix to
     */
    public void getProjection(FloatBuffer fb) {
        _projectionMatrix.identity();
        _projectionMatrix.perspective(_fovy, Display.getAspect(), _zNear, _zFar);
        if (fb == null) {
            fb = BufferUtils.createFloatBuffer(16);
        }
        _projectionMatrix.get(fb);
    }

    /**
     * Gets the view matrix
     * 
     * @param fb buffer to load the matrix to
     */
    public void getView(FloatBuffer fb) {
        _viewMatrix.identity();
        _center = new Vector3f(_forward);
        _center.add(_eye);
        _viewMatrix.lookAt(_eye, _center, _up);
        if (fb == null) {
            fb = BufferUtils.createFloatBuffer(16);
        }
        _viewMatrix.get(fb);
    }

    /**
     * Gets the forward vector
     * 
     * @return forward vector
     */
    public Vector3f getForward() {
        return new Vector3f(_forward);
    }

    /**
     * Gets the right vector
     * 
     * @return right vector
     */
    public Vector3f getRight() {
        Vector3f right = new Vector3f(_up);
        right.cross(_forward);
        return right;
    }

}
