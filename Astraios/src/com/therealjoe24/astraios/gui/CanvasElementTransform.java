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
package com.therealjoe24.astraios.gui;

import org.joml.Vector2f;

/**
 * The Transform of a canvas element
 * 
 * @author TheRealJoe24
 *
 */
public class CanvasElementTransform {

    private Vector2f _position;
    private float _width, _height;
    private CanvasElementTransform _parent;

    /**
     * Create an element transform
     * 
     * @param position
     */
    public CanvasElementTransform(Vector2f position, float width, float height) {
        _position = new Vector2f(position);
        _width = width;
        _height = height;
        _parent = null;
    }
    
    /**
     * 
     * @param parent
     */
    public void SetParent(CanvasElementTransform parent) {
        _parent = parent;
    }
    
    /**
     * Create an element transform
     * 
     * @param position
     * @param parent
     */
    public CanvasElementTransform(Vector2f position, float width, float height, CanvasElementTransform parent) {
        this(position, width, height);
        _parent = parent;
    }
    
    /**
     * 
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public CanvasElementTransform(float x, float y, float width, float height) {
        this(new Vector2f(x, y), width, height);
    }
    
    /**
     * 
     * 
     * @param x
     * @param y
     * @return
     */
    public boolean Contains(CanvasElementTransform other) {float m_left = getLeft();
        float m_right = getRight();
        float m_top = getTop();
        float m_bottom = getBottom();
        
        float o_left = other.getLeft();
        float o_right = other.getRight();
        float o_top = other.getTop();
        float o_bottom = other.getBottom();
        
        if (o_right > m_left && o_left < m_right
         && o_bottom > m_top && o_top < m_bottom) {
            return true;
        }
        return false;
    }
    
    /**
     * 
     * @param x
     * @param y
     * @return
     */
    public boolean Contains(float x, float y) {
        float m_left = getLeft();
        float m_right = getRight();
        float m_top = getTop();
        float m_bottom = getBottom();
        
        if (x > m_left && x < m_right
         && y > m_top && y < m_bottom) {
            return true;
        }
        return false;
    }
    
    public float getLeft() {
        return getPosition().x;
    }
    
    public float getTop() {
        return getPosition().y;
    }
    
    public float getRight() {
        return getPosition().x + _width;
    }
    
    public float getBottom() {
        return getPosition().y + _height;
    }
    
    public float getWidth() {
        return _width;
    }
    
    public float getHeight() {
        return _height;
    }
    
    /**
     * Hacky way to get the viewport width
     * 
     * @return
     */
    public float traverseViewportWidth() {
        if (_parent != null) {
            return _parent.traverseViewportWidth();
        }
        return _width;
    }
    
    /**
     * Hacky way to get the viewport height
     * 
     * @return
     */
    public float traverseViewportHeight() {
        if (_parent != null) {
            return _parent.traverseViewportHeight();
        }
        return _height;
    }

    /**
     * get the position in pixel space
     * 
     * @return position
     */
    public Vector2f getPosition() {
        float x = _position.x;
        float y = _position.y;
        if (_parent != null) {
            Vector2f offset = _parent.getPosition();
            x *= _parent.getWidth();
            y *= _parent.getHeight();
            x += offset.x;
            y += offset.y;
            return new Vector2f(x, y);
        }
        return new Vector2f(x*traverseViewportWidth(), y*traverseViewportHeight());
    }
    
    public void SetSize(float width, float height) {
        _width = width;
        _height = height;
    }
    
}
