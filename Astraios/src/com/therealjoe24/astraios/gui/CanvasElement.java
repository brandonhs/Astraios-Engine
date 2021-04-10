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

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;

import com.therealjoe24.astraios.gui.elements.ButtonElement;

/**
 * Base class for canvas elements
 * 
 * @author TheRealJoe24
 *
 */
public abstract class CanvasElement {

    /**
     * canvas element transform
     */
    protected CanvasElementTransform _transform;
    
    protected CanvasElement _parent;
    
    /**
     * Canvas element child elements
     */
    protected List<CanvasElement> _children = new ArrayList<>();
    
    CanvasElementAlign _align = CanvasElementAlign.ALIGN_CENTER;
    
    protected float _width, _height;

    /**
     * Create base canvas element
     * 
     * @param nx
     * @param ny
     */
    public CanvasElement(float nx, float ny, float nw, float nh) {
        _transform = new CanvasElementTransform(new Vector2f(nx, ny));
        _width = nw;
        _height = nh;
    }
    
    /**
     * Add child to element
     * 
     * @param child
     */
    public void AddChild(CanvasElement child) {
        child.SetParent(this);
        _children.add(child);
    }
    
    /**
     * 
     * @param parent
     */
    public void SetParent(CanvasElement parent) {
        _parent = parent;
        _transform.SetParent(parent._transform);
    }
    
    /**
     * Ignore the event
     * 
     * @param evt
     */
    protected void SupressEvent(CanvasElementEvent evt) {
        
    }
    
    /**
     * TODO: CONVERT FROM ENUM TO CLASS
     * This will allow for multiple child classes to detect if the event "contains" an element
     * If it doesn't, supress it
     * @param evt
     */
    public void SendEvent(CanvasElementEvent evt, double mouseX, double mouseY, int frameWidth, int frameHeight) {
        // if (!Contains((float)mouseX/(float)frameWidth, (float)mouseY/(float)frameHeight)) return;
        ReceiveEvent(evt, mouseX/frameWidth, mouseY/frameHeight, frameWidth, frameHeight);
        for (CanvasElement el : _children) {
            el.SendEvent(evt, mouseX, mouseY, frameWidth, frameHeight);
        }
    }
    
    /**
     * Send an event to the element
     * 
     * @param evt
     */
    protected abstract void ReceiveEvent(CanvasElementEvent evt, double mouseX, double mouseY, int frameWidth, int frameHeight);

    /**
     * Initialize the element from render context
     * 
     * @param vg
     */
    public void InitFromContext(long vg) {
    }
    
    public void RenderChildren(int frameWidth, int frameHeight, long vg) {
        for (CanvasElement el : _children) {
            el.Render(frameWidth, frameHeight, vg);
        }
    }
    
    /**
     * Set the element alignment
     * 
     * @param align
     */
    public void SetAlign(CanvasElementAlign align) {
        _align = align;
        
    }
    
    public boolean Contains(float x, float y, int frameWidth, int frameHeight) {
        Vector2f m_vec = _transform.getPosition();
        if (x > m_vec.x && x < m_vec.x + (_width / frameWidth)
                && y > m_vec.y && y < m_vec.y + (_height / frameHeight)) {
            return true;
        }
        return false;
    }
    
    /**
     * 
     * @param frameWidth
     * @param frameHeight
     * @param vg
     */
    public void Render(int frameWidth, int frameHeight, long vg) {
        RenderToCanvas(frameWidth, frameHeight, vg);
    }

    /**
     * Render the element to the render context
     * 
     * @param frameWidth
     * @param frameHeight
     * @param vg
     */
    public void RenderToCanvas(int frameWidth, int frameHeight, long vg) { 
    }

}
