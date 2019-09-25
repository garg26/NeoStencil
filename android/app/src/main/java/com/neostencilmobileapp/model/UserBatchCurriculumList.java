package com.neostencilmobileapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.LinkedHashMap;
import java.util.List;

public class UserBatchCurriculumList {

    @SerializedName("response")
    @Expose
    LinkedHashMap<String,List<UserBatchUnitModel>> response;

    public LinkedHashMap<String, List<UserBatchUnitModel>> getResponse() {
        return response;
    }

    public void setResponse(LinkedHashMap<String, List<UserBatchUnitModel>> response) {
        this.response = response;
    }
}
