package com.neostencilmobileapp.model;

public class UserAssignmentSubmitRequest {

    int unitId;
    String name;
    String url;
    String type;

    public UserAssignmentSubmitRequest(int unitId, String name, String url, String type) {
        this.unitId = unitId;
        this.name = name;
        this.url = url;
        this.type = type;
    }

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
