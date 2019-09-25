package com.neostencilmobileapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserBatchUnitModel {

    @SerializedName("linkageId")
    @Expose
    private Integer linkageId;
    @SerializedName("active")
    @Expose
    private Boolean active;
    @SerializedName("watchStatus")
    @Expose
    private String watchStatus;
    @SerializedName("noOfClicks")
    @Expose
    private Integer noOfClicks;
    @SerializedName("resumeFrom")
    @Expose
    private Integer resumeFrom;
    @SerializedName("unit")
    @Expose
    private UnitModel unit;
    @SerializedName("assignmentSubmission")
    @Expose
    private Object assignmentSubmission;


    public Integer getLinkageId() {
        return linkageId;
    }

    public void setLinkageId(Integer linkageId) {
        this.linkageId = linkageId;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getWatchStatus() {
        return watchStatus;
    }

    public void setWatchStatus(String watchStatus) {
        this.watchStatus = watchStatus;
    }

    public Integer getNoOfClicks() {
        return noOfClicks;
    }

    public void setNoOfClicks(Integer noOfClicks) {
        this.noOfClicks = noOfClicks;
    }

    public Integer getResumeFrom() {
        return resumeFrom;
    }

    public void setResumeFrom(Integer resumeFrom) {
        this.resumeFrom = resumeFrom;
    }


    public UnitModel getUnit() {
        return unit;
    }

    public void setUnit(UnitModel unit) {
        this.unit = unit;
    }

    public Object getAssignmentSubmission() {
        return assignmentSubmission;
    }

    public void setAssignmentSubmission(Object assignmentSubmission) {
        this.assignmentSubmission = assignmentSubmission;
    }
}
