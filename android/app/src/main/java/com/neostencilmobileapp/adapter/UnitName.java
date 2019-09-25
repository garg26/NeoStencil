package com.neostencilmobileapp.adapter;

import android.os.Parcel;
import android.os.Parcelable;

public class UnitName implements Parcelable {

    private String name;
    private String unitTopic;
    private int unitId;
    private int colorResId;

    public UnitName(String name,String unitTopic,int unitId,int colorResId) {
        this.name = name;
        this.unitTopic = unitTopic;
        this.unitId = unitId;
        this.colorResId = colorResId;
    }

    protected UnitName(Parcel in) {
        name = in.readString();
        unitTopic = in.readString();
        unitId = in.readInt();
        colorResId = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(unitTopic);
        dest.writeInt(unitId);
        dest.writeInt(colorResId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UnitName> CREATOR = new Creator<UnitName>() {
        @Override
        public UnitName createFromParcel(Parcel in) {
            return new UnitName(in);
        }

        @Override
        public UnitName[] newArray(int size) {
            return new UnitName[size];
        }
    };

    public String getUnitTopic() {
        return unitTopic;
    }


    public String getName() {
        return name;
    }

    public int getUnitId() {
        return unitId;
    }

    public int getColorResId() {
        return colorResId;
    }

    public void setColorResId(int colorResId) {
        this.colorResId = colorResId;
    }
}
