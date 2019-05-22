package graphEditor.models;

import java.util.Observable;

abstract class Selectable extends Observable {

    private boolean selected = false;

    public boolean isSelected(){
        return selected;
    }

    void setSelected(boolean selected) {
        this.selected = selected;
    }
}
