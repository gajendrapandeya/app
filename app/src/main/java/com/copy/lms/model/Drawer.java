package com.copy.lms.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class Drawer extends BaseObservable {

    private String name, id;
    private int type, drawableID, drawerIdSelected, intViewType;
    private boolean isClickable, isSelected;


    public Drawer(String id, String name, int type, int drawableID, int drawerIdSelected, int intViewType, boolean isClickable, boolean isSelected) {
        this.name = name;
        this.id = id;
        this.type = type;
        this.drawableID = drawableID;
        this.drawerIdSelected = drawerIdSelected;
        this.intViewType = intViewType;
        this.isClickable = isClickable;
        this.isSelected = isSelected;
    }

    public int getDrawerIdSelected() {
        return drawerIdSelected;
    }

    public void setDrawerIdSelected(int drawerIdSelected) {
        this.drawerIdSelected = drawerIdSelected;
    }

    public int getIntViewType() {
        return intViewType;
    }

    public void setIntViewType(int intViewType) {
        this.intViewType = intViewType;
    }

    public boolean isClickable() {
        return isClickable;
    }

    public void setClickable(boolean clickable) {
        isClickable = clickable;
    }

    @Bindable
    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
        notifyChange();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getDrawableID() {
        return drawableID;
    }

    public void setDrawableID(int drawableID) {
        this.drawableID = drawableID;
    }

}
