package com.neostencilmobileapp.adapter;

import android.graphics.drawable.Drawable;

import com.neostencilmobileapp.adapter.UnitName;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class UnitTopic extends ExpandableGroup<UnitName> {

    private int drawableId;

    public UnitTopic(String title, List<UnitName> items, int drawableId) {
        super(title, items);
        this.drawableId = drawableId;
    }

    @Override
    public List<UnitName> getItems() {
        return super.getItems();
    }

    public int getDrawableId() {
        return drawableId;
    }

    public void setDrawableId(int drawableId) {
        this.drawableId = drawableId;
    }
}
