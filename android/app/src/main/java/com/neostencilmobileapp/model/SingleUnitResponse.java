package com.neostencilmobileapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SingleUnitResponse implements Serializable{


    @SerializedName("unitType")
    @Expose
    private String unitType;
    @SerializedName("unitOnMobile")
    @Expose
    private UnitOnMobile unitOnMobile;

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public UnitOnMobile getUnitOnMobile() {
        return unitOnMobile;
    }

    public void setUnitOnMobile(UnitOnMobile unitOnMobile) {
        this.unitOnMobile = unitOnMobile;
    }
}
