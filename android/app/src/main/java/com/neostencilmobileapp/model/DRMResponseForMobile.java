package com.neostencilmobileapp.model;

import java.io.Serializable;

public class DRMResponseForMobile implements Serializable{

    String url;
    String customData;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCustomData() {
        return customData;
    }

    public void setCustomData(String customData) {
        this.customData = customData;
    }
}
