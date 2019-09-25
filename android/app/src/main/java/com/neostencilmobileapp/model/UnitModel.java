package com.neostencilmobileapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UnitModel implements Serializable{

    @SerializedName("unitId")
    @Expose
    private Integer unitId;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("typeId")
    @Expose
    private Integer typeId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("product")
    @Expose
    private Boolean product;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("topic")
    @Expose
    private String topic;
    @SerializedName("position")
    @Expose
    private Integer position;
    @SerializedName("free")
    @Expose
    private Boolean free;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("noOfViews")
    @Expose
    private Integer noOfViews;


    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Boolean getProduct() {
        return product;
    }

    public void setProduct(Boolean product) {
        this.product = product;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Boolean getFree() {
        return free;
    }

    public void setFree(Boolean free) {
        this.free = free;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getNoOfViews() {
        return noOfViews;
    }

    public void setNoOfViews(Integer noOfViews) {
        this.noOfViews = noOfViews;
    }
}
