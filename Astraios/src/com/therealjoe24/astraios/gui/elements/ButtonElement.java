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
package com.therealjoe24.astraios.gui.elements;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;

import com.therealjoe24.astraios.gui.CanvasElement;
import com.therealjoe24.astraios.gui.CanvasElementEvent;

/**
 * Button element
 * 
 * @author TheRealJoe24
 *
 */
public class ButtonElement extends CanvasElement {

    public enum ButtonState {
        BUTTON_PRESSED, BUTTON_RELEASED
    }
    
    /**
     * Current state of the button
     */
    private ButtonState _state;
    
    /**
     * Reference to child rect
     */
    private ColorRectElement _childRect;
    /**
     * Reference to text element contained by child rect
     */
    private TextElement _childText;
    
    /**
     * Handles button events
     * 
     * @author TheRealJoe24
     *
     */
    public static abstract class ButtonEventHandler {
        public abstract void invoke(ButtonState state);
    }
    
    /**
     * List of button event handlers to be invoked on an event
     */
    private List<ButtonEventHandler> _buttonEventHandlers = new ArrayList<ButtonEventHandler>();
    
    /**
     * Adds a button event handler
     * 
     * @param callback
     */
    public void AddButtonEventHandler(ButtonEventHandler callback) {
        _buttonEventHandlers.add(callback);
    }
    
    /**
     * Creates a button element
     * 
     * @param nx
     * @param ny
     * @param nw
     * @param nh
     */
    public ButtonElement(float nx, float ny, float nw, float nh) {
        super(nx, ny, nw, nh);
        _childRect = new ColorRectElement(0, 0, nw, nh);
        AddChild(_childRect);
        _state = ButtonState.BUTTON_RELEASED;
    }
    
    /**
     * Creates a button element with text
     * 
     * @param nx
     * @param ny
     * @param nw
     * @param nh
     * @param text
     */
    public ButtonElement(float nx, float ny, float nw, float nh, String text) {
        this(nx, ny, nw, nh);
        _childText = new TextElement(text, 0.5f, 0.5f, 1, 1, 1, 1, 48);
        _childRect.AddChild(_childText);
    }

    @Override
    public void InitFromContext(long vg) { }

    @Override
    public void RenderToCanvas(long vg) { }

    @Override
    protected void ReceiveEvent(CanvasElementEvent evt, double mouseX, double mouseY) {
        boolean contains = _transform.Contains((float)mouseX, (float)mouseY);
        _state = ButtonState.BUTTON_RELEASED;
        if (evt == CanvasElementEvent.ELEMENT_MOUSE_DOWN && contains) {
            _state = ButtonState.BUTTON_PRESSED;
            _childRect.SetColor(0,1,0,1);
        } else if (evt == CanvasElementEvent.ELEMENT_MOUSE_UP) {
            _state = ButtonState.BUTTON_RELEASED;
            _childRect.SetColor(1,0,0,1);
        }
        
        for (ButtonEventHandler handler : _buttonEventHandlers) {
            handler.invoke(_state);
        }
    }

}
