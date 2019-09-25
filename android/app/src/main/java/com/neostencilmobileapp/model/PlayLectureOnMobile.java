package com.neostencilmobileapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlayLectureOnMobile {

    @SerializedName("manifestFile")
    @Expose
    private String manifestFile;
    @SerializedName("playlistFile")
    @Expose
    private String playlistFile;
    @SerializedName("widevine")
    @Expose
    private DRMResponseForMobile widevine;
    @SerializedName("playreadyDRM")
    @Expose
    private DRMResponseForMobile playreadyDRM;
    @SerializedName("icon")
    @Expose
    private String icon;
    @SerializedName("unit")
    @Expose
    private UnitModel unit;


    public String getManifestFile() {
        return manifestFile;
    }

    public void setManifestFile(String manifestFile) {
        this.manifestFile = manifestFile;
    }

    public String getPlaylistFile() {
        return playlistFile;
    }

    public void setPlaylistFile(String playlistFile) {
        this.playlistFile = playlistFile;
    }

    public DRMResponseForMobile getWidevine() {
        return widevine;
    }

    public void setWidevine(DRMResponseForMobile widevine) {
        this.widevine = widevine;
    }

    public DRMResponseForMobile getPlayreadyDRM() {
        return playreadyDRM;
    }

    public void setPlayreadyDRM(DRMResponseForMobile playreadyDRM) {
        this.playreadyDRM = playreadyDRM;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public UnitModel getUnit() {
        return unit;
    }

    public void setUnit(UnitModel unit) {
        this.unit = unit;
    }
}
