package com.neostencilmobileapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class LectureModel {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("releaseDate")
    @Expose
    private String releaseDate;
    @SerializedName("duration")
    @Expose
    private Object duration;
    @SerializedName("heading")
    @Expose
    private String heading;
    @SerializedName("teacherName")
    @Expose
    private String teacherName;
    @SerializedName("footerNotes")
    @Expose
    private Object footerNotes;
    @SerializedName("noOfViews")
    @Expose
    private Integer noOfViews;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Object getDuration() {
        return duration;
    }

    public void setDuration(Object duration) {
        this.duration = duration;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public Object getFooterNotes() {
        return footerNotes;
    }

    public void setFooterNotes(Object footerNotes) {
        this.footerNotes = footerNotes;
    }

    public Integer getNoOfViews() {
        return noOfViews;
    }

    public void setNoOfViews(Integer noOfViews) {
        this.noOfViews = noOfViews;
    }
}
