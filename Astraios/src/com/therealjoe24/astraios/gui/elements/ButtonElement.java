package com.therealjoe24.astraios.gui.elements;

import org.joml.Vector2f;

import com.therealjoe24.astraios.gui.CanvasElement;
import com.therealjoe24.astraios.gui.CanvasElementEvent;

public class ButtonElement extends CanvasElement {

    public enum ButtonState {
        BUTTON_PRESSED, BUTTON_RELEASED
    }
    
    private ButtonState _state;
    
    private ColorRectElement _childRect;
    
    public ButtonElement(float nx, float ny, float nw, float nh) {
        super(nx, ny, nw, nh);
        _childRect = new ColorRectElement(nx, ny, nw, nh);
        AddChild(_childRect);
        _state = ButtonState.BUTTON_RELEASED;
    }

    @Override
    public void InitFromContext(long vg) {
        
    }

    @Override
    public void RenderToCanvas(int frameWidth, int frameHeight, long vg) {
        RenderChildren(frameWidth, frameHeight, vg);
    }

    @Override
    protected void ReceiveEvent(CanvasElementEvent evt, double mouseX, double mouseY, int frameWidth, int frameHeight) {
        boolean contains = Contains((float)mouseX, (float)mouseY);
        if (evt == CanvasElementEvent.ELEMENT_MOUSE_DOWN && contains) {
            _state = ButtonState.BUTTON_PRESSED;
            _childRect.SetColor(0,1,0,1);
        } else if (evt == CanvasElementEvent.ELEMENT_MOUSE_UP) {
            _state = ButtonState.BUTTON_PRESSED;
            _childRect.SetColor(1,0,0,1);
        }
    }

}
