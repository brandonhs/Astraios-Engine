package com.therealjoe24.astraios.gui.elements;

import com.therealjoe24.astraios.gui.CanvasElement;
import com.therealjoe24.astraios.gui.CanvasElementEvent;

public class ButtonElement extends CanvasElement {

    public enum ButtonState {
        BUTTON_PRESSED, BUTTON_RELEASED
    }
    
    private ButtonState _state;
    
    public ButtonElement(float nx, float ny, float nw, float nh) {
        super(nx, ny, nw, nh);
        AddChild(new ColorRectElement(nx, ny, nw, nh));
        _state = ButtonState.BUTTON_RELEASED;
    }

    @Override
    public void SendEvent(CanvasElementEvent evt) {
        if (evt == CanvasElementEvent.ELEMENT_MOUSE_DOWN) {
            _state = ButtonState.BUTTON_PRESSED;
        }
    }

    @Override
    public void InitFromContext(long vg) {
        
    }

    @Override
    public void RenderToCanvas(int frameWidth, int frameHeight, long vg) {
        RenderChildren(frameWidth, frameHeight, vg);
    }

}
