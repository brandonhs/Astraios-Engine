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
    private CanvasElementTransform _parent;
    
    private Vector2f _offset;

    /**
     * Create an element transform
     * 
     * @param position
     */
    public CanvasElementTransform(Vector2f position) {
        _position = new Vector2f(position);
        _offset = new Vector2f(0);
        _parent = null;
    }
    
    public void SetParent(CanvasElementTransform parent) {
        _parent = parent;
    }
    
    /**
     * Create an element transform
     * 
     * @param position
     * @param parent
     */
    public CanvasElementTransform(Vector2f position, CanvasElementTransform parent) {
        this(position);
        _parent = parent;
    }

    /**
     * get the position
     * 
     * @return position
     */
    public Vector2f getPosition() {
        float x = _position.x;
        float y = _position.y;
        if (_parent != null) {
            Vector2f offset = _parent.getPosition();
//            System.out.println(offset.x);
            x += offset.x;
            y += offset.y;
        }
        return new Vector2f(x, y);
    }

    /**
     * Set the transform offset
     * 
     * @param offset
     */
    public void SetOffset(Vector2f offset) {
        _offset = new Vector2f(offset);
    }

}
