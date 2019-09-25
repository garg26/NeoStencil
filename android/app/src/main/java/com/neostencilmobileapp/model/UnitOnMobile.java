package com.neostencilmobileapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UnitOnMobile {

    @SerializedName("note")
    @Expose
    private MobileNote note;

    @SerializedName("lectureOnMobile")
    @Expose
    PlayLectureOnMobile lectureOnMobile;

    @SerializedName("unit")
    @Expose
    UnitModel unit;

    public MobileNote getNote() {
        return note;
    }

    public void setNote(MobileNote note) {
        this.note = note;
    }

    public PlayLectureOnMobile getLectureOnMobile() {
        return lectureOnMobile;
    }

    public void setLectureOnMobile(PlayLectureOnMobile lectureOnMobile) {
        this.lectureOnMobile = lectureOnMobile;
    }

    public UnitModel getUnit() {
        return unit;
    }

    public void setUnit(UnitModel unit) {
        this.unit = unit;
    }
}
